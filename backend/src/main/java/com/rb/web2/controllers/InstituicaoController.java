package com.rb.web2.controllers;

import java.util.List;
import java.util.Optional;

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
import com.rb.web2.services.InstituicaoService;

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
    public ResponseEntity<Instituicao> createInstituicao(@Valid @RequestBody Instituicao instituicao) {
        Instituicao createdInstituicao = instituicaoService.create(instituicao);
        return new ResponseEntity<>(createdInstituicao, HttpStatus.CREATED);
    }

    // Buscar instituição pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Instituicao> getInstituicaoById(@PathVariable String id) {
        Optional<Instituicao> instituicao = instituicaoService.getInstituicaoById(id);
        return instituicao.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Listar todas as instituições
    @GetMapping
    public ResponseEntity<List<Instituicao>> getAllInstituicaos() {
        List<Instituicao> instituicoes = instituicaoService.getAllInstituicaos();
        return ResponseEntity.ok(instituicoes);
    }

    // Atualizar instituição
    @PutMapping("/{id}")
    public ResponseEntity<Instituicao> updateInstituicao(@PathVariable String id,
            @Valid @RequestBody Instituicao updatedInstituicao) {
        Instituicao instituicao = instituicaoService.updateInstituicao(id, updatedInstituicao);
        return instituicao != null ? ResponseEntity.ok(instituicao) : ResponseEntity.notFound().build();
    }

    // Excluir instituição
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstituicao(@PathVariable String id) {
        return instituicaoService.deleteInstituicao(id) ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
