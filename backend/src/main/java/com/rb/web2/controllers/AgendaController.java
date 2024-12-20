package com.rb.web2.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rb.web2.domain.agenda.Agenda;
import com.rb.web2.domain.agenda.dto.AgendaDTO;
import com.rb.web2.domain.agenda.dto.AgendaResponseDTO;
import com.rb.web2.services.AgendaService;
import com.rb.web2.services.UserService;
import com.rb.web2.shared.RestMessage.RestSuccessMessage;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/agendas")
public class AgendaController {

    AgendaService service;
        private UserService userService;
    
        public AgendaController(AgendaService service) {
            this.service = service;
            this.userService = userService;
        }
    
        @PostMapping("/")
        public ResponseEntity<RestSuccessMessage> createAgenda(@Valid @RequestBody AgendaDTO dto) {
        var agenda = service.create(dto);

        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(agenda.getId())
                .toUri();

        RestSuccessMessage successMessage = new RestSuccessMessage("Nova agenda criada com o ID: ", agenda.getId());

        return ResponseEntity.created(location).body(successMessage);
    }

    @GetMapping("{id}")
    public ResponseEntity<Agenda> getAgenda(@PathVariable Long id) {
        var agenda = this.service.getAgendaById(id);
        return ResponseEntity.ok().body(agenda);
    }

    @GetMapping("/")
    public ResponseEntity<List<AgendaResponseDTO>> getAllAgendas() {
        List<AgendaResponseDTO> agendasDTO = this.service.getAllAgendas();
        return ResponseEntity.ok().body(agendasDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<RestSuccessMessage> removeAgenda(@PathVariable Long id) {
        this.service.deleteAgenda(id);

        RestSuccessMessage successMessage = new RestSuccessMessage("Agenda com o ID " + id + " removida com sucesso.",
                null);

        return ResponseEntity.ok().body(successMessage);
    }

    @PutMapping("{id}")
    public ResponseEntity<RestSuccessMessage> updateAgenda(@PathVariable Long id, @Valid @RequestBody AgendaDTO dto) {
        var agenda = this.service.updateAgenda(id, dto);
        return ResponseEntity.ok().body(new RestSuccessMessage("Agenda atualizada com sucesso.", agenda));
    }
}