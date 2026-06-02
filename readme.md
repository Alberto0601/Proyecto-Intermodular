Prepara tu base de datos local
  - Genera un archivo de respaldo de tu base de datos actual
    
Crea tu Base de Datos en AWS (Amazon RDS)
  -  Inicia sesión en la Consola de administración de AWS y busca el servicio RDS.
  -  Haz clic en Crear base de datos y elige el motor compatible con tu base de datos.
  -  Selecciona la configuración de tu entorno (puedes elegir Capa gratuita para pruebas).
  -  Define un identificador, nombre de usuario y contraseña maestra.
  -  En la sección de conectividad, asegúrate de habilitar el Acceso público para que puedas comunicarte con ella desde tu ordenador.

Ajusta la seguridad de AWS
  -  Una vez creada la base de datos, ve a su pestaña de Conectividad y seguridad.
  -  Haz clic en el Grupo de seguridad asignado.
  -  Añade una Regla de entrada (Inbound rule) que permita el tráfico desde tu dirección IP pública en el puerto de tu base de datos (por ejemplo, el puerto 3306 para MySQL).

Conéctate y sube tu información
  -  Usa herramientas cliente como MySQL Workbench, y conéctate a AWS usando el Punto de enlace (Endpoint) que proporciona RDS junto con el usuario y contraseña que creaste.
  -  Una vez dentro, importa el archivo de respaldo .sql para cargar toda la información.
