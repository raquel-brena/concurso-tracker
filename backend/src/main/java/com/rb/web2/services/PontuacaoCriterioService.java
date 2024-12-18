package com.rb.web2.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rb.web2.domain.inscricao.Inscricao;
import com.rb.web2.domain.pontuacaoCriterio.PontuacaoCriterio;
import com.rb.web2.domain.pontuacaoCriterio.dto.RequestPontuacaoDTO;
import com.rb.web2.domain.pontuacaoCriterio.dto.ResponsePontuacaoDTO;
import com.rb.web2.repositories.PontuacaoCriterioRepository;
import com.rb.web2.shared.exceptions.NotFoundException;

@Service
public class PontuacaoCriterioService {

    @Autowired
    private PontuacaoCriterioRepository pontuacaoCriterioRepository;

    @Autowired
    private CriterioAvaliacaoService criterioAvaliacaoService;

    @Autowired
    private InscricaoService inscricaoService;

    public ResponsePontuacaoDTO create(RequestPontuacaoDTO dto) {
        if (dto.criterioId() == null) {
            throw new IllegalArgumentException("Criterio ID cannot be null");
        }

        PontuacaoCriterio pontuacaoCriterio = new PontuacaoCriterio();
        pontuacaoCriterio.setNota(dto.nota());
        pontuacaoCriterio.setCriterio(criterioAvaliacaoService.getCriterioById(dto.criterioId()));
        pontuacaoCriterio.setInscricao(inscricaoService.buscarInscricaoPorId(dto.inscricaoId()));

        PontuacaoCriterio savedEntity = pontuacaoCriterioRepository.save(pontuacaoCriterio);
        return new ResponsePontuacaoDTO(savedEntity);
    }

    public Optional<ResponsePontuacaoDTO> getPontuacaoCriterioById(Long id) {
        return pontuacaoCriterioRepository.findById(id)
                .map(ResponsePontuacaoDTO::new);
    }

    public ResponsePontuacaoDTO update(Long id, RequestPontuacaoDTO dto) {
        PontuacaoCriterio pontuacaoCriterio = pontuacaoCriterioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pontuação Critério não encontrada"));

        pontuacaoCriterio.setNota(dto.nota());
        pontuacaoCriterio.setCriterio(criterioAvaliacaoService.getCriterioById(dto.criterioId()));
        pontuacaoCriterio.setInscricao(inscricaoService.buscarInscricaoPorId(dto.inscricaoId()));

        PontuacaoCriterio savedEntity = pontuacaoCriterioRepository.save(pontuacaoCriterio);
        return new ResponsePontuacaoDTO(savedEntity);
    }

    public List<ResponsePontuacaoDTO> findByCriterio(Long criterioId) {
        boolean exists = criterioAvaliacaoService.existsByCriterioId(criterioId);
        if (!exists) {
            throw new NotFoundException("Critério de Avaliação não encontrado");
        }

        return pontuacaoCriterioRepository.findByCriterioId(criterioId).stream()
                .map(ResponsePontuacaoDTO::new)
                .toList();
    }

    public List<ResponsePontuacaoDTO> findByInscricao(String inscricaoId) {
        boolean exists = inscricaoService.existsByInscricaoId(inscricaoId);
        if (!exists) {
            throw new NotFoundException("Inscrição não encontrada");
        }

        return pontuacaoCriterioRepository.findByInscricaoId(inscricaoId).stream()
                .map(ResponsePontuacaoDTO::new)
                .toList();
    }

    public BigDecimal calcularNotaTotal(String inscricaoId) {
        List<PontuacaoCriterio> pontuacoes = pontuacaoCriterioRepository.findByInscricaoId(inscricaoId);

        BigDecimal somaNotasPonderadas = pontuacoes.stream()
                .map(pontuacao -> pontuacao.getNota().multiply(BigDecimal.valueOf(pontuacao.getCriterio().getPeso())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal somaPesos = pontuacoes.stream()
                .map(pontuacao -> BigDecimal.valueOf(pontuacao.getCriterio().getPeso()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (somaPesos.compareTo(BigDecimal.ZERO) > 0) {
            return somaNotasPonderadas.divide(somaPesos, 2, RoundingMode.HALF_UP);
        } else {
            throw new RuntimeException("A soma dos pesos não pode ser zero");
        }
    }

    public void delete(Long id) {
        PontuacaoCriterio pontuacaoCriterio = pontuacaoCriterioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pontuação Critério não encontrada"));
        pontuacaoCriterioRepository.delete(pontuacaoCriterio);
    }

    public Map<String, BigDecimal> calcularNotaTotalPorInscricaoDoProcesso(String processoId) {
        List<Inscricao> inscricoes = inscricaoService.findByProcesso(processoId);
        List<Inscricao> inscricoesComPontuacoes = inscricoes.stream()
                .filter(inscricao -> !pontuacaoCriterioRepository.findByInscricaoId(inscricao.getId()).isEmpty())
                .toList();

        if (inscricoesComPontuacoes.isEmpty()) {
            throw new RuntimeException("Nenhuma inscrição válida encontrada para o processo.");
        }

        return inscricoesComPontuacoes.stream()
                .collect(Collectors.toMap(
                        Inscricao::getId,
                        inscricao -> calcularNotaTotal(inscricao.getId())));
    }

}
