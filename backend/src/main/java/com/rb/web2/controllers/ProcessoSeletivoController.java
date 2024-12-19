package com.rb.web2.controllers;

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

    @PostMapping()
    public ResponseEntity<RestSuccessMessage> create(@Valid @RequestBody ProcessoRequestDTO dto) {

        ProcessoResponseDTO processo = service.create(dto);

        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(processo.id())
                .toUri();

        return ResponseEntity.created(location)
                .body(new RestSuccessMessage("Processo criado com sucesso.", processo));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getProcessoSeletivo(@PathVariable String id) {
        var processo = this.service.getProcessoSeletivoById(id);
        return ResponseEntity.ok().body(processo);
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
        var processo = this.service.getProcessoSeletivoById(id);

        return ResponseEntity.ok()
                .body(new RestSuccessMessage(
                        "Consulta realizada com sucesso.",
                        ProcessoResponseDTO.from(processo)));
    }

    @GetMapping()
    public ResponseEntity<RestSuccessMessage> getAll() {
        var processos = this.service.getAllProcessoSeletivos();

        return ResponseEntity.ok()
                .body(new RestSuccessMessage(
                        "Consulta realizada com sucesso.",
                        processos));
    }

    @GetMapping("participante/{id}")
    public ResponseEntity<RestSuccessMessage> getProcessoSeletivoByParticipante(@PathVariable String id) {
        var processos = this.service.getProcessoSeletivoByParticipante(id);

        return ResponseEntity.ok()
                .body(new RestSuccessMessage(
                        "Consulta realizada com sucesso.",
                        processos));
    }

    @GetMapping("comissao/{id}")
    public ResponseEntity<RestSuccessMessage> getProcessoSeletivoByComissao(@PathVariable String id) {
        var processos = this.service.getProcessoSeletivoByComissao(id);

        return ResponseEntity.ok()
                .body(new RestSuccessMessage(
                        "Consulta realizada com sucesso.",
                        processos));
    }

    @PutMapping("{id}")
    public ResponseEntity<RestSuccessMessage> update(@PathVariable String id,
            @Valid @RequestBody UpdateProcessoDTO dto) {
        var processo = this.service.atualizar(id, dto);
        return ResponseEntity.ok().body(new RestSuccessMessage("Processo seletivo atualizado com sucesso",
                ProcessoResponseDTO.from(processo)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<RestSuccessMessage> delete(@PathVariable String id) {
        this.service.deleteById(id);

        String mensagem = "O edital com id " + id + " foi deletado com sucesso.";

        return ResponseEntity.ok()
                .body(new RestSuccessMessage(mensagem));
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