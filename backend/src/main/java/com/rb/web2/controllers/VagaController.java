package com.rb.web2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rb.web2.domain.vaga.Vaga;
import com.rb.web2.domain.vaga.dto.VagasRequestDTO;
import com.rb.web2.services.VagaService;

@RestController
@RequestMapping("/api/vagas")
public class VagaController {

    @Autowired
    private VagaService vagasService;

    @PostMapping
public ResponseEntity<?> criarVaga(@RequestBody VagasRequestDTO vagasRequestDTO) {
    try {
        System.out.println("Vaga recebida: " + vagasRequestDTO);

        Vaga vagaSalva = vagasService.salvar(vagasRequestDTO);
        return new ResponseEntity<>(vagaSalva, HttpStatus.CREATED);
    } catch (IllegalArgumentException e) {
        // Em caso de erro esperado (ex: dados inválidos)
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
        // Em caso de erro inesperado
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


    @GetMapping
    public ResponseEntity<List<Vaga>> buscarVagas() {
        try {
            List<Vaga> vagas = vagasService.buscarTodasVagas();

            if (!vagas.isEmpty()) {
                return new ResponseEntity<>(vagas, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vaga> buscarVagaPorId(@PathVariable Long id) {
        try {
            Vaga vaga = vagasService.buscarVagaPorId(id);
            if (vaga != null) {
                return new ResponseEntity<>(vaga, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vaga> atualizarVaga(@PathVariable Long id, @RequestBody VagasRequestDTO dto) {
        try {
            Vaga vagaAtualizada = vagasService.atualizar(id, dto);
            if (vagaAtualizada != null) {
                return new ResponseEntity<>(vagaAtualizada, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // - Excluir vaga @TODO: Implementar
}
