package com.labprog.PortalEgressos.repositories;

import com.labprog.PortalEgressos.models.Depoimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface DepoimentoRepository extends JpaRepository<Depoimento, Long> {

    Set<Depoimento> findAllByEgressoId(Long id);
}