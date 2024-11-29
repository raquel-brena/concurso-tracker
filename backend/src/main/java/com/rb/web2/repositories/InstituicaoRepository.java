package com.rb.web2.repositories;

import com.rb.web2.domain.instituicao.Instituicao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstituicaoRepository extends JpaRepository<Instituicao, String> {
    Optional<Instituicao> findById(String id);
    
    Optional<Instituicao> findByName(String name);
    
    List<Instituicao> findByLocation(String location);
    
}
