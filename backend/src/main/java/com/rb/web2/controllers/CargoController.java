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

import com.rb.web2.domain.cargo.dto.CargoRequestDTO;
import com.rb.web2.domain.cargo.dto.CargoResponseDTO;
import com.rb.web2.services.CargoService;
import com.rb.web2.shared.RestMessage.RestSuccessMessage;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/cargo")
public class CargoController {

    @Autowired
    private final CargoService cargoService;

    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @PostMapping
    public ResponseEntity<RestSuccessMessage> criarCargo(@Valid @RequestBody CargoRequestDTO dto) {    
        CargoResponseDTO createdCargo = cargoService.criarCargo(dto);
        RestSuccessMessage successMessage = new RestSuccessMessage("Cargo criado com sucesso", createdCargo);
        return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<RestSuccessMessage> listarCargos() {
        List<CargoResponseDTO> cargos = cargoService.listarTodos();
        RestSuccessMessage successMessage = new RestSuccessMessage("Cargos encontrados com sucesso", cargos);
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestSuccessMessage> buscarCargoPorId(@PathVariable Long id) {
        CargoResponseDTO cargo = cargoService.buscarPorId(id);
        RestSuccessMessage successMessage = new RestSuccessMessage("Cargo encontrado com sucesso", cargo);
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestSuccessMessage> atualizarCargo(@PathVariable Long id, 
                                                @Valid @RequestBody CargoRequestDTO dto) {
        CargoResponseDTO updatedCargo = cargoService.atualizar(id, dto);
        RestSuccessMessage successMessage = new RestSuccessMessage("Cargo atualizado com sucesso", updatedCargo);
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    // Endpoint para deletar um cargo @TODO: Implementar
    // Provavelmente não é necessário
    /* 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCargo(@PathVariable Long id) {
        boolean isDeleted = cargoService.deletarCargo(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) 
                         : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/
}
