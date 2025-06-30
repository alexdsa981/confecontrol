package com.palomino.confecontrol.controller;

import com.palomino.confecontrol.model.dynamic.Marcacion;
import com.palomino.confecontrol.model.dynamic.Usuario;
import com.palomino.confecontrol.model.fixed.Prenda;
import com.palomino.confecontrol.repository.MarcacionRepository;
import com.palomino.confecontrol.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/app/marcacion")
public class MarcacionController {
    @Autowired
    MarcacionRepository marcacionRepository;
    @Autowired
    UsuarioRepository usuarioRepository;


    public Model listarMarcaciones(Model model) {
        model.addAttribute("ListaAsistencia", marcacionRepository.findAll());
        return model;
    }

    @PostMapping("/marcar/llegada")
    public String marcarAsistencia(@RequestParam("dni") String dni) {
        try {
            Optional<Usuario> optionalUser = usuarioRepository.findByDni(dni);

            if (optionalUser.isPresent()) {
                Usuario user = optionalUser.get();


                if (!user.getIsActive()) {
                    return "redirect:/marcacion?error=usuarioInactivo";
                }

                LocalDate hoy = LocalDate.now();

                List<Marcacion> marcacionesHoy = marcacionRepository.findByUsuarioAndFecha(user, hoy);
                boolean yaMarcoLlegada = marcacionesHoy.stream().anyMatch(m -> m.getHoraEntrada() != null);

                if (yaMarcoLlegada) {
                    return "redirect:/marcacion?error=yaMarcoLlegada";
                }

                Marcacion marcacion = new Marcacion();
                marcacion.setUsuario(user);
                marcacion.setFecha(hoy);
                marcacion.setHoraEntrada(LocalTime.now());

                LocalTime llegadaATiempo = LocalTime.of(8, 10);
                marcacion.setEstadoLlegada(!LocalTime.now().isAfter(llegadaATiempo));

                marcacionRepository.save(marcacion);
                return "redirect:/marcacion?successNombre=" + URLEncoder.encode(user.getNombre(), StandardCharsets.UTF_8);
            } else {
                return "redirect:/marcacion?error=noExiste";
            }
        } catch (Exception e) {
            return "redirect:/marcacion?error";
        }
    }


    @PostMapping("/marcar/salida")
    public String marcarSalida(@RequestParam("dni") String dni) {
        try {
            Optional<Usuario> optionalUser = usuarioRepository.findByDni(dni);

            if (optionalUser.isPresent()) {
                Usuario user = optionalUser.get();
                LocalDate hoy = LocalDate.now();

                List<Marcacion> marcaciones = marcacionRepository.findByUsuarioAndFechaAndHoraSalidaIsNull(user, hoy);

                if (!marcaciones.isEmpty()) {
                    Marcacion ultimaMarcacion = marcaciones.get(marcaciones.size() - 1);

                    LocalTime horaActual = LocalTime.now();
                    LocalTime salidaLimite = LocalTime.of(17, 0);

                    ultimaMarcacion.setHoraSalida(horaActual);

                    ultimaMarcacion.setEstadoSalida(horaActual.isAfter(salidaLimite) || horaActual.equals(salidaLimite));


                    marcacionRepository.save(ultimaMarcacion);
                    return "redirect:/marcacion?salidaNombre=" + URLEncoder.encode(user.getNombre(), StandardCharsets.UTF_8);
                } else {
                    return "redirect:/marcacion?error=noMarcacionPrev";
                }
            } else {
                return "redirect:/marcacion?error=noExiste";
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "redirect:/marcacion?error";
        }
    }


}
