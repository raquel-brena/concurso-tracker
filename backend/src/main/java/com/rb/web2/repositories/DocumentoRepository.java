package com.rb.web2.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rb.web2.domain.documento.Documento;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    Optional<List<Documento>> findByUsuarioIsNotNull();
    Optional<List<Documento>> findByProcessoSeletivoIsNotNull();
    Optional<Documento> findByDownloadUrl(String downloadUrl);
}
