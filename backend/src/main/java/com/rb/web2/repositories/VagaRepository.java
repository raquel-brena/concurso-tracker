package com.rb.web2.repositories;

import com.rb.web2.domain.agenda.Agenda;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
import com.rb.web2.domain.vaga.Vaga;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface VagaRepository extends JpaRepository<Vaga, Long> {
    List<Agenda> findAllByAtivoTrue();
    List<Vaga> findByProcessoSeletivo(ProcessoSeletivo processoSeletivo);
}
