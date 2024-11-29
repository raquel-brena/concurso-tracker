package com.rb.web2.controllers;

import java.util.List;
import java.util.Optional;

import com.rb.web2.domain.pontuacaoCriterio.PontuacaoCriterio;
import com.rb.web2.domain.pontuacaoCriterio.dto.RequestPontuacaoDTO;
import com.rb.web2.services.PontuacaoCriterioService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pontuacao")
public class PontuacaoCriterioController {
    
    @Autowired
    private PontuacaoCriterioService pontuacaoCriterioService;
    
    @PostMapping
    public ResponseEntity<?> criarPontuacao(@Valid @RequestBody RequestPontuacaoDTO dto) {
        try {
            PontuacaoCriterio pontuacaoCriada = pontuacaoCriterioService.create(dto);
            return new ResponseEntity<>(pontuacaoCriada, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            String errorMessage = "Erro ao criar pontuação: " + e.getMessage();
    
            Logger logger = LoggerFactory.getLogger(PontuacaoCriterioController.class);
            logger.error("Erro ao criar pontuação: ", e); 
    
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/inscricao")
    public ResponseEntity<List<PontuacaoCriterio>> listarPontuacoesPorInscricao(@RequestParam("id") String inscricaoId) {
        try {
            List<PontuacaoCriterio> pontuacoes = pontuacaoCriterioService.findByInscricao(inscricaoId);
            if (!pontuacoes.isEmpty()) {
                return new ResponseEntity<>(pontuacoes, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/criterio")
    public ResponseEntity<List<PontuacaoCriterio>> listarPontuacoesPorCriterio(@RequestParam("id") String criterioId) {
        try {
            List<PontuacaoCriterio> pontuacoes = pontuacaoCriterioService.findByCriterio(criterioId);
            if (!pontuacoes.isEmpty()) {
                return new ResponseEntity<>(pontuacoes, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PontuacaoCriterio> buscarPontuacaoPorId(@PathVariable String id) {
        try {
            Optional<PontuacaoCriterio> pontuacao = pontuacaoCriterioService.getPontuacaoCriterioById(id);
            if (pontuacao.isPresent()) {
                return new ResponseEntity<>(pontuacao.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PontuacaoCriterio> atualizarPontuacao(@PathVariable String id, @RequestBody RequestPontuacaoDTO dto) {
        try {
            PontuacaoCriterio pontuacaoAtualizada = pontuacaoCriterioService.update(id, dto);
            return new ResponseEntity<>(pontuacaoAtualizada, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } 

    // @TODO: Implementar endpoint para deletar uma nota de um critério de avaliação
}