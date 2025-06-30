package com.palomino.confecontrol.security;


import com.palomino.confecontrol.model.dynamic.Usuario;
import com.palomino.confecontrol.model.fixed.RolUsuario;
import com.palomino.confecontrol.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class CustomUsersDetailsService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;
    private final HttpServletResponse response;

    @Autowired
    public CustomUsersDetailsService(UsuarioRepository usuarioRepository, HttpServletResponse response) {
        this.usuarioRepository = usuarioRepository;
        this.response = response;
    }

    //se crea una lista de autoridades por medio de una lista de roles
    public Collection<? extends GrantedAuthority> mapAuthority(Collection<RolUsuario> roles) {
        return roles.stream().map(rol -> new SimpleGrantedAuthority(rol.getNombre())).collect(Collectors.toList());
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!usuario.getIsActive()) {
            throw new UsernameNotFoundException("User not found");
        }
        Collection<RolUsuario> roles = Collections.singletonList(usuario.getRolUsuario());
        return new User(usuario.getUsername(), usuario.getPassword(), mapAuthority(roles));
    }
}


