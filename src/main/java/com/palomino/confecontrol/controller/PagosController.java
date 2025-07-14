package com.palomino.confecontrol.controller;

import com.palomino.confecontrol.model.dto.ResumenLoteUsuarioDTO;
import com.palomino.confecontrol.model.dto.pagoPorUsuarioDTO;
import com.palomino.confecontrol.model.dynamic.*;
import com.palomino.confecontrol.repository.*;
import com.palomino.confecontrol.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/app/pagos")
public class PagosController {
    @Autowired
    LoteRepository loteRepository;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    DetalleDescuentoRepository detalleDescuentoRepository;
    @Autowired
    DetalleTrabajoRepository detalleTrabajoRepository;
    @Autowired
    PagoTrabajoRepository pagoTrabajoRepository;

//    public Model listarDetallePagosPorUsuario(Model model, Long idUsuario) {
//
//        //crear lista de DTO
//
//        //creo que seria un loop para crear de 1 en 1
//
//        //encontrar todos los usuarios y añadirlos al dto
//
//        //desde aqui todos los query seria en semana de lunes a sabado
//
//        //encontrar lote por usuario trabajo y añadirlo al dto
//        //se puede acceder al lote trabajado por un usuario de la siguiente manera: lote.listaPaquetes[0].listaDetallePaqueteLote[0].trabajador
//        //creo que esta parte está complicada porque lote tiene una lista de paquetes y el paquete tiene una lista de detalles y en los detalles está el trabajador, por lo que debe
//        //seleccionar los lotes que hayan sido trabajados por el trbajador buscado
//
//        //encontrar detalleDescuentos por usuario trabajo y lote(encontrados en el anterior de lote por usuario trabajo), se puede encontrar de la siguiente manera lote.listaDetalleDescuentos[0])
//
//        //encontrar detallePagos por usuario trabajo y lote (encontrados en el anterior de lote por usuario trabajo), se puede encontrar de la siguiente manera lote.listaDetalleTrabajo[0])
//
//        //por ultimo el total a pagar que seria sumar la lista de todos los detalle descuentos y detalle pagos encontrados, obviamente el total de pagos seria restado por el total de descuentos.
//        return model;
//    }



    public Model listarHistorialPago(Model model, Long idUsuario) {
        List<PagoTrabajo> pagos;

        if (idUsuario != null) {
            Usuario usuario = usuarioRepository.findById(idUsuario)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            pagos = pagoTrabajoRepository.findByUsuarioTrabajadorOrderByFechaDesc(usuario);
        } else {
            pagos = pagoTrabajoRepository.findAllByOrderByFechaDesc();
        }

        model.addAttribute("historialPagos", pagos);
        return model;
    }



        public Model listarDetallePagosPorUsuario(Model model, Long idUsuario) {
        LocalDate hoy = LocalDate.now();
        LocalDate[] semana = calcularSemana(hoy);
        LocalDate lunes = semana[0];
        LocalDate sabado = semana[1];

        List<Usuario> usuarios = (idUsuario != null)
                ? List.of(usuarioRepository.findById(idUsuario).orElseThrow())
                : usuarioRepository.findAll();

        List<pagoPorUsuarioDTO> listaPagosUsuarios = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            List<Lote> lotes = loteRepository.findLotesTrabajadosPorUsuario(usuario.getId(), lunes, sabado);

            List<ResumenLoteUsuarioDTO> resumenPorLote = new ArrayList<>();
            BigDecimal totalPago = BigDecimal.ZERO;
            BigDecimal totalDescuentos = BigDecimal.ZERO;

            for (Lote lote : lotes) {
                // Filtrar trabajos por usuario
                List<DetalleTrabajo> trabajos = lote.getListaDetalleTrabajo().stream()
                        .filter(p -> p.getUsuario().getId().equals(usuario.getId()))
                        .collect(Collectors.toList());

                BigDecimal subtotalPagos = trabajos.stream()
                        .map(DetalleTrabajo::getMonto)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                // Filtrar descuentos por usuario
                List<DetalleDescuentos> descuentos = lote.getListaDetalleDescuentos().stream()
                        .filter(d -> d.getUsuario().getId().equals(usuario.getId()))
                        .collect(Collectors.toList());

                BigDecimal subtotalDescuentos = descuentos.stream()
                        .map(DetalleDescuentos::getMonto)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                ResumenLoteUsuarioDTO resumen = new ResumenLoteUsuarioDTO();
                resumen.setLote(lote);
                resumen.setTrabajos(trabajos);
                resumen.setDescuentos(descuentos);
                resumen.setSubtotalPagos(subtotalPagos);
                resumen.setSubtotalDescuentos(subtotalDescuentos);
                resumenPorLote.add(resumen);

                totalPago = totalPago.add(subtotalPagos);
                totalDescuentos = totalDescuentos.add(subtotalDescuentos);
            }

            // Verificar si ya se registró un pago
            Optional<PagoTrabajo> pagoExistente = pagoTrabajoRepository
                    .findByUsuarioTrabajadorAndFechaInicioAndFechaFin(usuario, lunes, sabado);

            pagoPorUsuarioDTO dto = new pagoPorUsuarioDTO();
            dto.setUsuario(usuario);
            dto.setResumenPorLote(resumenPorLote);
            dto.setTotalPago(totalPago.subtract(totalDescuentos));
            dto.setPagado(pagoExistente.isPresent());

            listaPagosUsuarios.add(dto);
        }

        model.addAttribute("listaPagosUsuarios", listaPagosUsuarios);
        model.addAttribute("fechaInicio", lunes);
        model.addAttribute("fechaFin", sabado);

        return model;
    }




    public LocalDate[] calcularSemana(LocalDate fecha) {
        LocalDate lunes = fecha.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate sabado = lunes.plusDays(5);
        return new LocalDate[]{lunes, sabado};
    }


    @PostMapping("/admin/registrar-pago")
    public ResponseEntity<String> registrarPago(
            @RequestParam Long idUsuario,
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin,
            @RequestParam BigDecimal totalPago,
            @RequestParam BigDecimal subtotalPago,
            @RequestParam BigDecimal subtotalDescuento
    ) {
        PagoTrabajo pago = new PagoTrabajo();
        pago.setUsuarioTrabajador(usuarioRepository.findById(idUsuario).orElseThrow());
        pago.setUsuarioAdmin(usuarioService.getUsuarioPorId(usuarioService.getIDdeUsuarioLogeado())); // Usa tu campo de autenticación
        pago.setFechaInicio(LocalDate.parse(fechaInicio));
        pago.setFechaFin(LocalDate.parse(fechaFin));
        pago.setTotalAPagar(totalPago);
        pago.setSubtotalPago(subtotalPago);
        pago.setSubtotalDescuento(subtotalDescuento);
        pago.setFecha(LocalDateTime.now());

        pagoTrabajoRepository.save(pago);
        return ResponseEntity.ok("Pago registrado correctamente");
    }





}
