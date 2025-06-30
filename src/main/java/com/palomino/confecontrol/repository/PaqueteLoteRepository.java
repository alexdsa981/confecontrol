package com.palomino.confecontrol.repository;

import com.palomino.confecontrol.model.dynamic.Lote;
import com.palomino.confecontrol.model.dynamic.PaqueteLote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaqueteLoteRepository extends JpaRepository<PaqueteLote, Long> {
    List<PaqueteLote> findByLoteId(Long idLote);
    List<PaqueteLote> findByLoteIsActiveTrue();

}
