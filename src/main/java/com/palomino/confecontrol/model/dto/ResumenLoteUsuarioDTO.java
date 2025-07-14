package com.palomino.confecontrol.model.dto;

import com.palomino.confecontrol.model.dynamic.DetalleDescuentos;
import com.palomino.confecontrol.model.dynamic.DetalleTrabajo;
import com.palomino.confecontrol.model.dynamic.Lote;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
@Getter
@Setter
public class ResumenLoteUsuarioDTO {
    private Lote lote;
    private List<DetalleTrabajo> trabajos;
    private List<DetalleDescuentos> descuentos;
    private BigDecimal subtotalPagos;
    private BigDecimal subtotalDescuentos;
}
