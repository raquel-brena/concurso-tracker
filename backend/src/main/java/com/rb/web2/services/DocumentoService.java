package com.rb.web2.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rb.web2.domain.documento.Documento;
import com.rb.web2.domain.documento.dto.CreateDocumentoDTO;
import com.rb.web2.domain.documento.dto.DocumentoResponseDTO;
import com.rb.web2.domain.documento.mapper.DocumentoMapper;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
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

  @Autowired
  private ProcessoSeletivoService processoSeletivoService;

  public DocumentoService(FileStorageProperties fileStorageProperties) {
    this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
        .toAbsolutePath().normalize();
  }

  private void verificarPermissaoDeCriacaoOuAlteracao() {
    String login = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = (User) userService.loadUserByUsername(login);

    if (!user.hasPermissionToCreateCriterios()) {
      throw new AccessDeniedException("Usuário não tem permissão para criar ou alterar critérios.");
    }
  }

  public Documento create(CreateDocumentoDTO dto, MultipartFile file) throws IOException {
    verificarPermissaoDeCriacaoOuAlteracao();
    
    String id = null;
    Documento documento = new Documento();

    if (dto.userId() != null) {
      User user = this.userService.getUserById(dto.userId());
      id = dto.userId();
      documento.setUsuario(user);
    }

    if (dto.processoId() != null) {
      ProcessoSeletivo processo = this.processoSeletivoService.getProcessoSeletivoById(dto.processoId());
      id = dto.processoId();
      documento.setProcessoSeletivo(processo);
    }

    String uri = this.uploadFile(file, id);
    documento.setNome(dto.nome());
    documento.setDescricao(dto.descricao());
    documento.setDownloadUrl(uri);

    return repository.save(documento);
  }

  public Documento buscarDocumentoPorId(Long id) {
    return repository.findById(id).orElseThrow(() -> new NotFoundException("Documento não encontrado. ID: " + id));
  }

  public Optional<Documento> getDocumentoByUrl(String link) {
    return repository.findByDownloadUrl(link);
  }

  public List<Documento> getAllDocumentos() {
    return repository.findAll();
  }

  public List<DocumentoResponseDTO> getAllDocumentosUsuarios() {
    List<Documento> documentos = repository.findByUsuarioIsNotNull().orElseThrow(
        () -> new NotFoundException("Nenhum documento encontrado."));
    return documentos.stream().map(DocumentoMapper::toDocumentoResponseDTO).toList();
  }

  public List<DocumentoResponseDTO> getAllDocumentosProcessosSeletivos() {
    List<Documento> documentos = repository.findByProcessoSeletivoIsNotNull().orElseThrow(
        () -> new NotFoundException("Nenhum documento encontrado."));
    return documentos.stream().map(DocumentoMapper::toDocumentoResponseDTO).toList();
  }

  public String uploadFile(MultipartFile file, String id) throws IOException {
    String originalFilename = file.getOriginalFilename();
    if (originalFilename == null || originalFilename.isBlank()) {
      throw new IllegalArgumentException("O arquivo enviado não possui um nome válido.");
    }

    String fileName = StringUtils.cleanPath(originalFilename);

    Path processoDir = fileStorageLocation.resolve(String.valueOf(id)).normalize();

    if (!Files.exists(processoDir)) {
      Files.createDirectories(processoDir);
    }

    Path targetLocation = processoDir.resolve(fileName);
    file.transferTo(targetLocation);

    String fileDonwloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
        .path("api/documentos/download/")
        .path(id + "/")
        .path(fileName)
        .toUriString();

    return fileDonwloadUri;
  }

  public Resource downloadFile(String filename, String id, String directoryName1, String directoryName2)
      throws MalformedURLException {

    Path directory = fileStorageLocation.resolve(directoryName2).normalize();

    if (!Files.exists(directory)) {
      throw new NotFoundException("Diretório " + id + " não encontrado.");
    }

    Path filePath = directory.resolve(filename).normalize();

    if (!Files.exists(filePath)) {
      throw new NotFoundException(
          "Arquivo " + filename + " não encontrado no diretório de " + id + ".");
    }

    Resource resource = new UrlResource(filePath.toUri());

    if (!resource.exists() || !resource.isReadable()) {
      throw new BadRequestException("Erro ao tentar acessar o arquivo " + filename + ".");
    }

    return resource;
  }
}
