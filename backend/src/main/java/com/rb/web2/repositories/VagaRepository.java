package com.rb.web2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
import com.rb.web2.domain.vaga.Vaga;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, Long> {
    List<Vaga> findAllByAtivoTrue();
    List<Vaga> findByProcessoSeletivoAndAtivoTrue(ProcessoSeletivo processoSeletivo);
    List<Vaga> findByCargoNome(String cargoNome);
}
