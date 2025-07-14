package com.palomino.confecontrol.controller;

import com.palomino.confecontrol.model.dto.LoteConPaquetesDTO;
import com.palomino.confecontrol.model.dynamic.*;
import com.palomino.confecontrol.model.fixed.OperacionPrenda;
import com.palomino.confecontrol.model.fixed.Prenda;
import com.palomino.confecontrol.model.fixed.TipoDescuento;
import com.palomino.confecontrol.repository.*;
import com.palomino.confecontrol.service.NotificacionesService;
import com.palomino.confecontrol.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/app/lote")
public class LoteController {
    @Autowired
    LoteRepository loteRepository;
    @Autowired
    PrendaRepository prendaRepository;
    @Autowired
    PaqueteLoteRepository paqueteLoteRepository;
    @Autowired
    DetallePaqueteLoteRepository detallePaqueteLoteRepository;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    OperacionPrendaRepository operacionPrendaRepository;
    @Autowired
    TipoDescuentoRepository tipoDescuentoRepository;
    @Autowired
    NotificacionesService notificacionesService;
    @Autowired
    DetalleTrabajoRepository detalleTrabajoRepository;
    @Autowired
    DetalleDescuentoRepository detalleDescuentoRepository;

    public void listarLotes(Model model, Boolean verDesactivados) {
        List<Lote> lotes = (verDesactivados != null && verDesactivados)
                ? loteRepository.findByIsActiveFalse()
                : loteRepository.findByIsActiveTrue();

        model.addAttribute("ListaLotes", lotes);
        model.addAttribute("verDesactivados", verDesactivados != null && verDesactivados);
    }

    public void listarLotesEnPaginaPaquetes(Model model, Long idLote) {
        List<Lote> lotes;

        if (idLote != null) {
            Optional<Lote> lote = loteRepository.findById(idLote);
            if (lote.isPresent() && Boolean.TRUE.equals(lote.get().getIsActive()) && !Boolean.TRUE.equals(lote.get().getIsTerminado())) {
                lotes = List.of(lote.get());
            } else {
                lotes = List.of(); // Vacío si no cumple condiciones
            }
        } else {
            lotes = loteRepository.findByIsActiveTrueAndIsTerminadoFalse();
        }

        model.addAttribute("ListaLotes", lotes);
        model.addAttribute("idLoteSeleccionado", idLote);
    }

    public Model listarPaquetes(Model model, Long idLote) {
        if (idLote != null) {
            model.addAttribute("ListaPaquetes", paqueteLoteRepository.findByLoteId(idLote));
        } else {
            model.addAttribute("ListaPaquetes", paqueteLoteRepository.findByLoteIsActiveTrue());
        }
        return model;
    }



    public Model listarDetallePaquetes(Model model, Long paqueteId) {
        if (paqueteId != null) {
            model.addAttribute("ListaDetallePaquetes", detallePaqueteLoteRepository.findByPaqueteLoteId(paqueteId));
        } else {
            model.addAttribute("ListaDetallePaquetes", detallePaqueteLoteRepository.findByPaqueteLoteLoteIsActiveTrue());
        }

        return model;
    }

    public Model listarDetallePaquetesParaOperario(Model model) {
        Long idOperario = usuarioService.getIDdeUsuarioLogeado();

        List<DetallePaqueteLote> detallesAsignados = detallePaqueteLoteRepository
                .findByTrabajadorIdAndPaqueteLoteLoteIsActiveTrue(idOperario);

        model.addAttribute("ListaDetallePaquetes", detallesAsignados);
        return model;
    }





    @PostMapping("/guardar")
    @ResponseBody
    public ResponseEntity<String> guardarLoteConPaquetes(@RequestBody LoteConPaquetesDTO dto) {
        if (dto.getPaquetes() == null || dto.getPaquetes().isEmpty()) {
            return ResponseEntity.badRequest().body("Debe incluir al menos un paquete.");
        }

        // Obtener prenda
        Prenda prendaSeleccionada = prendaRepository.findById(dto.getTipoPrendaId()).orElse(null);
        if (prendaSeleccionada == null) {
            return ResponseEntity.badRequest().body("Prenda no encontrada.");
        }

        // Crear lote
        Lote nuevoLote = new Lote();
        nuevoLote.setCantidadPrenda(dto.getCantidadLote());
        nuevoLote.setFechaCreacion(LocalDate.now());
        nuevoLote.setHoraCreacion(LocalTime.now());
        nuevoLote.setIsActive(true);
        nuevoLote.setIsTerminado(false);

        nuevoLote.setPrenda(prendaSeleccionada);
        nuevoLote.setCreadorLote(usuarioService.getUsuarioPorId(usuarioService.getIDdeUsuarioLogeado()));
        nuevoLote.setCodigo(generarCodigoLote());

        loteRepository.save(nuevoLote);

        List<OperacionPrenda> operaciones = prendaSeleccionada.getListaOperaciones();
        Usuario supervisor = usuarioService.getUsuarioPorId(dto.getSupervisorId());


        int numeroPaquete = 1;
        for (Integer cantidadPrendasPaquete : dto.getPaquetes()) {
            // Crear paquete
            PaqueteLote paquete = new PaqueteLote();
            paquete.setCantidad(cantidadPrendasPaquete);
            paquete.setLote(nuevoLote);
            paquete.setSupervisorPaqueteLote(supervisor);
            paquete.setCodigo(generarCodigoPaquete(nuevoLote.getCodigo(), numeroPaquete++));
            paquete.setIsTerminado(false);
            paqueteLoteRepository.save(paquete);

            Notificacion notificacion = new Notificacion();
            notificacion.setFecha(LocalDate.now());
            notificacion.setHora(LocalTime.now());
            notificacion.setLeido(false);
            notificacion.setAbierto(false);
            notificacion.setPaqueteLote(paquete);
            notificacion.setUrl("/operaciones?paqueteId=" + paquete.getId());
            notificacion.setMensaje("Se te asignó un paquete");
            notificacion.setUsuario(supervisor);
            notificacionesService.saveNotiicacion(notificacion);

            // Crear detalle por cada operación * cantidad del paquete
            for (OperacionPrenda operacion : operaciones) {
                DetallePaqueteLote detalle = new DetallePaqueteLote();
                detalle.setIsTerminado(false);
                detalle.setIsNotificado(false);
                detalle.setOperacionPrenda(operacion);
                detalle.setPaqueteLote(paquete);
                detallePaqueteLoteRepository.save(detalle);
            }

        }

        return ResponseEntity.ok("Lote y paquetes guardados exitosamente.");
    }


    public String generarCodigoLote() {
        LocalDate hoy = LocalDate.now();
        String fechaFormateada = hoy.format(DateTimeFormatter.ofPattern("ddMMyy"));
        long countHoy = loteRepository.countByFechaCreacion(hoy);
        String secuencia = String.format("%02d", countHoy + 1); // solo 2 dígitos
        return "LOTE-" + fechaFormateada + "-" + secuencia;
    }

    public String generarCodigoPaquete(String codigoLote, int numeroPaquete) {
        String secuencia = String.format("%02d", numeroPaquete); // solo 2 dígitos
        return codigoLote + "-PAQ" + secuencia;
    }


    @GetMapping("/operaciones-por-lote/{idLote}")
    @ResponseBody
    public List<OperacionPrenda> obtenerOperacionesPorLote(@PathVariable Long idLote) {
        Optional<Lote> loteOptional = loteRepository.findById(idLote);
        if (loteOptional.isPresent()) {
            Prenda prenda = loteOptional.get().getPrenda();
            return prenda.getListaOperaciones();
        }
        return Collections.emptyList();
    }


    @PostMapping("/asignar-operario")
    public ResponseEntity<?> asignarOperario(
            @RequestParam Long loteId,
            @RequestParam Long operacionId,
            @RequestParam Long operarioId) {

        Optional<Lote> loteOpt = loteRepository.findById(loteId);
        Optional<OperacionPrenda> operacionOpt = operacionPrendaRepository.findById(operacionId);

        if (loteOpt.isEmpty() || operacionOpt.isEmpty() || operarioId == null) {
            return ResponseEntity.badRequest().body("Datos inválidos.");
        }

        Usuario usuario = usuarioService.getUsuarioPorId(operarioId);
        if (usuario == null) {
            return ResponseEntity.badRequest().body("Operario no encontrado.");
        }

        List<DetallePaqueteLote> detalles = detallePaqueteLoteRepository.findByLoteIdAndOperacionId(loteId, operacionId);

        for (DetallePaqueteLote detalle : detalles) {
            detalle.setTrabajador(usuario);
            Notificacion notificacion = new Notificacion();
            notificacion.setFecha(LocalDate.now());
            notificacion.setHora(LocalTime.now());
            notificacion.setLeido(false);
            notificacion.setAbierto(false);
            notificacion.setPaqueteLote(detalle.getPaqueteLote());
            notificacion.setUrl("/operaciones?paqueteId=" + detalle.getPaqueteLote().getId());
            notificacion.setMensaje("Se te asignó la operación: " + detalle.getOperacionPrenda().getNombre());
            notificacion.setUsuario(usuario);
            notificacionesService.saveNotiicacion(notificacion);
        }



        detallePaqueteLoteRepository.saveAll(detalles);

        return ResponseEntity.ok("Operario asignado correctamente a " + detalles.size() + " operaciones.");
    }


    @PostMapping("/cambiar-estado-operacion")
    public ResponseEntity<?> cambiarEstado(
            @RequestParam Long id,
            @RequestParam(required = false) Long tipoObservacion,
            @RequestParam(required = false) String comentario) {

        Optional<DetallePaqueteLote> opt = detallePaqueteLoteRepository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        DetallePaqueteLote detallePL = opt.get();
        detallePL.setIsTerminado(true);

        // Agregar observación si aplica
        if (tipoObservacion != null) {
            Optional<TipoDescuento> tipo = tipoDescuentoRepository.findById(tipoObservacion);
            if (tipo.isEmpty()) {
                return ResponseEntity.badRequest().body("Tipo de observación no válido.");
            }

            String observacion = tipo.get().getNombre();
            if (comentario != null && !comentario.isBlank()) {
                observacion += ": " + comentario;
            }
            detallePL.setDescripcionObservacion(observacion);

            TipoDescuento tipodescuento = tipo.get();
            DetalleDescuentos detalleDescuento = new DetalleDescuentos();
            if (tipodescuento.getNombre().equalsIgnoreCase("Prenda dañada")) {
                //descuento del costo total de la prenda
                detalleDescuento.setMonto(detallePL.getOperacionPrenda().getPrenda().getCostoTotal());
            }

            if (tipodescuento.getNombre().equalsIgnoreCase("Pérdida de materiales")) {
                detalleDescuento.setMonto(detallePL.getOperacionPrenda().getPrenda().getCostoTotal());
            }

            if (tipodescuento.getNombre().equalsIgnoreCase("Error en costura")) {
                detalleDescuento.setMonto(detallePL.getOperacionPrenda().getPrenda().getCostoTotal());
            }
            detalleDescuento.setLote(detallePL.getPaqueteLote().getLote());
            detalleDescuento.setFecha(LocalDateTime.now());
            detalleDescuento.setUsuario(detallePL.getTrabajador());
            detalleDescuento.setTipoDescuento(tipodescuento);
            detalleDescuentoRepository.save(detalleDescuento);
        }

        detallePaqueteLoteRepository.save(detallePL);

        DetalleTrabajo detalleTrabajo = new DetalleTrabajo();
        detalleTrabajo.setLote(detallePL.getPaqueteLote().getLote());
        detalleTrabajo.setUsuario(detallePL.getTrabajador());
        detalleTrabajo.setDetallePaqueteLote(detallePL);
        detalleTrabajo.setFecha(LocalDateTime.now());

        BigDecimal precioTrabajo = detallePL.getOperacionPrenda().getPrecioNormal();
        Integer cantidad = detallePL.getPaqueteLote().getCantidad();

        BigDecimal monto = precioTrabajo.multiply(BigDecimal.valueOf(cantidad));

        detalleTrabajo.setMonto(monto);

        detalleTrabajoRepository.save(detalleTrabajo);


        // Verificar si todos los detalles del paquete están terminados
        PaqueteLote paquete = detallePL.getPaqueteLote();
        boolean todosTerminados = paquete.getListaDetallePaqueteLote().stream()
                .allMatch(DetallePaqueteLote::getIsTerminado);

        if (todosTerminados && !Boolean.TRUE.equals(paquete.getIsTerminado())) {
            paquete.setIsTerminado(true);
            paqueteLoteRepository.save(paquete);
        }

        // Verificar si todos los paquetes del lote están terminados
        Lote lote = paquete.getLote();
        boolean todosPaquetesTerminados = lote.getListaPaquetes().stream()
                .allMatch(p -> Boolean.TRUE.equals(p.getIsTerminado()));

        if (todosPaquetesTerminados && !Boolean.TRUE.equals(lote.getIsTerminado())) {
            lote.setIsTerminado(true);
            loteRepository.save(lote);
        }

        return ResponseEntity.ok("Operación confirmada");
    }


    @PostMapping("/desactivar")
    public ResponseEntity<String> desactivarLote(@RequestParam Long id) {
        Optional<Lote> optionalLote = loteRepository.findById(id);
        if (optionalLote.isPresent()) {
            Lote lote = optionalLote.get();
            lote.setIsActive(false);
            loteRepository.save(lote);
            return ResponseEntity.ok("Desactivado");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        }
    }

    @PostMapping("/reactivar")
    public ResponseEntity<String> reactivarLote(@RequestParam Long id) {
        Optional<Lote> optionalLote = loteRepository.findById(id);
        if (optionalLote.isPresent()) {
            Lote lote = optionalLote.get();
            lote.setIsActive(true);
            loteRepository.save(lote);
            return ResponseEntity.ok("Reactivado");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        }
    }

}
