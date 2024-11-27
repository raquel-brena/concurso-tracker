package com.rb.web2.controllers;

import com.rb.web2.domain.formacao.Formacao;
import com.rb.web2.domain.formacao.dto.FormacaoRequestDTO;
import com.rb.web2.services.FormacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /* 

    // Endpoint para listar todas as formações
    @GetMapping
    public ResponseEntity<List<FormacaoDTO>> listarFormacoes() {
        List<FormacaoDTO> formacoes = formacaoService.listarFormacoes();
        return new ResponseEntity<>(formacoes, HttpStatus.OK);
    }

    // Endpoint para buscar uma formação por ID
    @GetMapping("/{id}")
    public ResponseEntity<FormacaoDTO> buscarFormacaoPorId(@PathVariable Long id) {
        FormacaoDTO formacao = formacaoService.buscarFormacaoPorId(id);
        return formacao != null ? new ResponseEntity<>(formacao, HttpStatus.OK) 
                                 : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Endpoint para atualizar uma formação
    @PutMapping("/{id}")
    public ResponseEntity<FormacaoDTO> atualizarFormacao(@PathVariable Long id, 
                                                         @RequestBody FormacaoDTO formacaoDTO) {
        FormacaoDTO updatedFormacao = formacaoService.atualizarFormacao(id, formacaoDTO);
        return updatedFormacao != null ? new ResponseEntity<>(updatedFormacao, HttpStatus.OK) 
                                       : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/

    // Endpoint para deletar uma formação
    /* 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFormacao(@PathVariable Long id) {
        boolean isDeleted = formacaoService.deletarFormacao(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) 
                         : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/
}
