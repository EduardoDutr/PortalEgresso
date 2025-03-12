package com.labprog.PortalEgressos.repositories;

import com.labprog.PortalEgressos.models.Egresso;
import com.labprog.PortalEgressos.models.Egresso.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EgressoRepository extends JpaRepository<Egresso, Long> {

    List<Egresso> findByStatus(Status status);

    @Query("SELECT e FROM Egresso e WHERE e.id = :id AND e.status = 'ACTIVE'")
    Optional<Egresso> findActiveById(@Param("id") Long id);

    @Query("SELECT ce.egresso FROM CursoEgresso ce WHERE ce.curso.id = :cursoId AND ce.egresso.status = 'ACTIVE'")
    Set<Egresso> findActiveByCursoId(@Param("cursoId") Long cursoId);

    @Query("SELECT c.egresso FROM Cargo c WHERE c.id = :cargoId AND c.egresso.status = 'ACTIVE'")
    Optional<Egresso> findActiveByCargoId(@Param("cargoId") Long cargoId);

    @Query("SELECT ce.egresso FROM CursoEgresso ce WHERE ce.anoFim = :ano AND ce.egresso.status = 'ACTIVE'")
    Set<Egresso> findActiveByAnoFim(@Param("ano") Long ano);
}
