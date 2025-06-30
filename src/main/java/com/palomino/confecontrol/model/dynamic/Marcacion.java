package com.palomino.confecontrol.model.dynamic;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Marcacion {

    @Id
    @Column(name = "id_marcacion")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(nullable = false)
    private LocalDate fecha;

    private LocalTime horaEntrada;
    private Boolean estadoLlegada;

    private LocalTime horaSalida;
    private Boolean estadoSalida;

    @Transient
    private String horaEntradaFormateada;

    @Transient
    private String horaSalidaFormateada;

    public String getHoraEntradaFormateada() {
        if (horaEntrada != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return horaEntrada.atDate(fecha).format(formatter);
        }
        return "";
    }

    public String getHoraSalidaFormateada() {
        if (horaSalida != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return horaSalida.atDate(fecha).format(formatter);
        }
        return "";
    }


}
