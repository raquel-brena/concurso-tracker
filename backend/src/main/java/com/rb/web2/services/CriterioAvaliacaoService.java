package com.rb.web2.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rb.web2.domain.criterioAvaliacao.CriterioAvaliacao;
import com.rb.web2.domain.criterioAvaliacao.dto.RequestCriterioDTO;
import com.rb.web2.domain.inscricao.Inscricao;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
import com.rb.web2.repositories.CriterioAvaliacaoRepository;

@Service
public class CriterioAvaliacaoService {
    @Autowired
    private CriterioAvaliacaoRepository repository;

    @Autowired
    private ProcessoSeletivoService processoSeletivoService;

    @Autowired
    private InscricaoService inscricaoService;

    public CriterioAvaliacao create(RequestCriterioDTO dto) {
        CriterioAvaliacao criterioAvaliacao = new CriterioAvaliacao();
        criterioAvaliacao.setNome(dto.nome());
        criterioAvaliacao.setPeso(dto.peso());
        criterioAvaliacao.setProcessoSeletivo(processoSeletivoService.getProcessoSeletivoById(dto.processoSeletivoId()));
        return repository.save(criterioAvaliacao);
    }

    public Optional<CriterioAvaliacao> getCriterioById(String id) {
        return repository.findById(id);
    }

    public CriterioAvaliacao update(String id, RequestCriterioDTO dto) {
        CriterioAvaliacao criterioAvaliacao = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("CriterioAvaliacao not found"));

        criterioAvaliacao.setNome(dto.nome());
        criterioAvaliacao.setPeso(dto.peso());
        /* criterioAvaliacao.setProcessoSeletivo(processoSeletivoService.getProcessoSeletivoById(dto.processoSeletivoId())
                .orElseThrow(() -> new RuntimeException("Processo Seletivo não encontrado"))); */ // Pode não ser necessário
        return repository.save(criterioAvaliacao);
    }

    public List<CriterioAvaliacao> findAllByProcessoSeletivo(String processoSeletivoId) {
        ProcessoSeletivo processoSeletivo = processoSeletivoService.getProcessoSeletivoById(processoSeletivoId);
        return repository.findByProcessoSeletivo(processoSeletivo);
    }

    public List<CriterioAvaliacao> findAllByInscricao(String inscricaoId) {
        Inscricao inscricao = inscricaoService.getInscricaoById(inscricaoId);
              
        return repository.findByParticipantes(inscricao);
    }

    public void softDelete(String id) {
        CriterioAvaliacao criterioAvaliacao = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("CriterioAvaliacao not found"));
        criterioAvaliacao.setAtivo(false);
        repository.save(criterioAvaliacao);
    }

    public List<CriterioAvaliacao> buscarCriteriosPorIds(List<Long> avaliacoes) {
        return repository.findByIdIn(avaliacoes);
    }
}