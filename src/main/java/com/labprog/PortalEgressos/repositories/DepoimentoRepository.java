package com.labprog.PortalEgressos.repositories;

import com.labprog.PortalEgressos.models.Depoimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepoimentoRepository extends JpaRepository<Depoimento, Long> {

    @Query("SELECT d FROM Depoimento d WHERE d.egresso.id = :egressoId AND d.egresso.status = 'ACTIVE'")
    List<Depoimento> findAllByActiveEgressoId(Long id);
}
