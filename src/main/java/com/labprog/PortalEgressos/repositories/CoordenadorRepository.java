package com.labprog.PortalEgressos.repositories;

import com.labprog.PortalEgressos.models.Coordenador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoordenadorRepository extends JpaRepository<Coordenador, Long> {
    Optional<Coordenador> findByLogin(String login);
}
