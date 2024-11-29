package com.rb.web2.controllers;

import com.rb.web2.domain.formacao.Formacao;
import com.rb.web2.domain.formacao.dto.FormacaoRequestDTO;
import com.rb.web2.services.FormacaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/formacoes")
public class FormacaoController {

    @Autowired
    private final FormacaoService formacaoService;

    public FormacaoController(FormacaoService formacaoService) {
        this.formacaoService = formacaoService;
    }

    // Endpoint para criar uma nova formação
    @PostMapping
    public ResponseEntity<Formacao> criarFormacao(@RequestBody FormacaoRequestDTO dto) {    
        Formacao createdFormacao = formacaoService.salvar(dto);
        return new ResponseEntity<>(createdFormacao, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Formacao>> listarFormacoes() {
        List<Formacao> formacoes = formacaoService.listarTodas();
        return new ResponseEntity<>(formacoes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Formacao> buscarFormacaoPorId(@PathVariable Long id) {
        Optional<Formacao> optionalFormacao = formacaoService.buscarPorId(id);
        return optionalFormacao.map(formacao -> new ResponseEntity<>(formacao, HttpStatus.OK))
                           .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Formacao> atualizarFormacao(@PathVariable Long id, 
                                                            @RequestBody FormacaoRequestDTO dto) {
        Formacao updatedFormacao = formacaoService.atualizar(id, dto);
        return updatedFormacao != null ? new ResponseEntity<>(updatedFormacao, HttpStatus.OK) 
                                        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Endpoint para deletar uma formação @TODO: Implementar
    /* 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFormacao(@PathVariable Long id) {
        boolean isDeleted = formacaoService.deletarFormacao(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) 
                         : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/
}
