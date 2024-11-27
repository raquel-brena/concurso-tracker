package com.rb.web2.services;

import com.rb.web2.domain.vaga.Vaga;
import com.rb.web2.domain.vaga.dto.VagaRequestDTO;
import com.rb.web2.domain.vaga.mapper.VagaMapper;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
import com.rb.web2.domain.formacao.Formacao;
import com.rb.web2.services.ProcessoSeletivoService;
// import com.rb.web2.services.FormacaoService;
import com.rb.web2.repositories.VagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VagaService {

    @Autowired
    private VagaRepository vagaRepository;

    @Autowired
    private ProcessoSeletivoService processoSeletivoService;

    @Autowired
    private FormacaoService formacaoService;
    
    public Vaga salvar(VagaRequestDTO dto) {
        ProcessoSeletivo processoSeletivo = processoSeletivoService.getProcessoSeletivoById(dto.processoSeletivoId())
            .orElseThrow(() -> new RuntimeException("Processo Seletivo não encontrado"));
        Formacao formacao = formacaoService.buscarPorId(dto.formacaoId())
            .orElseThrow(() -> new RuntimeException("Processo Seletivo não encontrado"));

        if (dto.quantidade() <= 0) {
            throw new IllegalArgumentException("A quantidade de vagas deve ser maior que zero.");
        }

        Vaga vaga = VagaMapper.toEntity(dto, processoSeletivo, formacao);
        
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

    // @TODO: Deletar vaga
}
