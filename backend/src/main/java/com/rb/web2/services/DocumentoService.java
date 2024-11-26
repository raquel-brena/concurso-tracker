package com.rb.web2.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.util.StringUtils;

import com.rb.web2.domain.documento.Documento;
import com.rb.web2.domain.documento.dto.CreateDocumentoDTO;
import com.rb.web2.domain.user.User;
import com.rb.web2.infra.properties.FileStorageProperties;
import com.rb.web2.repositories.DocumentoRepository;
import com.rb.web2.shared.exceptions.BadRequestException;
import com.rb.web2.shared.exceptions.NotFoundException;

@Service
public class DocumentoService {

  private final Path fileStorageLocation;

  @Autowired
  private UserService userService;

  @Autowired
  private DocumentoRepository repository;

  public DocumentoService(FileStorageProperties fileStorageProperties, DocumentoRepository repository) {
    this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
        .toAbsolutePath().normalize();

    this.repository = repository;
  }

  public Documento create(CreateDocumentoDTO dto, MultipartFile file) throws IOException {
    String uri = this.uploadFile(file, dto.userId());
    User user = this.userService.getUserById(dto.userId());

    Documento documento = new Documento();
    documento.setNome(dto.nome());
    documento.setTipo(dto.tipo());
    documento.setDownloadUrl(uri);
    documento.setUsuario(user);
    return repository.save(documento);
  }

  public Optional<Documento> getDocumentoById(Long id) {
    return repository.findById(id);
  }

  public List<Documento> getAllDocumentos() {
    return repository.findAll();
  }

  public String uploadFile(MultipartFile file, String userId) throws IOException {
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());

    Path userDirectory = fileStorageLocation.resolve(String.valueOf(userId)).normalize();
   
    if (!Files.exists(userDirectory)) {
      Files.createDirectories(userDirectory);
    }

    Path targetLocation = userDirectory.resolve(fileName);
    file.transferTo(targetLocation);

    String fileDonwloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
        .path("api/documentos/download/")
        .path(userId + "/")
        .path(fileName)
        .toUriString();

    return fileDonwloadUri;

  }

  public Resource downloadFile(String filename, String userId) throws MalformedURLException {
    Path userDirectory = fileStorageLocation.resolve(String.valueOf(userId)).normalize();
    if (!Files.exists(userDirectory)) {
      throw new NotFoundException("Diretório do usuário " + userId + " não encontrado.");
    }

    Path filePath = userDirectory.resolve(filename).normalize();

    if (!Files.exists(filePath)) {
      throw new NotFoundException(
          "Arquivo " + filename + " não encontrado no diretório do usuário " + userId + ".");
    }

    Resource resource = new UrlResource(filePath.toUri());

    if (!resource.exists() || !resource.isReadable()) {
      throw new BadRequestException("Erro ao tentar acessar o arquivo " + filename + ".");
    }

    return resource;

  }
}
