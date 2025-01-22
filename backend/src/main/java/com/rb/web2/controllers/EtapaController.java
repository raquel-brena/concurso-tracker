package com.rb.web2.controllers;

import com.rb.web2.domain.etapa.dto.EtapaRequestDTO;
import com.rb.web2.domain.etapa.dto.EtapaResponseDTO;
import com.rb.web2.services.EtapaService;
import com.rb.web2.shared.RestMessage.RestSuccessMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/etapas")
public class EtapaController {

    @Autowired
    private EtapaService etapaService;

    @PostMapping
    public ResponseEntity<RestSuccessMessage> criarEtapa(@Valid @RequestBody EtapaRequestDTO requestDTO) {
        EtapaResponseDTO etapaCriada = etapaService.create(requestDTO);
        RestSuccessMessage successMessage = new RestSuccessMessage("Etapa criada com sucesso", etapaCriada);
        return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<RestSuccessMessage> listarEtapas() {
        List<EtapaResponseDTO> etapas = etapaService.findAll();
        RestSuccessMessage successMessage = new RestSuccessMessage("Etapas listadas com sucesso", etapas);
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestSuccessMessage> buscarEtapaPorId(@PathVariable Long id) {
        EtapaResponseDTO etapa = etapaService.findById(id);
        RestSuccessMessage successMessage = new RestSuccessMessage("Etapa encontrada com sucesso", etapa);
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestSuccessMessage> atualizarEtapa(@PathVariable Long id, @Valid @RequestBody EtapaRequestDTO requestDTO) {
        EtapaResponseDTO etapaAtualizada = etapaService.update(id, requestDTO);
        RestSuccessMessage successMessage = new RestSuccessMessage("Etapa atualizada com sucesso", etapaAtualizada);
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestSuccessMessage> deletarEtapa(@PathVariable Long id) {
        etapaService.delete(id);
        RestSuccessMessage successMessage = new RestSuccessMessage("Etapa deletada com sucesso", null);
        return new ResponseEntity<>(successMessage, HttpStatus.NO_CONTENT);
    }
}
