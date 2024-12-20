package com.rb.web2.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rb.web2.domain.criterioAvaliacao.CriterioAvaliacao;
import com.rb.web2.domain.criterioAvaliacao.dto.CriterioRequestDTO;
import com.rb.web2.domain.criterioAvaliacao.dto.CriterioResponseDTO;
import com.rb.web2.domain.inscricao.Inscricao;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
import com.rb.web2.infra.util.AuthorizationUtil;
import com.rb.web2.repositories.CriterioAvaliacaoRepository;
import com.rb.web2.repositories.InscricaoRepository;
import com.rb.web2.repositories.ProcessoSeletivoRepository;
import com.rb.web2.shared.exceptions.NotFoundException;

import jakarta.annotation.PostConstruct;

@Service
public class CriterioAvaliacaoService {
    @Autowired
    private CriterioAvaliacaoRepository repository;

    @Autowired
    private ProcessoSeletivoRepository processoRepository;

    @Autowired
    private InscricaoRepository inscricaoRepository;

    @Autowired
    private UserService userService;

    private AuthorizationUtil authorizationUtil;

    @PostConstruct
    public void init() {
        this.authorizationUtil = new AuthorizationUtil(userService);
    }

    private void verificarPermissaoDeCriacaoOuAlteracao(Long criterioId) {
        authorizationUtil.<Long>verificarPermissaoOuComissao(
                criterioId,
                "EDIT_CRITERIOS",
                id -> repository.findById(id)
                            .orElseThrow(() -> new NotFoundException("Critério de Avaliação não encontrado.")),
                (entity, user) -> {
                    CriterioAvaliacao criterio = (CriterioAvaliacao) entity;
                    return criterio.getEtapa().getProcessoSeletivo().getComissaoOrganizadora().contains(user);
                });
    }

    private void verificarPermissaoDeLeitura(Long criterioId) {
        authorizationUtil.<Long>verificarPermissaoOuComissao(
                criterioId,
                "VIEW_CRITERIOS",
                id -> repository.findById(id)
                            .orElseThrow(() -> new NotFoundException("Critério de Avaliação não encontrado.")),
                (entity, user) -> {
                    CriterioAvaliacao criterio = (CriterioAvaliacao) entity;
                    var isComissao = criterio.getEtapa().getProcessoSeletivo().getComissaoOrganizadora().contains(user);
                    var isParticipante = criterio.getEtapa().getProcessoSeletivo().getVagas().stream()
                            .anyMatch(vaga -> vaga.getInscricoes().stream()
                                    .anyMatch(inscricao -> inscricao.getCandidato().equals(user)));

                    return isComissao || isParticipante;
                });
    }

    private void verificarPermissaoDeLeituraPorProcesso(String processoId) {
        authorizationUtil.<String>verificarPermissaoOuComissao(
                processoId,
                "VIEW_CRITERIOS",
                id -> processoRepository.findById(id)
                            .orElseThrow(() -> new NotFoundException("Critério de Avaliação não encontrado.")),
                (entity, user) -> {
                    ProcessoSeletivo processo = (ProcessoSeletivo) entity;
                    var isComissao = processo.getComissaoOrganizadora().contains(user);
                    var isParticipante = processo.getVagas().stream()
                            .anyMatch(vaga -> vaga.getInscricoes().stream()
                                    .anyMatch(inscricao -> inscricao.getCandidato().equals(user)));

                    return isComissao || isParticipante;
                });
    }

    private void verificarPermissaoDeLeituraPorParticipante(String inscricaoId) {
        authorizationUtil.<String>verificarPermissaoOuComissao(
                inscricaoId,
                "VIEW_CRITERIOS",
                id -> inscricaoRepository.findById(id)
                            .orElseThrow(() -> new NotFoundException("Critério de Avaliação não encontrado.")),
                (entity, user) -> {
                    Inscricao inscricao = (Inscricao) entity;
                    var isParticipante = inscricao.getVaga().getProcessoSeletivo().getComissaoOrganizadora().contains(user);
                    var isComissao = inscricao.getCandidato().equals(user);

                    return isParticipante || isComissao;
                });
    }

    public CriterioResponseDTO create(CriterioRequestDTO dto) {
        verificarPermissaoDeCriacaoOuAlteracao(null);

        CriterioAvaliacao criterioAvaliacao = new CriterioAvaliacao();
        criterioAvaliacao.setNome(dto.nome());
        criterioAvaliacao.setPeso(dto.peso());
        // criterioAvaliacao.setProcessoSeletivo(processoSeletivoService.getProcessoSeletivoById(dto.processoSeletivoId()));
        repository.save(criterioAvaliacao);
        return CriterioResponseDTO.from(criterioAvaliacao);
    }

    public CriterioResponseDTO getById(Long id) {
        verificarPermissaoDeLeitura(id);

        CriterioAvaliacao criterio = repository.findById(id).orElseThrow(() -> new NotFoundException(""));
        return CriterioResponseDTO.from(criterio);
    }

    public CriterioAvaliacao getCriterioById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("CriterioAvaliacao not found"));
    }

    public CriterioResponseDTO update(Long id, CriterioRequestDTO dto) {
        verificarPermissaoDeCriacaoOuAlteracao(id);
        this.repository.existsById(id);

        CriterioAvaliacao criterioAvaliacao = this.repository.findById(id)
                .orElseThrow(() -> new NotFoundException("CriterioAvaliacao not found"));

        criterioAvaliacao.setNome(dto.nome());
        criterioAvaliacao.setPeso(dto.peso());

        criterioAvaliacao = repository.save(criterioAvaliacao);
        return CriterioResponseDTO.from(criterioAvaliacao);
    }

    public List<CriterioResponseDTO> findAllByProcessoSeletivo(String processoSeletivoId) {
        this.verificarPermissaoDeLeituraPorProcesso(processoSeletivoId);

        List<CriterioAvaliacao> criterios = repository.findByEtapaProcessoSeletivoId(processoSeletivoId)
                .orElseThrow(() -> new NotFoundException("Criterios de Avaliação não encontrados"));

        if (criterios.isEmpty()) {
            throw new NotFoundException("Criterios de Avaliação não encontrados");
        }

        return criterios.stream()
                .map(CriterioResponseDTO::from)
                .collect(Collectors.toList());
    }

    public List<CriterioResponseDTO> findAllByInscricao(String inscricaoId) {
        verificarPermissaoDeLeituraPorParticipante(inscricaoId);

        return repository.findByPontuacoesInscricaoId(inscricaoId);
    }

    // public List<CriterioAvaliacao> findAllByInscricao(String inscricaoId) {
    // Inscricao inscricao = inscricaoRepository.findById(inscricaoId)
    // .orElseThrow(() -> new RuntimeException("Inscricao not found"));

    // return repository.findByParticipantes(inscricao);
    // }

    public void softDelete(Long id) {
        verificarPermissaoDeCriacaoOuAlteracao(id);
        CriterioAvaliacao criterioAvaliacao = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("CriterioAvaliacao not found"));
        criterioAvaliacao.setAtivo(false);
        repository.save(criterioAvaliacao);
    }

    public List<CriterioResponseDTO> buscarCriteriosPorIds(List<Long> criteriosIds) {
        return repository.findAllByIdIn(criteriosIds).stream()
                .map(CriterioResponseDTO::from)
                .collect(Collectors.toList());  
    }

    public boolean existsByCriterioId(Long criterioId) {
        return repository.existsById(criterioId);
    }
}