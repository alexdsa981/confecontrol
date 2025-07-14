package com.palomino.confecontrol.repository;

import com.palomino.confecontrol.model.dynamic.DetalleDescuentos;
import com.palomino.confecontrol.model.dynamic.DetalleTrabajo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleDescuentoRepository extends JpaRepository<DetalleDescuentos, Long> {

}
