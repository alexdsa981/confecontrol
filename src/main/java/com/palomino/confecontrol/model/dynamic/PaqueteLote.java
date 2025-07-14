package com.palomino.confecontrol.model.dynamic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.palomino.confecontrol.model.fixed.Prenda;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@Entity
public class PaqueteLote {

    @Id
    @Column(name = "id_paquete_lote")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String codigo;
    private Boolean isTerminado;


    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario supervisorPaqueteLote;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "lote_id")
    private Lote lote;


    @OneToMany(mappedBy = "paqueteLote")
    private List<DetallePaqueteLote> listaDetallePaqueteLote;


}
