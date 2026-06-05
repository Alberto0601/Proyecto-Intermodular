package org.example.interfaz;

import entidades.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import servicios.ServicioUsuarios;

public class Controlador {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblMensaje;

    @FXML
    protected void iniciarSesion() {    //metodo para iniciar sesion
        //sanitizar la string de usuario y password, importante decir que es de login
        //si existe el usuario pasar a la siguiente ventana y llevarte al siguiente controlador los objetos que necesite
        String usuario = txtUsuario.getText();
        String password = txtPassword.getText();


        try (EntityManager em = Persistence.createEntityManagerFactory("concursoFotos").createEntityManager();) {
            ServicioUsuarios u = new ServicioUsuarios(em);
            Usuario log = u.login(usuario, password);
            if (log != null) {//si el usuario existe
                System.out.println(log.getNombre());
                if(log.getRol()==1) {
                    System.out.println("Rol 1 administrador");
                    //que te mande a la vista de admin-->su pantalla
                    //controlador siguiente vista, mandar con un metodo el objeto y luego otro metodo para mostrar
                }else if(log.getRol()==2) {
                    System.out.println("Rol 2 jueces");
                    //que te mande a la vista de juez-->su pantalla
                }else if(log.getRol()==3) {
                    System.out.println("Rol 3 participantes");
                    //que te mande a la vista de participante-->su pantalla
                }
            }else  {
                System.out.println("Usuario no encontrado");
            }


        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
