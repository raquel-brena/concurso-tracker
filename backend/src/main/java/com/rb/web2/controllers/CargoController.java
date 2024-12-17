package com.rb.web2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rb.web2.domain.cargo.Cargo;
import com.rb.web2.domain.cargo.dto.CargoRequestDTO;
import com.rb.web2.services.CargoService;

import java.util.List;
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
    /* 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCargo(@PathVariable Long id) {
        boolean isDeleted = cargoService.deletarCargo(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) 
                         : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/
}
