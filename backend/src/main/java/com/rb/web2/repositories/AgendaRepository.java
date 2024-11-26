package com.rb.web2.repositories;

import com.rb.web2.domain.agenda.Agenda;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    Optional<Agenda> findById(Long id);
    
}
