package com.palomino.confecontrol.model.fixed;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PiezaPrenda {

    public PiezaPrenda(String nombre, Integer cantidad, Boolean isActive, Prenda prenda) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.isActive = isActive;
        this.prenda = prenda;
    }

    @Id
    @Column(name = "id_pieza_prenda")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private Boolean isActive;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "prenda_id", nullable = false)
    private Prenda prenda;



}
