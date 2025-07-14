package com.palomino.confecontrol.model.dynamic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.palomino.confecontrol.model.fixed.OperacionPrenda;
import com.palomino.confecontrol.model.fixed.Prenda;
import com.palomino.confecontrol.model.fixed.TipoDescuento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class DetallePaqueteLote {

    @Id
    @Column(name = "id_detalle_paquete_lote")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isNotificado;
    private Boolean isTerminado;

    @ManyToOne
    @JoinColumn(name = "operacion_prenda_id")
    private OperacionPrenda operacionPrenda;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario trabajador;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "paquete_lote_id")
    private PaqueteLote paqueteLote;

    @ManyToOne
    @JoinColumn(name = "tipo_descuento_id")
    private TipoDescuento tipoDescuento;

    private String descripcionObservacion;

}
