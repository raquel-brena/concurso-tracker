package com.rb.web2.controllers;

import com.rb.web2.domain.user.User;
import com.rb.web2.domain.inscricao.Inscricao;
import com.rb.web2.domain.inscricao.dto.RequestInscricaoDTO;
import com.rb.web2.domain.inscricao.mapper.RequestInscricaoMapper;
import com.rb.web2.services.InscricaoService;
import com.rb.web2.services.PontuacaoCriterioService;
import com.rb.web2.domain.inscricao.dto.UpdateInscricaoDTO;

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
public class InscricaoController {
    
        @Autowired
        InscricaoService service;

        @Autowired
        PontuacaoCriterioService pontuacaoCriterioService;
    
        @GetMapping
        public ResponseEntity<List<Inscricao>> getAllInscricaos() {
            List<Inscricao> applications = service.getAllInscricaos();
            return ResponseEntity.ok(applications);
        }
    
        @PostMapping
        public ResponseEntity<String> createInscricao(@RequestBody @Validated RequestInscricaoDTO dto) {
            try {
                Inscricao createdApplication = service.create(dto); 
                service.create(dto); 
                return ResponseEntity.ok("Incrição com id: " + createdApplication.getId() + " criada com sucesso");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Error creating application: " + e.getMessage());
            }
        }

        @GetMapping("/{id}")
        public ResponseEntity<Inscricao> getInscricao(@PathVariable String id) {
            return this.service.getInscricaoById(id)
                    .map(ResponseEntity::ok) // Se o Optional tiver valor, retorna 200 OK com o valor
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)); // Se vazio, retorna 404 NOT FOUND
        }
    
        @PutMapping("/{id}")
        public ResponseEntity updateInscricao(@PathVariable String id, @RequestBody @Validated UpdateInscricaoDTO dto) {
            var updatedApplication = this.service.updateInscricao(id, dto);
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
        public ResponseEntity<Void> deleteInscricao(@PathVariable String id) {
            return null;
        }
         */
        

}