
INSERT INTO `confecontrol`.`rol_usuario`
(`id_rol`,
`nombre`)
VALUES
(1,
"Administrador");


INSERT INTO `confecontrol`.`usuario`
(`id_usuario`,
`changed_pass`,
`correo`,
`dni`,
`is_active`,
`nombre`,
`num_celular`,
`password`,
`username`,
`rol_usuario_id`)
VALUES	
(1,
1,
"dsd6@gmail.com",
"21311",
1,
"Admin User Prueba",
"12312",
"$2a$12$7SW6dd16qcrYSdV0L4Uzp.qzCEe6ricYOH9fdr1r/bGlF2ItBun4a",
"admin",
1);



INSERT INTO `confecontrol`.`prenda` (`id_prenda`, `codigo`, `costo_total`, `is_active`, `nombre`) VALUES
(1, 'P001', 25.50, 1, 'Polo'),
(2, 'C001', 40.00, 1, 'Camisa'),
(3, 'P002', 55.00, 1, 'Pantalón'),
(4, 'F001', 30.00, 1, 'Falda');

-- Piezas para Polo
INSERT INTO `confecontrol`.`pieza_prenda` (`id_pieza_prenda`, `cantidad`, `is_active`, `nombre`, `prenda_id`) VALUES
(1, 1, 1, 'Cuello', 1),
(2, 2, 1, 'Costado', 1),
(3, 2, 1, 'Mangas', 1),
(4, 1, 1, 'Espalda', 1),
(5, 1, 1, 'Frente', 1);

-- Piezas para Camisa
INSERT INTO `confecontrol`.`pieza_prenda` VALUES
(6, 1, 1, 'Cuello', 2),
(7, 2, 1, 'Mangas', 2),
(8, 1, 1, 'Espalda', 2),
(9, 1, 1, 'Frente', 2),
(10, 1, 1, 'Puños', 2);

-- Piezas para Pantalón
INSERT INTO `confecontrol`.`pieza_prenda` VALUES
(11, 2, 1, 'Pierna', 3),
(12, 2, 1, 'Bolsillos', 3),
(13, 1, 1, 'Cintura', 3),
(14, 1, 1, 'Bragueta', 3);

-- Piezas para Falda
INSERT INTO `confecontrol`.`pieza_prenda` VALUES
(15, 1, 1, 'Cintura', 4),
(16, 1, 1, 'Cuerpo', 4),
(17, 1, 1, 'Forro', 4);

-- Operaciones para Polo
INSERT INTO `confecontrol`.`operacion_prenda` (`id_operacion_prenda`, `is_active`, `nombre`, `precio_feriado`, `precio_horas_extra`, `precio_normal`, `prenda_id`) VALUES
(1, 1, 'Cosido de costados', 2.50, 2.00, 1.50, 1),
(2, 1, 'Unión de mangas', 2.00, 1.80, 1.20, 1),
(3, 1, 'Pegado de cuello', 1.50, 1.30, 1.00, 1);

-- Operaciones para Camisa
INSERT INTO `confecontrol`.`operacion_prenda` VALUES
(4, 1, 'Pegado de cuello', 3.00, 2.50, 2.00, 2),
(5, 1, 'Costura de mangas', 2.50, 2.20, 1.80, 2),
(6, 1, 'Colocación de botones', 2.00, 1.80, 1.50, 2);

-- Operaciones para Pantalón
INSERT INTO `confecontrol`.`operacion_prenda` VALUES
(7, 1, 'Costura de piernas', 3.50, 3.00, 2.50, 3),
(8, 1, 'Colocación de bragueta', 2.50, 2.00, 1.70, 3),
(9, 1, 'Colocación de bolsillos', 2.80, 2.40, 2.00, 3);

-- Operaciones para Falda
INSERT INTO `confecontrol`.`operacion_prenda` VALUES
(10, 1, 'Costura de cintura', 2.00, 1.80, 1.40, 4),
(11, 1, 'Unión de forro', 2.20, 2.00, 1.60, 4),
(12, 1, 'Cierre de falda', 2.50, 2.20, 1.90, 4);
