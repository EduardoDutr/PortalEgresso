package com.labprog.PortalEgressos.repositories;

import com.labprog.PortalEgressos.models.Oportunidade;
import com.labprog.PortalEgressos.models.Oportunidade.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OportunidadeRepository extends JpaRepository<Oportunidade, Long> {
    List<Oportunidade> findByStatus(Status status);
}
