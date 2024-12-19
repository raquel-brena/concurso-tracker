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

import com.rb.web2.domain.criterioAvaliacao.CriterioAvaliacao;
import com.rb.web2.domain.criterioAvaliacao.dto.CriterioRequestDTO;
import com.rb.web2.domain.criterioAvaliacao.dto.CriterioResponseDTO;
import com.rb.web2.services.CriterioAvaliacaoService;
import com.rb.web2.shared.RestMessage.RestSuccessMessage;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/criterios")
public class CriterioAvaliacaoController {

    @Autowired
    private CriterioAvaliacaoService criterioAvaliacaoService;

    @PostMapping
    public ResponseEntity<RestSuccessMessage> criarCriterio(@Valid @RequestBody CriterioRequestDTO requestCriterioDTO) {
        CriterioAvaliacao criterioSalvo = criterioAvaliacaoService.create(requestCriterioDTO);
        RestSuccessMessage successMessage = new RestSuccessMessage("Critério de avaliação criado com sucesso",
                criterioSalvo);
        return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
    }

    @GetMapping("/processo")
    public ResponseEntity<RestSuccessMessage> buscarCriteriosPorProcessoSeletivo(
            @RequestParam("id") String processoSeletivoId) {

        List<CriterioResponseDTO> criterios = criterioAvaliacaoService.findAllByProcessoSeletivo(processoSeletivoId);
        RestSuccessMessage successMessage = new RestSuccessMessage("Critérios de avaliação encontrados com sucesso",
                criterios);
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @GetMapping("/inscricao")
    public ResponseEntity<RestSuccessMessage> buscarCriteriosPorInscricao(
            @RequestParam("id") String inscricaoId) {
        List<CriterioResponseDTO> criterios = criterioAvaliacaoService.findAllByInscricao(inscricaoId);
        RestSuccessMessage successMessage = new RestSuccessMessage("Critérios de avaliação encontrados com sucesso",
                criterios);
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestSuccessMessage> buscarCriterioPorId(@PathVariable Long id) {
        CriterioAvaliacao criterioOptional = criterioAvaliacaoService.getCriterioById(id);
        RestSuccessMessage successMessage = new RestSuccessMessage("Critério de avaliação encontrado com sucesso",
                criterioOptional);
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestSuccessMessage> atualizarCriterio(@PathVariable Long id,
            @Valid @RequestBody CriterioRequestDTO requestCriterioDTO) {

        CriterioAvaliacao criterioAtualizado = criterioAvaliacaoService.update(id, requestCriterioDTO);
        RestSuccessMessage successMessage = new RestSuccessMessage("Critério de avaliação atualizado com sucesso",
                criterioAtualizado);
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestSuccessMessage> deletarCriterio(@PathVariable Long id) {
        criterioAvaliacaoService.softDelete(id);
        RestSuccessMessage successMessage = new RestSuccessMessage("Critério de avaliação deletado com sucesso" + id);
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }
}