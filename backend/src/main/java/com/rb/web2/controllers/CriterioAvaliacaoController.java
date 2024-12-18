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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rb.web2.domain.criterioAvaliacao.CriterioAvaliacao;
import com.rb.web2.domain.criterioAvaliacao.dto.RequestCriterioDTO;
import com.rb.web2.services.CriterioAvaliacaoService;
import com.rb.web2.shared.RestMessage.RestSuccessMessage;

@RestController
@RequestMapping("/api/criterios")
public class CriterioAvaliacaoController {

    @Autowired
    private CriterioAvaliacaoService criterioAvaliacaoService;

    @PostMapping
    public ResponseEntity<CriterioAvaliacao> criarCriterio(@RequestBody RequestCriterioDTO requestCriterioDTO) {
        CriterioAvaliacao criterioSalvo = criterioAvaliacaoService.create(requestCriterioDTO);
        return new ResponseEntity<>(criterioSalvo, HttpStatus.CREATED);

    }

    // @GetMapping
    // public ResponseEntity<List<CriterioAvaliacao>> buscarCriterios(@RequestParam String processoSeletivoId) {

    //     List<CriterioAvaliacao> criterios = criterioAvaliacaoService.findAllByProcessoSeletivo(processoSeletivoId);
    //     return new ResponseEntity<>(criterios, HttpStatus.OK);

    // }

    @GetMapping("/{id}")
    public ResponseEntity<CriterioAvaliacao> buscarCriterioPorId(@PathVariable Long id) {
        CriterioAvaliacao criterioOptional = criterioAvaliacaoService.getCriterioById(id);
        return new ResponseEntity<>(criterioOptional, HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<CriterioAvaliacao> atualizarCriterio(@PathVariable Long id,
            @RequestBody RequestCriterioDTO requestCriterioDTO) {

        CriterioAvaliacao criterioAtualizado = criterioAvaliacaoService.update(id, requestCriterioDTO);
        return new ResponseEntity<>(criterioAtualizado, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestSuccessMessage> deletarCriterio(@PathVariable Long id) {
        try {
            criterioAvaliacaoService.softDelete(id);
            return ResponseEntity.ok().body(new RestSuccessMessage("Critério de avaliação deletado com sucesso.", id));
        } catch (Exception e) {
            return new ResponseEntity<>(new RestSuccessMessage("Erro ao deletar critério de avaliação.", null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}