package com.rb.web2.controllers;

import java.util.Collections;
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

@RestController
@RequestMapping("/api/vagas")
public class VagaController {

    @Autowired
    private VagaService vagasService;

    @PostMapping
    public ResponseEntity<RestSuccessMessage> criarVaga(@RequestBody VagasRequestDTO vagasRequestDTO) {
        try {
            System.out.println("Vaga recebida: " + vagasRequestDTO);

            // Chama o serviço para salvar a vaga
            VagaResponseDTO vagaSalva = vagasService.salvar(vagasRequestDTO);

            // Criando a resposta com uma mensagem de sucesso e dados da vaga
            RestSuccessMessage sucessoMessage = new RestSuccessMessage(
                    "Vaga criada com sucesso.", vagaSalva);

            return new ResponseEntity<>(sucessoMessage, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Em caso de erro esperado (ex: dados inválidos)
            return new ResponseEntity<>(new RestSuccessMessage(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Em caso de erro inesperado
            return new ResponseEntity<>(new RestSuccessMessage("Erro interno: " + e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/todas")
    public ResponseEntity<List<VagaResponseDTO>> buscarVagas() {
        try {
            List<VagaResponseDTO> vagas = vagasService.buscarTodasVagas();

            if (!vagas.isEmpty()) {
                return new ResponseEntity<>(vagas, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<VagaResponseDTO>> buscarVagas(@RequestParam String processoId) {
        try {
            List<VagaResponseDTO> vagas = vagasService.buscarVagasPorProcessoSeletivo(processoId);

            if (!vagas.isEmpty()) {
                return new ResponseEntity<>(vagas, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<VagaResponseDTO> buscarVagaPorId(@PathVariable Long id) {
        try {
            VagaResponseDTO vaga = vagasService.buscarVagaResponseDTOPorId(id);
            if (vaga != null) {
                return new ResponseEntity<>(vaga, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cargo")
    public ResponseEntity<List<VagaResponseDTO>> buscarVagaPorCargo(@RequestParam("cn") String cargoNome) {
        try {
            List<VagaResponseDTO> vagas = vagasService.buscarVagasPorCargo(cargoNome);
            return new ResponseEntity<>(vagas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<VagaResponseDTO> atualizarVaga(@PathVariable Long id, @RequestBody VagaUpdateDTO dto) {
        try {
            VagaResponseDTO vagaAtualizada = vagasService.atualizar(id, dto);
            if (vagaAtualizada != null) {
                return new ResponseEntity<>(vagaAtualizada, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestSuccessMessage> deletarVaga(@PathVariable Long id) {
        try {
            vagasService.softDelete(id);
            return new ResponseEntity<>(new RestSuccessMessage("Vaga deletada com sucesso.", id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RestSuccessMessage("Erro interno: " + e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
