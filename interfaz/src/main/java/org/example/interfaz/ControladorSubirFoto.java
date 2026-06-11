package org.example.interfaz;

import entidades.Foto;
import entidades.Participante;
import entidades.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;

public class ControladorSubirFoto {

    @FXML private TextField txtNombreFoto;
    @FXML private TextField txtRutaArchivo;

    private Usuario usuarioLogueado;
    private File archivoSeleccionado;

    /**
     * Permite inyectar el Usuario activo que abrió la ventana modal.
     */
    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;
    }

    /**
     * Lanza el buscador nativo de archivos del sistema operativo para ficheros PNG/JPG.
     */
    @FXML
    private void seleccionarImagen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Fotografía");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes (*.png, *.jpg, *.jpeg)", "*.png", "*.jpg", "*.jpeg")
        );

        Stage stage = (Stage) txtRutaArchivo.getScene().getWindow();
        archivoSeleccionado = fileChooser.showOpenDialog(stage);

        if (archivoSeleccionado != null) {
            txtRutaArchivo.setText(archivoSeleccionado.getAbsolutePath());
            // Sugiere el nombre del archivo si el campo de texto está limpio
            if (txtNombreFoto.getText().trim().isEmpty()) {
                txtNombreFoto.setText(archivoSeleccionado.getName().replaceAll("\\.[^.]+$", ""));
            }
        }
    }

    /**
     * Ejecuta el insert en la base de datos mapeando la foto con JPA.
     */
    @FXML
    private void subirFoto() {
        String nombreFoto = txtNombreFoto.getText().trim();

        if (nombreFoto.isEmpty() || archivoSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos Vacíos", "Por favor, introduce un título y selecciona un archivo válido.");
            return;
        }

        EntityManagerFactory emf = null;
        EntityManager em = null;

        try {
            // Se convierte la imagen en crudo a array de bytes para la BD
            byte[] bytesImagen = Files.readAllBytes(archivoSeleccionado.toPath());

            emf = Persistence.createEntityManagerFactory("concursoFotos");
            em = emf.createEntityManager();
            em.getTransaction().begin();

            // Buscamos el participante correspondiente usando la Id
            Participante participante = em.find(Participante.class, usuarioLogueado.getId());

            if (participante == null) {
                throw new Exception("No se encontró el registro de Participante para este usuario.");
            }

            // Instancio el objeto de datos que se van a guardar
            Foto nuevaFoto = new Foto();
            nuevaFoto.setNombreFoto(nombreFoto);
            nuevaFoto.setImagen(bytesImagen);
            nuevaFoto.setIdUsuarioParticipante(participante);

            // JPA
            em.persist(nuevaFoto);
            em.getTransaction().commit();

            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "¡Fotografía subida al concurso con éxito!");
            cerrarVentana();

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Persistencia", "No se pudo guardar la fotografía: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
            if (emf != null) emf.close();
        }
    }

    @FXML
    private void cerrarVentana() {
        Stage stage = (Stage) txtRutaArchivo.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
