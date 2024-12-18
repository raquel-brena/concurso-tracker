package com.rb.web2.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rb.web2.domain.criterioAvaliacao.CriterioAvaliacao;
import com.rb.web2.domain.criterioAvaliacao.dto.RequestCriterioDTO;
import com.rb.web2.repositories.CriterioAvaliacaoRepository;
import com.rb.web2.repositories.InscricaoRepository;
import com.rb.web2.shared.exceptions.NotFoundException;

@Service
public class CriterioAvaliacaoService {
    @Autowired
    private CriterioAvaliacaoRepository repository;

    @Autowired
    private ProcessoSeletivoService processoSeletivoService;

    @Autowired
    private InscricaoRepository inscricaoRepository;

    public CriterioAvaliacao create(RequestCriterioDTO dto) {
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
        CriterioAvaliacao criterioAvaliacao = this.getCriterioById(id);

        criterioAvaliacao.setNome(dto.nome());
        criterioAvaliacao.setPeso(dto.peso());
        /* criterioAvaliacao.setProcessoSeletivo(processoSeletivoService.getProcessoSeletivoById(dto.processoSeletivoId())
                .orElseThrow(() -> new RuntimeException("Processo Seletivo não encontrado"))); */ // Pode não ser necessário
        return repository.save(criterioAvaliacao);
    }

    // public List<CriterioAvaliacao> findAllByProcessoSeletivo(String processoSeletivoId) {
    //     ProcessoSeletivo processoSeletivo = processoSeletivoService.getProcessoSeletivoById(processoSeletivoId);
    //     return repository.findByProcessoSeletivo(processoSeletivo);
    // }

    // public List<CriterioAvaliacao> findAllByInscricao(String inscricaoId) {
    //     Inscricao inscricao = inscricaoRepository.findById(inscricaoId)
    //             .orElseThrow(() -> new RuntimeException("Inscricao not found"));
              
    //     return repository.findByParticipantes(inscricao);
    // }

    public void softDelete(Long id) {
        CriterioAvaliacao criterioAvaliacao = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("CriterioAvaliacao not found"));
        criterioAvaliacao.setAtivo(false);
        repository.save(criterioAvaliacao);
    }

    public List<CriterioAvaliacao> buscarCriteriosPorIds(List<Long> avaliacoes) {
        return repository.findByIdIn(avaliacoes);
    }

    public boolean existsByCriterioId(Long criterioId) {
        return repository.existsById(criterioId);
    }
}