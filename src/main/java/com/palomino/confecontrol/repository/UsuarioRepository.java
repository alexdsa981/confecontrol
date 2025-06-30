package com.palomino.confecontrol.repository;

import com.palomino.confecontrol.model.dynamic.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    List<Usuario> findByIsActiveTrue();

    List<Usuario> findByIsActiveFalse();

    List<Usuario> findByRolUsuarioId(Long idRolUsuario);

    Optional<Usuario> findByDni(String dni);

    List<Usuario> findByRolUsuarioNombre(String nombre);
}
