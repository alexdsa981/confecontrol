package com.palomino.confecontrol.service;

import com.palomino.confecontrol.model.dynamic.Usuario;
import com.palomino.confecontrol.model.fixed.RolUsuario;
import com.palomino.confecontrol.security.ConstantesSeguridad;
import com.palomino.confecontrol.security.JwtAuthenticationFilter;
import com.palomino.confecontrol.security.JwtTokenProvider;
import com.palomino.confecontrol.repository.RolUsuarioRepository;
import com.palomino.confecontrol.repository.UsuarioRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    RolUsuarioRepository rolUsuarioRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Long getIDdeUsuarioLogeado() {

        String token = jwtAuthenticationFilter.tokenActual;
        String username = jwtTokenProvider.obtenerUsernameDeJWT(token);
        final Usuario usuario = usuarioRepository.findByUsername(username).get();
        return usuario.getId();
    }

    public Usuario getUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).get();
    }

    // Crear un nuevo usuario
    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    //activos
    public List<Usuario> getListaUsuariosActivos() {
        return usuarioRepository.findByIsActiveTrue();
    }

    //desactivados
    public List<Usuario> getListaUsuariosDesactivados() {
        return usuarioRepository.findByIsActiveFalse();
    }

    public Usuario actualizarUsuario(Long id, Usuario usuarioActualizado) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);
        if (usuarioExistente.isPresent()) {
            Usuario usuario = usuarioExistente.get();
            usuario.setUsername(usuarioActualizado.getUsername());
            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setRolUsuario(usuarioActualizado.getRolUsuario());
            // Encriptar la nueva contraseña si es que se actualiza
            if (usuarioActualizado.getPassword() != null && !usuarioActualizado.getPassword().isEmpty()) {
                usuario.asignarYEncriptarPassword(usuarioActualizado.getPassword());
            }
            return usuarioRepository.save(usuario);
        }
        return null;
    }

    public void desactivarUsuario(Long id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setIsActive(false);
            usuarioRepository.save(usuario);
        }
    }

    public void activarUsuario(Long id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setIsActive(true);
            usuarioRepository.save(usuario);
        }
    }

    public List<Usuario> ListaUsuariosPorRol(Long idRolUsuario) {
        return usuarioRepository.findByRolUsuarioId(idRolUsuario);
    }

    //    //retorna todos los roles:
    public List<RolUsuario> getListaRoles() {
        return rolUsuarioRepository.findAll();
    }

    public RolUsuario getRolPorId(Long id) {
        return rolUsuarioRepository.findById(id).get();
    }

    public Optional<Usuario> getUsuarioPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public Boolean existeUsuarioPorUsername(String username) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);
        if (usuarioOpt.isPresent()) {
            System.out.println("Existe en bd Tickets");
            return true;
        } else {
            System.out.println("No existe en bd tickets");
            return false;
        }

    }

    public ResponseEntity<Void> logearUsuarioAlSistema(String username, String password, HttpServletResponse response) throws IOException {
        try {
            Optional<Usuario> optionalUsuario = getUsuarioPorUsername(username);

            if (optionalUsuario.isEmpty()) {
                response.sendRedirect("/login?error=badCredentials&username=" + username);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            Usuario usuario = optionalUsuario.get();

            if (!usuario.getIsActive()) {
                response.sendRedirect("/login?error=inactive&username=" + username);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // Autenticación
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generar y agregar cookie JWT
            String token = jwtTokenProvider.generarToken(authentication);
            Cookie jwtCookie = new Cookie("JWT", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setMaxAge((int) (ConstantesSeguridad.JWT_EXPIRATION_TOKEN) / 1000);
            jwtCookie.setPath("/");
            response.addCookie(jwtCookie);

            if (!usuario.getChangedPass()) {
                response.sendRedirect("/lotes?changePassword");
                return ResponseEntity.ok().build();
            }

            // Redirección según el rol
            String rol = usuario.getRolUsuario().getNombre(); // Suponiendo que tienes un getRol().getNombre()

            switch (rol) {
                case "Administrador":
                    response.sendRedirect("/lotes");
                    break;
                case "Operario":
                    response.sendRedirect("/operaciones");
                    break;
                case "Supervisor":
                    response.sendRedirect("/paquetes");
                    break;
                default:
                    response.sendRedirect("/login?error=unauthorized&username=" + username);
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            return ResponseEntity.ok().build();

        } catch (BadCredentialsException e) {
            response.sendRedirect("/login?error=badCredentials&username=" + username);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            response.sendRedirect("/login?error=unknown&username=" + username);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



}
