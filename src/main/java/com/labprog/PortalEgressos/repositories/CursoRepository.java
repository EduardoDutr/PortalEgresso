package com.labprog.PortalEgressos.repositories;

import com.labprog.PortalEgressos.models.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    Set<Curso> findAllByEgressoId(Long id);

}
