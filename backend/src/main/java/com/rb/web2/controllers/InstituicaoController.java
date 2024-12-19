package com.rb.web2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rb.web2.domain.instituicao.Instituicao;
import com.rb.web2.domain.instituicao.dto.InstituicaoResponseDTO;
import com.rb.web2.services.InstituicaoService;
import com.rb.web2.shared.RestMessage.RestSuccessMessage;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/instituicoes")
public class InstituicaoController {

    // @TODO // Classe inteira. Por enquanto, apenas o esqueleto das funções foi
    // feito.

    @Autowired
    private InstituicaoService instituicaoService;

    // Criar nova instituição
    @PostMapping
    public ResponseEntity<RestSuccessMessage> createInstituicao(@Valid @RequestBody InstituicaoResponseDTO instituicao) {
        InstituicaoResponseDTO createdInstituicao = instituicaoService.create(instituicao);
        RestSuccessMessage successMessage = new RestSuccessMessage("Instituição criada com sucesso",
                createdInstituicao);
        return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
    }

    // Buscar instituição pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<RestSuccessMessage> getInstituicaoById(@PathVariable String id) {
        InstituicaoResponseDTO instituicao = instituicaoService.getById(id);
        RestSuccessMessage successMessage = new RestSuccessMessage("Instituição encontrada com sucesso", instituicao);
        return ResponseEntity.ok(successMessage);
    }

    // Listar todas as instituições
    @GetMapping
    public ResponseEntity<RestSuccessMessage> getAllInstituicaos() {
        List<InstituicaoResponseDTO> instituicoes = instituicaoService.getAllInstituicaos();
        RestSuccessMessage successMessage = new RestSuccessMessage("Instituições encontradas com sucesso",
                instituicoes);
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    // Atualizar instituição
    @PutMapping("/{id}")
    public ResponseEntity<RestSuccessMessage> updateInstituicao(@PathVariable String id,
            @Valid @RequestBody Instituicao updatedInstituicao) {
        InstituicaoResponseDTO instituicao = instituicaoService.updateInstituicao(id, updatedInstituicao);
        RestSuccessMessage successMessage = new RestSuccessMessage("Instituição atualizada com sucesso", instituicao);
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    // Excluir instituição
    @DeleteMapping("/{id}")
    public ResponseEntity<RestSuccessMessage> deleteInstituicao(@PathVariable String id) {
        instituicaoService.softDelete(id);
        RestSuccessMessage successMessage = new RestSuccessMessage("Instituição excluída com sucesso");
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }
}
