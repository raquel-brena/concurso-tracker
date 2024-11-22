package com.rb.web2.controllers;

import com.rb.web2.domain.documento.Documento;
import com.rb.web2.infra.properties.FileStorageProperties;
import com.rb.web2.services.DocumentoService;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.http.HttpHeaders;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/documentos")
public class DocumentoController {

    private DocumentoService service;

    private final Path fileStorageLocation;

    public DocumentoController(FileStorageProperties fileStorageProperties, DocumentoService service) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
        .toAbsolutePath().normalize();

        this.service = service;
    }

    // Criar nova instituição
    @PostMapping
    public ResponseEntity createDocumento(@RequestBody Documento Documento) {
        var id = this.service.create(Documento);

        var location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(id)
        .toUri();

        return ResponseEntity.created(location).build();
    }

    // Buscar instituição pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getDocumentoById(@PathVariable Long id) {
        try { 
            var documento = service.getDocumentoById(id);
            if (documento.isEmpty()) { 
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(documento.get());
        } catch (Error e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Listar todas as instituições
    @GetMapping
    public ResponseEntity<List<Documento>> getAllDocumentos() {
        List<Documento> Documentos = service.getAllDocumentos();
        return ResponseEntity.ok(Documentos);
    }


    @PostMapping("path/{userId}")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable String userId) throws IllegalStateException, IOException {
       String fileName = StringUtils.cleanPath(file.getOriginalFilename());
       try {
          Path userDirectory = fileStorageLocation.resolve(String.valueOf(userId)).normalize();
        if (!Files.exists(userDirectory)) {
            Files.createDirectories(userDirectory);
        }
        Path targetLocation = userDirectory.resolve(fileName);
        file.transferTo(targetLocation);

        String fileDonwloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
        .path("api/documentos/download/")
        .path(fileName)
        .toUriString();

        return ResponseEntity.ok()
        .body(fileDonwloadUri);
       } catch (Error e) {
        return ResponseEntity.badRequest().body(e.getMessage());
       }
    }
    

    @GetMapping("/download/{userId}/{filename:.+}")
    public ResponseEntity downloadFile(@PathVariable String filename, @PathVariable String userId, HttpServletRequest request) throws IOException {

       Path userDirectory = fileStorageLocation.resolve(String.valueOf(userId)).normalize();
        if (!Files.exists(userDirectory)) {
            return ResponseEntity.badRequest().body("invalid path name");
        }
        Path filePath = userDirectory.resolve(filename).normalize();

        try {
            Resource resource = new UrlResource(filePath.toUri());
            String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

            if (contentType == null) { 
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename =\""+resource.getFilename() +"\"")
            .body(resource);
        } catch (Error e) {
            return ResponseEntity.badRequest().body("Invalid file path");
        }

    }
    

    // // Atualizar instituição
    // @PutMapping("/{id}")
    // public ResponseEntity<Documento> updateDocumento(@PathVariable String id, @RequestBody Documento updatedDocumento) {
    //     Documento Documento = service.updateDocumento(id, updatedDocumento);
    //     return Documento != null ? ResponseEntity.ok(Documento) : ResponseEntity.notFound().build();
    // }

    // // Excluir instituição
    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> deleteDocumento(@PathVariable String id) {
    //     return service.deleteDocumento(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    // }
}
