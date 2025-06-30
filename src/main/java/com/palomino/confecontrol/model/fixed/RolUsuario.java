package com.palomino.confecontrol.model.fixed;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.palomino.confecontrol.model.dynamic.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class RolUsuario {
    public RolUsuario(String nombre) {
        this.nombre = nombre;
    }

    @Id
    @Column(name = "id_rol")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombre;

    @JsonIgnore
    @OneToMany(mappedBy = "rolUsuario")
    private List<Usuario> listaUsuarios;


}