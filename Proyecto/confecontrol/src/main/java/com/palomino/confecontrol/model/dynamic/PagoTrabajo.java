package com.palomino.confecontrol.model.dynamic;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class PagoTrabajo {
    public PagoTrabajo() {
    }

    @Id
    @Column(name = "id_pago_trabajo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private LocalDate fechaInicio;

    @Column(nullable = false)
    private LocalDate fechaFin;

    @Column(nullable = false)
    private BigDecimal totalAPagar;

    @Column(nullable = false)
    private BigDecimal subtotalPago;

    @Column(nullable = false)
    private BigDecimal subtotalDescuento;

    @Column(nullable = false)
    private LocalDateTime fecha;


    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public BigDecimal getTotalAPagar() {
        return totalAPagar;
    }

    public void setTotalAPagar(BigDecimal totalAPagar) {
        this.totalAPagar = totalAPagar;
    }

    public BigDecimal getSubtotalPago() {
        return subtotalPago;
    }

    public void setSubtotalPago(BigDecimal subtotalPago) {
        this.subtotalPago = subtotalPago;
    }

    public BigDecimal getSubtotalDescuento() {
        return subtotalDescuento;
    }

    public void setSubtotalDescuento(BigDecimal subtotalDescuento) {
        this.subtotalDescuento = subtotalDescuento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
