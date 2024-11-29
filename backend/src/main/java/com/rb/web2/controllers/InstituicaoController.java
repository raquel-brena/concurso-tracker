package com.rb.web2.controllers;

import com.rb.web2.domain.instituicao.Instituicao;
import com.rb.web2.services.InstituicaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/instituicaos")
public class InstituicaoController {

    // @TODO // Classe inteira. Por enquanto, apenas o esqueleto das funções foi
    // feito.

    @Autowired
    private InstituicaoService instituicaoService;

    // Criar nova instituição
    @PostMapping
    public ResponseEntity<Instituicao> createInstituicao(@RequestBody Instituicao instituicao) {
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
        List<Instituicao> instituicaos = instituicaoService.getAllInstituicaos();
        return ResponseEntity.ok(instituicaos);
    }

    // Atualizar instituição
    @PutMapping("/{id}")
    public ResponseEntity<Instituicao> updateInstituicao(@PathVariable String id,
            @RequestBody Instituicao updatedInstituicao) {
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
