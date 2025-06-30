package com.palomino.confecontrol.controller;


import com.palomino.confecontrol.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;


@Controller
public class WebController {
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    UsuarioController usuarioController;
    @Autowired
    PrendaController prendaController;
    @Autowired
    MarcacionController marcacionController;
    @Autowired
    LoteController loteController;
    @Autowired
    DescuentosController descuentosController;

    @GetMapping("/")
    public String redirectToInicio() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String redirigePaginaLogin(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "username", required = false) String username,
            Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() &&
                !authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/lotes";
        }

        model.addAttribute("error", error);
        model.addAttribute("username", username);

        return "index";
    }
    @GetMapping("/marcacion")
    public String redirigePaginaMarcacion(Model model) {
        model.addAttribute("Titulo", "ConFeControl | Marcación de Asistencia");
        return "marcacion";
    }

    @GetMapping("/inicio")
    public String redirigePaginaInicio(Model model) {
        model.addAttribute("SubTitulo", "Bienvenido :)");
        model.addAttribute("Titulo", "ConFeControl | Inicio");
        return "general/inicio";
    }

    @GetMapping("/prendas")
    public String redirigePaginaPrenda(@RequestParam(value = "selectedId", required = false) Long selectedId, Model model) {
        prendaController.listarPrendas(model);
        prendaController.prendaSeleccionada(selectedId, model);
        Boolean isAdmin = false;
        if (usuarioService.getUsuarioPorId(usuarioService.getIDdeUsuarioLogeado()).getRolUsuario().getId() == 1){
            isAdmin = true;
        }
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("SubTitulo", "Gestión de Prendas");
        model.addAttribute("Titulo", "ConFeControl | Gestión de Prendas");
        return "general/prendas";
    }

    @GetMapping("/lotes")
    public String redirigePaginaLotes(@RequestParam(required = false) Boolean verDesactivados, Model model) {
        prendaController.listarPrendas(model);
        loteController.listarLotes(model, verDesactivados);
        usuarioController.listarSupervisores(model);
        model.addAttribute("SubTitulo", "Gestión de Lotes");
        model.addAttribute("Titulo", "ConFeControl | Gestión de Lotes");
        return "general/lotes";
    }

    @GetMapping("/paquetes")
    public String redirigePaginaPaquetes(@RequestParam(name = "idLote", required = false) Long idLote, Model model) {
        loteController.listarPaquetes(model, idLote);
        loteController.listarLotesEnPaginaPaquetes(model, idLote); // ← nuevo método
        usuarioController.listarOperarios(model);
        model.addAttribute("SubTitulo", "Gestión de Paquetes");
        model.addAttribute("Titulo", "ConFeControl | Gestión de Paquetes");
        return "general/paquetes";
    }


    @GetMapping("/operaciones")
    public String redirigePaginaOperaciones(@RequestParam(name = "paqueteId", required = false) Long paqueteId, Model model) {
        if (Objects.equals(usuarioService.getUsuarioPorId(usuarioService.getIDdeUsuarioLogeado()).getRolUsuario().getNombre(), "Operario")){
            loteController.listarDetallePaquetesParaOperario(model);
        }else{
            loteController.listarDetallePaquetes(model, paqueteId);
            descuentosController.listarTiposDescuento(model);
        }
        model.addAttribute("SubTitulo", "Gestión de Operación");
        model.addAttribute("Titulo", "ConFeControl | Gestión de Operación");
        return "general/operaciones";
    }

    @GetMapping("admin/usuarios")
    public String redirigePaginaUsuario(Model model) {
        usuarioController.listarRoles(model);
        usuarioController.listarUsuariosActivos(model);
        usuarioController.listarUsuariosDesactivados(model);
        model.addAttribute("SubTitulo", "Gestión de Usuarios");
        model.addAttribute("Titulo", "ConFeControl | Gestión de Usuarios");
        return "admin/usuarios";
    }

    @GetMapping("admin/asistencia")
    public String redirigePaginaMarcaciones(Model model) {
        marcacionController.listarMarcaciones(model);
        model.addAttribute("SubTitulo", "Historial de Marcaciones");
        model.addAttribute("Titulo", "ConFeControl | Historial de Marcaciones");
        return "admin/asistencia";
    }

    @GetMapping("admin/dashboard")
    public String redirigePaginaDashboard(Model model) {
        model.addAttribute("SubTitulo", "Dashboard");
        model.addAttribute("Titulo", "ConFeControl | Dashboard");
        return "admin/dashboard";
    }

    @GetMapping("admin/pagos")
    public String redirigePaginaPagos(Model model) {
        model.addAttribute("SubTitulo", "Gestión de Pagos");
        model.addAttribute("Titulo", "ConFeControl | Pagos");
        return "admin/pagos";
    }
    @GetMapping("admin/pagos/historial")
    public String redirigePaginaPagosHistorial(Model model) {
        model.addAttribute("SubTitulo", "Historial de Pagos");
        model.addAttribute("Titulo", "ConFeControl | Historial de Pagos");
        return "admin/pagos-historial";
    }

    @GetMapping("operario/pagos")
    public String redirigePaginaPagosOperario(Model model) {
        model.addAttribute("SubTitulo", "Pago de la Semana");
        model.addAttribute("Titulo", "ConFeControl | Pago Semanal");
        return "admin/pagos";
    }
    @GetMapping("operario/pagos/historial")
    public String redirigePaginaPagosHistorialOperario(Model model) {
        model.addAttribute("SubTitulo", "Historial de Pagos");
        model.addAttribute("Titulo", "ConFeControl | Historial de Pagos");
        return "admin/pagos-historial";
    }
}
