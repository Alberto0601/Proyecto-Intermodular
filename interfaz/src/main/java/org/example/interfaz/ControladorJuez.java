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

public class ControladorJuez {

    @FXML
    private Label lblSaludo;

    private Usuario usuarioIniciadoJ;

    /**
     * Cierra sesión y te devuelve a la pantalla de login
     */
    @FXML
    private void cerrarJ(ActionEvent evento) {
        try {
            FXMLLoader fxmlLoad = new FXMLLoader(getClass().getResource("/org/example/interfaz/hello-view.fxml"));
            Parent root = fxmlLoad.load();

            Stage interfazLogin = new Stage();
            interfazLogin.setScene(new Scene(root));
            interfazLogin.setWidth(650);
            interfazLogin.setHeight(650);
            interfazLogin.show();

            Stage stageActual = (Stage) ((Node) evento.getSource()).getScene().getWindow();
            stageActual.close();

        } catch (IOException e) {
            System.err.println("Error al cerrar " + e.getMessage());
        }
    }

    /**
     * Abre la ventana modal con el historial visual de puntuaciones puestas por este juez.
     */
    @FXML
    private void abrirConsultarResultados() {
        if (usuarioIniciadoJ == null) {
            System.err.println("No se pueden consultar datos sin sesión de juez activa.");
            return;
        }

        try {
            // Carga relativa infalible: busca el FXML en la misma carpeta que este controlador
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ver-resultados-juez-view.fxml"));
            Parent root = loader.load();

            // Pasamos la sesión al controlador de la tabla modal
            ControladorVerResultadosJuez modalController = loader.getController();
            modalController.setJuezLogueado(usuarioIniciadoJ);

            // Configuramos el comportamiento modal
            Stage stageModal = new Stage();
            stageModal.setTitle("Historial de Calificaciones Emitidas");
            stageModal.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            stageModal.setScene(new Scene(root));
            stageModal.setResizable(false);
            stageModal.showAndWait();

        } catch (IOException e) {
            System.err.println("Error al cargar la modal de resultados del juez.");
            e.printStackTrace();
        }
    }

    /**
     * Configura el mensaje de saludo inicial
     */
    public void setUsuarioJuez(Usuario usuario) {
        this.usuarioIniciadoJ = usuario;

        if (usuarioIniciadoJ != null) {
            lblSaludo.setText("El juez " + usuarioIniciadoJ.getNombre() + " ha iniciado sesión");
        }
    }
    @FXML
    private void abrirAsignarPuntuaciones() {
        if (usuarioIniciadoJ == null) {
            System.err.println("No se puede asignar puntuación sin una sesión activa.");
            return;
        }

        try {
            // Carga relativa: busca el FXML justo donde reside esta misma clase
            FXMLLoader loader = new FXMLLoader(getClass().getResource("asignar-puntuacion-view.fxml"));
            Parent root = loader.load();

            // Pasamos la sesión del juez conectado al formulario de asignación
            ControladorAsignarPuntuacion modalController = loader.getController();
            modalController.setJuezLogueado(usuarioIniciadoJ);

            // Montamos la escena modal bloqueando interacciones traseras
            Stage stageModal = new Stage();
            stageModal.setTitle("Nueva Calificación - Panel de Jueces");
            stageModal.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            stageModal.setScene(new Scene(root));
            stageModal.setResizable(false);
            stageModal.showAndWait();

        } catch (IOException e) {
            System.err.println("Error crítico al cargar el formulario FXML de asignar puntuaciones.");
            e.printStackTrace();
        }
    }
}
