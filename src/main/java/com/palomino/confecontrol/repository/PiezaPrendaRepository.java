package com.palomino.confecontrol.repository;

import com.palomino.confecontrol.model.fixed.OperacionPrenda;
import com.palomino.confecontrol.model.fixed.PiezaPrenda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PiezaPrendaRepository extends JpaRepository<PiezaPrenda, Long> {

    List<PiezaPrenda> findByPrendaId(Long id);

}
