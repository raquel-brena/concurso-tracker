package com.rb.web2.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rb.web2.domain.agenda.Agenda;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
import com.rb.web2.domain.vaga.Vaga;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, Long> {
    // List<Vaga> findByProcessoSeletivo(ProcessoSeletivo processoSeletivo);
    Optional<Vaga> findById(Long id);
    List<Agenda> findAllByAtivoTrue();
    List<Vaga> findByProcessoSeletivo(ProcessoSeletivo processoSeletivo);
}
