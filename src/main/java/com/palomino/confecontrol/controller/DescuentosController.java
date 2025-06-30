package com.palomino.confecontrol.controller;

import com.palomino.confecontrol.repository.TipoDescuentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/app/descuentos")
public class DescuentosController {

    @Autowired
    TipoDescuentoRepository tipoDescuentoRepository;

    public Model listarTiposDescuento(Model model) {;
        model.addAttribute("ListaTiposDescuento", tipoDescuentoRepository.findAll());
        return model;
    }
}
