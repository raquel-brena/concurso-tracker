package com.rb.web2.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rb.web2.domain.inscricao.dto.InscricaoRequestDTO;
import com.rb.web2.domain.inscricao.dto.InscricaoResponseDTO;
import com.rb.web2.domain.inscricao.dto.UpdateInscricaoDTO;
import com.rb.web2.services.InscricaoService;
import com.rb.web2.services.PontuacaoCriterioService;
import com.rb.web2.shared.RestMessage.RestSuccessMessage;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/inscricoes")
public class InscricaoController {

    @Autowired
    InscricaoService service;

    @Autowired
    PontuacaoCriterioService pontuacaoCriterioService;

    @GetMapping("/todas")
    public ResponseEntity<RestSuccessMessage> getAllInscricoes() {
        List<InscricaoResponseDTO> applications = service.getAllInscricoes();
        RestSuccessMessage successMessage = new RestSuccessMessage("Inscrições encontradas com sucesso", applications);
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @GetMapping("/candidato")
    public ResponseEntity<RestSuccessMessage> getAllInscricoes(@RequestParam String candidatoId) {
        List<InscricaoResponseDTO> applications = service.getAllInscricoesPorCandidato(candidatoId);
        RestSuccessMessage successMessage = new RestSuccessMessage("Inscrições encontradas com sucesso", applications);
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @GetMapping("/vaga")
    public ResponseEntity<RestSuccessMessage> getAllInscricoes(@RequestParam Long vagaId) {
        List<InscricaoResponseDTO> applications = service.getAllInscricoesPorVaga(vagaId);
        if (!applications.isEmpty()) {
            RestSuccessMessage successMessage = new RestSuccessMessage("Inscrições encontradas com sucesso",
                    applications);
            return new ResponseEntity<>(successMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping
    public ResponseEntity<RestSuccessMessage> createInscricao(@Valid @RequestBody InscricaoRequestDTO dto) {
        InscricaoResponseDTO inscricaoCriada = service.create(dto);
        RestSuccessMessage successMessage = new RestSuccessMessage(
                "Inscrição com ID: " + inscricaoCriada.id() + " criada com sucesso.");

        return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestSuccessMessage> getInscricao(@PathVariable String id) {
        InscricaoResponseDTO incricao = this.service.getResponseInscricaoDTOById(id);
        RestSuccessMessage successMessage = new RestSuccessMessage("Inscrição encontrada com sucesso", incricao);
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestSuccessMessage> updateInscricao(
            @PathVariable String id,
            @Valid @RequestBody @Validated UpdateInscricaoDTO dto) {

        InscricaoResponseDTO updatedApplication = this.service.atualizarInscricao(id, dto);
        RestSuccessMessage successMessage = new RestSuccessMessage("Inscrição atualizada com sucesso", updatedApplication);

        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @GetMapping("processo")
    public ResponseEntity<RestSuccessMessage> getAllInscricoesPorProcessoSeletivo(
            @RequestParam("id") String processoId) {
        List<InscricaoResponseDTO> applications = service.getAllInscricoesPorProcessoSeletivo(processoId);
        if (!applications.isEmpty()) {
            RestSuccessMessage successMessage = new RestSuccessMessage("Inscrições encontradas com sucesso",
                    applications);
            return new ResponseEntity<>(successMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/{id}/nota")
    public ResponseEntity<RestSuccessMessage> calcularNotaTotal(@PathVariable String id) {
        BigDecimal notaTotal = pontuacaoCriterioService.calcularNotaTotal(id);
        if (notaTotal != null) {
            RestSuccessMessage successMessage = new RestSuccessMessage("Nota total calculada com sucesso", notaTotal);
            return new ResponseEntity<>(successMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestSuccessMessage> deleteInscricao(@PathVariable String id) {
        service.softDelete(id);
        RestSuccessMessage successMessage = new RestSuccessMessage("Inscrição deletada com sucesso");
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }
}