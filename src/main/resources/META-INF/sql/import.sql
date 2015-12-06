INSERT INTO usuario (id_usuario, apellido, email, nombre, num_doc, password, tipo_doc)
 VALUES (1, "Usuario1", "usuario1@hotmail.com", "Augusto", 37708822, "usuario", 0);

INSERT INTO deporte (id_deporte, nom_deporte) 
values (1, "Rugby"), (2, "Futbol"), (3, "Tenis"),
 (4, "Basquet"), (5, "Voley"), (6, "Ping Pong"),
 (7, "Futbol 5"), (8, "Handball"), (9, "Seven");

INSERT INTO lugarderealizacion (codigo_lugar, nom_lugar, id_usuario, eliminado)
 VALUES (1, "Campo de Deportes", 1, 0),
 (2, "CRAI", 1, 0),
 (3, "Cantona", 1, 0),
 (4, "Marado", 1, 0),
 (5, "Seven", 1, 0),
 (6, "Il Calcio", 1, 0),
 (7, "El Uniazo", 1, 0),
 (8, "Marangoni", 1, 0),
 (9, "Lawn Tennis Club", 1, 0),
 (10, "Macabi", 1, 0),
 (11, "Las Heras F5", 1, 0),
 (12, "Canchas Ping Pong", 1, 0);

INSERT INTO se_practica_en (id_deporte, id_lugar) 
VALUES (1, 1), (1, 2), (2, 1), (2,3), (2,4), (2,5), (2,6), (2,7), (2,8), (3,9), (4,10);

INSERT INTO fixture (id_fixture) values (1);

INSERT INTO competencia (id_competencia, acepta_empates, cant_sets, Estado, modalidad, nom_comp, pts_part_empatado, pts_partido_ganado,
 pts_por_presentarse, sistemaPuntuacion, tantos_a_favor_por_no_presentarse, id_deporte, id_fixture, id_usuario, eliminada, otorga_tantos)
VALUES (1, 1, 0, 1, 0, "Liga Santafesina", 1, 3, 0, 1, 0, 2, 1, 1, 0, 0),
(2, 1, 0, 0, 0, "Cuadrangular", 1, 3, 0, 1, 0, 2, NULL, 1, 0, 0),
(3, 0, 0, 0, 1, "Rugby Elim Simple", 0, 1, 0, 2, 0, 1, NULL, 1, 0, 0),
(4, 0, 5, 0, 1, "Tenis Elim Simple", 0, 1, 0, 0, 0, 3, NULL, 1, 0, 0),
(5, 0, 0, 0, 2, "Futbol Elim Doble", 0, 1, 0, 2, 0, 2, NULL, 1, 0, 0),
(6, 0, 3, 0, 0, "Tenis Liga", 0, 3, 1, 0, 0, 3, NULL, 1, 0, 0);

INSERT INTO disponibilidad (id_disponibilidad, disponibilidad, codigo_lugar, id_competencia)
VALUES (1, 2, 1, 1), (2, 1, 3, 1), (3, 1, 4, 1);

INSERT INTO participante (id_participante, email_partic, esLibre, nom_partic, id_competencia)
VALUES (1, "unl@hotmail.com" , 0, "UNL", 1),
(2, "elquilla@hotmail.com" , 0, "El Quilla", 1),
(3, "colon@hotmail.com" , 0, "Colon", 1),
(4, "union@hotmail.com" , 0, "Union", 1),
(5, "lasalle@hotmail.com" , 0, "La Salle", 1),
(6, "sanjustino@hotmail.com" , 0, "Sanjustino", 1),
(7, "colondesanjusto@hotmail.com" , 0, "Colon de San Justo", 1),
(8, "aguayenergia@hotmail.com" , 0, "Agua y Energia", 1),
(9, "dglozano@live.com", 0, "Diego", 2),
(10, "juan@flioh.com", 0, "Juan", 2),
(11, "augusto.tibalt@hotmail.com", 0, "Augusto", 2),
(12, "kevinraud@hotmail.com", 0, "Kevin", 2),
(13, "dglozano@live.com", 0, "Diego", 3),
(14, "juan@flioh.com", 0, "Juan", 3),
(15, "augusto.tibalt@hotmail.com", 0, "Augusto", 3),
(16, "kevinraud@hotmail.com", 0, "Kevin", 3),
(17, "dglozano@live.com", 0, "Diego", 4),
(18, "juan@flioh.com", 0, "Juan", 4),
(19, "augusto.tibalt@hotmail.com", 0, "Augusto", 4),
(20, "kevinraud@hotmail.com", 0, "Kevin", 4),
(21, "dglozano@live.com", 0, "Diego", 5),
(22, "juan@flioh.com", 0, "Juan", 5),
(23, "augusto.tibalt@hotmail.com", 0, "Augusto", 5),
(24, "kevinraud@hotmail.com", 0, "Kevin", 5),
(25, "dglozano@live.com", 0, "Diego", 6),
(26, "juan@flioh.com", 0, "Juan", 6),
(27, "augusto.tibalt@hotmail.com", 0, "Augusto", 6),
(28, "kevinraud@hotmail.com", 0, "Kevin", 6);

INSERT INTO fecha (id_fecha,numero_fecha,ronda,id_fixture) 
VALUES 
(1, 1, 1, 1), 
(2, 2, 2, 1), 
(3, 3, 3, 1), 
(4, 4, 4, 1), 
(5, 5, 5, 1), 
(6, 6, 6, 1), 
(7, 7, 7, 1);

INSERT INTO partido (id_partido, codigo_lugar, id_fecha, id_participante, es_libre) 
VALUES 
(1, 1, 1, 3, 0),
(2, 1, 1, 1, 0),
(3, 3, 1, 4, 0),
(5, 1, 2, 3, 0),
(7, 3, 2, 1, 0),
(8, 4, 2, 7, 0),
(10, 1, 3, 6, 0),
(11, 3, 3, 8, 0),
(12, 4, 3, 1, 0),
(14, 1, 4, 4, 0),
(15, 3, 4, 2, 0),
(16, 4, 4, 1, 0),
(17, 1, 5, 3, 0),
(19, 3, 5, 1, 0),
(20, 4, 5, 6, 0),
(21, 1, 6, 3, 0),
(23, 3, 6, 5, 0),
(25, 1, 7, 1, 0),
(27, 3, 7, 7, 0),
(28, 4, 7, 5, 0);

INSERT INTO partido (id_partido, codigo_lugar, id_fecha, es_libre) 
VALUES (4, 4, 1, 0), (6, 1, 2, 0), (9, 1, 3, 0), (13, 1, 4, 0),
(18, 1, 5, 0), (22, 1, 6, 0), (24, 4, 6, 0), (26, 1, 7, 0);

INSERT INTO partidoslocales (id_participante, id_partido)
VALUES (3, 1), (1, 2), (2, 3), (7, 4),
(6, 5), (4, 6), (5, 7), (7, 8),
(3, 9), (6, 10), (8, 11), (1, 12),
(5, 13), (7, 14), (2, 15), (1, 16),
(3, 17), (5, 18), (4, 19), (6, 20),
(2, 21), (1, 22), (8, 23), (6, 24),
(3, 25), (2, 26), (7, 27), (5, 28);
INSERT INTO partidosvisitantes (id_participante, id_partido) 
VALUES (8, 1), (6, 2), (4, 3), (5, 4),
 (3, 5), (8, 6), (1, 7), (2, 8),
 (4, 9), (5, 10), (7, 11), (2, 12),
 (3, 13), (4, 14), (6, 15), (8, 16),
 (7, 17), (2, 18), (1, 19), (8, 20),
 (3, 21), (7, 22), (5, 23), (4, 24),
 (1, 25), (8, 26), (6, 27), (4, 28);

INSERT INTO resultado (id_resultado, jugo_local, jugo_visitante, equipo_local, equipo_visitante, id_partido, gano_local_desempate)
VALUES 
(1, 1, 1, 2, 0, 1, 0),
(2, 1, 1, 3, 1, 2, 0),
(3, 1, 1, 0, 2, 3, 0),
(4, 1, 1, 1, 1, 4, 0),
(5, 1, 1, 1, 2, 5, 0),
(6, 1, 1, 1, 1, 6, 0),
(7, 1, 1, 2, 3, 7, 0),
(8, 1, 1, 3, 0, 8, 0),
(9, 1, 1, 1, 1, 9, 0),
(10, 1, 1, 2, 1, 10, 0), 
(11, 1, 1, 2, 0, 11, 0), 
(12, 1, 1, 3, 1, 12, 0), 
(13, 1, 1, 3, 3, 13, 0), 
(14, 1, 1, 1, 2, 14, 0), 
(15, 1, 1, 2, 0, 15, 0), 
(16, 1, 1, 1, 0, 16, 0), 
(17, 1, 1, 3, 2, 17, 0), 
(18, 1, 1, 0, 0, 18, 0), 
(19, 1, 1, 1, 2, 19, 0), 
(20, 1, 1, 1, 1, 20, 0), 
(21, 1, 1, 0, 5, 21, 0), 
(22, 1, 1, 2, 2, 22, 0), 
(23, 1, 1, 1, 4, 23, 0), 
(24, 1, 1, 1, 1, 24, 0), 
(25, 1, 1, 3, 4, 25, 0), 
(26, 1, 1, 1, 1, 26, 0), 
(27, 1, 1, 5, 4, 27, 0), 
(28, 1, 1, 3, 2, 28, 0); 
