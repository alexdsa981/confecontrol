package com.palomino.confecontrol.controller;

import com.palomino.confecontrol.model.dynamic.Usuario;
import com.palomino.confecontrol.model.fixed.RolUsuario;
import com.palomino.confecontrol.repository.UsuarioRepository;
import com.palomino.confecontrol.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/app/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Model listarUsuariosActivos(Model model) {
        List<Usuario> listaUsuariosActivos = usuarioService.getListaUsuariosActivos();
        model.addAttribute("ListaUsuariosActivos", listaUsuariosActivos);
        return model;
    }
    public Model listarUsuariosDesactivados(Model model) {
        List<Usuario> listaUsuariosDesactivados = usuarioService.getListaUsuariosDesactivados();
        model.addAttribute("ListaUsuariosDesactivados", listaUsuariosDesactivados);
        return model;
    }
    public Model listarRoles(Model model){
        List<RolUsuario> listaRoles = usuarioService.getListaRoles();
        model.addAttribute("ListaRoles", listaRoles);
        return model;
    }

    public Model listarSupervisores(Model model) {;
        model.addAttribute("ListaSupervisores", usuarioRepository.findByRolUsuarioId(2l));
        return model;
    }
    public Model listarOperarios(Model model) {;
        model.addAttribute("ListaOperarios", usuarioRepository.findByRolUsuarioId(3l));
        return model;
    }

    @PostMapping("/nuevo")
    public String crearUsuario(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("nombre") String nombre,
            @RequestParam("rolUsuario") Long rolId,
            @RequestParam("dni") String dni,
            @RequestParam("correo") String correo,
            @RequestParam("numCelular") String numCelular) {

        try {
            Usuario usuario = new Usuario();
            usuario.setRolUsuario(usuarioService.getRolPorId(rolId));
            usuario.setNombre(nombre);
            usuario.setDni(dni);
            usuario.setCorreo(correo);
            usuario.setNumCelular(numCelular);
            usuario.setUsername(username);
            usuario.asignarYEncriptarPassword(password);
            usuario.setIsActive(Boolean.TRUE);
            usuario.setChangedPass(Boolean.FALSE);

            usuarioService.guardarUsuario(usuario);

            return "redirect:/admin/usuarios?success";

        } catch (DataIntegrityViolationException e) {
            return "redirect:/admin/usuarios?error=duplicated";
        } catch (Exception e) {
            return "redirect:/admin/usuarios?error=general";
        }
    }

    @PostMapping("/editar")
    public String editarUsuario(
            @RequestParam("id") Long id,
            @RequestParam("username") String username,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam("nombre") String nombre,
            @RequestParam("rolUsuario") Long rolId,
            @RequestParam("dni") String dni,
            @RequestParam("correo") String correo,
            @RequestParam("numCelular") String numCelular) {

        try {
            Usuario usuario = usuarioService.getUsuarioPorId(id);
            if (usuario == null) {
                return "redirect:/admin/usuarios?error=notFound"; // Si no se encuentra el usuario
            }

            usuario.setRolUsuario(usuarioService.getRolPorId(rolId));
            usuario.setNombre(nombre);
            usuario.setDni(dni);
            usuario.setCorreo(correo);
            usuario.setNumCelular(numCelular);
            usuario.setUsername(username);

            if (password != null && !password.isEmpty()) {
                usuario.asignarYEncriptarPassword(password);
            }

            usuarioService.guardarUsuario(usuario);

            return "redirect:/admin/usuarios?success"; // Redirigir con parámetro success

        } catch (DataIntegrityViolationException e) {
            return "redirect:/admin/usuarios?error=duplicated"; // Redirigir con parámetro error
        } catch (Exception e) {
            return "redirect:/admin/usuarios?error=general"; // Redirigir con parámetro error
        }
    }

    @GetMapping("/desactivar/{id}")
    public String desactivarUsuario(@PathVariable Long id) {
        if (!Objects.equals(usuarioService.getIDdeUsuarioLogeado(), id)) {
            usuarioService.desactivarUsuario(id);
        }
        return "redirect:/admin/usuarios";
    }
    @GetMapping("/activar/{id}")
    public String activarUsuario(@PathVariable Long id) {
        if (!Objects.equals(usuarioService.getIDdeUsuarioLogeado(), id)) {
            usuarioService.activarUsuario(id);
        }
        return "redirect:/admin/usuarios";
    }

}
