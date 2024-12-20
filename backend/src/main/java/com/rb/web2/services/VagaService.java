package com.rb.web2.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rb.web2.domain.cargo.Cargo;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
import com.rb.web2.domain.vaga.Vaga;
import com.rb.web2.domain.vaga.dto.VagaResponseDTO;
import com.rb.web2.domain.vaga.dto.VagaUpdateDTO;
import com.rb.web2.domain.vaga.dto.VagasRequestDTO;
import com.rb.web2.domain.vaga.mapper.VagaMapper;
import com.rb.web2.infra.util.AuthorizationUtil;
import com.rb.web2.repositories.VagaRepository;
import com.rb.web2.shared.exceptions.BadRequestException;
import com.rb.web2.shared.exceptions.NotFoundException;

import jakarta.annotation.PostConstruct;

@Service
public class VagaService {

    @Autowired
    private VagaRepository repository;

    @Autowired
    private ProcessoSeletivoService processoSeletivoService;

    @Autowired
    private CargoService formacaoService;

    @Autowired
    private UserService userService;

    private AuthorizationUtil authorizationUtil;

    @PostConstruct
    public void init() {
        this.authorizationUtil = new AuthorizationUtil(userService);
    }

    private void verificarPermissaoDeCriacaoOuAlteracao(Long vagaId) {
        authorizationUtil.<Long>verificarPermissaoOuComissao(
                vagaId,
                "EDIT_VAGAS",
                id -> repository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Vaga não encontrada.")),
                (entity, user) -> {
                    Vaga vaga = (Vaga) entity;
                    return vaga.getProcessoSeletivo().getComissaoOrganizadora().contains(user);
                });
    }

    public VagaResponseDTO salvar(VagasRequestDTO dto) {
        try {
            verificarPermissaoDeCriacaoOuAlteracao(null);

            ProcessoSeletivo processoSeletivo = processoSeletivoService
                    .getProcessoSeletivoById(dto.processoSeletivoId());
            Cargo cargo = formacaoService.buscarCargoPorId(dto.cargoId());

            if (dto.quantidade() <= 0) {
                throw new BadRequestException("A quantidade de vagas deve ser maior que zero.");
            }

            Vaga vaga = VagaMapper.toEntity(dto, processoSeletivo, cargo);
            Vaga vagaSalva = repository.save(vaga);

            return VagaResponseDTO.from(vagaSalva);
        } catch (BadRequestException e) {
            throw new RuntimeException("Erro ao salvar vaga: " + e.getMessage(), e);
        }
    }

    public List<VagaResponseDTO> buscarTodasVagas() {
        try {
            List<Vaga> vagas = repository.findAllByAtivoTrue();
            return vagas.stream()
                    .map(VagaResponseDTO::from)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar todas as vagas: " + e.getMessage(), e);
        }
    }

    public VagaResponseDTO buscarVagaResponseDTOPorId(Long id) {
        try {
            Vaga vaga = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Vaga não encontrada com o id " + id));
            return VagaResponseDTO.from(vaga);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar vaga por id: " + e.getMessage(), e);
        }
    }

    public Vaga buscarVagaPorId(Long id) {
        try {
            Optional<Vaga> vagaOptional = repository.findById(id);
            return vagaOptional.orElseThrow(() -> new RuntimeException("Vaga não encontrada com o id " + id));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar vaga por id: " + e.getMessage(), e);
        }
    }

    public List<VagaResponseDTO> buscarVagasPorProcessoSeletivo(String processoId) {
        try {
            ProcessoSeletivo processoSeletivo = processoSeletivoService.getProcessoSeletivoById(processoId);
            List<Vaga> vagas = repository.findByProcessoSeletivoAndAtivoTrue(processoSeletivo);
            return vagas.stream()
                    .map(VagaResponseDTO::from)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar todas as vagas por processo: " + e.getMessage(), e);
        }
    }

    public List<VagaResponseDTO> buscarVagasPorCargo(String cargoNome) {
        try {
            List<Vaga> vagas = repository.findByCargoNome(cargoNome);
            return vagas.stream()
                    .map(VagaResponseDTO::from)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar todas as vagas por cargo: " + e.getMessage(), e);
        }
    }

    public VagaResponseDTO atualizar(Long id, VagaUpdateDTO dto) {
        try {
            verificarPermissaoDeCriacaoOuAlteracao(id);

            Vaga vaga = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Vaga não encontrada com o id " + id));

            if (vaga.getProcessoSeletivo().getId() == null ? dto.getProcessoSeletivoId() != null
                    : !vaga.getProcessoSeletivo().getId().equals(dto.getProcessoSeletivoId())) {
                ProcessoSeletivo processoSeletivo = processoSeletivoService
                        .getProcessoSeletivoById(dto.getProcessoSeletivoId());
                vaga.setProcessoSeletivo(processoSeletivo);
            }

            if (vaga.getProcessoSeletivo().getId() == null ? dto.getProcessoSeletivoId() != null
                    : !vaga.getProcessoSeletivo().getId().equals(dto.getProcessoSeletivoId())) {
                Cargo cargo = formacaoService.buscarCargoPorId(dto.getCargoId());
                vaga.setCargo(cargo);
            }

            if (dto.getQuantidade() <= 0) {
                throw new IllegalArgumentException("A quantidade de vagas deve ser maior que zero.");
            }

            vaga.setQuantidade(dto.getQuantidade());
            vaga.setDescricao(dto.getDescricao());
            vaga.setTaxaInscricao(dto.getTaxaInscricao());
            vaga.setAtivo(dto.isAtivo());

            repository.save(vaga);

            return VagaResponseDTO.from(vaga);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Erro ao atualizar vaga: " + e.getMessage(), e);
        }
    }

    public void softDelete(Long id) {
        try {
            verificarPermissaoDeCriacaoOuAlteracao(id);

            Vaga vaga = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Vaga não encontrada com o id " + id));
            vaga.setAtivo(false);
            repository.save(vaga);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao desativar vaga: " + e.getMessage(), e);
        }
    }
}
