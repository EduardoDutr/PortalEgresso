package com.labprog.PortalEgressos.repositories;

import com.labprog.PortalEgressos.models.Coordenador;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserAuthenticated implements UserDetails {
    private final Coordenador coordenador;

    public UserAuthenticated(Coordenador coordenador) {
        this.coordenador = coordenador;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "adm");
    }

    @Override
    public String getPassword() {
        return coordenador.getSenha();
    }

    @Override
    public String getUsername() {
        return coordenador.getLogin();
    }
}
