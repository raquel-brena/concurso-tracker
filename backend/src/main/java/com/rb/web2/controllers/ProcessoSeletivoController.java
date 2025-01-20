package com.rb.web2.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
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

import com.rb.web2.domain.processoComissao.dto.MembroComissaoRequestDTO;
import com.rb.web2.domain.processoSeletivo.dto.ProcessoRequestDTO;
import com.rb.web2.domain.processoSeletivo.dto.ProcessoResponseDTO;
import com.rb.web2.domain.processoSeletivo.dto.UpdateProcessoDTO;
import com.rb.web2.services.ProcessoSeletivoService;
import com.rb.web2.shared.RestMessage.RestSuccessMessage;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/processo")
public class ProcessoSeletivoController {

    ProcessoSeletivoService service;

    public ProcessoSeletivoController(ProcessoSeletivoService service) {
        this.service = service;
    }

    @PostMapping("/")
    public ResponseEntity<RestSuccessMessage> create(@Valid @RequestBody ProcessoRequestDTO dto) {

        ProcessoResponseDTO processo = service.create(dto);

        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(processo.id())
                .toUri();

        RestSuccessMessage successMessage = new RestSuccessMessage("Processo criado com sucesso", processo);
        return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestSuccessMessage> getProcessoSeletivo(@PathVariable String id) {
        ProcessoResponseDTO processo = this.service.getById(id);
        RestSuccessMessage successMessage = new RestSuccessMessage("Consulta realizada com sucesso", processo);
        return ResponseEntity.ok(successMessage);
    }

    @PostMapping("/membro-comissao")
    public ResponseEntity<RestSuccessMessage> adicionarMembroComissao(
            @Valid @RequestBody MembroComissaoRequestDTO dto) {
        this.service.adicionarMembroComissao(dto);
        RestSuccessMessage successMessage = new RestSuccessMessage("Membro adicionado com sucesso", dto);
        return ResponseEntity.ok(successMessage);
    }

    @DeleteMapping("/membro-comissao")
    public ResponseEntity<RestSuccessMessage> removerMembroComissao(@Valid @RequestBody MembroComissaoRequestDTO dto) {
        this.service.removerMembroComissao(dto);
        RestSuccessMessage successMessage = new RestSuccessMessage("Membro removido com sucesso");
        return ResponseEntity.ok(successMessage);
    }

    public ResponseEntity<RestSuccessMessage> get(@PathVariable String id) {
        ProcessoResponseDTO processo = this.service.getById(id);

        RestSuccessMessage successMessage = new RestSuccessMessage("Consulta realizada com sucesso", processo);
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<RestSuccessMessage> getAll() {
        List<ProcessoResponseDTO> processos = this.service.getAllProcessoSeletivos();
        
        RestSuccessMessage successMessage = new RestSuccessMessage("Consulta realizada com sucesso", processos);
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @GetMapping("participante/{id}")
    public ResponseEntity<RestSuccessMessage> getProcessoSeletivoByParticipante(@PathVariable String id) {
        List<ProcessoResponseDTO> processos = this.service.getProcessoSeletivoByParticipante(id);
        
        RestSuccessMessage successMessage = new RestSuccessMessage("Consulta realizada com sucesso", processos);
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @GetMapping("comissao/{id}")
    public ResponseEntity<RestSuccessMessage> getProcessoSeletivoByComissao(@PathVariable String id) {
        List<ProcessoResponseDTO> processos = this.service.getProcessoSeletivoByComissao(id);
        
        RestSuccessMessage successMessage = new RestSuccessMessage("Consulta realizada com sucesso", processos);
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestSuccessMessage> update(@PathVariable String id,
            @Valid @RequestBody UpdateProcessoDTO dto) {
        ProcessoResponseDTO processo = this.service.atualizar(id, dto);

        RestSuccessMessage successMessage = new RestSuccessMessage("Processo atualizado com sucesso", processo);
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<RestSuccessMessage> delete(@PathVariable String id) {
        this.service.deleteById(id);
        RestSuccessMessage successMessage = new RestSuccessMessage("Processo deletado com sucesso");
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    // @PostMapping("{id}/edital/{filename:.+}")
    // public ResponseEntity downloadEdital(String filename, String id,
    // HttpServletRequest request) throws IOException {

    // Resource resource = this.service.downloadEdital(filename, id);

    // String contentType = request.getServletContext()
    // .getMimeType(resource.getFile().getAbsolutePath());

    // if (contentType == null) {
    // contentType = "application/octet-stream";
    // }

    // return ResponseEntity.ok()
    // .contentType(MediaType.parseMediaType(contentType))
    // .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename =\"" +
    // resource.getFilename() + "\"")
    // .body(resource);
    // }
}