package com.palomino.confecontrol.repository;

import com.palomino.confecontrol.model.fixed.OperacionPrenda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperacionPrendaRepository extends JpaRepository<OperacionPrenda, Long> {
    List<OperacionPrenda> findByPrendaId(Long id);
}
