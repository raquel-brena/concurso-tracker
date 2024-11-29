package com.rb.web2.services;

import com.rb.web2.repositories.FormacaoRepository;
import com.rb.web2.domain.formacao.Formacao;
import com.rb.web2.domain.formacao.dto.FormacaoRequestDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormacaoService {

    @Autowired
    private FormacaoRepository formacaoRepository;

    public Formacao salvar(FormacaoRequestDTO dto) {
        Formacao formacao = new Formacao(
            null,                          // O id geralmente é null ao criar um novo registro
            dto.nome(),                    // Nome da formação do DTO
            dto.descricao(),               // Descrição da formação do DTO
            dto.ativo(),
            null,                          // As datas de criação e atualização podem ser definidas automaticamente pelo JPA
            null
        );

        return formacaoRepository.save(formacao);
    }

    public List<Formacao> listarTodas() {
        return formacaoRepository.findAll();
    }

    public Optional<Formacao> buscarPorId(Long id) {
        return formacaoRepository.findById(id);
    }

    public Formacao atualizar(Long id, FormacaoRequestDTO formacaoAtualizada) {
        // Verifica se a formação existe
        Formacao formacaoExistente = formacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Formação não encontrada."));
    
        formacaoExistente.setNome(formacaoAtualizada.nome());
        formacaoExistente.setDescricao(formacaoAtualizada.descricao());

        return formacaoRepository.save(formacaoExistente);
    }

    // @TODO: Método para excluir uma formação
}
