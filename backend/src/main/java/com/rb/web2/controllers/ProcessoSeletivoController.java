package com.rb.web2.controllers;

import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
import com.rb.web2.domain.processoSeletivo.dto.RequestProcessoDTO;
import com.rb.web2.domain.processoSeletivo.dto.ResponseProcessoDTO;
import com.rb.web2.domain.processoSeletivo.dto.UpdateProcessoDTO;
import com.rb.web2.domain.processoSeletivo.mapper.ProcessoSeletivoMapper;
import com.rb.web2.services.ProcessoSeletivoService;
import com.rb.web2.shared.RestMessage.RestSuccessMessage;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @PostMapping("/")
    public ResponseEntity create(@RequestBody RequestProcessoDTO dto) {

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
    public ResponseEntity get(@PathVariable String id) {
        var processo = this.service.getProcessoSeletivoById(id);

        return ResponseEntity.ok()
                .body(new RestSuccessMessage(
                        "Consulta realizada com sucesso.",
                        ProcessoSeletivoMapper.toResponseProcessoDTO(processo)));
    }

    @GetMapping("/")
    public ResponseEntity getAll() {
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
    public ResponseEntity delete(@PathVariable String id) {
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