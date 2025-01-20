package com.rb.web2.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<RestSuccessMessage> createDocumento(
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
        RestSuccessMessage successMessage = new RestSuccessMessage("Documento criado com sucesso", response);
        return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestSuccessMessage> getDocumentoById(@PathVariable Long id) {
        Documento documento = service.buscarDocumentoPorId(id);
        RestSuccessMessage successMessage = new RestSuccessMessage("Documento encontrado com sucesso", documento);
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<RestSuccessMessage> getAllDocumentos() {
        List<Documento> Documentos = service.getAllDocumentos();
        RestSuccessMessage successMessage = new RestSuccessMessage("Documentos encontrados com sucesso", Documentos);
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @GetMapping("/usuarios")
    public ResponseEntity<RestSuccessMessage> getAllDocumentosUsuarios() {
        var documento = service.getAllDocumentosUsuarios();
        RestSuccessMessage successMessage = new RestSuccessMessage("Documentos encontrados com sucesso", documento);
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @GetMapping("/processos")
    public ResponseEntity<RestSuccessMessage> getAllDocumentosProcessosSeletivos() {
        var documento = service.getAllDocumentosProcessosSeletivos();
        RestSuccessMessage successMessage = new RestSuccessMessage("Documentos encontrados com sucesso", documento);
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    // @TODO: Checar resposta do download
    @GetMapping("/download/{id}/{filename:.+}")
    public ResponseEntity<?> downloadFile(@PathVariable String filename, @PathVariable String id,
            HttpServletRequest request) throws IOException {

        Resource resource = this.service.downloadFile(filename, id, "documentos", String.valueOf(id));
        String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        RestSuccessMessage successMessage = new RestSuccessMessage(
                "Download realizado com sucesso. Arquivo: " + resource.getFilename(),
                Map.of(
                        "filename", resource.getFilename(),
                        "contentType", contentType));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
