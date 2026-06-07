DROP DATABASE IF EXISTS ConcursoFotografia;
CREATE DATABASE IF NOT EXISTS ConcursoFotografia;
USE ConcursoFotografia;

-- Tabla para normalizar los roles
CREATE TABLE IF NOT EXISTS roles (
    id_rol INT PRIMARY KEY,
    nombre_rol VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    id_rol INT,
    nombre VARCHAR(100) NOT NULL,
    pass VARCHAR(100),
    activo BOOLEAN DEFAULT TRUE,	-- Añadí la columna 'activo' para permitir el caso de uso 'Dar de baja'.
    FOREIGN KEY (id_rol) REFERENCES roles(id_rol)
);

CREATE TABLE IF NOT EXISTS administrador (
    id_usuario INT PRIMARY KEY, -- id_usuario es la id de admin,participante y juez
    email VARCHAR(150) UNIQUE NOT NULL,
    telefono VARCHAR(20),
    fecha_registro DATE DEFAULT (CURRENT_DATE),
    hora_registro TIME DEFAULT (CURRENT_TIME),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

CREATE TABLE IF NOT EXISTS participantes (
    id_usuario INT PRIMARY KEY,
    email VARCHAR(150) UNIQUE NOT NULL,
    telefono VARCHAR(20),
    fecha_registro DATE DEFAULT (CURRENT_DATE),
    hora_registro TIME DEFAULT (CURRENT_TIME),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

CREATE TABLE IF NOT EXISTS jurado (
    id_usuario INT PRIMARY KEY,
    email VARCHAR(150) UNIQUE NOT NULL,
    telefono VARCHAR(20),
    fecha_registro DATE DEFAULT (CURRENT_DATE),
    hora_registro TIME DEFAULT (CURRENT_TIME),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

CREATE TABLE IF NOT EXISTS fotos (
    id_foto INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario_participante INT NOT NULL, -- Apunta al id_usuario del participante
    nombre_foto VARCHAR(150),
    imagen LONGBLOB NOT NULL,
    fecha_subida DATE DEFAULT (CURRENT_DATE),
    hora_subida TIME DEFAULT (CURRENT_TIME),
    FOREIGN KEY (id_usuario_participante) REFERENCES participantes(id_usuario)
);

CREATE TABLE IF NOT EXISTS estudios (
    id_estudio INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    ubicacion VARCHAR(150)
);

CREATE TABLE IF NOT EXISTS reservas (
    id_reserva INT AUTO_INCREMENT PRIMARY KEY,
    id_estudio INT NOT NULL,
    id_usuario_participante INT NOT NULL, -- Apunta al id_usuario del participante
    fecha_inicio DATETIME NOT NULL,
    fecha_fin DATETIME NOT NULL,
    FOREIGN KEY (id_estudio) REFERENCES estudios(id_estudio),
    FOREIGN KEY (id_usuario_participante) REFERENCES participantes(id_usuario)
);

CREATE TABLE IF NOT EXISTS concurso (
    id_concurso INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    descripcion TEXT,
    estado VARCHAR(20) DEFAULT 'Activo'-- Añadí la columna 'estado' para permitir el caso de uso 'Dar de baja concurso'.
);

CREATE TABLE IF NOT EXISTS resultados (
    id_resultado INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario_participante INT NOT NULL, -- Apunta al id_usuario del participante
    id_concurso INT NOT NULL,
    id_usuario_jurado INT NOT NULL,	 -- Apunta al id_usuario del juez
    puntuacion INT NOT NULL,
    fecha_registro DATE DEFAULT (CURRENT_DATE),
    hora_registro TIME DEFAULT (CURRENT_TIME),
    FOREIGN KEY (id_usuario_participante) REFERENCES participantes(id_usuario),
    FOREIGN KEY (id_concurso) REFERENCES concurso(id_concurso),
    FOREIGN KEY (id_usuario_jurado) REFERENCES jurado(id_usuario),
    UNIQUE KEY unq_voto_juez (id_usuario_participante, id_concurso, id_usuario_jurado)
);

-- INSERCIÓN DE DATOS
INSERT INTO roles (id_rol, nombre_rol) VALUES 
(1, 'Administrador'), 
(2, 'Jurado'), 
(3, 'Participante');

INSERT INTO usuarios (id_usuario, id_rol, nombre, pass) VALUES
(1, 1, 'Admin', '$2b$10$Y8G1l.SkyUZ5Exr/71/jHOPwF/e5zXjaByJUqeuXELmpIXIMkVRqC'),
(2, 2, 'Ana López', '$2b$10$Ol6nzxvOFUVOm5rnJfnGVuodxzzP.3.5SBp1k.ErMw/.NpPQZCKBy'),
(3, 2, 'Pedro Martín', '$2b$10$qv5n9huF/yNQoG2pBJ..e.QLGrALyKu1YNDmlgk1xLqgoGGgUu0SC'),
(4, 3, 'Juan Pérez', '$2b$10$xNigS.bZJDhJTGXcrkLiwebHUoQPb22YJaOAE77HPYgxg2dfY6aqW'),
(5, 3, 'Laura Gómez', '$2b$10$fFcWOLS3PILkex9ireJiwedqaE9Ob0OV7FZOjhIq7ZlMB73aAnsmG'),
(6, 3, 'Carlos Ruiz', '$2b$10$grHcMDzGX0Lk.27/hxO83.L.xtZ3WSxnC2bCIMuER0eeBy.w.eR1e');

INSERT INTO administrador (id_usuario, email, telefono) VALUES 
(1, 'admin@concurso.com', '600111222');

INSERT INTO participantes (id_usuario, email, telefono) VALUES 
(4, 'juan@correo.com', '600222333'), 
(5, 'laura@correo.com', '600333444'), 
(6, 'carlos@correo.com', '600444555');

INSERT INTO jurado (id_usuario, email, telefono) VALUES 
(2, 'ana@jurado.com', '611111111'), 
(3, 'pedro@jurado.com', '622222222');

INSERT INTO concurso (nombre, fecha_inicio, fecha_fin, descripcion) VALUES
('Concurso Naturaleza 2026', '2026-01-01', '2026-02-01', 'Fotos de naturaleza'),
('Concurso Urbano 2026', '2026-03-01', '2026-04-01', 'Fotografía urbana');

-- Modificados los IDs para que correspondan con los participantes (4, 5, 6)
INSERT INTO fotos (id_usuario_participante, nombre_foto, imagen) VALUES
(4, 'Bosque mágico', 0x1234),
(5, 'Ciudad nocturna', 0x1234),
(6, 'Montaña nevada', 0x1234);

-- CAMBIO: Se han reestructurado las inserciones de la tabla resultados para incorporar el ID del Juez.

INSERT INTO resultados (id_usuario_participante, id_concurso, id_usuario_jurado, puntuacion) VALUES
(4, 1, 2, 85), -- Ana que es juez (2) califica al Participante Juan (4) en el Concurso 1
(4, 1, 3, 90), 
(5, 1, 2, 92), 
(6, 1, 3, 78), 
(4, 2, 2, 88), 
(5, 2, 3, 95); 

INSERT INTO estudios (nombre, ubicacion) VALUES
('Estudio Central', 'Madrid'),
('Estudio Norte', 'Madrid Norte');

-- Modificados los IDs para que correspondan con los participantes (4, 5)
INSERT INTO reservas (id_estudio, id_usuario_participante, fecha_inicio, fecha_fin) VALUES
(1, 4, '2026-06-10 10:00:00', '2026-06-10 12:00:00'),
(2, 5, '2026-06-11 15:00:00', '2026-06-11 17:00:00');

