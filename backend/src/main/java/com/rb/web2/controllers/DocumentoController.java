package com.rb.web2.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rb.web2.domain.documento.Documento;
import com.rb.web2.domain.documento.dto.CreateDocumentoDTO;
import com.rb.web2.domain.documento.dto.DocumentoResponseDTO;
import com.rb.web2.domain.documento.mapper.DocumentoMapper;
import com.rb.web2.services.DocumentoService;
import com.rb.web2.shared.RestMessage.RestSuccessMessage;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/documentos")
public class DocumentoController {

    private final DocumentoService service;

    public DocumentoController(DocumentoService service) {
        this.service = service;
    }

    @PostMapping("/")
    public ResponseEntity<?> createDocumento(
            @RequestParam("file") MultipartFile file,
            @RequestParam("nome") String nome,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "processoId", required = false) String processoId,
            @RequestParam("observacao") String observacao) throws IOException {

        var dto = new CreateDocumentoDTO(nome, observacao, userId, processoId);
        Documento doc = this.service.create(dto, file);

        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(doc.getId())
                .toUri();

        DocumentoResponseDTO response = DocumentoMapper.toDocumentoResponseDTO(doc);
        return ResponseEntity.created(location).body(new RestSuccessMessage("Upload realizado com sucesso.", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDocumentoById(@PathVariable Long id) {
        Documento documento = service.buscarDocumentoPorId(id);
        return ResponseEntity.ok().body(documento);

    }

    @GetMapping
    public ResponseEntity<List<Documento>> getAllDocumentos() {
        List<Documento> Documentos = service.getAllDocumentos();
        return ResponseEntity.ok(Documentos);
    }

    @GetMapping("/usuarios")
    public ResponseEntity<?> getAllDocumentosUsuarios() {
        var documento = service.getAllDocumentosUsuarios();
        return ResponseEntity.ok().body(documento);
    }

    @GetMapping("/processos")
    public ResponseEntity<?> getAllDocumentosProcessosSeletivos() {
        var documento = service.getAllDocumentosProcessosSeletivos();

        return ResponseEntity.ok().body(documento);
    }

    @GetMapping("/download/{id}/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename, @PathVariable String id,
            HttpServletRequest request) throws IOException {

        Resource resource = this.service.downloadFile(filename, id, "documentos", String.valueOf(id));
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
