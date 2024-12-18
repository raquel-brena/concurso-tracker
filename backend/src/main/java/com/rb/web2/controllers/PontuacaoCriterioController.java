package com.rb.web2.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rb.web2.domain.pontuacaoCriterio.PontuacaoCriterio;
import com.rb.web2.domain.pontuacaoCriterio.dto.RequestPontuacaoDTO;
import com.rb.web2.services.PontuacaoCriterioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pontuacao")
public class PontuacaoCriterioController {

    @Autowired
    private PontuacaoCriterioService pontuacaoCriterioService;

    @PostMapping
    public ResponseEntity<String> criarPontuacao(@Valid @RequestBody RequestPontuacaoDTO dto) {
        try {
            pontuacaoCriterioService.create(dto);
            return new ResponseEntity<>("Pontuação criada com sucesso!", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            String errorMessage = "Erro ao criar pontuação: " + e.getMessage();

            Logger logger = LoggerFactory.getLogger(PontuacaoCriterioController.class);
            logger.error("Erro ao criar pontuação: ", e);

            // Retorna uma resposta 400 com a mensagem de erro
            return ResponseEntity.badRequest().body(errorMessage);
        } catch (Exception e) {
            String errorMessage = "Erro inesperado: " + e.getMessage();

            Logger logger = LoggerFactory.getLogger(PontuacaoCriterioController.class);
            logger.error("Erro inesperado: ", e);

            // Retorna uma resposta 500 para erro interno
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @GetMapping("/inscricao")
    public ResponseEntity<List<PontuacaoCriterio>> listarPontuacoesPorInscricao(
            @RequestParam("id") String inscricaoId) {

        List<PontuacaoCriterio> pontuacoes = pontuacaoCriterioService.findByInscricao(inscricaoId);
        return new ResponseEntity<>(pontuacoes, HttpStatus.OK);

    }

    @GetMapping("/criterio")
    public ResponseEntity<List<PontuacaoCriterio>> listarPontuacoesPorCriterio(@RequestParam("id") Long criterioId) {

        List<PontuacaoCriterio> pontuacoes = pontuacaoCriterioService.findByCriterio(criterioId);
        return new ResponseEntity<>(pontuacoes, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<PontuacaoCriterio> buscarPontuacaoPorId(@PathVariable Long id) {

        var pontuacaoCriteriopontuacao = pontuacaoCriterioService.getPontuacaoCriterioById(id);
        return new ResponseEntity<>(pontuacaoCriteriopontuacao, HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<PontuacaoCriterio> atualizarPontuacao(@PathVariable Long id,
            @RequestBody RequestPontuacaoDTO dto) {

        PontuacaoCriterio pontuacaoAtualizada = pontuacaoCriterioService.update(id, dto);
        return new ResponseEntity<>(pontuacaoAtualizada, HttpStatus.OK);

    }

    // @TODO: Implementar endpoint para deletar uma nota de um critério de avaliação
}