package com.palomino.confecontrol.other;

import com.palomino.confecontrol.model.dynamic.Usuario;
import com.palomino.confecontrol.model.fixed.*;
import com.palomino.confecontrol.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RolUsuarioRepository rolUsuarioRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PrendaRepository prendaRepository;
    @Autowired
    private PiezaPrendaRepository piezaPrendaRepository;
    @Autowired
    private OperacionPrendaRepository operacionPrendaRepository;
    @Autowired
    TipoDescuentoRepository tipoDescuentoRepository;



    @Override
    public void run(String... args) {
        if (rolUsuarioRepository.count() == 0) {
            RolUsuario rolAdmin = new RolUsuario("Administrador");
            rolUsuarioRepository.save(rolAdmin);

            RolUsuario rolSupervisor = new RolUsuario("Supervisor");
            rolUsuarioRepository.save(rolSupervisor);

            RolUsuario rolOperario = new RolUsuario("Operario");
            rolUsuarioRepository.save(rolOperario);

            String passwordEncriptada = "$2a$12$7SW6dd16qcrYSdV0L4Uzp.qzCEe6ricYOH9fdr1r/bGlF2ItBun4a"; // Contraseña encriptada

            Usuario admin = new Usuario();
            admin.setChangedPass(true);
            admin.setCorreo("admin@sistema.com");
            admin.setDni("10000001");
            admin.setIsActive(true);
            admin.setNombre("Administrador General");
            admin.setNumCelular("900000001");
            admin.setPassword(passwordEncriptada);
            admin.setUsername("admin");
            admin.setRolUsuario(rolAdmin);
            usuarioRepository.save(admin);

            Usuario supervisor = new Usuario();
            supervisor.setChangedPass(true);
            supervisor.setCorreo("supervisor1@sistema.com");
            supervisor.setDni("10000002");
            supervisor.setIsActive(true);
            supervisor.setNombre("Supervisor de Producción1");
            supervisor.setNumCelular("900000002");
            supervisor.setPassword(passwordEncriptada);
            supervisor.setUsername("supervisor");
            supervisor.setRolUsuario(rolSupervisor);
            usuarioRepository.save(supervisor);

            Usuario supervisor2 = new Usuario();
            supervisor2.setChangedPass(true);
            supervisor2.setCorreo("supervisor2@sistema.com");
            supervisor2.setDni("11230002");
            supervisor2.setIsActive(true);
            supervisor2.setNombre("Supervisor de Producción2");
            supervisor2.setNumCelular("901232002");
            supervisor2.setPassword(passwordEncriptada);
            supervisor2.setUsername("supervisor2");
            supervisor2.setRolUsuario(rolSupervisor);
            usuarioRepository.save(supervisor2);


            Usuario colaborador = new Usuario();
            colaborador.setChangedPass(true);
            colaborador.setCorreo("operario1@sistema.com");
            colaborador.setDni("10000003");
            colaborador.setIsActive(true);
            colaborador.setNombre("Operario de Línea1");
            colaborador.setNumCelular("900000003");
            colaborador.setPassword(passwordEncriptada);
            colaborador.setUsername("operario1");
            colaborador.setRolUsuario(rolOperario);
            usuarioRepository.save(colaborador);


            Usuario colaborador2 = new Usuario();
            colaborador2.setChangedPass(true);
            colaborador2.setCorreo("operario2@sistema.com");
            colaborador2.setDni("10003212");
            colaborador2.setIsActive(true);
            colaborador2.setNombre("Operario de Línea2");
            colaborador2.setNumCelular("90323");
            colaborador2.setPassword(passwordEncriptada);
            colaborador2.setUsername("operario2");
            colaborador2.setRolUsuario(rolOperario);
            usuarioRepository.save(colaborador2);

            Usuario colaborador3 = new Usuario();
            colaborador3.setChangedPass(true);
            colaborador3.setCorreo("operario3@sistema.com");
            colaborador3.setDni("100123212");
            colaborador3.setIsActive(true);
            colaborador3.setNombre("Operario de Línea3");
            colaborador3.setNumCelular("90323");
            colaborador3.setPassword(passwordEncriptada);
            colaborador3.setUsername("operario3");
            colaborador3.setRolUsuario(rolOperario);
            usuarioRepository.save(colaborador3);
        }

        if (tipoDescuentoRepository.count() == 0) {
            List<TipoDescuento> tipos = List.of(
                    new TipoDescuento("Prenda dañada", "Descuento por prendas que no cumplen con los estándares de calidad"),
                    new TipoDescuento("Tardanza", "Descuento por entregar con retraso la producción asignada"),
                    new TipoDescuento("Inasistencia", "Descuento por no presentarse a trabajar sin justificación"),
                    new TipoDescuento("Pérdida de materiales", "Descuento por pérdida o daño de materiales provistos"),
                    new TipoDescuento("Error en costura", "Descuento por errores evidentes en la confección")
            );
            tipoDescuentoRepository.saveAll(tipos);
        }



        if (prendaRepository.count() == 0) {
            Prenda polo = new Prenda("P001", "Polo", BigDecimal.valueOf(25.50), true);
            Prenda camisa = new Prenda("C001", "Camisa", BigDecimal.valueOf(40.00), true);
            Prenda pantalon = new Prenda("P002", "Pantalón", BigDecimal.valueOf(55.00), true);
            Prenda falda = new Prenda("F001", "Falda", BigDecimal.valueOf(30.00), true);

            prendaRepository.saveAll(List.of(polo, camisa, pantalon, falda));

            piezaPrendaRepository.saveAll(List.of(
                    // Polo
                    new PiezaPrenda( "Cuello", 1, true, polo),
                    new PiezaPrenda( "Costado", 2, true, polo),
                    new PiezaPrenda( "Mangas", 2, true, polo),
                    new PiezaPrenda( "Espalda", 1, true, polo),
                    new PiezaPrenda( "Frente", 1, true, polo),
                    // Camisa
                    new PiezaPrenda( "Cuello", 1, true, camisa),
                    new PiezaPrenda( "Mangas", 2, true, camisa),
                    new PiezaPrenda( "Espalda", 1, true, camisa),
                    new PiezaPrenda( "Frente", 1, true, camisa),
                    new PiezaPrenda( "Puños", 1, true, camisa),
                    // Pantalón
                    new PiezaPrenda( "Pierna", 2, true, pantalon),
                    new PiezaPrenda( "Bolsillos", 2, true, pantalon),
                    new PiezaPrenda( "Cintura", 1, true, pantalon),
                    new PiezaPrenda( "Bragueta", 1, true, pantalon),
                    // Falda
                    new PiezaPrenda( "Cintura", 1, true, falda),
                    new PiezaPrenda( "Cuerpo", 1, true, falda),
                    new PiezaPrenda( "Forro", 1, true, falda)
            ));

            operacionPrendaRepository.saveAll(List.of(
                    // Polo
                    new OperacionPrenda("Cosido de costados", BigDecimal.valueOf(1.50), BigDecimal.valueOf(2.00), BigDecimal.valueOf(2.50), true, polo),
                    new OperacionPrenda("Unión de mangas", BigDecimal.valueOf(1.20), BigDecimal.valueOf(1.80), BigDecimal.valueOf(2.00), true, polo),
                    new OperacionPrenda("Pegado de cuello", BigDecimal.valueOf(1.00), BigDecimal.valueOf(1.30), BigDecimal.valueOf(1.50), true, polo),

                    // Camisa
                    new OperacionPrenda("Pegado de cuello", BigDecimal.valueOf(2.00), BigDecimal.valueOf(2.50), BigDecimal.valueOf(3.00), true, camisa),
                    new OperacionPrenda("Costura de mangas", BigDecimal.valueOf(1.80), BigDecimal.valueOf(2.20), BigDecimal.valueOf(2.50), true, camisa),
                    new OperacionPrenda("Colocación de botones", BigDecimal.valueOf(1.50), BigDecimal.valueOf(1.80), BigDecimal.valueOf(2.00), true, camisa),

                    // Pantalón
                    new OperacionPrenda("Costura de piernas", BigDecimal.valueOf(2.50), BigDecimal.valueOf(3.00), BigDecimal.valueOf(3.50), true, pantalon),
                    new OperacionPrenda("Colocación de bragueta", BigDecimal.valueOf(1.70), BigDecimal.valueOf(2.00), BigDecimal.valueOf(2.50), true, pantalon),
                    new OperacionPrenda("Colocación de bolsillos", BigDecimal.valueOf(2.00), BigDecimal.valueOf(2.40), BigDecimal.valueOf(2.80), true, pantalon),

                    // Falda
                    new OperacionPrenda("Costura de cintura", BigDecimal.valueOf(1.40), BigDecimal.valueOf(1.80), BigDecimal.valueOf(2.00), true, falda),
                    new OperacionPrenda("Unión de forro", BigDecimal.valueOf(1.60), BigDecimal.valueOf(2.00), BigDecimal.valueOf(2.20), true, falda),
                    new OperacionPrenda("Cierre de falda", BigDecimal.valueOf(1.90), BigDecimal.valueOf(2.20), BigDecimal.valueOf(2.50), true, falda)
            ));

        }
    }
}
