package com.rb.web2.controllers;

import com.rb.web2.domain.institution.Institution;
import com.rb.web2.services.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/institutions")
public class InstitutionController {

    @TODO // Classe inteira. Por enquanto, apenas o esqueleto das funções foi feito.

    @Autowired
    private InstitutionService institutionService;

    // Criar nova instituição
    @PostMapping
    public ResponseEntity<Institution> createInstitution(@RequestBody Institution institution) {
        Institution createdInstitution = institutionService.createInstitution(institution);
        return new ResponseEntity<>(createdInstitution, HttpStatus.CREATED);
    }

    // Buscar instituição pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Institution> getInstitutionById(@PathVariable String id) {
        Optional<Institution> institution = institutionService.getInstitutionById(id);
        return institution.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Listar todas as instituições
    @GetMapping
    public ResponseEntity<List<Institution>> getAllInstitutions() {
        List<Institution> institutions = institutionService.getAllInstitutions();
        return ResponseEntity.ok(institutions);
    }

    // Atualizar instituição
    @PutMapping("/{id}")
    public ResponseEntity<Institution> updateInstitution(@PathVariable String id, @RequestBody Institution updatedInstitution) {
        Institution institution = institutionService.updateInstitution(id, updatedInstitution);
        return institution != null ? ResponseEntity.ok(institution) : ResponseEntity.notFound().build();
    }

    // Excluir instituição
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstitution(@PathVariable String id) {
        return institutionService.deleteInstitution(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
