package com.palomino.confecontrol.repository;

import com.palomino.confecontrol.model.fixed.RolUsuario;
import com.palomino.confecontrol.model.fixed.TipoDescuento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoDescuentoRepository extends JpaRepository<TipoDescuento, Long> {
}
