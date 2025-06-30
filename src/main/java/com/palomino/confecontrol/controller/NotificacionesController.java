package com.palomino.confecontrol.controller;


import com.palomino.confecontrol.model.dto.NotificacionDTO;
import com.palomino.confecontrol.model.dynamic.DetallePaqueteLote;
import com.palomino.confecontrol.model.dynamic.Notificacion;
import com.palomino.confecontrol.model.dynamic.PaqueteLote;
import com.palomino.confecontrol.model.dynamic.Usuario;
import com.palomino.confecontrol.repository.DetallePaqueteLoteRepository;
import com.palomino.confecontrol.repository.LoteRepository;
import com.palomino.confecontrol.repository.PaqueteLoteRepository;
import com.palomino.confecontrol.repository.PrendaRepository;
import com.palomino.confecontrol.service.NotificacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/app/notificaciones")
public class NotificacionesController {
    @Autowired
    LoteRepository loteRepository;
    @Autowired
    PaqueteLoteRepository paqueteLoteRepository;
    @Autowired
    DetallePaqueteLoteRepository detallePaqueteLoteRepository;
    @Autowired
    NotificacionesService notificacionesService;

    @GetMapping()
    public ResponseEntity<List<NotificacionDTO>> getNotificaciones() {
        List<Notificacion> ListaNotificaciones = notificacionesService.getTOP15NotificacionesDeUsuarioLogeado();
        List<NotificacionDTO> ListaDTO = new ArrayList<>();
        for (Notificacion notificacion : ListaNotificaciones){
            NotificacionDTO notificacionDTO = new NotificacionDTO(notificacion);
            ListaDTO.add(notificacionDTO);
        }
        return ResponseEntity.ok(ListaDTO);
    }

    @PostMapping("/marcar-leidas")
    public ResponseEntity<Void> marcarNotificacionesComoLeidas() {
        notificacionesService.CambiarNotificacionesALeido();
        return ResponseEntity.noContent().build(); // Respuesta HTTP 204 (No Content)
    }

    @PostMapping("/marcar-abierto/{id}")
    public ResponseEntity<Void> marcarNotificacionComoAbiertoPorID(
            @PathVariable Long id
    ) {
        notificacionesService.CambiarNotificacionPorAbierto(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/avisar-supervisor/{detalleId}")
    public ResponseEntity<Void> avisarSupervisor(@PathVariable Long detalleId) {
        Optional<DetallePaqueteLote> optionalDetalle = detallePaqueteLoteRepository.findById(detalleId);

        if (optionalDetalle.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        DetallePaqueteLote detalle = optionalDetalle.get();

        // Lógica para obtener el supervisor del paquete
        PaqueteLote paquete = detalle.getPaqueteLote();
        Usuario supervisor = paquete.getSupervisorPaqueteLote(); // asegúrate de que exista este campo en PaqueteLote

        if (supervisor == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Notificacion notificacion = new Notificacion();
        notificacion.setFecha(LocalDate.now());
        notificacion.setHora(LocalTime.now());
        notificacion.setLeido(false);
        notificacion.setAbierto(false);
        notificacion.setUrl("/operaciones?paqueteId=" + detalle.getPaqueteLote().getId());
        notificacion.setMensaje("Operación terminada. Revisión requerida por el operario: " + detalle.getTrabajador().getNombre());
        notificacion.setUsuario(supervisor);

        detalle.setIsNotificado(true);
        detallePaqueteLoteRepository.save(detalle);

        notificacionesService.saveNotiicacion(notificacion);

        return ResponseEntity.ok().build();
    }



}
