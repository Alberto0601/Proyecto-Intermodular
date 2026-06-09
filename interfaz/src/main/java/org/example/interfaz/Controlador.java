package org.example.interfaz;

import entidades.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import servicios.ServicioLog;
import servicios.ServicioUsuarios;

import java.io.IOException;

public class Controlador {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblMensaje;

    //metodo para cambiar ventana según el usuario
    private void cambiarDeVentana(String fxml, Usuario usuario) throws IOException {

        FXMLLoader fxmlLoad = new FXMLLoader(getClass().getResource(fxml));//objeto clase FXMLLoader

        Scene scene = new Scene(fxmlLoad.load()); //objeto de la clase Scene

        Object controladorDest = fxmlLoad.getController();//me da el controlador de la ventana que acaba de cargar
        if (controladorDest instanceof ControladorAdministrador) {//esto de aquí es para llevarme el objeto usuario al resto de roles
            ((ControladorAdministrador) controladorDest).setUsuarioAdmin(usuario);
        } else if (controladorDest instanceof ControladorJuez) {
            ((ControladorJuez) controladorDest).setUsuarioJuez(usuario);
        } else if (controladorDest instanceof ControladorParticipante) {
            ((ControladorParticipante) controladorDest).setUsuarioParticipante(usuario);
        }

        Stage stg = (Stage) txtUsuario.getScene().getWindow();
        stg.setScene(scene);
        stg.show();//mostrar
    }

    @FXML
    protected void iniciarSesion() {    //metodo para iniciar sesion
        //sanitizar la string de usuario y password, importante decir que es de login
        //si existe el usuario pasar a la siguiente ventana y llevarte al siguiente controlador los objetos que necesite
        String usuario = txtUsuario.getText();
        String password = txtPassword.getText();

        try (EntityManager em = Persistence.createEntityManagerFactory("concursoFotos").createEntityManager();) {

            ServicioLog.registrar("INFORMACIÓN: ", "Intento de inicio de sesión: " + usuario);
            ServicioUsuarios u = new ServicioUsuarios(em);
            Usuario log = u.login(usuario, password);

            if (log != null) {//si el usuario existe
                ServicioLog.registrar("INFO", "Login EXITOSO. Usuario: " + log.getNombre() + " - Rol ID: " + log.getIdRol().getId());
                System.out.println(log.getNombre());
                if (log.getIdRol().getId() == 1) {
                    System.out.println("Rol 1 administrador");
                    cambiarDeVentana("/org/example/interfaz/administrador-view.fxml", log);//la ruta dnde está el fxml para que cargue la interfaz nueva y paso el log tbn
                    //que te mande a la vista de admin-->su pantalla
                    //controlador siguiente vista, mandar con un metodo el objeto y luego otro metodo para mostrar
                } else if (log.getIdRol().getId() == 2) {
                    System.out.println("Rol 2 jueces");
                    //que te mande a la vista de juez-->su pantalla
                    cambiarDeVentana("/org/example/interfaz/juez-view.fxml", log);
                } else if (log.getIdRol().getId() == 3) {
                    System.out.println("Rol 3 participantes");
                    //que te mande a la vista de participante-->su pantalla
                    cambiarDeVentana("/org/example/interfaz/participantes-view.fxml", log);
                }
            } else {
                ServicioLog.registrar("ADVERTENCIA", "Login FALLIDO. Credenciales incorrectas para: " + usuario);
                System.out.println("Usuario no encontrado");
            }
        } catch (Exception e) {
            ServicioLog.registrar("ERROR", "Error crítico en la base de datos al loguear a " + usuario + ": " + e.getMessage());
            System.err.println(e.getMessage());
        }
    }
}
