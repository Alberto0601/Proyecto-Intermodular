package org.example.interfaz;

import entidades.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ControladorAdministrador {

    @FXML
    private void cerrar(ActionEvent evento) {//evento para cerrar sesión
        try {
            //cargamos el login
            FXMLLoader fxmlLoad = new FXMLLoader(getClass().getResource("/org/example/interfaz/hello-view.fxml"));//vuelvo hacia atrás caragando la interfaz anterior
            Parent root = fxmlLoad.load();

            //crear el nuevo escenario para login
            Stage interfazLogin = new Stage();//crear la interfaz vacia
            interfazLogin.setScene(new Scene(root));//crear el decorado y colocarlo

            //tamaños para la ventana que regresa
            interfazLogin.setWidth(650);
            interfazLogin.setHeight(650);

            //muestra la interfaz(se vuelve visible la interfaz)
            interfazLogin.show();

            Stage stageActual = (Stage) ((Node) evento.getSource()).getScene().getWindow();
            stageActual.close();

        } catch (IOException e) {
            System.err.println("Error al cerrar " + e.getMessage());
        }
    }

    //metodo para traer el objeto usuario a la ventana administrador
    @FXML
    private Label lblSaludo;

    private Usuario usuarioIniciado;

    public void setUsuarioAdmin(Usuario usuario) {
        this.usuarioIniciado = usuario;

        if (usuarioIniciado != null) {//si usuario iniciado existe...
            lblSaludo.setText("Panel de Administrador: Hola " + usuarioIniciado.getNombre());
        }
    }

    @FXML
    private void darAltaConcurso() {

        System.out.println("Dar de alta el concurso");
        //metodo de un insert de un concurso
    }

    @FXML
    private void modificarConcurso() {

        System.out.println("Modificar el concurso");
        //metodo que es un update de la tabla concursos
    }

    @FXML
    private void darBajaConcurso() {

        System.out.println("Dar de baja el concurso");
        //metodo que es un remove de la tabla concursos
    }

    //cad metodo te lleva a una interfaz nueva y ahí es donde se hacen las operaciones
}

