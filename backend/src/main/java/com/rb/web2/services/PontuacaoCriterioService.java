package com.rb.web2.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.RoundingMode;

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
        if (dto.criterioId() == null) {
            throw new IllegalArgumentException("Criterio ID cannot be null");
        }

        PontuacaoCriterio pontuacaoCriterio = new PontuacaoCriterio();
        pontuacaoCriterio.setNota(dto.nota());
        pontuacaoCriterio.setCriterio(criterioAvaliacaoService.findById(dto.criterioId())
                .orElseThrow(() -> new RuntimeException("Criterio not found")));
        pontuacaoCriterio.setCandidateApplication(candidateApplicationService.getCandidateApplicationById(dto.candidateApplicationId())
                .orElseThrow(() -> new RuntimeException("Inscrição not found")));

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
                .orElseThrow(() -> new RuntimeException("Inscrição not found")));

        return pontuacaoCriterioRepository.save(pontuacaoCriterio);
    }

    public List<PontuacaoCriterio> findByCriterio(String criterioId) {
        CriterioAvaliacao criterio = criterioAvaliacaoService.findById(criterioId)
                .orElseThrow(() -> new RuntimeException("Critério de Avaliação não encontrado"));
        return pontuacaoCriterioRepository.findByCriterio(criterio);
    }

    public List<PontuacaoCriterio> findByInscricao(String inscricaoId) {
        CandidateApplication inscricao = candidateApplicationService.getCandidateApplicationById(inscricaoId)
                .orElseThrow(() -> new RuntimeException("Inscrição não encontrada"));
        return pontuacaoCriterioRepository.findByCandidateApplication(inscricao);
    }

    public BigDecimal calcularNotaTotal(String candidateApplicationId) {
        CandidateApplication candidateApplication = candidateApplicationService.getCandidateApplicationById(candidateApplicationId)
                .orElseThrow(() -> new RuntimeException("Inscrição não encontrada"));

        List<PontuacaoCriterio> pontuacoes = pontuacaoCriterioRepository.findByCandidateApplication(candidateApplication);

        BigDecimal somaNotasPonderadas = pontuacoes.stream()
            .map(pontuacao -> pontuacao.getNota().multiply(BigDecimal.valueOf(pontuacao.getCriterio().getPeso())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal somaPesos = pontuacoes.stream()
                .map(pontuacao -> BigDecimal.valueOf(pontuacao.getCriterio().getPeso()))  // Extrair os pesos
                .reduce(BigDecimal.ZERO, BigDecimal::add);  // Somar todos os pesos

        if (somaPesos.compareTo(BigDecimal.ZERO) > 0) {
            return somaNotasPonderadas.divide(somaPesos, 2, RoundingMode.HALF_UP);  // Média ponderada com 2 casas decimais
        } else {
            throw new RuntimeException("A soma dos pesos não pode ser zero");
        }
    }

    // @TODO: Implementar método deletePontuacaoCriterio
}
