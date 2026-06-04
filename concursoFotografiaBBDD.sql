drop database if exists ConcursoFotografia;
create database if not exists ConcursoFotografia;
use ConcursoFotografia;

CREATE TABLE if not exists equipos (
    id_equipo INT AUTO_INCREMENT PRIMARY KEY,
    nombre_equipo VARCHAR(100) NOT NULL,
    descripcion TEXT
);

CREATE TABLE if not exists usuarios (
	id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    rol INT,
    nombre VARCHAR(100) NOT NULL,
    contraseña VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS administrador (
    id_administrador INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    telefono VARCHAR(20),
    fecha_registro DATE DEFAULT (CURRENT_DATE),
    hora_registro TIME DEFAULT (CURRENT_TIME),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

CREATE TABLE if not exists participantes (
    id_participante INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    telefono VARCHAR(20),
    id_equipo INT,
    fecha_registro DATE DEFAULT (CURRENT_DATE),
    hora_registro TIME DEFAULT (CURRENT_TIME),
    FOREIGN KEY (id_equipo) REFERENCES equipos(id_equipo),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

CREATE TABLE if not exists jurado (
    id_jurado INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    telefono VARCHAR(20),
    fecha_registro DATE DEFAULT (CURRENT_DATE),
    hora_registro TIME DEFAULT (CURRENT_TIME),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

CREATE TABLE if not exists fotos (
    id_foto INT AUTO_INCREMENT PRIMARY KEY,
    id_participante INT NOT NULL,
    nombre_foto VARCHAR(150),
    imagen LONGBLOB NOT NULL,
    fecha_subida DATE DEFAULT (CURRENT_DATE),
    hora_subida TIME DEFAULT (CURRENT_TIME),
    FOREIGN KEY (id_participante) REFERENCES participantes(id_participante)
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

-- USUARIOS
INSERT INTO usuarios (rol, nombre, contraseña) VALUES
(1, 'Admin Principal', 'admin123'),
(2, 'Jurado Ana', 'jurado123'),
(2, 'Jurado Pedro', 'jurado456'),
(3, 'Participante Juan', 'pass123'),
(3, 'Participante Laura', 'pass456'),
(3, 'Participante Carlos', 'pass789');

-- EQUIPOS
INSERT INTO equipos (nombre_equipo, descripcion) VALUES
('Equipo A', 'Fotografía urbana'),
('Equipo B', 'Naturaleza y paisaje');

-- ADMINISTRADOR
INSERT INTO administrador (id_usuario, nombre, email, telefono) VALUES
(1, 'Admin Principal', 'admin@concurso.com', '600111222');

-- PARTICIPANTES
INSERT INTO participantes (id_usuario, id_equipo, nombre, email, telefono) VALUES
(4, 1, 'Juan Pérez', 'juan@correo.com', '600222333'),
(5, 2, 'Laura Gómez', 'laura@correo.com', '600333444'),
(6, 1, 'Carlos Ruiz', 'carlos@correo.com', '600444555');

-- JURADO
INSERT INTO jurado (id_usuario, nombre, email, telefono) VALUES
(2, 'Ana López', 'ana@jurado.com', '611111111'),
(3, 'Pedro Martín', 'pedro@jurado.com', '622222222');

-- CONCURSOS
INSERT INTO concurso (nombre, fecha_inicio, fecha_fin, descripcion) VALUES
('Concurso Naturaleza 2026', '2026-01-01', '2026-02-01', 'Fotos de naturaleza'),
('Concurso Urbano 2026', '2026-03-01', '2026-04-01', 'Fotografía urbana');

-- FOTOS (datos de prueba)
INSERT INTO fotos (id_participante, nombre_foto, imagen) VALUES
(1, 'Bosque mágico', 0x1234),
(2, 'Ciudad nocturna', 0x1234),
(3, 'Montaña nevada', 0x1234);

-- RESULTADOS
INSERT INTO resultados (id_participante, id_concurso, puntuacion) VALUES
(1, 1, 85),
(2, 1, 92),
(3, 1, 78),
(1, 2, 88),
(2, 2, 95);

-- ESTUDIOS
INSERT INTO estudios (nombre, ubicacion) VALUES
('Estudio Central', 'Madrid'),
('Estudio Norte', 'Madrid Norte');

-- RESERVAS
INSERT INTO reservas (id_estudio, id_participante, fecha_inicio, fecha_fin) VALUES
(1, 1, '2026-06-10 10:00:00', '2026-06-10 12:00:00'),
(2, 2, '2026-06-11 15:00:00', '2026-06-11 17:00:00');


SELECT * FROM equipos;
SELECT * FROM usuarios;
SELECT * FROM administrador;
SELECT * FROM participantes;
SELECT * FROM jurado;
SELECT * FROM fotos;
SELECT * FROM estudios;
SELECT * FROM reservas;
SELECT * FROM concurso;
SELECT * FROM resultados;






