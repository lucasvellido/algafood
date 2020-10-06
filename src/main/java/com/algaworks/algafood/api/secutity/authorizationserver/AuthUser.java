package com.algaworks.algafood.api.secutity.authorizationserver;

import com.algaworks.algafood.api.domain.model.Usuario;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class AuthUser extends User {

    private static final long serialVersionUID = 5897274344413927444L;

    private Long userId;
    private String fullName;

    public AuthUser(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
        super(usuario.getEmail(), usuario.getSenha(), authorities);

        this.userId = usuario.getId();
        this.fullName = usuario.getNome();
    }
}
