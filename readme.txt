la ip elastica es: 3.230.64.41
con puerto: 3306
usuario: root
contraseña: root123

Tabla con las contraseñas normales:
INSERT INTO usuarios (rol, nombre, pass) VALUES (1, 'Admin Principal', 'admin123'), (2, 'Ana López', 'jurado123'), (2, 'Pedro Martín', 'jurado456'), (3, 'Juan Pérez', 'pass123'), (3, 'Laura Gómez', 'pass456'), (3, 'Carlos Ruiz', 'pass789');

Esta tabla tiene las contraseña sin el hash mientras que en la bbdd están hasheadas, es para hacer pruebas con los login
