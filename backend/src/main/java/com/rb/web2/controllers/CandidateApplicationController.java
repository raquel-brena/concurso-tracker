package com.rb.web2.controllers;

import com.rb.web2.domain.user.User;
import com.rb.web2.domain.candidateApplication.CandidateApplication;
import com.rb.web2.domain.candidateApplication.dto.RequestInscricaoDTO;
import com.rb.web2.domain.candidateApplication.mapper.RequestInscricaoMapper;
import com.rb.web2.services.CandidateApplicationService;
import com.rb.web2.services.PontuacaoCriterioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/inscricoes")
public class CandidateApplicationController {
    
        @Autowired
        CandidateApplicationService service;

        @Autowired
        PontuacaoCriterioService pontuacaoCriterioService;
    
        @GetMapping
        public ResponseEntity<List<CandidateApplication>> getAllCandidateApplications() {
            List<CandidateApplication> applications = service.getAllCandidateApplications();
            return ResponseEntity.ok(applications);
        }
    
        @PostMapping
    public ResponseEntity<String> createCandidateApplication(@RequestBody @Validated RequestInscricaoDTO dto) {
        try {
            service.create(dto); 
        return ResponseEntity.ok("Application created successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating application: " + e.getMessage());
        }
    }

    
        @GetMapping("/{id}")
        public ResponseEntity<CandidateApplication> getCandidateApplication(@PathVariable String id) {
            return this.service.getCandidateApplicationById(id)
                    .map(ResponseEntity::ok) // Se o Optional tiver valor, retorna 200 OK com o valor
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)); // Se vazio, retorna 404 NOT FOUND
        }
    
        @PutMapping("/{id}")
        public ResponseEntity updateCandidateApplication(@PathVariable String id, @RequestBody @Validated RequestInscricaoDTO dto) {
            var updatedApplication = this.service.updateCandidateApplication(id, dto);
            if (updatedApplication == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                    .body(null);
            }
            return ResponseEntity.ok(updatedApplication);
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
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    
        // @TODO Implement this method
        /*
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteCandidateApplication(@PathVariable String id) {
            return null;
        }
         */
        

}