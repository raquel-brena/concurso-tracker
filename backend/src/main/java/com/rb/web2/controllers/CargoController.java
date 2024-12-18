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

import com.rb.web2.domain.cargo.Cargo;
import com.rb.web2.domain.cargo.dto.CargoRequestDTO;
import com.rb.web2.services.CargoService;
@RestController
@RequestMapping("/api/cargo")
public class CargoController {

    @Autowired
    private final CargoService cargoService;

    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    // Endpoint para criar um novo cargo
    @PostMapping
    public ResponseEntity<Cargo> criarCargo(@RequestBody CargoRequestDTO dto) {    
        Cargo createdCargo = cargoService.criarCargo(dto);
        return new ResponseEntity<>(createdCargo, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Cargo>> listarCargos() {
        List<Cargo> cargos = cargoService.listarTodos();
        return new ResponseEntity<>(cargos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cargo> buscarCargoPorId(@PathVariable Long id) {
        Cargo cargo = cargoService.buscarPorId(id);
        return new ResponseEntity<>(cargo, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cargo> atualizarCargo(@PathVariable Long id, 
                                                @RequestBody CargoRequestDTO dto) {
        Cargo updatedCargo = cargoService.atualizar(id, dto);
        return updatedCargo != null ? new ResponseEntity<>(updatedCargo, HttpStatus.OK) 
                                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
