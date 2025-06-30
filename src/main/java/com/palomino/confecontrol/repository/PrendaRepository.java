package com.palomino.confecontrol.repository;

import com.palomino.confecontrol.model.fixed.Prenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PrendaRepository extends JpaRepository<Prenda, Long> {
    @Query("SELECT MAX(CAST(SUBSTRING(p.codigo, 4) AS int)) FROM Prenda p")
    Integer obtenerUltimoNumeroRegistro();
}
