Métodos
-	Iniciar sesión participante
-	Iniciar sesión jurado
-	Subir imagen
-	Consultar resultados
-	Mostrar imagen (para jurado)
-	Traer puntuación
-	Subir puntuación actualizada
Admin
-	Ver concursos
-	Modificar concursos
-	Ver participantes
-	Modificar participantes
-	Ver jueces
-	Modificar jueces

Si sobra tiempo un método para volver al inicio

Tabla con las contraseñas normales:
INSERT INTO usuarios (rol, nombre, pass) VALUES (1, 'Admin Principal', 'admin123'), (2, 'Ana López', 'jurado123'), (2, 'Pedro Martín', 'jurado456'), (3, 'Juan Pérez', 'pass123'), (3, 'Laura Gómez', 'pass456'), (3, 'Carlos Ruiz', 'pass789');

esta tabla tiene las contraseña sin el hash mientras que en la bbdd están hasheadas, es para hacer pruebas con los login
