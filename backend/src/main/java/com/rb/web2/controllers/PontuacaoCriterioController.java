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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rb.web2.domain.pontuacaoCriterio.dto.PontuacaoRequestDTO;
import com.rb.web2.domain.pontuacaoCriterio.dto.PontuacaoResponseDTO;
import com.rb.web2.services.PontuacaoCriterioService;
import com.rb.web2.shared.RestMessage.RestSuccessMessage;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pontuacao")
public class PontuacaoCriterioController {

    @Autowired
    private PontuacaoCriterioService pontuacaoCriterioService;

    @PostMapping
    public ResponseEntity<RestSuccessMessage> criarPontuacao(@Valid @RequestBody PontuacaoRequestDTO dto) {
        pontuacaoCriterioService.create(dto);
        RestSuccessMessage successMessage = new RestSuccessMessage("Pontuação criada com sucesso!", dto);
        return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
    }

    @GetMapping("/inscricao")
    public ResponseEntity<List<PontuacaoResponseDTO>> listarPontuacoesPorInscricao(
            @RequestParam("id") String inscricaoId) {

        List<PontuacaoResponseDTO> pontuacoes = pontuacaoCriterioService.findByInscricao(inscricaoId);
        return new ResponseEntity<>(pontuacoes, HttpStatus.OK);

    }

    @GetMapping("/criterio")
    public ResponseEntity<List<PontuacaoResponseDTO>> listarPontuacoesPorCriterio(@RequestParam("id") Long criterioId) {

        List<PontuacaoResponseDTO> pontuacoes = pontuacaoCriterioService.findByCriterio(criterioId);
        return new ResponseEntity<>(pontuacoes, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<PontuacaoResponseDTO> buscarPontuacaoPorId(@PathVariable Long id) {
        PontuacaoResponseDTO pontuacao = pontuacaoCriterioService.getPontuacaoCriterioById(id).orElseThrow(
                () -> new RuntimeException("Pontuação Critério não encontrada com o id " + id));
        return new ResponseEntity<>(pontuacao, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PontuacaoResponseDTO> atualizarPontuacao(@PathVariable Long id,
            @Valid @RequestBody PontuacaoRequestDTO dto) {

        PontuacaoResponseDTO pontuacaoAtualizada = pontuacaoCriterioService.update(id, dto);
        return new ResponseEntity<>(pontuacaoAtualizada, HttpStatus.OK);

    }

    @GetMapping("/total")
    public ResponseEntity<RestSuccessMessage> calcularNotaTotalPorInscricao(@RequestParam("id") String inscricaoId) {
        String notaTotal = pontuacaoCriterioService.calcularNotaTotal(inscricaoId).toString();

        RestSuccessMessage successMessage = new RestSuccessMessage("Nota total calculada com sucesso!", notaTotal);
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @GetMapping("/total/processo")
    public String calcularNotaTotalPorProcesso(@RequestParam("id") String processoId) {
        return pontuacaoCriterioService.calcularNotaTotalPorInscricaoDoProcesso(processoId).toString();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestSuccessMessage> deletarPontuacao(@PathVariable Long id) {
        pontuacaoCriterioService.delete(id);
        RestSuccessMessage successMessage = new RestSuccessMessage("Pontuação deletada com sucesso!");
        return ResponseEntity.ok(successMessage);
    }

}