package com.rb.web2.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rb.web2.domain.inscricao.Inscricao;
import com.rb.web2.domain.inscricao.dto.RequestInscricaoDTO;
import com.rb.web2.domain.inscricao.dto.UpdateReqInscricaoDTO;
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

    @GetMapping
    public ResponseEntity<RestSuccessMessage> getAllInscricaos() {
        List<Inscricao> applications = service.getAllInscricaos();
        return ResponseEntity.ok()
                .body(new RestSuccessMessage("Inscrições buscadas com sucesso.", applications));
    }

    @PostMapping
    public ResponseEntity<RestSuccessMessage> createInscricao(@RequestBody @Validated RequestInscricaoDTO dto) {

        Inscricao createdApplication = service.create(dto);
        return ResponseEntity.ok()
                .body(new RestSuccessMessage("Incrição com id: " + createdApplication.getId() + " criada com sucesso",
                        createdApplication));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestSuccessMessage> getInscricao(@PathVariable String id) {
        Inscricao incricao = this.service.buscarInscricaoPorId(id);
        return ResponseEntity.ok()
                .body(new RestSuccessMessage("Inscrição buscada com sucesso.", incricao));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateInscricao(@PathVariable String id,
            @RequestBody @Validated UpdateReqInscricaoDTO dto) {
        var updatedApplication = this.service.atualizarInscricao(id, dto);
        if (updatedApplication == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
        return ResponseEntity.ok()
                .body(new RestSuccessMessage("Inscrição atualizada com sucesso.", updatedApplication));
    }

    @GetMapping("/{id}/nota")
    public ResponseEntity<BigDecimal> calcularNotaTotal(@PathVariable String id) {

        BigDecimal notaTotal = pontuacaoCriterioService.calcularNotaTotal(id);
        if (notaTotal != null) {
            return new ResponseEntity<>(notaTotal, HttpStatus.OK);
        }
        return null;
    }

    // @TODO Implement this method
    /*
     * @DeleteMapping("/{id}")
     * public ResponseEntity<Void> deleteInscricao(@PathVariable String id) {
     * return null;
     * }
     */

}