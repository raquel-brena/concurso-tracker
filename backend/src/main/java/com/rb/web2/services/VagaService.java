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
import com.rb.web2.repositories.VagaRepository;
import com.rb.web2.shared.exceptions.BadRequestException;

@Service
public class VagaService {

    @Autowired
    private VagaRepository vagaRepository;

    @Autowired
    private ProcessoSeletivoService processoSeletivoService;

    @Autowired
    private CargoService formacaoService;

    public VagaResponseDTO salvar(VagasRequestDTO dto) {
        try {
            ProcessoSeletivo processoSeletivo = processoSeletivoService.getProcessoSeletivoById(dto.processoSeletivoId());
            Cargo cargo = formacaoService.buscarPorId(dto.cargoId());

            if (dto.quantidade() <= 0) {
                throw new BadRequestException("A quantidade de vagas deve ser maior que zero.");
            }

            Vaga vaga = VagaMapper.toEntity(dto, processoSeletivo, cargo);
            Vaga vagaSalva = vagaRepository.save(vaga);

            return VagaResponseDTO.from(vagaSalva);
        } catch (BadRequestException e) {
            throw new RuntimeException("Erro ao salvar vaga: " + e.getMessage(), e);
        }
    }

    public List<VagaResponseDTO> buscarTodasVagas() {
        try {
            List<Vaga> vagas = vagaRepository.findAllByAtivoTrue();
            return vagas.stream()
                    .map(VagaResponseDTO::from)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar todas as vagas: " + e.getMessage(), e);
        }
    }

    public VagaResponseDTO buscarVagaResponseDTOPorId(Long id) {
        try {
            Vaga vaga = vagaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Vaga não encontrada com o id " + id));
            return VagaResponseDTO.from(vaga);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar vaga por id: " + e.getMessage(), e);
        }
    }

    public Vaga buscarVagaPorId(Long id) {
        try {
            Optional<Vaga> vagaOptional = vagaRepository.findById(id);
            return vagaOptional.orElseThrow(() -> new RuntimeException("Vaga não encontrada com o id " + id));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar vaga por id: " + e.getMessage(), e);
        }
    }

    public List<VagaResponseDTO> buscarVagasPorProcessoSeletivo(String processoId) {
        try {
            ProcessoSeletivo processoSeletivo = processoSeletivoService.getProcessoSeletivoById(processoId);
            List<Vaga> vagas = vagaRepository.findByProcessoSeletivoAndAtivoTrue(processoSeletivo);
            return vagas.stream()
                    .map(VagaResponseDTO::from)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar todas as vagas por processo: " + e.getMessage(), e);
        }
    }

    public List<VagaResponseDTO> buscarVagasPorCargo(String cargoNome) {
        try {
            List<Vaga> vagas = vagaRepository.findByCargoNome(cargoNome);
            return vagas.stream()
                    .map(VagaResponseDTO::from)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar todas as vagas por cargo: " + e.getMessage(), e);
        }
    }
    
    public VagaResponseDTO atualizar(Long id, VagaUpdateDTO dto) {
        try {
            Vaga vaga = vagaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Vaga não encontrada com o id " + id));

            if (vaga.getProcessoSeletivo().getId() == null ? dto.getProcessoSeletivoId() != null : !vaga.getProcessoSeletivo().getId().equals(dto.getProcessoSeletivoId())) {
                ProcessoSeletivo processoSeletivo = processoSeletivoService
                        .getProcessoSeletivoById(dto.getProcessoSeletivoId());
                vaga.setProcessoSeletivo(processoSeletivo);
            }

            if (vaga.getProcessoSeletivo().getId() == null ? dto.getProcessoSeletivoId() != null : !vaga.getProcessoSeletivo().getId().equals(dto.getProcessoSeletivoId())) {
                Cargo cargo = formacaoService.buscarPorId(dto.getCargoId());
                vaga.setCargo(cargo);
            }

            if (dto.getQuantidade() <= 0) {
                throw new IllegalArgumentException("A quantidade de vagas deve ser maior que zero.");
            }

            vaga.setQuantidade(dto.getQuantidade());
            vaga.setDescricao(dto.getDescricao());
            vaga.setTaxaInscricao(dto.getTaxaInscricao());
            vaga.setAtivo(dto.isAtivo());

            vagaRepository.save(vaga);
            
            return VagaResponseDTO.from(vaga);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Erro ao atualizar vaga: " + e.getMessage(), e);
        }
    }

    public void softDelete(Long id) {
        try {
            Vaga vaga = vagaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Vaga não encontrada com o id " + id));
            vaga.setAtivo(false);
            vagaRepository.save(vaga);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao desativar vaga: " + e.getMessage(), e);
        }
    }
}
