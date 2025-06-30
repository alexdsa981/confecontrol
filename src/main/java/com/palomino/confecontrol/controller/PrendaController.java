package com.palomino.confecontrol.controller;

import com.palomino.confecontrol.model.fixed.OperacionPrenda;
import com.palomino.confecontrol.model.fixed.PiezaPrenda;
import com.palomino.confecontrol.model.fixed.Prenda;
import com.palomino.confecontrol.repository.OperacionPrendaRepository;
import com.palomino.confecontrol.repository.PiezaPrendaRepository;
import com.palomino.confecontrol.repository.PrendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/app/prendas")
public class PrendaController {
    @Autowired
    PrendaRepository prendaRepository;
    @Autowired
    OperacionPrendaRepository operacionPrendaRepository;
    @Autowired
    PiezaPrendaRepository piezaPrendaRepository;


    public Model listarPrendas(Model model) {;
        model.addAttribute("ListaPrendas", prendaRepository.findAll());
        return model;
    }
    public  Model prendaSeleccionada(Long selectedId, Model model){
        List<Prenda> prendas = prendaRepository.findAll();
        if (selectedId == null && !prendas.isEmpty()) {
            selectedId = prendas.get(0).getId();
        }
        model.addAttribute("selectedId", selectedId);
        return model;
    }

    // Obtener operaciones por id de prenda
    @ResponseBody
    @GetMapping("/{id}/operaciones")
    public List<OperacionPrenda> operacionesPorPrenda(@PathVariable Long id) {
        return operacionPrendaRepository.findByPrendaId(id);
    }

    // Obtener piezas por id de prenda
    @ResponseBody
    @GetMapping("/{id}/piezas")
    public List<PiezaPrenda> piezasPorPrenda(@PathVariable Long id) {
        return piezaPrendaRepository.findByPrendaId(id);
    }

    //PRENDA
    // Crear prenda
    @PostMapping("/nuevo")
    public String crearPrenda(
            @RequestParam("nombre") String nombre,
            @RequestParam("costoTotal") BigDecimal costoTotal) {
        try {
            Prenda prenda = new Prenda();

            Integer ultimoNumero = prendaRepository.obtenerUltimoNumeroRegistro();
            if (ultimoNumero == null) {
                ultimoNumero = 0;
            }
            String codigo = String.format("PR-%03d", ultimoNumero + 1);
            prenda.setCodigo(codigo);

            prenda.setNombre(nombre);
            prenda.setCostoTotal(costoTotal);
            prenda.setIsActive(true);
            prendaRepository.save(prenda);
            return "redirect:/prendas?success&selectedId=" + prenda.getId();
        } catch (Exception e) {
            return "redirect:/prendas?error";
        }
    }
    @PostMapping("/editar")
    public String editarPrenda(
            @RequestParam("id") Long id,
            @RequestParam("nombre") String nombre,
            @RequestParam("costoTotal") BigDecimal costoTotal) {
        try {
            Prenda prenda = prendaRepository.findById(id).get();
            prenda.setNombre(nombre);
            prenda.setCostoTotal(costoTotal);
            prendaRepository.save(prenda);
            return "redirect:/prendas?success&selectedId=" + prenda.getId();
        } catch (Exception e) {
            return "redirect:/prendas?error";
        }
    }


    //PIEZA
    @PostMapping("/pieza/nuevo")
    public String crearPieza(
            @RequestParam("nombre") String nombre,
            @RequestParam("cantidad") Integer cantidad,
            @RequestParam("prendaId") Long prendaId) {
        try {
            PiezaPrenda pieza = new PiezaPrenda();
            pieza.setNombre(nombre);
            pieza.setCantidad(cantidad);
            pieza.setIsActive(true);
            pieza.setPrenda(prendaRepository.findById(prendaId).orElse(null));
            piezaPrendaRepository.save(pieza);
            return "redirect:/prendas?success&selectedId=" + pieza.getPrenda().getId();
        } catch (Exception e) {
            return "redirect:/prendas?error";
        }
    }
    @PostMapping("/pieza/editar")
    public String editarPieza(
            @RequestParam("id") Long id,
            @RequestParam("nombre") String nombre,
            @RequestParam("cantidad") Integer cantidad) {
        try {
            PiezaPrenda pieza = piezaPrendaRepository.findById(id).orElse(null);
            if (pieza == null) return "redirect:/admin/prendas?error=notfound";
            pieza.setNombre(nombre);
            pieza.setCantidad(cantidad);
            piezaPrendaRepository.save(pieza);
            return "redirect:/prendas?success&selectedId=" + pieza.getPrenda().getId();
        } catch (Exception e) {
            return "redirect:/prendas?error";
        }
    }


    //OPERACION PRENDA
    @PostMapping("/operacion/nuevo")
    public String crearOperacion(
            @RequestParam("nombre") String nombre,
            @RequestParam("precioNormal") BigDecimal precioNormal,
            @RequestParam("precioHorasExtra") BigDecimal precioHorasExtra,
            @RequestParam("precioFeriado") BigDecimal precioFeriado,
            @RequestParam("prendaId") Long prendaId) {
        try {
            OperacionPrenda operacion = new OperacionPrenda();
            operacion.setNombre(nombre);
            operacion.setPrecioNormal(precioNormal);
            operacion.setPrecioHorasExtra(precioHorasExtra);
            operacion.setPrecioFeriado(precioFeriado);
            operacion.setIsActive(true);
            operacion.setPrenda(prendaRepository.findById(prendaId).orElse(null));
            operacionPrendaRepository.save(operacion);
            return "redirect:/prendas?success&selectedId=" + operacion.getPrenda().getId();
        } catch (Exception e) {
            return "redirect:/prendas?error";
        }
    }
    @PostMapping("/operacion/editar")
    public String editarOperacion(
            @RequestParam("id") Long id,
            @RequestParam("nombre") String nombre,
            @RequestParam("precioNormal") BigDecimal precioNormal,
            @RequestParam("precioHorasExtra") BigDecimal precioHorasExtra,
            @RequestParam("precioFeriado") BigDecimal precioFeriado) {
        try {
            OperacionPrenda operacion = operacionPrendaRepository.findById(id).orElse(null);
            if (operacion == null) return "redirect:/admin/prendas?error=notfound";
            operacion.setNombre(nombre);
            operacion.setPrecioNormal(precioNormal);
            operacion.setPrecioHorasExtra(precioHorasExtra);
            operacion.setPrecioFeriado(precioFeriado);
            operacionPrendaRepository.save(operacion);
            return "redirect:/prendas?success&selectedId=" + operacion.getPrenda().getId();
        } catch (Exception e) {
            return "redirect:/prendas?error";
        }
    }


    //ACTIVAR Y DESACTIVAR
    // Activar prenda
    @GetMapping("/activar/{id}")
    public String activarPrenda(@PathVariable Long id) {
        Prenda prenda = prendaRepository.findById(id).get();
        prenda.setIsActive(true);
        prendaRepository.save(prenda);
        return "redirect:/prendas?success&selectedId=" + prenda.getId();
    }

    // Desactivar prenda
    @GetMapping("/desactivar/{id}")
    public String desactivarPrenda(@PathVariable Long id) {
        Prenda prenda = prendaRepository.findById(id).get();
        prenda.setIsActive(false);
        prendaRepository.save(prenda);
        return "redirect:/prendas?success&selectedId=" + prenda.getId();
    }

    // Activar pieza
    @GetMapping("/pieza/activar/{id}")
    public String activarPieza(@PathVariable Long id) {
        PiezaPrenda pieza = piezaPrendaRepository.findById(id).get();
        pieza.setIsActive(true);
        piezaPrendaRepository.save(pieza);
        return "redirect:/prendas?success&selectedId=" + pieza.getPrenda().getId();
    }

    // Desactivar pieza
    @GetMapping("/pieza/desactivar/{id}")
    public String desactivarPieza(@PathVariable Long id) {
        PiezaPrenda pieza = piezaPrendaRepository.findById(id).get();
        pieza.setIsActive(false);
        piezaPrendaRepository.save(pieza);
        return "redirect:/prendas?success&selectedId=" + pieza.getPrenda().getId();
    }

    // Activar operación
    @GetMapping("/operacion/activar/{id}")
    public String activarOperacion(@PathVariable Long id) {
        OperacionPrenda operacion = operacionPrendaRepository.findById(id).get();
        operacion.setIsActive(true);
        operacionPrendaRepository.save(operacion);
        return "redirect:/prendas?success&selectedId=" + operacion.getPrenda().getId();
    }

    // Desactivar operación
    @GetMapping("/operacion/desactivar/{id}")
    public String desactivarOperacion(@PathVariable Long id) {
        OperacionPrenda operacion = operacionPrendaRepository.findById(id).get();
        operacion.setIsActive(false);
        operacionPrendaRepository.save(operacion);
        return "redirect:/prendas?success&selectedId=" + operacion.getPrenda().getId();
    }


}
