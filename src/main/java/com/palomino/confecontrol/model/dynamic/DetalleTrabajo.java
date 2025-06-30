package com.palomino.confecontrol.model.dynamic;

import com.palomino.confecontrol.model.fixed.OperacionPrenda;
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
public class DetalleTrabajo {
    @Id
    @Column(name = "id_detalle_trabajo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private LocalDateTime fecha;


    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "detalle_paquete_lote_id")
    private DetallePaqueteLote detallePaqueteLote;


    private BigDecimal monto;

}
