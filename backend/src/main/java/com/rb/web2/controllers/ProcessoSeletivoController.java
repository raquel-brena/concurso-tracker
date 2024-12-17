package com.rb.web2.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rb.web2.domain.processoComissao.dto.RequestMembroComissaoDTO;
import com.rb.web2.domain.processoSeletivo.dto.RequestProcessoDTO;
import com.rb.web2.domain.processoSeletivo.dto.ResponseProcessoDTO;
import com.rb.web2.domain.processoSeletivo.dto.UpdateProcessoDTO;
import com.rb.web2.domain.processoSeletivo.mapper.ProcessoSeletivoMapper;
import com.rb.web2.services.ProcessoSeletivoService;
import com.rb.web2.shared.RestMessage.RestSuccessMessage;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/processo")
public class ProcessoSeletivoController {

    ProcessoSeletivoService service;

    public ProcessoSeletivoController(ProcessoSeletivoService service) {
        this.service = service;
    }

    @PostMapping("/")
    public ResponseEntity<RestSuccessMessage> create(@RequestBody RequestProcessoDTO dto) {

        ResponseProcessoDTO processo = service.create(dto);

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

    public ResponseEntity<RestSuccessMessage> get(@PathVariable String id) {
        var processo = this.service.getProcessoSeletivoById(id);

        return ResponseEntity.ok()
                .body(new RestSuccessMessage(
                        "Consulta realizada com sucesso.",
                        ProcessoSeletivoMapper.toResponseProcessoDTO(processo)));
    }

    @GetMapping("/")
    public ResponseEntity<RestSuccessMessage> getAll() {
        var processos = this.service.getAllProcessoSeletivos();

        return ResponseEntity.ok()
                .body(new RestSuccessMessage(
                        "Consulta realizada com sucesso.",
                        processos));
    }

    @PutMapping("{id}")
    public ResponseEntity<RestSuccessMessage> update(@PathVariable String id,
            @RequestBody UpdateProcessoDTO dto) {
        var processo = this.service.atualizar(id, dto);
        return ResponseEntity.ok().body(new RestSuccessMessage("Proesso seletivo atualizado com sucesso",
                ProcessoSeletivoMapper.toResponseProcessoDTO(processo)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<RestSuccessMessage> delete(@PathVariable String id) {
        this.service.deleteById(id);

        return ResponseEntity.ok()
                .body(new RestSuccessMessage(
                        "Consulta realizada com sucesso.",
                        id));
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

    // UPDATE
    // vincular vagas
    // vincular agenda
    // vincular documentos
    // vincular criterios
    // vincular comissao
    // vincular participantes
}