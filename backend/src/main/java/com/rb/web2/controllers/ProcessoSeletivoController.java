package com.rb.web2.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rb.web2.domain.processoComissao.dto.RequestMembroComissaoDTO;
import com.rb.web2.domain.processoSeletivo.dto.RequestProcessoDTO;
import com.rb.web2.services.ProcessoSeletivoService;
import com.rb.web2.shared.exceptions.NotFoundException;

import jakarta.persistence.EntityNotFoundException;

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

    @GetMapping("{id}")
    public ResponseEntity getProcessoSeletivo(@PathVariable String id) {
        var processo = this.service.getProcessoSeletivoById(id);

        if (processo.isEmpty()) {
            throw new NotFoundException("Processo seletivo com o ID " + id + " não encontrado.");
        }
        return ResponseEntity.ok().body(processo);
    }

    @PostMapping("/membro-comissao")
    public ResponseEntity<String> adicionarMembroComissao(@Validated @RequestBody RequestMembroComissaoDTO dto) {
        try {
            this.service.adicionarMembroComissao(dto);
            return ResponseEntity.ok("Membro adicionado com sucesso");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entidade não encontrada: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao adicionar membro: " + e.getMessage());
        }
    }

    @DeleteMapping("/membro-comissao")
    public ResponseEntity<String> removerMembroComissao(@Validated @RequestBody RequestMembroComissaoDTO dto) {
        try {
            this.service.removerMembroComissao(dto);
            return ResponseEntity.ok("Membro removido com sucesso");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entidade não encontrada: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao deletar membro: " + e.getMessage());
        }
    }

    // UPDATE
    // vincular vagas
    // vincular agenda
    // vincular documentos
    // vincular criterios
    // vincular comissao
    // vincular participantes
}