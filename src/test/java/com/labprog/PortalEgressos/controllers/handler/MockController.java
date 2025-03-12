package com.labprog.PortalEgressos.controllers.handler;

import com.labprog.PortalEgressos.service.exceptions.AuthorizationException;
import com.labprog.PortalEgressos.service.exceptions.CursoNotFoundException;
import com.labprog.PortalEgressos.service.exceptions.EgressoNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class MockController {
    @GetMapping("/dummy/authorization")
    public void throwAuthorizationException() {
        throw new AuthorizationException();
    }

    @GetMapping("/dummy/curso-not-found")
    public void throwCursoNotFoundException() {
        throw new CursoNotFoundException(1L);
    }

    @GetMapping("/dummy/egresso-not-found")
    public void throwEgressoNotFoundException() {
        throw new EgressoNotFoundException(2L);
    }

    @GetMapping("/dummy/illegal-argument")
    public void throwIllegalArgumentException() {
        throw new IllegalArgumentException("Argumento inválido.");
    }

    @GetMapping("/dummy/runtime-exception")
    public void throwRuntimeException() {
        throw new RuntimeException("Erro inesperado.");
    }
}
