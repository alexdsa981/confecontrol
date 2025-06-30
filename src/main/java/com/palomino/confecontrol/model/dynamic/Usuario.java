package com.palomino.confecontrol.model.dynamic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.palomino.confecontrol.model.fixed.RolUsuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Usuario {

    @Id
    @Column(name = "id_usuario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String dni;

    private String numCelular;
    private String correo;

    @Column(nullable = false)
    private Boolean isActive;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "rol_usuario_id", nullable = false)
    private RolUsuario rolUsuario;


    @Column(nullable = false)
    private Boolean changedPass;


    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    private List<Marcacion> listaMarcaciones;

    @JsonIgnore
    @OneToMany(mappedBy = "creadorLote")
    private List<Lote> listaLotes;

    @JsonIgnore
    @OneToMany(mappedBy = "supervisorPaqueteLote")
    private List<PaqueteLote> listaPaqueteLotes;

    @JsonIgnore
    @OneToMany(mappedBy = "trabajador")
    private List<DetallePaqueteLote> listaDetallePaqueteLote;

    public void asignarYEncriptarPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }
}
