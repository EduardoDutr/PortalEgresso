package com.labprog.PortalEgressos.repositories;

import com.labprog.PortalEgressos.models.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface CargoRepository extends JpaRepository<Cargo, Long> {

    @Query("SELECT c FROM Cargo c WHERE c.egresso.id = :egressoId AND c.egresso.status = 'ACTIVE'")
    Set<Cargo> findAllByActiveEgressoId(Long id);
}
