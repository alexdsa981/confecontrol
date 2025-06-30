package com.palomino.confecontrol.model.fixed;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@Entity
public class OperacionPrenda {

    public OperacionPrenda(String nombre, BigDecimal precioNormal, BigDecimal precioHorasExtra, BigDecimal precioFeriado, Boolean isActive, Prenda prenda) {
        this.nombre = nombre;
        this.precioNormal = precioNormal;
        this.precioHorasExtra = precioHorasExtra;
        this.precioFeriado = precioFeriado;
        this.isActive = isActive;
        this.prenda = prenda;
    }

    @Id
    @Column(name = "id_operacion_prenda")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private BigDecimal precioNormal;
    @Column(nullable = false)
    private BigDecimal precioHorasExtra;
    @Column(nullable = false)
    private BigDecimal precioFeriado;

    @Column(nullable = false)
    private Boolean isActive;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "prenda_id", nullable = false)
    private Prenda prenda;


}
