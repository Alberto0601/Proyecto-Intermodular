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

public class ControladorParticipante {

    @FXML
    private Label lblSaludo;

    @FXML
    private void cerrarP(ActionEvent evento) {//evento para cerrar sesión
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

    private Usuario usuarioIniciadoP;

    public void setUsuarioParticipante(Usuario usuario) {
        this.usuarioIniciadoP = usuario;

        if (usuarioIniciadoP != null) {//si usuario iniciado existe...
            lblSaludo.setText("El participante: " + usuarioIniciadoP.getNombre() + " ha iniciado sesión");
        }
    }
}
