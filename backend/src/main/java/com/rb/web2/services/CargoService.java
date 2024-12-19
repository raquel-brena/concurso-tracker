package com.rb.web2.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.rb.web2.domain.cargo.Cargo;
import com.rb.web2.domain.cargo.dto.CargoRequestDTO;
import com.rb.web2.domain.cargo.dto.CargoResponseDTO;
import com.rb.web2.domain.user.User;
import com.rb.web2.repositories.CargoRepository;
import com.rb.web2.shared.exceptions.NotFoundException;

@Service
public class CargoService {

    @Autowired
    private CargoRepository repository;

    @Autowired
    private UserService userService;

    private void verificarPermissaoDeCriacaoOuAlteracao() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = (User) userService.loadUserByUsername(login);
    }

    public CargoResponseDTO criarCargo(CargoRequestDTO dto) {
        verificarPermissaoDeCriacaoOuAlteracao();

        Cargo cargo = new Cargo();

        cargo.setNome(dto.nome());
        cargo.setDescricao(dto.descricao());
        cargo.setRemuneracao(dto.remuneracao());
        cargo.setTemporario(dto.temporario());

        repository.save(cargo);

        CargoResponseDTO response = CargoResponseDTO.from(cargo);
        return response;
    }

    public List<CargoResponseDTO> listarTodos() {
        List<Cargo> cargos = repository.findAll();

        List<CargoResponseDTO> responseList = cargos.stream()
                .map(CargoResponseDTO::from)
                .collect(Collectors.toList());

        return (responseList);
    }

    public CargoResponseDTO buscarPorId(Long id) {
        Cargo cargo = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cargo não encontrado"));
                
        return CargoResponseDTO.from(cargo);
    }

    public Cargo buscarCargoPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cargo não encontrado"));
    }    

    public CargoResponseDTO atualizar(Long id, CargoRequestDTO cargoAtualizado) {
        verificarPermissaoDeCriacaoOuAlteracao();

        Cargo cargoExistente = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cargo não encontrado"));

        cargoExistente.setNome(cargoAtualizado.nome());
        cargoExistente.setDescricao(cargoAtualizado.descricao());
        cargoExistente.setRemuneracao(cargoAtualizado.remuneracao());
        cargoExistente.setTemporario(cargoAtualizado.temporario());

        repository.save(cargoExistente);

        return CargoResponseDTO.from(cargoExistente);
    }
}
