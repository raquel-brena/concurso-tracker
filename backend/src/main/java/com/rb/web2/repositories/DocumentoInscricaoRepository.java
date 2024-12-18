package com.rb.web2.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rb.web2.domain.documentoInscricao.DocumentoInscricao;

@Repository
public interface DocumentoInscricaoRepository extends JpaRepository<DocumentoInscricao, Long> {
}
