package com.rb.web2.repositories;

import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessoSeletivoRepository extends JpaRepository<ProcessoSeletivo, String> {
    Optional<ProcessoSeletivo> findById(String id);
    Optional<ProcessoSeletivo> findByTitulo(String titulo);
    
}
