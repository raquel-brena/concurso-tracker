package com.rb.web2.controllers;

import com.rb.web2.domain.agenda.dto.AgendaDTO;
import com.rb.web2.domain.processoSeletivo.dto.RequestProcessoDTO;
import com.rb.web2.domain.processoSeletivo.dto.UpdateProcessoDTO;
import com.rb.web2.domain.processoSeletivo.mapper.ProcessoSeletivoMapper;
import com.rb.web2.infra.properties.FileStorageProperties;
import com.rb.web2.services.ProcessoSeletivoService;
import com.rb.web2.shared.RestMessage.RestSuccessMessage;
import com.rb.web2.shared.exceptions.BadRequestException;
import com.rb.web2.shared.exceptions.NotFoundException;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
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
    public ResponseEntity<RestSuccessMessage> atualizar(@PathVariable String id,
            @RequestBody UpdateProcessoDTO dto) {
        var processo = this.service.atualizar(id, dto);
        return ResponseEntity.ok().body(new RestSuccessMessage("Proesso seletivo atualizado com sucesso", ProcessoSeletivoMapper.toResponseProcessoDTO(processo)));
    }

    @PostMapping("{id}/edital")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable String id)
            throws IllegalStateException, IOException {
        var fileaDownloadURI = this.service.uploadEdital(file, id);
        return ResponseEntity.ok(fileaDownloadURI);
    }
    

    
  public ResponseEntity downloadEdital(String filename, String id, HttpServletRequest request) throws IOException {
   Resource resource = this.service.downloadEdital(filename, id);

   String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

   if (contentType == null) {
       contentType = "application/octet-stream";
   }

      return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename =\"" + resource.getFilename() + "\"")
                .body(resource);
  }


    @GetMapping("{id}")
    public ResponseEntity getProcessoSeletivo(@PathVariable String id) {
        var processo = this.service.getProcessoSeletivoById(id);
    
        return ResponseEntity.ok()
        .body(new RestSuccessMessage(
            "Consulta realizada com sucesso.",
            ProcessoSeletivoMapper.toResponseProcessoDTO(processo)));
    }





    // UPDATE
    // vincular vagas
    // vincular agenda
    // vincular documentos
    // vincular criterios
    // vincular comissao
    // vincular participantes
}