package com.labprog.PortalEgressos.repositories;

import com.labprog.PortalEgressos.models.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CargoRepository extends JpaRepository<Cargo, Long> {

    Set<Cargo> findAllByEgressoId(Long id);
}
