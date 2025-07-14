package com.palomino.confecontrol.model.dto;

import com.palomino.confecontrol.model.dynamic.DetalleDescuentos;
import com.palomino.confecontrol.model.dynamic.DetalleTrabajo;
import com.palomino.confecontrol.model.dynamic.Lote;
import com.palomino.confecontrol.model.dynamic.Usuario;
import lombok.Getter;
import lombok.Setter;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class pagoPorUsuarioDTO {
    private Usuario usuario;
    private List<ResumenLoteUsuarioDTO> resumenPorLote;
    private BigDecimal totalPago;
    private Boolean pagado;
}
