package com.palomino.confecontrol.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LoteConPaquetesDTO {
    private int cantidadLote;
    private Long tipoPrendaId;
    private List<Integer> paquetes;
    private Long supervisorId;
}
