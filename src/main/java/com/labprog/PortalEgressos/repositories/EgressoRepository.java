package com.labprog.PortalEgressos.repositories;

import com.labprog.PortalEgressos.models.Egresso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EgressoRepository extends JpaRepository<Egresso, Long> {
}
