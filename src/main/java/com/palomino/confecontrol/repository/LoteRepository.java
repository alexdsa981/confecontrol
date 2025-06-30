package com.palomino.confecontrol.repository;

import com.palomino.confecontrol.model.dynamic.Lote;
import com.palomino.confecontrol.model.dynamic.Marcacion;
import com.palomino.confecontrol.model.dynamic.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LoteRepository extends JpaRepository<Lote, Long> {
    @Query("SELECT COUNT(l) FROM Lote l WHERE l.fechaCreacion = :fecha")
    long countByFechaCreacion(@Param("fecha") LocalDate fecha);

    List<Lote> findByIsActiveTrue();
    List<Lote> findByIsActiveFalse();
    List<Lote> findByIsActiveTrueAndIsTerminadoFalse();

}
