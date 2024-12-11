package com.labprog.PortalEgressos.repositories;

import com.labprog.PortalEgressos.models.CursoEgresso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CursoEgressoRepository extends JpaRepository<CursoEgresso, Long> {
    Set<CursoEgresso> findByCursoId(Long idCurso);
    Set<CursoEgresso> findByAnoFim(Long ano);
}
