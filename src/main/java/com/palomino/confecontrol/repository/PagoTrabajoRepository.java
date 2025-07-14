package com.palomino.confecontrol.repository;

import com.palomino.confecontrol.model.dynamic.PagoTrabajo;
import com.palomino.confecontrol.model.dynamic.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PagoTrabajoRepository extends JpaRepository<PagoTrabajo, Long> {
    List<PagoTrabajo> findByUsuarioTrabajadorOrderByFechaDesc(Usuario usuario);

    List<PagoTrabajo> findAllByOrderByFechaDesc();

    Optional<PagoTrabajo> findByUsuarioTrabajadorAndFechaInicioAndFechaFin(Usuario usuarioTrabajador, LocalDate fechaInicio, LocalDate fechaFin);



    @Query("SELECT COALESCE(SUM(p.totalAPagar), 0) FROM PagoTrabajo p WHERE p.fechaInicio = :lunes AND p.fechaFin = :sabado")
    BigDecimal totalPagadoSemana(@Param("lunes") LocalDate lunes, @Param("sabado") LocalDate sabado);

}
