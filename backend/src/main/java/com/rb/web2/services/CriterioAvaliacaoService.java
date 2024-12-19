package com.rb.web2.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.rb.web2.domain.criterioAvaliacao.CriterioAvaliacao;
import com.rb.web2.domain.criterioAvaliacao.dto.CriterioRequestDTO;
import com.rb.web2.domain.criterioAvaliacao.dto.CriterioResponseDTO;
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
    }

    public CriterioResponseDTO create(CriterioRequestDTO dto) {
        verificarPermissaoDeCriacaoOuAlteracao();

        CriterioAvaliacao criterioAvaliacao = new CriterioAvaliacao();
        criterioAvaliacao.setNome(dto.nome());
        criterioAvaliacao.setPeso(dto.peso());
        // criterioAvaliacao.setProcessoSeletivo(processoSeletivoService.getProcessoSeletivoById(dto.processoSeletivoId()));
        repository.save(criterioAvaliacao);
        return CriterioResponseDTO.from(criterioAvaliacao);
    }

    public CriterioResponseDTO getById(Long id) {
        CriterioAvaliacao criterio = repository.findById(id).orElseThrow(() -> new NotFoundException(""));
        return CriterioResponseDTO.from(criterio);
    }

    public CriterioAvaliacao getCriterioById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("CriterioAvaliacao not found"));
    }

    public CriterioResponseDTO update(Long id, CriterioRequestDTO dto) {
        verificarPermissaoDeCriacaoOuAlteracao();
        this.repository.existsById(id);

        CriterioAvaliacao criterioAvaliacao = this.repository.findById(id)
                .orElseThrow(() -> new NotFoundException("CriterioAvaliacao not found"));

        criterioAvaliacao.setNome(dto.nome());
        criterioAvaliacao.setPeso(dto.peso());

        criterioAvaliacao = repository.save(criterioAvaliacao);
        return CriterioResponseDTO.from(criterioAvaliacao);
    }

    public List<CriterioResponseDTO> findAllByProcessoSeletivo(String processoSeletivoId) {
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

    public List<CriterioResponseDTO> buscarCriteriosPorIds(List<Long> avaliacoes) {
        return repository.findAllById(avaliacoes).stream()
                .map(CriterioResponseDTO::from)
                .collect(Collectors.toList());
    }

    public boolean existsByCriterioId(Long criterioId) {
        return repository.existsById(criterioId);
    }
}