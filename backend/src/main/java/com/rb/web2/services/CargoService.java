package com.rb.web2.services;

import com.rb.web2.repositories.CargoRepository;
import com.rb.web2.shared.exceptions.NotFoundException;
import com.rb.web2.domain.formacao.Cargo;
import com.rb.web2.domain.formacao.dto.CargoRequestDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CargoService {

    @Autowired
    private CargoRepository repository;

    public Cargo criarCargo(CargoRequestDTO dto) {
        
        Cargo cargo = new Cargo();
        
        cargo.setNome(dto.nome());  
        cargo.setDescricao(dto.descricao());  
        cargo.setRemuneracao(dto.remuneracao());  
        cargo.setTemporario(dto.temporario());  
    
        return repository.save(cargo);
    }

    public List<Cargo> listarTodos() {
        return repository.findAll(); 
    }

    public Cargo buscarPorId(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Cargo não encontrado"));  // Exceção caso o cargo não seja encontrado
    }

    public Cargo atualizar(Long id, CargoRequestDTO cargoAtualizado) {

        Cargo cargoExistente = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cargo não encontrado"));

        cargoExistente.setNome(cargoAtualizado.nome());
        cargoExistente.setDescricao(cargoAtualizado.descricao());
        cargoExistente.setRemuneracao(cargoAtualizado.remuneracao());
        cargoExistente.setTemporario(cargoAtualizado.temporario());

        return repository.save(cargoExistente); 
    }

    // @TODO: Método para excluir um cargo (caso necessário)
}
