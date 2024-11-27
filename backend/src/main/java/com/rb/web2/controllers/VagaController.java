package com.rb.web2.controllers;

import com.rb.web2.domain.vaga.dto.VagaRequestDTO;
import com.rb.web2.services.VagaService;
import com.rb.web2.domain.vaga.Vaga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vagas")
public class VagaController {

    @Autowired
    private VagaService vagaService;

    @PostMapping
    public ResponseEntity<Vaga> criarVaga(@RequestBody VagaRequestDTO vagaRequestDTO) {
        try {
            // Chama o serviço para salvar a vaga
            Vaga vagaSalva = vagaService.salvar(vagaRequestDTO);
            return new ResponseEntity<>(vagaSalva, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Em caso de erro (ex: quantidade <= 0), retorna o erro com status BAD_REQUEST
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Em caso de erro inesperado
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // - Buscar todas as vagas
    // - Buscar vagas por id
    // - Atualizar vaga
    // - Excluir vaga
}
