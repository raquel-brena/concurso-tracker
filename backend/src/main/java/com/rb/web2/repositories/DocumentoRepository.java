package com.rb.web2.repositories;

import com.rb.web2.domain.documento.Documento;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    Optional<Documento> findById(Long id);
    
}
