package com.labprog.PortalEgressos.repositories;

import com.labprog.PortalEgressos.models.Depoimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepoimentoRepository extends JpaRepository<Depoimento, Long> {

    List<Depoimento> findAllByEgressoId(Long id);
}
