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
INSERT INTO usuarios (rol, nombre, pass) VALUES (1, 'Admin Principal', 'admin123'), (2, 'Jurado Ana', 'jurado123'), (2, 'Jurado Pedro', 'jurado456'), (3, 'Participante Juan', 'pass123'), (3, 'Participante Laura', 'pass456'), (3, 'Participante Carlos', 'pass789'); tengo esta tabla, hazme el hash de esas contraseñas con el algoritmo de BCrypt para java
