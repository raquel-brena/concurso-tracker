package com.rb.web2.controllers;

import com.rb.web2.domain.agenda.dto.AgendaDTO;
import com.rb.web2.domain.processoSeletivo.dto.RequestProcessoDTO;
import com.rb.web2.domain.processoSeletivo.dto.UpdateProcessoDTO;
import com.rb.web2.domain.processoSeletivo.mapper.ProcessoSeletivoMapper;
import com.rb.web2.services.ProcessoSeletivoService;
import com.rb.web2.shared.RestMessage.RestSuccessMessage;
import com.rb.web2.shared.exceptions.NotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/processo")
public class ProcessoSeletivoController {

    ProcessoSeletivoService service;

    public ProcessoSeletivoController(ProcessoSeletivoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity createProcessoSeletivo(@RequestBody RequestProcessoDTO dto) {

        var processo = service.create(dto);

        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(processo.getId())
                .toUri();

        return ResponseEntity.created(location).body("Novo processo seletivo criado com o ID: " + processo.getId());
    }

    @PutMapping("{id}")
    public ResponseEntity<RestSuccessMessage> updateAgenda(@PathVariable String id,
            @RequestBody UpdateProcessoDTO dto) {
        var agenda = this.service.atualizar(id, dto);
        return ResponseEntity.ok().body(new RestSuccessMessage("Agenda atualizada com sucesso.", agenda));
    }

    @GetMapping("{id}")
    public ResponseEntity getProcessoSeletivo(@PathVariable String id) {
        var processo = this.service.getProcessoSeletivoById(id);
    
        return ResponseEntity.ok()
        .body( new RestSuccessMessage("Consulta realizada com sucesso.", ProcessoSeletivoMapper.toResponseProcessoDTO(processo)));
    }

    // UPDATE
    // vincular vagas
    // vincular agenda
    // vincular documentos
    // vincular criterios
    // vincular comissao
    // vincular participantes
}