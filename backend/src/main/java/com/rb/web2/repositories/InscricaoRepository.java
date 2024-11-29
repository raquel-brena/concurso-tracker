package com.rb.web2.repositories;

import com.rb.web2.domain.inscricao.Inscricao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao, String> {
    
}