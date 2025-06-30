package com.palomino.confecontrol.controller;

import com.palomino.confecontrol.model.dynamic.Usuario;
import com.palomino.confecontrol.repository.RolUsuarioRepository;
import com.palomino.confecontrol.repository.UsuarioRepository;
import com.palomino.confecontrol.service.UsuarioService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/app")
public class LoginController {

    private UsuarioRepository usuarioRepository;
    private UsuarioService usuarioService;

    @Autowired
    public LoginController(UsuarioService usuarioService, UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestParam String username, @RequestParam String password, HttpServletResponse response) throws IOException {
        return usuarioService.logearUsuarioAlSistema(username, password, response);
    }




    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) throws IOException {
        Cookie jwtCookie = new Cookie("JWT", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setMaxAge(0); // elimina la cookie
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);
        response.sendRedirect("/login");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/cambiar-password")
    public ResponseEntity<Void> cambiarPassword(@RequestParam String newPassword) {
        Long iDUsuarioLogeado = usuarioService.getIDdeUsuarioLogeado();
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(iDUsuarioLogeado);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.asignarYEncriptarPassword(newPassword);
            usuario.setChangedPass(true);
            usuarioRepository.save(usuario);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


}
