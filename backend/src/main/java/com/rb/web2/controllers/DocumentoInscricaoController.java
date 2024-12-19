package com.rb.web2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rb.web2.domain.documentoInscricao.dto.DocInscricaoRequestDTO;
import com.rb.web2.domain.documentoInscricao.dto.HomologarDocInscricaoRequestDTO;
import com.rb.web2.services.DocumentoInscricaoService;
import com.rb.web2.shared.RestMessage.RestSuccessMessage;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/docInscricao")
public class DocumentoInscricaoController {

    @Autowired
    private DocumentoInscricaoService service;
    
    @PostMapping
    public ResponseEntity<RestSuccessMessage> criarDocumentoInscricao(@Valid @RequestBody DocInscricaoRequestDTO dto) {
        var docInscricao = service.criarDocumentoInscricao(dto);
        return ResponseEntity.ok()
        .body(new RestSuccessMessage("Documento adicionado na inscrição com sucesso.", docInscricao));
    }

    @PostMapping("/homologar")
    public ResponseEntity<RestSuccessMessage> homologarDocumento (@Valid @RequestBody HomologarDocInscricaoRequestDTO dto) {
        var docInscricao = service.homologarDocumento(dto);
        return ResponseEntity.ok()
            .body(new RestSuccessMessage("Documento homologado com sucesso.", docInscricao));
    }
    
}