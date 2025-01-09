package com.labprog.PortalEgressos.repositories;

import com.labprog.PortalEgressos.models.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}
