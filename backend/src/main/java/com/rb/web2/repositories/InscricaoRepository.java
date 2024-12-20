package com.rb.web2.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rb.web2.domain.inscricao.Inscricao;
import com.rb.web2.domain.inscricao.dto.InscricaoResponseDTO;

@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao, String> {
    List<Inscricao> findAllByDeletadoEmNull();
    List<Inscricao> findAllByCandidatoId(String candidatoId);
    List<Inscricao> findAllByVagaId(Long vagaId);
    List<Inscricao> findAllByVagaIdAndDeletadoEmNull(Long vagaId);
    List<Inscricao> findByVagaProcessoSeletivoId(String processoId);
    Optional<InscricaoResponseDTO> findByCandidatoIdAndVagaId(String candidatoId, Long vagaId);
}