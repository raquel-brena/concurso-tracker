package com.rb.web2.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rb.web2.domain.pontuacaoCriterio.PontuacaoCriterio;
import com.rb.web2.domain.pontuacaoCriterio.dto.RequestPontuacaoDTO;
import com.rb.web2.repositories.PontuacaoCriterioRepository;
import com.rb.web2.domain.candidateApplication.CandidateApplication;
import com.rb.web2.domain.criterioAvaliacao.CriterioAvaliacao;

@Service
public class PontuacaoCriterioService {

    @Autowired
    private PontuacaoCriterioRepository pontuacaoCriterioRepository;

    @Autowired
    private CriterioAvaliacaoService criterioAvaliacaoService;

    @Autowired
    private CandidateApplicationService candidateApplicationService;

    public PontuacaoCriterio create(RequestPontuacaoDTO dto) {
        PontuacaoCriterio pontuacaoCriterio = new PontuacaoCriterio();
        pontuacaoCriterio.setNota(dto.nota());
        pontuacaoCriterio.setCriterio(criterioAvaliacaoService.findById(dto.criterioId())
                .orElseThrow(() -> new RuntimeException("Criterio not found")));
        pontuacaoCriterio.setCandidateApplication(candidateApplicationService.getCandidateApplicationById(dto.candidateApplicationId())
                .orElseThrow(() -> new RuntimeException("Candidate Application not found")));
        pontuacaoCriterio.setAtivo(dto.ativo());

        return pontuacaoCriterioRepository.save(pontuacaoCriterio);
    }

    public Optional<PontuacaoCriterio> getPontuacaoCriterioById(String id) {
        return pontuacaoCriterioRepository.findById(id);
    }

    public PontuacaoCriterio update(String id, RequestPontuacaoDTO dto) {
        PontuacaoCriterio pontuacaoCriterio = pontuacaoCriterioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PontuacaoCriterio not found"));

        pontuacaoCriterio.setNota(dto.nota());
        pontuacaoCriterio.setCriterio(criterioAvaliacaoService.findById(dto.criterioId())
                .orElseThrow(() -> new RuntimeException("Criterio not found")));
        pontuacaoCriterio.setCandidateApplication(candidateApplicationService.getCandidateApplicationById(dto.candidateApplicationId())
                .orElseThrow(() -> new RuntimeException("Candidate Application not found")));
        pontuacaoCriterio.setAtivo(dto.ativo());

        return pontuacaoCriterioRepository.save(pontuacaoCriterio);
    }

    public List<PontuacaoCriterio> findByCriterio(String criterioId) {
        CriterioAvaliacao criterio = criterioAvaliacaoService.findById(criterioId)
                .orElseThrow(() -> new RuntimeException("Critério de Avaliação não encontrado"));
        return pontuacaoCriterioRepository.findByCriterio(criterio);
    }

    public List<PontuacaoCriterio> findByIncricao(String inscricaoId) {
        CandidateApplication inscricao = candidateApplicationService.getCandidateApplicationById(inscricaoId)
                .orElseThrow(() -> new RuntimeException("Inscrição não encontrada"));
        return pontuacaoCriterioRepository.findByCandidateApplication(inscricao);
    }

    public BigDecimal calcularNotaTotal(String candidateApplicationId) {
        CandidateApplication candidateApplication = candidateApplicationService.getCandidateApplicationById(candidateApplicationId)
                .orElseThrow(() -> new RuntimeException("Inscrição não encontrada"));

        return pontuacaoCriterioRepository.findByCandidateApplication(candidateApplication)
                .stream()
                .map(PontuacaoCriterio::getNota)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // @TODO: Implementar método deletePontuacaoCriterio
}
