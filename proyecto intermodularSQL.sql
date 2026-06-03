create database if not exists ConcursoFotografia;
use ConcursoFotografia;

CREATE TABLE if not exists equipos (
    id_equipo INT AUTO_INCREMENT PRIMARY KEY,
    nombre_equipo VARCHAR(100) NOT NULL,
    descripcion TEXT
);

CREATE TABLE if not exists participantes (
    id_participante INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    telefono VARCHAR(20),
    id_equipo INT,
    contraseña varchar(40),
    fecha_registro DATE DEFAULT (CURRENT_DATE),
    hora_registro TIME DEFAULT (CURRENT_TIME),
    FOREIGN KEY (id_equipo) REFERENCES equipos(id_equipo)
);

CREATE TABLE fotos (
    id_foto INT AUTO_INCREMENT PRIMARY KEY,
    id_participante INT NOT NULL,
    nombre_foto VARCHAR(150),
    imagen LONGBLOB NOT NULL,
    fecha_subida DATE DEFAULT (CURRENT_DATE),
    hora_subida TIME DEFAULT (CURRENT_TIME),
    FOREIGN KEY (id_participante) REFERENCES participantes(id_participante)
);


CREATE TABLE if not exists jurado (
    id_jurado INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    telefono VARCHAR(20),
    contraseña varchar(40),
    fecha_registro DATE DEFAULT (CURRENT_DATE),
    hora_registro TIME DEFAULT (CURRENT_TIME)
);

CREATE TABLE if not exists estudios (
    id_estudio INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    ubicacion VARCHAR(150)
);
CREATE TABLE if not exists reservas (
    id_reserva INT AUTO_INCREMENT PRIMARY KEY,
    id_estudio INT NOT NULL,
    id_participante INT NOT NULL,
    fecha_inicio DATETIME NOT NULL,
    fecha_fin DATETIME NOT NULL,
    FOREIGN KEY (id_estudio) REFERENCES estudios(id_estudio),
    FOREIGN KEY (id_participante) REFERENCES participantes(id_participante)
);
CREATE TABLE if not exists concurso (
    id_concurso INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    descripcion TEXT
);
CREATE TABLE if not exists resultados (
    id_resultado INT AUTO_INCREMENT PRIMARY KEY,
    id_participante INT NOT NULL,
    id_concurso INT NOT NULL,
    puntuacion INT NOT NULL,
    fecha_registro DATE DEFAULT (CURRENT_DATE),
    hora_registro TIME DEFAULT (CURRENT_TIME),
    FOREIGN KEY (id_participante) REFERENCES participantes(id_participante),
    FOREIGN KEY (id_concurso) REFERENCES concurso(id_concurso)
);

INSERT INTO equipos (nombre_equipo, descripcion) VALUES
('Luz y Sombra', 'Equipo especializado en fotografía artística'),
('Pixel Masters', 'Equipo experto en edición digital'),
('FotoExplorers', 'Grupo dedicado a fotografía de naturaleza');

INSERT INTO participantes (nombre, email, telefono, id_equipo, fecha_registro, hora_registro) VALUES
('Alberto García', 'alberto@example.com', '600123456', 1, '2026-06-02', '10:15:00'),
('Álvaro Ruiz', 'alvaro@example.com', '611987654', 2, '2026-06-02', '10:20:00'),
('Héctor López', 'hector@example.com', '622555444', 3, '2026-06-02', '10:25:00'),
('María Torres', 'maria@example.com', '633444333', 1, '2026-06-03', '09:10:00'),
('Lucía Fernández', 'lucia@example.com', '644333222', 2, '2026-06-03', '09:12:00');

INSERT INTO jurado (nombre, email, telefono, contraseña, fecha_registro, hora_registro) VALUES
('Carlos Méndez', 'carlos.mendez@jurado.com', '600112233', 'passCarlos2026', '2026-06-03', '10:00:00'),
('Elena Rodríguez', 'elena.rodriguez@jurado.com', '611223344', 'elenaSecure26', '2026-06-03', '10:05:00'),
('Javier Santos', 'javier.santos@jurado.com', '622334455', 'javierFoto26', '2026-06-03', '10:10:00');


INSERT INTO estudios (nombre, ubicacion) VALUES
('Estudio A', 'Planta 1 - Sala 3'),
('Estudio B', 'Planta 2 - Sala 1'),
('Estudio C', 'Planta 2 - Sala 4');

INSERT INTO reservas (id_estudio, id_participante, fecha_inicio, fecha_fin) VALUES
(1, 1, '2026-06-05 10:00:00', '2026-06-05 11:00:00'),
(2, 2, '2026-06-05 11:00:00', '2026-06-05 12:00:00'),
(3, 3, '2026-06-06 09:30:00', '2026-06-06 10:30:00'),
(1, 4, '2026-06-06 11:00:00', '2026-06-06 12:00:00'),
(2, 5, '2026-06-07 10:00:00', '2026-06-07 11:00:00');

INSERT INTO concurso (nombre, fecha_inicio, fecha_fin, descripcion) VALUES
('Concurso Primavera 2026', '2026-06-02', '2026-06-15', 'Concurso anual de fotografía artística'),
('Concurso Naturaleza 2026', '2026-06-10', '2026-06-20', 'Fotografía de paisajes y fauna');

INSERT INTO resultados (id_participante, id_concurso, puntuacion, fecha_registro, hora_registro) VALUES
(1, 1, 85, '2026-06-05', '12:00:00'),
(2, 1, 90, '2026-06-05', '12:05:00'),
(3, 1, 78, '2026-06-05', '12:10:00'),
(4, 1, 92, '2026-06-06', '13:00:00'),
(5, 2, 88, '2026-06-11', '14:00:00');

SELECT * FROM participantes;
SELECT * FROM equipos;
SELECT * FROM estudios;
SELECT * FROM reservas;
SELECT * FROM concurso;
SELECT * FROM resultados;






