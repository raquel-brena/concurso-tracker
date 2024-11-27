
package com.rb.web2.repositories;

import com.rb.web2.domain.vaga.Vaga;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, Long> {
    List<Vaga> findByProcessoSeletivo(ProcessoSeletivo processoSeletivo);
}
