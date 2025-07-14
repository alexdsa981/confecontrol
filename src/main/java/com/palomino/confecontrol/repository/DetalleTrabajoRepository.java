package com.palomino.confecontrol.repository;

import com.palomino.confecontrol.model.dynamic.DetallePaqueteLote;
import com.palomino.confecontrol.model.dynamic.DetalleTrabajo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DetalleTrabajoRepository extends JpaRepository<DetalleTrabajo, Long> {



    @Query(value = """
    SELECT COALESCE(SUM(sub.total), 0)
    FROM (
        SELECT l.id_lote, l.cantidad_prenda * p.costo_total AS total
        FROM lote l
        JOIN paquete_lote pqt ON l.id_lote = pqt.lote_id
        JOIN detalle_paquete_lote dpl ON pqt.id_paquete_lote = dpl.paquete_lote_id
        JOIN operacion_prenda op ON dpl.operacion_prenda_id = op.id_operacion_prenda
        JOIN prenda p ON op.prenda_id = p.id_prenda
        WHERE DATE(l.fecha_creacion) = :fecha
        GROUP BY l.id_lote, l.cantidad_prenda, p.costo_total
    ) AS sub
""", nativeQuery = true)
    BigDecimal produccionTotalHoy(@Param("fecha") LocalDate fecha);

    @Query(value = """
    SELECT dia, SUM(total) as total_por_dia FROM (
        SELECT DATE(l.fecha_creacion) AS dia,
               l.id_lote,
               (l.cantidad_prenda * p.costo_total) AS total
        FROM lote l
        JOIN paquete_lote pqt ON l.id_lote = pqt.lote_id
        JOIN detalle_paquete_lote dpl ON pqt.id_paquete_lote = dpl.paquete_lote_id
        JOIN operacion_prenda op ON dpl.operacion_prenda_id = op.id_operacion_prenda
        JOIN prenda p ON op.prenda_id = p.id_prenda
        WHERE DATE(l.fecha_creacion) BETWEEN :inicio AND :fin
        GROUP BY l.id_lote, l.cantidad_prenda, p.costo_total, l.fecha_creacion
    ) sub
    GROUP BY dia
    ORDER BY dia
""", nativeQuery = true)
    List<Object[]> produccionSemanal(@Param("inicio") LocalDate inicio, @Param("fin") LocalDate fin);


}
