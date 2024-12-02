package com.rb.web2.controllers;

import com.rb.web2.domain.agenda.dto.AgendaDTO;
import com.rb.web2.services.AgendaService;
import com.rb.web2.shared.RestMessage.RestSuccessMessage;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/agendas")
public class AgendaController {

    AgendaService service;

    public AgendaController(AgendaService service) {
        this.service = service;
    }

    @PostMapping("/")
    public ResponseEntity createAgenda(@Valid @RequestBody AgendaDTO dto) {
        var agenda = service.create(dto);

        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(agenda.getId())
                .toUri();

        return ResponseEntity.created(location).body("Nova agenda criada com o ID: " + agenda.getId());
    }

    @GetMapping("{id}")
    public ResponseEntity getAgenda(@PathVariable Long id) {
        var agenda = this.service.getAgendaById(id);
        return ResponseEntity.ok().body(agenda);
    }

    @GetMapping("/")
    public ResponseEntity getAllAgendas() {
        var agendas = this.service.getAllAgendas();
        return ResponseEntity.ok().body(agendas);
    }

    @DeleteMapping("{id}")
    public ResponseEntity removeAgenda(@PathVariable Long id) {
        this.service.deleteAgenda(id);

        return ResponseEntity.ok().body("Agenda com o ID " + id + " removida com sucesso.");
    }

    @PutMapping("{id}")
    public ResponseEntity<RestSuccessMessage> updateAgenda(@PathVariable Long id, @RequestBody AgendaDTO dto) {
        var agenda = this.service.updateAgenda(id, dto);
        return ResponseEntity.ok().body(new RestSuccessMessage("Agenda atualizada com sucesso.", agenda));
    }
}