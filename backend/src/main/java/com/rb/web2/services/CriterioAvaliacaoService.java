package com.rb.web2.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.rb.web2.domain.criterioAvaliacao.CriterioAvaliacao;
import com.rb.web2.domain.criterioAvaliacao.dto.RequestCriterioDTO;
import com.rb.web2.domain.criterioAvaliacao.dto.ResponseCriterioDTO;
import com.rb.web2.domain.user.User;
import com.rb.web2.repositories.CriterioAvaliacaoRepository;
import com.rb.web2.shared.exceptions.NotFoundException;

@Service
public class CriterioAvaliacaoService {
    @Autowired
    private CriterioAvaliacaoRepository repository;

    @Autowired
    private UserService userService;

    private void verificarPermissaoDeCriacaoOuAlteracao() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = (User) userService.loadUserByUsername(login);

        if (!user.hasPermissionToCreateCriterios()) {
            throw new AccessDeniedException("Usuário não tem permissão para criar ou alterar critérios.");
        }
    }

    public CriterioAvaliacao create(RequestCriterioDTO dto) {
        verificarPermissaoDeCriacaoOuAlteracao();

        CriterioAvaliacao criterioAvaliacao = new CriterioAvaliacao();
        criterioAvaliacao.setNome(dto.nome());
        criterioAvaliacao.setPeso(dto.peso());
        // criterioAvaliacao.setProcessoSeletivo(processoSeletivoService.getProcessoSeletivoById(dto.processoSeletivoId()));
        return repository.save(criterioAvaliacao);
    }

    public CriterioAvaliacao getCriterioById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(""));
    }

    public CriterioAvaliacao update(Long id, RequestCriterioDTO dto) {
        verificarPermissaoDeCriacaoOuAlteracao();
        CriterioAvaliacao criterioAvaliacao = this.getCriterioById(id);

        criterioAvaliacao.setNome(dto.nome());
        criterioAvaliacao.setPeso(dto.peso());

        return repository.save(criterioAvaliacao);
    }

    public List<ResponseCriterioDTO> findAllByProcessoSeletivo(String processoSeletivoId) {
        List<CriterioAvaliacao> criterios = repository.findByEtapaProcessoSeletivoId(processoSeletivoId)
                .orElseThrow(() -> new NotFoundException("Criterios de Avaliação não encontrados"));

        if (criterios.isEmpty()) {
            throw new NotFoundException("Criterios de Avaliação não encontrados");
        }

        return criterios.stream()
                .map(ResponseCriterioDTO::from)
                .collect(Collectors.toList());
    }

    public List<ResponseCriterioDTO> findAllByInscricao(String inscricaoId) {
        return repository.findByPontuacoesInscricaoId(inscricaoId);
    }

    // public List<CriterioAvaliacao> findAllByInscricao(String inscricaoId) {
    // Inscricao inscricao = inscricaoRepository.findById(inscricaoId)
    // .orElseThrow(() -> new RuntimeException("Inscricao not found"));

    // return repository.findByParticipantes(inscricao);
    // }

    public void softDelete(Long id) {
        verificarPermissaoDeCriacaoOuAlteracao();
        CriterioAvaliacao criterioAvaliacao = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("CriterioAvaliacao not found"));
        criterioAvaliacao.setAtivo(false);
        repository.save(criterioAvaliacao);
    }

    public List<ResponseCriterioDTO> buscarCriteriosPorIds(List<Long> avaliacoes) {
        return repository.findAllById(avaliacoes).stream()
                .map(ResponseCriterioDTO::from)
                .collect(Collectors.toList());
    }

    public boolean existsByCriterioId(Long criterioId) {
        return repository.existsById(criterioId);
    }
}