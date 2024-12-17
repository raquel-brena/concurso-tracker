package com.rb.web2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rb.web2.domain.cargo.Cargo;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {
}
