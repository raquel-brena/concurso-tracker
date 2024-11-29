package com.rb.web2.repositories;

import com.rb.web2.domain.formacao.Formacao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormacaoRepository extends JpaRepository<Formacao, Long> {
    Optional<Formacao> findById(Long id);
}
