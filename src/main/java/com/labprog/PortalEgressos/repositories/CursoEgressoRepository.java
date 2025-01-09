package com.labprog.PortalEgressos.repositories;

import com.labprog.PortalEgressos.models.CursoEgresso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CursoEgressoRepository extends JpaRepository<CursoEgresso, Long> {
    List<CursoEgresso> findByCursoId(Long idCurso);
    List<CursoEgresso> findByAnoFim(Long ano);
}
