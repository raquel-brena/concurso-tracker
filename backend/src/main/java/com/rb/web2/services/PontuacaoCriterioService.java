package com.rb.web2.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rb.web2.domain.criterioAvaliacao.CriterioAvaliacao;
import com.rb.web2.domain.inscricao.Inscricao;
import com.rb.web2.domain.pontuacaoCriterio.PontuacaoCriterio;
import com.rb.web2.domain.pontuacaoCriterio.dto.PontuacaoRequestDTO;
import com.rb.web2.domain.pontuacaoCriterio.dto.PontuacaoResponseDTO;
import com.rb.web2.infra.util.AuthorizationUtil;
import com.rb.web2.repositories.CriterioAvaliacaoRepository;
import com.rb.web2.repositories.InscricaoRepository;
import com.rb.web2.repositories.PontuacaoCriterioRepository;
import com.rb.web2.shared.exceptions.NotFoundException;

import jakarta.annotation.PostConstruct;

@Service
public class PontuacaoCriterioService {

    @Autowired
    private PontuacaoCriterioRepository pontuacaoCriterioRepository;

    @Autowired
    private CriterioAvaliacaoService criterioAvaliacaoService;

    @Autowired
    private CriterioAvaliacaoRepository criterioAvaliacaoRepository;

    @Autowired
    private InscricaoRepository inscricaoRepository;

    @Autowired
    private InscricaoService inscricaoService;

    @Autowired
    private UserService userService;

    private AuthorizationUtil authorizationUtil;

    @PostConstruct
    public void init() {
        this.authorizationUtil = new AuthorizationUtil(userService);
    }

    private void verificarPermissaoDeCriacaoOuAlteracao(Long pontuacaoId) {
        authorizationUtil.<Long>verificarPermissaoOuComissao(
                pontuacaoId,
                "EDIT_PONTUACOES",
                id -> pontuacaoCriterioRepository.findById(id)
                            .orElseThrow(() -> new NotFoundException("Pontuação não encontrada.")),
                (entity, user) -> {
                    PontuacaoCriterio pontuacao = (PontuacaoCriterio) entity;
                    return pontuacao.getCriterio().getEtapa().getProcessoSeletivo().getComissaoOrganizadora()
                            .contains(user);
                });
    }

    private void verificarPermissaoDeLeituraPorPontuacao(Long pontuacaoId) {
        authorizationUtil.<Long>verificarPermissaoOuComissao(
                pontuacaoId,
                "VIEW_PONTUACOES",
                id -> pontuacaoCriterioRepository.findById(id)
                            .orElseThrow(() -> new NotFoundException("Pontuação não encontrada.")),
                (entity, user) -> {
                    PontuacaoCriterio pontuacao = (PontuacaoCriterio) entity;
                    var isParticipante = pontuacao.getInscricao().getCandidato().equals(user);
                    var isComissao = pontuacao.getCriterio().getEtapa().getProcessoSeletivo().getComissaoOrganizadora()
                            .contains(user);

                    return isParticipante || isComissao;
                });
    }

    private void verificarPermissaoDeLeituraPorInscricao(String criterioId) {
        authorizationUtil.<String>verificarPermissaoOuComissao(
                criterioId,
                "VIEW_PONTUACOES",
                id -> inscricaoRepository.findById(id)
                            .orElseThrow(() -> new NotFoundException("Pontuação não encontrada.")),
                (entity, user) -> {
                    Inscricao inscricao = (Inscricao) entity;
                    var isParticipante = inscricao.getCandidato().equals(user);

                    return isParticipante;
                });
    }

    private void verificarPermissaoDeLeituraPorCriterio(Long criterioId) {
        authorizationUtil.<Long>verificarPermissaoOuComissao(
                criterioId,
                "VIEW_PONTUACOES",
                id -> criterioAvaliacaoRepository.findById(id)
                            .orElseThrow(() -> new NotFoundException("Pontuação não encontrada.")),
                (entity, user) -> {
                    CriterioAvaliacao criterio = (CriterioAvaliacao) entity;
                
                var isParticipante = criterio.getPontuacoes().stream()
                        .anyMatch(pontuacao -> pontuacao.getInscricao().getCandidato().equals(user));
                
                var isComissao = criterio.getEtapa().getProcessoSeletivo().getComissaoOrganizadora()
                        .contains(user);

                return isParticipante || isComissao;
                });
    }

    public PontuacaoResponseDTO create(PontuacaoRequestDTO dto) {
        verificarPermissaoDeCriacaoOuAlteracao(null);
        if (dto.criterioId() == null) {
            throw new IllegalArgumentException("Criterio ID cannot be null");
        }

        PontuacaoCriterio pontuacaoCriterio = new PontuacaoCriterio();
        pontuacaoCriterio.setNota(dto.nota());
        pontuacaoCriterio.setCriterio(criterioAvaliacaoService.getCriterioById(dto.criterioId()));
        pontuacaoCriterio.setInscricao(inscricaoService.buscarInscricaoPorId(dto.inscricaoId()));

        PontuacaoCriterio savedEntity = pontuacaoCriterioRepository.save(pontuacaoCriterio);
        return new PontuacaoResponseDTO(savedEntity);
    }

    public Optional<PontuacaoResponseDTO> getPontuacaoCriterioById(Long id) {
        verificarPermissaoDeLeituraPorPontuacao(id);

        return pontuacaoCriterioRepository.findById(id)
                .map(PontuacaoResponseDTO::new);
    }

    public PontuacaoResponseDTO update(Long id, PontuacaoRequestDTO dto) {
        verificarPermissaoDeCriacaoOuAlteracao(id);

        PontuacaoCriterio pontuacaoCriterio = pontuacaoCriterioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pontuação Critério não encontrada"));

        pontuacaoCriterio.setNota(dto.nota());
        pontuacaoCriterio.setCriterio(criterioAvaliacaoService.getCriterioById(dto.criterioId()));
        pontuacaoCriterio.setInscricao(inscricaoService.buscarInscricaoPorId(dto.inscricaoId()));

        PontuacaoCriterio savedEntity = pontuacaoCriterioRepository.save(pontuacaoCriterio);
        return new PontuacaoResponseDTO(savedEntity);
    }

    public List<PontuacaoResponseDTO> findByCriterio(Long criterioId) {
        verificarPermissaoDeLeituraPorCriterio(criterioId);
        boolean exists = criterioAvaliacaoService.existsByCriterioId(criterioId);
        if (!exists) {
            throw new NotFoundException("Critério de Avaliação não encontrado");
        }

        return pontuacaoCriterioRepository.findByCriterioId(criterioId).stream()
                .map(PontuacaoResponseDTO::new)
                .toList();
    }

    public List<PontuacaoResponseDTO> findByInscricao(String inscricaoId) {
        verificarPermissaoDeLeituraPorInscricao(inscricaoId);
        boolean exists = inscricaoService.existsByInscricaoId(inscricaoId);
        if (!exists) {
            throw new NotFoundException("Inscrição não encontrada");
        }

        return pontuacaoCriterioRepository.findByInscricaoId(inscricaoId).stream()
                .map(PontuacaoResponseDTO::new)
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
        verificarPermissaoDeCriacaoOuAlteracao(id);

        PontuacaoCriterio pontuacaoCriterio = pontuacaoCriterioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pontuação Critério não encontrada"));
        pontuacaoCriterioRepository.delete(pontuacaoCriterio);
    }

    public Map<String, BigDecimal> calcularNotaTotalPorInscricaoDoProcesso(String processoId) {
        List<Inscricao> inscricoes = inscricaoService.findInscricaoByProcesso(processoId);
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
