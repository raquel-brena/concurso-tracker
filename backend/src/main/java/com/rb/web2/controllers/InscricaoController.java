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

import com.rb.web2.domain.inscricao.Inscricao;
import com.rb.web2.domain.inscricao.dto.InscricaoRequestDTO;
import com.rb.web2.domain.inscricao.dto.InscricaoResponseDTO;
import com.rb.web2.domain.inscricao.dto.UpdateInscricaoDTO;
import com.rb.web2.domain.inscricao.mapper.InscricaoMapper;
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
    public ResponseEntity<List<InscricaoResponseDTO>> getAllInscricoes() {
        List<InscricaoResponseDTO> applications = service.getAllInscricoes();
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/candidato")
    public ResponseEntity<List<InscricaoResponseDTO>> getAllInscricoes(@RequestParam String candidatoId) {
        List<InscricaoResponseDTO> applications = service.getAllInscricoesPorCandidato(candidatoId);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/vaga")
    public ResponseEntity<List<InscricaoResponseDTO>> getAllInscricoes(@RequestParam Long vagaId) {
        List<InscricaoResponseDTO> applications = service.getAllInscricoesPorVaga(vagaId);
        return ResponseEntity.ok(applications);
    }

    @PostMapping
    public ResponseEntity<RestSuccessMessage> createInscricao(@Valid @RequestBody InscricaoRequestDTO dto) {
        Inscricao inscricaoCriada = service.create(dto);
        service.create(dto);
        RestSuccessMessage successMessage = new RestSuccessMessage(
                "Inscrição com ID: " + inscricaoCriada.getId() + " criada com sucesso.");

        // Retornando a resposta com status 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body(successMessage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InscricaoResponseDTO> getInscricao(@PathVariable String id) {
        InscricaoResponseDTO incricao = this.service.getResponseInscricaoDTOById(id);
        return ResponseEntity.ok(incricao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InscricaoResponseDTO> updateInscricao(
            @PathVariable String id,
            @Valid @RequestBody @Validated UpdateInscricaoDTO dto) {

        var updatedApplication = this.service.atualizarInscricao(id, dto);
        InscricaoResponseDTO responseDTO = InscricaoMapper.toDTO(updatedApplication);

        return ResponseEntity.ok(responseDTO);

    }

    @GetMapping("processo")
    public ResponseEntity<List<InscricaoResponseDTO>> getAllInscricoesPorProcessoSeletivo(
            @RequestParam("id") String processoId) {
        List<InscricaoResponseDTO> applications = service.getAllInscricoesPorProcessoSeletivo(processoId);
        if (!applications.isEmpty()) {
            return new ResponseEntity<>(applications, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/{id}/nota")
    public ResponseEntity<BigDecimal> calcularNotaTotal(@PathVariable String id) {
        BigDecimal notaTotal = pontuacaoCriterioService.calcularNotaTotal(id);
        if (notaTotal != null) {
            return new ResponseEntity<>(notaTotal, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestSuccessMessage> deleteInscricao(@PathVariable String id) {
        service.softDelete(id);
        return new ResponseEntity<>(new RestSuccessMessage("Inscrição deletada com sucesso", id), HttpStatus.OK);
    }
}