package com.palomino.confecontrol.model.dynamic;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Marcacion {
    public Marcacion() {
    }

    @Id
    @Column(name = "id_marcacion")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEstadoSalida() {
        return estadoSalida;
    }

    public void setEstadoSalida(Boolean estadoSalida) {
        this.estadoSalida = estadoSalida;
    }

    public LocalTime getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(LocalTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    public Boolean getEstadoLlegada() {
        return estadoLlegada;
    }

    public void setEstadoLlegada(Boolean estadoLlegada) {
        this.estadoLlegada = estadoLlegada;
    }

    public LocalTime getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(LocalTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
