package com.palomino.confecontrol.model.fixed;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.palomino.confecontrol.model.dynamic.Lote;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Prenda {
    public Prenda(String codigo, String nombre, BigDecimal costoTotal, Boolean isActive) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.costoTotal = costoTotal;
        this.isActive = isActive;
    }

    @Id
    @Column(name = "id_prenda")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private BigDecimal costoTotal;

    @Column(nullable = false)
    private Boolean isActive;

    @JsonIgnore
    @OneToMany(mappedBy = "prenda")
    private List<Lote> listaLotes;

    @JsonIgnore
    @OneToMany(mappedBy = "prenda")
    private List<OperacionPrenda> listaOperaciones;

    @JsonIgnore
    @OneToMany(mappedBy = "prenda")
    private List<OperacionPrenda> listaPiezas;


}
