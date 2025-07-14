package com.palomino.confecontrol.model.dynamic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.palomino.confecontrol.model.fixed.Prenda;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Lote {

    @Id
    @Column(name = "id_lote")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo;
    private Integer cantidadPrenda;
    @ManyToOne
    @JoinColumn(name = "prenda_id")
    private Prenda prenda;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario creadorLote;
    @OneToMany(mappedBy = "lote")
    private List<PaqueteLote> listaPaquetes;
    @OneToMany(mappedBy = "lote")
    private List<DetalleDescuentos> listaDetalleDescuentos;
    @OneToMany(mappedBy = "lote")
    private List<DetalleTrabajo> listaDetalleTrabajo;
    private Boolean isTerminado;
    private Boolean isActive;
    private LocalDate fechaCreacion;
    private LocalTime horaCreacion;
    private LocalDate fechaFin;
    private LocalTime horaFin;
}
