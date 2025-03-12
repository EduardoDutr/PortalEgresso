package com.labprog.PortalEgressos.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements ApplicationRunner {
    private final JdbcTemplate jdbcTemplate;

    public DatabaseInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(ApplicationArguments args) {
        jdbcTemplate.update(
                """
                INSERT IGNORE INTO coordenador (login, senha)
                VALUES ('username', '$2a$12$I9nkUDrzjIe3CSiWAtOzyeNfINhfimB4pmlekUEuJDNZlFdVSB9dS');
                """
        );
    }
}
