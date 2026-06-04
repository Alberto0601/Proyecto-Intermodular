drop database if exists ConcursoFotografia;
create database if not exists ConcursoFotografia;
use ConcursoFotografia;

CREATE TABLE concursos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL
);

CREATE TABLE personas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    rol VARCHAR(20) NOT NULL CHECK (rol IN ('ADMIN', 'PARTICIPANTE', 'JUEZ'))
);

CREATE TABLE equipos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    fecha_creacion DATE DEFAULT (CURRENT_DATE)
);

CREATE TABLE estudios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    ubicacion VARCHAR(150),
    capacidad INT NOT NULL
);

CREATE TABLE fotos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    url_archivo VARCHAR(255) NOT NULL,
    fecha_subida TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    participante_id INT NOT NULL,
    concurso_id INT NOT NULL,
    FOREIGN KEY (participante_id) REFERENCES personas(id) ON DELETE CASCADE,
    FOREIGN KEY (concurso_id) REFERENCES concursos(id) ON DELETE CASCADE
);

CREATE TABLE reservas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    estudio_id INT NOT NULL,
    persona_id INT NOT NULL,
    fecha_reserva DATE NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    FOREIGN KEY (estudio_id) REFERENCES estudios(id) ON DELETE CASCADE,
    FOREIGN KEY (persona_id) REFERENCES personas(id) ON DELETE CASCADE
);

CREATE TABLE equipo_participantes (
    equipo_id INT NOT NULL,
    persona_id INT NOT NULL,
    PRIMARY KEY (equipo_id, persona_id),
    FOREIGN KEY (equipo_id) REFERENCES equipos(id) ON DELETE CASCADE,
    FOREIGN KEY (persona_id) REFERENCES personas(id) ON DELETE CASCADE
);

CREATE TABLE concurso_equipos (
    concurso_id INT NOT NULL,
    equipo_id INT NOT NULL,
    PRIMARY KEY (concurso_id, equipo_id),
    FOREIGN KEY (concurso_id) REFERENCES concursos(id) ON DELETE CASCADE,
    FOREIGN KEY (equipo_id) REFERENCES equipos(id) ON DELETE CASCADE
);

CREATE TABLE resultados (
    id INT AUTO_INCREMENT PRIMARY KEY,
    juez_id INT NOT NULL,
    foto_id INT NOT NULL,
    puntuacion INT NOT NULL CHECK (puntuacion BETWEEN 0 AND 10),
    comentario TEXT,
    CONSTRAINT unique_juez_foto UNIQUE (juez_id, foto_id),
    FOREIGN KEY (juez_id) REFERENCES personas(id) ON DELETE CASCADE,
    FOREIGN KEY (foto_id) REFERENCES fotos(id) ON DELETE CASCADE
);

INSERT INTO concursos (nombre, descripcion, fecha_inicio, fecha_fin) VALUES
('Enfoque Urbano 2026', 'Captura la esencia de las ciudades.', '2026-06-01', '2026-06-30'),
('Naturaleza Viva', 'Paisajes naturales y fauna salvaje.', '2026-07-15', '2026-08-15');

INSERT INTO personas (nombre, email, rol) VALUES
('Carlos Administrador', 'admin@concurso.com', 'ADMIN'),
('Ana Martínez', 'ana.participante@email.com', 'PARTICIPANTE'),
('Luis García', 'luis.participante@email.com', 'PARTICIPANTE'),
('Juan Pérez', 'juan.participante@email.com', 'PARTICIPANTE'),
('Sofía Castro', 'sofia.participante@email.com', 'PARTICIPANTE'),
('David Lynch', 'david.juez@email.com', 'JUEZ'),
('Elena Rostova', 'elena.juez@email.com', 'JUEZ');

INSERT INTO equipos (nombre, fecha_creacion) VALUES
('Pixel Pro Team', '2026-05-20'),
('Golden Hour Club', '2026-05-22'),
('Sombra y Luz', '2026-05-25');

INSERT INTO estudios (nombre, ubicacion, capacidad) VALUES
('Estudio Alfa (Ciclorama)', 'Planta 1, Puerta A', 6),
('Estudio Beta (Luz Natural)', 'Planta 2, Ático', 4),
('Laboratorio de Revelado', 'Sótano, Sala 3', 3);

INSERT INTO fotos (titulo, url_archivo, participante_id, concurso_id) VALUES
('Luces de la Gran Vía', 'https://storage.concurso.com/fotos/foto1.jpg', 2, 1),
('Reflejos bajo la lluvia', 'https://storage.concurso.com/fotos/foto2.jpg', 3, 1),
('Amanecer en la montaña', 'https://storage.concurso.com/fotos/foto3.jpg', 4, 2);

INSERT INTO reservas (estudio_id, persona_id, fecha_reserva, hora_inicio, hora_fin) VALUES
(1, 2, '2026-06-10', '10:00:00', '13:00:00'),
(2, 3, '2026-06-11', '16:00:00', '18:00:00'),
(1, 4, '2026-06-12', '09:00:00', '11:00:00');

INSERT INTO equipo_participantes (equipo_id, persona_id) VALUES
(1, 2),
(1, 3),
(2, 4),
(3, 5);

INSERT INTO concurso_equipos (concurso_id, equipo_id) VALUES
(1, 1),
(1, 2),
(2, 2),
(2, 3);

INSERT INTO resultados (juez_id, foto_id, puntuacion, comentario) VALUES
(6, 1, 9, 'Excelente composición y manejo de las luces de neón.'),
(7, 1, 8, 'Muy buena, aunque el encuadre está ligeramente inclinado.'),
(6, 2, 7, 'Buen concepto, pero le falta un poco de nitidez.'),
(7, 3, 10, 'Una captura del amanecer absolutamente impecable.');