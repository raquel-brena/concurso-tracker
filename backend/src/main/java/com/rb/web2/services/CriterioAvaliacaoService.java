package com.rb.web2.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.rb.web2.repositories.CriterioAvaliacaoRepository;
import com.rb.web2.domain.criterioAvaliacao.CriterioAvaliacao;
import com.rb.web2.domain.criterioAvaliacao.dto.RequestCriterioDTO;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
import com.rb.web2.domain.candidateApplication.CandidateApplication;

import java.util.List;
import java.util.Optional;

@Service
public class CriterioAvaliacaoService {
    @Autowired
    private CriterioAvaliacaoRepository repository;

    @Autowired
    private ProcessoSeletivoService processoSeletivoService;

    @Autowired
    private CandidateApplicationService candidateApplicationService;

    public CriterioAvaliacao create(RequestCriterioDTO dto) {
        CriterioAvaliacao criterioAvaliacao = new CriterioAvaliacao();
        criterioAvaliacao.setNome(dto.nome());
        criterioAvaliacao.setPeso(dto.peso());
        criterioAvaliacao.setProcessoSeletivo(processoSeletivoService.getProcessoSeletivoById(dto.processoSeletivoId())
                .orElseThrow(() -> new RuntimeException("Processo Seletivo não encontrado")));
        return repository.save(criterioAvaliacao);
    }

    public Optional<CriterioAvaliacao> findById(String id) {
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
        ProcessoSeletivo processoSeletivo = processoSeletivoService.getProcessoSeletivoById(processoSeletivoId)
                .orElseThrow(() -> new RuntimeException("Processo Seletivo não encontrado"));
        return repository.findByProcessoSeletivo(processoSeletivo);
    }

    public List<CriterioAvaliacao> findAllByCandidateApplication(String candidateApplicationId) {
        CandidateApplication candidateApplication = candidateApplicationService.getCandidateApplicationById(candidateApplicationId)
                .orElseThrow(() -> new RuntimeException("Inscrição não encontrada"));
        return repository.findByParticipantes(candidateApplication);
    }
}