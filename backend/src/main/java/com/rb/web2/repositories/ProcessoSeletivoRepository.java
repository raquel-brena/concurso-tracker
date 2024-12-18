package com.rb.web2.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;

@Repository
public interface ProcessoSeletivoRepository extends JpaRepository<ProcessoSeletivo, String> {
    Optional<ProcessoSeletivo> findByTitulo(String titulo);
    List<ProcessoSeletivo> findByTituloContainingIgnoreCaseOrDescricaoContainingOrderByAgendaInicioInscricaoDesc(String termo, String descricao);
    Optional<ProcessoSeletivo> findByVagasInscricoesCandidatoId(String id);
    Optional<ProcessoSeletivo> findByComissaoOrganizadoraId(String id);
}
