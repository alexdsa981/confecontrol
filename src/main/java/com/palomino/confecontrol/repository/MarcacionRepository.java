package com.palomino.confecontrol.repository;

import com.palomino.confecontrol.model.dynamic.Marcacion;
import com.palomino.confecontrol.model.dynamic.Usuario;
import com.palomino.confecontrol.model.fixed.Prenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MarcacionRepository extends JpaRepository<Marcacion, Long> {
    List<Marcacion> findByUsuarioAndFechaAndHoraSalidaIsNull(Usuario usuario, LocalDate fecha);
    List<Marcacion> findByUsuarioAndFecha(Usuario usuario, LocalDate fecha);
}
