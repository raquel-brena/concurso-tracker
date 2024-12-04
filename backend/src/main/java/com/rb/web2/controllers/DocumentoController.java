package com.rb.web2.controllers;

import com.rb.web2.domain.documento.Documento;
import com.rb.web2.domain.documento.dto.CreateDocumentoDTO;
import com.rb.web2.services.DocumentoService;

import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.http.HttpHeaders;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestController
@RequestMapping("/api/documentos")
public class DocumentoController {

    private DocumentoService service;

    public DocumentoController(DocumentoService service) {
        this.service = service;
    }

    @PostMapping("/")
    public ResponseEntity createDocumento(
        @RequestParam("file") MultipartFile file, 
        @RequestParam("nome") String nome,
        @RequestParam("id") String userId,
        @RequestParam("tipo") String tipo) throws IOException {
            
        var dto = new CreateDocumentoDTO(nome, tipo, userId);
        var id = this.service.create(dto, file);

        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }

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

    @GetMapping
    public ResponseEntity<List<Documento>> getAllDocumentos() {
        List<Documento> Documentos = service.getAllDocumentos();
        return ResponseEntity.ok(Documentos);
    }

    @PostMapping("path/{id}")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable String id)
            throws IllegalStateException, IOException {
        var fileaDownloadURI = this.service.uploadFile(file, id);
        return ResponseEntity.ok(fileaDownloadURI);
    }

    @GetMapping("/download/{id}/{filename:.+}")
    public ResponseEntity downloadFile(@PathVariable String filename, @PathVariable String id,
            HttpServletRequest request) throws IOException {

   
        Resource resource = this.service.downloadFile(filename, id, "documentos",String.valueOf(id));
        String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename =\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
