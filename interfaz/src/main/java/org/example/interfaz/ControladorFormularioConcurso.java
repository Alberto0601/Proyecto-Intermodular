package org.example.interfaz;

import excepciones.ConcursoException;
import excepciones.UsuariosException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.LocalDate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import entidades.Concurso;

public class ControladorFormularioConcurso {

    @FXML private TextField txtNombre;
    @FXML private DatePicker dpInicio;
    @FXML private DatePicker dpFin;
    @FXML private TextArea txtDescripcion;
    @FXML private Button btnCancelar;

    /**
     *metodo para guardar un concurso nuevo con JPA
     */
    @FXML
    private void guardarConcurso()throws ConcursoException {
        //Validar que los campos obligatorios no estén vacíos
        if (txtNombre.getText().trim().isEmpty() || dpInicio.getValue() == null || dpFin.getValue() == null) {
            mostrarAlerta("Campos obligatorios", "Por favor, rellena el nombre y las fechas.", Alert.AlertType.WARNING);
            throw new ConcursoException();
        }

        //Extraer datos de la interfaz
        String nombre = txtNombre.getText();
        LocalDate fechaInicio = dpInicio.getValue();
        LocalDate fechaFin = dpFin.getValue();
        String descripcion = txtDescripcion.getText();

        //JPA
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("concursoFotos");
             EntityManager em = emf.createEntityManager()) {

            Concurso c = new Concurso();
            c.setNombre(nombre);
            c.setFechaInicio(fechaInicio);
            c.setFechaFin(fechaFin);
            c.setDescripcion(descripcion);
            c.setEstado("Activo");

            em.getTransaction().begin();
            em.persist(c);
            em.getTransaction().commit();

            mostrarAlerta("Éxito", "El concurso se ha creado correctamente", Alert.AlertType.INFORMATION);
            cerrarVentana();

        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo guardar en la Base de Datos: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void cancelar() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
