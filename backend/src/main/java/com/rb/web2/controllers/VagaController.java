package com.rb.web2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rb.web2.domain.vaga.dto.VagaResponseDTO;
import com.rb.web2.domain.vaga.dto.VagaUpdateDTO;
import com.rb.web2.domain.vaga.dto.VagasRequestDTO;
import com.rb.web2.services.VagaService;
import com.rb.web2.shared.RestMessage.RestSuccessMessage;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/vagas")
public class VagaController {

    @Autowired
    private VagaService vagasService;

    @PostMapping
    public ResponseEntity<RestSuccessMessage> criarVaga(@Valid @RequestBody VagasRequestDTO vagasRequestDTO) {
        VagaResponseDTO vagaSalva = vagasService.salvar(vagasRequestDTO);

        RestSuccessMessage sucessoMessage = new RestSuccessMessage(
                "Vaga criada com sucesso.", vagaSalva);

        return new ResponseEntity<>(sucessoMessage, HttpStatus.CREATED);
    }

    @GetMapping("/todas")
    public ResponseEntity<RestSuccessMessage> buscarVagas() {
        List<VagaResponseDTO> vagas = vagasService.buscarTodasVagas();

        if (!vagas.isEmpty()) {
            RestSuccessMessage sucessoMessage = new RestSuccessMessage(
                    "Vagas encontradas com sucesso.", vagas);
            return new ResponseEntity<>(sucessoMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping
    public ResponseEntity<RestSuccessMessage> buscarVagas(@RequestParam String processoId) {
        List<VagaResponseDTO> vagas = vagasService.buscarVagasPorProcessoSeletivo(processoId);

        if (!vagas.isEmpty()) {
            RestSuccessMessage sucessoMessage = new RestSuccessMessage(
                    "Vagas encontradas com sucesso.", vagas);
            return new ResponseEntity<>(sucessoMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestSuccessMessage> buscarVagaPorId(@PathVariable Long id) {
        VagaResponseDTO vaga = vagasService.buscarVagaResponseDTOPorId(id);
        if (vaga != null) {
            RestSuccessMessage sucessoMessage = new RestSuccessMessage(
                    "Vaga encontrada com sucesso.", vaga);
            return new ResponseEntity<>(sucessoMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cargo")
    public ResponseEntity<RestSuccessMessage> buscarVagaPorCargo(@RequestParam("cn") String cargoNome) {
        List<VagaResponseDTO> vagas = vagasService.buscarVagasPorCargo(cargoNome);
        RestSuccessMessage sucessoMessage = new RestSuccessMessage(
                "Vagas encontradas com sucesso.", vagas);
        return new ResponseEntity<>(sucessoMessage, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestSuccessMessage> atualizarVaga(@PathVariable Long id,
            @Valid @RequestBody VagaUpdateDTO dto) {
        VagaResponseDTO vagaAtualizada = vagasService.atualizar(id, dto);
        if (vagaAtualizada != null) {
            RestSuccessMessage sucessoMessage = new RestSuccessMessage(
                    "Vaga atualizada com sucesso.", vagaAtualizada);
            return new ResponseEntity<>(sucessoMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestSuccessMessage> deletarVaga(@PathVariable Long id) {
        vagasService.softDelete(id);
        RestSuccessMessage sucessoMessage = new RestSuccessMessage(
                "Vaga deletada com sucesso.");
        return new ResponseEntity<>(sucessoMessage, HttpStatus.OK);
    }
}
