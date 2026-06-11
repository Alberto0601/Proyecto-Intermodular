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

    /**
     *  metodo que me muestra que si el usuario existe manda un mensaje del usuario que ha iniciado sesión
     * @param usuario
     */
    public void setUsuarioParticipante(Usuario usuario) {
        this.usuarioIniciadoP = usuario;

        if (usuarioIniciadoP != null) {//si usuario iniciado existe...
            lblSaludo.setText("El participante " + usuarioIniciadoP.getNombre() + " ha iniciado sesión");
        }
    }

    @FXML
    private void abrirSubirFotografia() {
        if (usuarioIniciadoP == null) {
            System.err.println("No se puede abrir la ventana sin una sesión de participante activa.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/interfaz/subir-foto-view.fxml"));
            Parent root = loader.load();

            // Obtenemos el controlador secundario e inyectamos el usuario logueado
            ControladorSubirFoto modalController = loader.getController();
            modalController.setUsuarioLogueado(usuarioIniciadoP);

            // Generamos la escena en estado APPLICATION_MODAL (Bloquea la interacción trasera)
            Stage stageModal = new Stage();
            stageModal.setTitle("Subir Archivo al Concurso");
            stageModal.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            stageModal.setScene(new Scene(root));
            stageModal.setResizable(false);
            stageModal.showAndWait();

        } catch (IOException e) {
            System.err.println("Error al cargar la modal de subida: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
