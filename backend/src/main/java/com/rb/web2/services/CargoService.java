package com.rb.web2.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.rb.web2.domain.cargo.Cargo;
import com.rb.web2.domain.cargo.dto.CargoRequestDTO;
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

    public Cargo criarCargo(CargoRequestDTO dto) {
        verificarPermissaoDeCriacaoOuAlteracao();

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
                .orElseThrow(() -> new NotFoundException("Cargo não encontrado")); // Exceção caso o cargo não seja
                                                                                   // encontrado
    }

    public Cargo atualizar(Long id, CargoRequestDTO cargoAtualizado) {
        verificarPermissaoDeCriacaoOuAlteracao();

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
