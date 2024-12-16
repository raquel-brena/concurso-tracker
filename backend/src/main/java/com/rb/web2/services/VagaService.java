package com.rb.web2.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rb.web2.domain.formacao.Cargo;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
import com.rb.web2.domain.vaga.Vaga;
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

    public Vaga salvar(VagasRequestDTO dto) {
        ProcessoSeletivo processoSeletivo = processoSeletivoService.getProcessoSeletivoById(dto.processoSeletivoId());
        Cargo cargo = formacaoService.buscarPorId(dto.cargoId());

        if (dto.quantidade() <= 0) {
            throw new BadRequestException("A quantidade de vagas deve ser maior que zero.");
        }

        Vaga vaga = VagaMapper.toEntity(dto, processoSeletivo, cargo);

        return vagaRepository.save(vaga);
    }

    public List<Vaga> buscarTodasVagas() {
        return vagaRepository.findAll();
    }

    public Vaga buscarVagaPorId(Long id) {
        Optional<Vaga> vagaOptional = vagaRepository.findById(id);
        return vagaOptional.orElseThrow(() -> new RuntimeException("Vaga não encontrada com o id " + id));
    }

    public List<Vaga> buscarVagasPorProcessoSeletivo(ProcessoSeletivo processoSeletivo) {
        return vagaRepository.findByProcessoSeletivo(processoSeletivo);
    }

    public Vaga atualizar(Long id, VagasRequestDTO dto) {
        vagaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vaga não encontrada com o id " + id));

        ProcessoSeletivo processoSeletivo = processoSeletivoService.getProcessoSeletivoById(dto.processoSeletivoId());
        Cargo cargo = formacaoService.buscarPorId(dto.cargoId());

        if (dto.quantidade() <= 0) {
            throw new IllegalArgumentException("A quantidade de vagas deve ser maior que zero.");
        }

        Vaga vagaExistente = VagaMapper.toEntity(dto, processoSeletivo, cargo);
        vagaExistente.setId(id);
        return vagaRepository.save(vagaExistente);
    }

    // @TODO: Deletar vaga
}
