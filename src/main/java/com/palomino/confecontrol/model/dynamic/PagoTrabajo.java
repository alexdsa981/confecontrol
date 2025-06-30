package com.palomino.confecontrol.model.dynamic;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PagoTrabajo {

    @Id
    @Column(name = "id_pago_trabajo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;


    private LocalDate fechaInicio;


    private LocalDate fechaFin;


    private BigDecimal totalAPagar;


    private BigDecimal subtotalPago;


    private BigDecimal subtotalDescuento;


    private LocalDateTime fecha;



}
