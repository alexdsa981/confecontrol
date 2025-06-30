package com.palomino.confecontrol.repository;

import com.palomino.confecontrol.model.dynamic.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificacionesRepository extends JpaRepository<Notificacion, Long> {

    @Query("SELECT n FROM Notificacion n WHERE n.usuario.id = :usuarioId")
    List<Notificacion> BuscarPorIdUsuario(@Param("usuarioId") Long usuarioId);

    @Query(value = "SELECT * FROM notificacion n WHERE n.usuario_id = :usuarioId ORDER BY n.fecha DESC, n.hora DESC LIMIT 15", nativeQuery = true)
    List<Notificacion> findTop15ByUsuarioId(@Param("usuarioId") Long usuarioId);



}
