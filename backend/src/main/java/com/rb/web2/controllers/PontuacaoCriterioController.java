package com.rb.web2.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.rb.web2.domain.pontuacaoCriterio.dto.RequestPontuacaoDTO;
import com.rb.web2.domain.pontuacaoCriterio.dto.ResponsePontuacaoDTO;
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

            return ResponseEntity.badRequest().body(errorMessage);
        } catch (Exception e) {
            String errorMessage = "Erro inesperado: " + e.getMessage();

            Logger logger = LoggerFactory.getLogger(PontuacaoCriterioController.class);
            logger.error("Erro inesperado: ", e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @GetMapping("/inscricao")
    public ResponseEntity<List<ResponsePontuacaoDTO>> listarPontuacoesPorInscricao(
            @RequestParam("id") String inscricaoId) {

        List<ResponsePontuacaoDTO> pontuacoes = pontuacaoCriterioService.findByInscricao(inscricaoId);
        return new ResponseEntity<>(pontuacoes, HttpStatus.OK);

    }

    @GetMapping("/criterio")
    public ResponseEntity<List<ResponsePontuacaoDTO>> listarPontuacoesPorCriterio(@RequestParam("id") Long criterioId) {

        List<ResponsePontuacaoDTO> pontuacoes = pontuacaoCriterioService.findByCriterio(criterioId);
        return new ResponseEntity<>(pontuacoes, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePontuacaoDTO> buscarPontuacaoPorId(@PathVariable Long id) {
        ResponsePontuacaoDTO pontuacao = pontuacaoCriterioService.getPontuacaoCriterioById(id).orElseThrow(
                () -> new RuntimeException("Pontuação Critério não encontrada com o id " + id));
        return new ResponseEntity<>(pontuacao, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePontuacaoDTO> atualizarPontuacao(@PathVariable Long id,
            @RequestBody RequestPontuacaoDTO dto) {

        ResponsePontuacaoDTO pontuacaoAtualizada = pontuacaoCriterioService.update(id, dto);
        return new ResponseEntity<>(pontuacaoAtualizada, HttpStatus.OK);

    }

    @GetMapping("/total")
    public ResponseEntity<String> calcularNotaTotalPorInscricao(@RequestParam("id") String inscricaoId) {
        try {
            return new ResponseEntity<>(pontuacaoCriterioService.calcularNotaTotal(inscricaoId).toString(),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/total/processo")
    public String calcularNotaTotalPorProcesso(@RequestParam("id") String processoId) {
        try {
            return pontuacaoCriterioService.calcularNotaTotalPorInscricaoDoProcesso(processoId).toString();
        } catch (Exception e) {
            return "Erro ao calcular nota total por processo: " + e.getMessage();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarPontuacao(@PathVariable Long id) {
        try {
            pontuacaoCriterioService.delete(id);
            return new ResponseEntity<>("Pontuação deletada com sucesso!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}