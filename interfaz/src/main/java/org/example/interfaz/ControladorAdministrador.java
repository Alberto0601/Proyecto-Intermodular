package org.example.interfaz;

import entidades.Concurso;
import entidades.Usuario;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class ControladorAdministrador {

    @FXML
    private void cerrar(ActionEvent evento) {//evento para cerrar sesión
        try {
            //cargamos el login
            FXMLLoader fxmlLoad = new FXMLLoader(getClass().getResource("/org/example/interfaz/hello-view.fxml"));//vuelvo hacia atrás cargando la interfaz anterior
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

    //metodo de abrir formulario alta concurso
    @FXML
    private void abrirFormularioAltaConcurso() {
        try {
            // Carga el FXML del formulario
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/interfaz/formulario-concurso.fxml"));
            Parent root = loader.load();

            // Crea una ventana emergente de tipo modal
            Stage stage = new Stage();
            stage.setTitle("Crear Nuevo Concurso");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.showAndWait(); // Muestra la ventana y espera a que termine

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar la vista del formulario: " + e.getMessage());
        }
    }

    //metodo de abrir formulario alta participante y jueces (usuarios)
    @FXML
    private void abrirFormularioAltaUsuario() {
        try {
            // Carga el FXML del formulario
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/interfaz/formulario-usuario.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Crear Nuevo Usuario");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.showAndWait(); // Muestra la ventana y espera a que termine

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar la vista del formulario: " + e.getMessage());
        }
    }

}

