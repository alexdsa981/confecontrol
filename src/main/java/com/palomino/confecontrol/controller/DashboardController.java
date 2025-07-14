package com.palomino.confecontrol.controller;

import com.palomino.confecontrol.repository.DetalleTrabajoRepository;
import com.palomino.confecontrol.repository.MarcacionRepository;
import com.palomino.confecontrol.repository.PagoTrabajoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    MarcacionRepository marcacionRepository;

    @Autowired
    DetalleTrabajoRepository detalleTrabajoRepository;

    @Autowired
    PagoTrabajoRepository pagoTrabajoRepository;

    public String mostrarDashboard(Model model) {
        LocalDate hoy = LocalDate.now();
        LocalDate[] semana = calcularSemana(hoy);
        LocalDate lunes = semana[0];
        LocalDate sabado = semana[1];

        // Métricas rápidas
        int asistenciasHoy = marcacionRepository.contarAsistenciasHoy(hoy);
        int tardanzasHoy = marcacionRepository.contarTardanzasHoy(hoy);
        BigDecimal produccionHoy = detalleTrabajoRepository.produccionTotalHoy(hoy);
        BigDecimal totalPagado = pagoTrabajoRepository.totalPagadoSemana(lunes, sabado);

        // Producción diaria de la semana
        List<Object[]> produccionSemanal = detalleTrabajoRepository.produccionSemanal(lunes, sabado);

        List<String> dias = new ArrayList<>();
        List<BigDecimal> montos = new ArrayList<>();

        for (Object[] fila : produccionSemanal) {
            dias.add(fila[0].toString());
            montos.add((BigDecimal) fila[1]);
        }

        model.addAttribute("asistenciasHoy", asistenciasHoy);
        model.addAttribute("tardanzasHoy", tardanzasHoy);
        model.addAttribute("produccionHoy", produccionHoy);
        model.addAttribute("totalPagado", totalPagado);
        model.addAttribute("diasProduccion", dias);
        model.addAttribute("montosProduccion", montos);

        return "admin/dashboard";
    }

    private LocalDate[] calcularSemana(LocalDate hoy) {
        LocalDate lunes = hoy.with(DayOfWeek.MONDAY);
        LocalDate sabado = lunes.plusDays(5);
        return new LocalDate[]{lunes, sabado};
    }
}
