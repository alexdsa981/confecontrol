package com.palomino.confecontrol.model.dynamic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.palomino.confecontrol.model.fixed.TipoDescuento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class DetalleDescuentos {
    @Id
    @Column(name = "id_detalle_descuento")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "lote_id")
    private Lote lote;
    private LocalDateTime fecha;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "tipo_descuento_id")
    private TipoDescuento tipoDescuento;
    private BigDecimal monto;
}
