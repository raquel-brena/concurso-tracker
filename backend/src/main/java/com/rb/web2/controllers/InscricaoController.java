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
import com.rb.web2.domain.inscricao.dto.RequestInscricaoDTO;
import com.rb.web2.domain.inscricao.dto.ResponseInscricaoDTO;
import com.rb.web2.domain.inscricao.dto.UpdateReqInscricaoDTO;
import com.rb.web2.domain.inscricao.mapper.InscricaoMapper;
import com.rb.web2.services.InscricaoService;
import com.rb.web2.services.PontuacaoCriterioService;
import com.rb.web2.shared.RestMessage.RestSuccessMessage;

@RestController
@RequestMapping("/api/inscricoes")
public class InscricaoController {

    @Autowired
    InscricaoService service;

    @Autowired
    PontuacaoCriterioService pontuacaoCriterioService;

    @GetMapping("/todas")
    public ResponseEntity<List<ResponseInscricaoDTO>> getAllInscricoes() {
        List<ResponseInscricaoDTO> applications = service.getAllInscricoes();
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/candidato")
    public ResponseEntity<List<ResponseInscricaoDTO>> getAllInscricoes(@RequestParam String candidatoId) {
        List<ResponseInscricaoDTO> applications = service.getAllInscricoesPorCandidato(candidatoId);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/vaga")
    public ResponseEntity<List<ResponseInscricaoDTO>> getAllInscricoes(@RequestParam Long vagaId) {
        List<ResponseInscricaoDTO> applications = service.getAllInscricoesPorVaga(vagaId);
        return ResponseEntity.ok(applications);
    }

    @PostMapping
    public ResponseEntity<String> createInscricao(@RequestBody @Validated RequestInscricaoDTO dto) {
        try {
            Inscricao inscricaoCriada = service.create(dto);
            service.create(dto);
            return ResponseEntity.ok("Incrição com id: " + inscricaoCriada.getId() + " criada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating application: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseInscricaoDTO> getInscricao(@PathVariable String id) {
        ResponseInscricaoDTO incricao = this.service.getResponseInscricaoDTOById(id);
        return ResponseEntity.ok(incricao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseInscricaoDTO> updateInscricao(
            @PathVariable String id,
            @RequestBody @Validated UpdateReqInscricaoDTO dto) {
    
            var updatedApplication = this.service.atualizarInscricao(id, dto);
            ResponseInscricaoDTO responseDTO = InscricaoMapper.toDTO(updatedApplication);

            return ResponseEntity.ok(responseDTO);
      
    }

    @GetMapping("processo")
    public ResponseEntity<List<ResponseInscricaoDTO>> getAllInscricoesPorProcessoSeletivo(@RequestParam("id") String processoId) {
        try {
            List<ResponseInscricaoDTO> applications = service.getAllInscricoesPorProcessoSeletivo(processoId);
            if (!applications.isEmpty()) {
                return new ResponseEntity<>(applications, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/nota")
    public ResponseEntity<BigDecimal> calcularNotaTotal(@PathVariable String id) {
        try {
            BigDecimal notaTotal = pontuacaoCriterioService.calcularNotaTotal(id);
            if (notaTotal != null) {
                return new ResponseEntity<>(notaTotal, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestSuccessMessage> deleteInscricao(@PathVariable String id) {
        try {
            service.softDelete(id);
            return new ResponseEntity<>(new RestSuccessMessage("Inscrição deletada com sucesso", id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}