package com.palomino.confecontrol.repository;

import com.palomino.confecontrol.model.dynamic.DetallePaqueteLote;
import com.palomino.confecontrol.model.dynamic.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DetallePaqueteLoteRepository extends JpaRepository<DetallePaqueteLote, Long> {
    List<DetallePaqueteLote> findByPaqueteLoteId(Long paqueteId);

    @Query("SELECT d FROM DetallePaqueteLote d WHERE d.paqueteLote.lote.id = :loteId AND d.operacionPrenda.id = :operacionId")
    List<DetallePaqueteLote> findByLoteIdAndOperacionId(@Param("loteId") Long loteId, @Param("operacionId") Long operacionId);

    List<DetallePaqueteLote> findByPaqueteLoteLoteIsActiveTrue();

    List<DetallePaqueteLote> findByTrabajadorIdAndPaqueteLoteLoteIsActiveTrue(Long trabajadorId);

}
