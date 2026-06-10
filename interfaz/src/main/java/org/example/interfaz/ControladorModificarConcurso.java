package org.example.interfaz;

import entidades.Concurso;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ControladorModificarConcurso {

    @FXML private TextField txtNombre;
    @FXML private TextArea txtDescripcion;
    @FXML private DatePicker dpFechaInicio;
    @FXML private DatePicker dpFechaFin;
    @FXML private ComboBox<String> comboEstado;
    @FXML private Button btnCancelar;

    private Integer idConcursoActual;

    @FXML
    public void initialize() {
        comboEstado.getItems().addAll("Activo", "Pausado", "Finalizado");
    }

    /**
     * Rellena la interfaz con los datos mapeados desde el Administrador
     */
    public void cargarConcurso(Concurso concurso) {
        if (concurso == null) return;

        this.idConcursoActual = concurso.getId();
        txtNombre.setText(concurso.getNombre());
        txtDescripcion.setText(concurso.getDescripcion());
        dpFechaInicio.setValue(concurso.getFechaInicio());
        dpFechaFin.setValue(concurso.getFechaFin());

        if (concurso.getEstado() != null) {
            comboEstado.setValue(concurso.getEstado());
        } else {
            comboEstado.setValue("Activo");
        }
    }

    @FXML
    private void guardarCambios() {
        // Validaciones internas del formulario
        if (txtNombre.getText().trim().isEmpty() || dpFechaInicio.getValue() == null || dpFechaFin.getValue() == null || comboEstado.getValue() == null) {
            AlertaHelper.mostrar("Campos incompletos", "Por favor, rellene todos los campos obligatorios.", Alert.AlertType.WARNING);
            return;
        }

        if (dpFechaFin.getValue().isBefore(dpFechaInicio.getValue())) {
            AlertaHelper.mostrar("Fechas erróneas", "La fecha de finalización no puede ser anterior a la de inicio.", Alert.AlertType.WARNING);
            return;
        }

        // Transacción JPA
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("concursoFotos");
             EntityManager em = emf.createEntityManager()) {

            em.getTransaction().begin();
            Concurso concursoAEditar = em.find(Concurso.class, idConcursoActual);

            if (concursoAEditar == null) {
                AlertaHelper.mostrar("Error", "El concurso ya no existe en la base de datos.", Alert.AlertType.ERROR);
                em.getTransaction().rollback();
                return;
            }

            concursoAEditar.setNombre(txtNombre.getText().trim());
            concursoAEditar.setDescripcion(txtDescripcion.getText().trim());
            concursoAEditar.setFechaInicio(dpFechaInicio.getValue());
            concursoAEditar.setFechaFin(dpFechaFin.getValue());
            concursoAEditar.setEstado(comboEstado.getValue());

            em.merge(concursoAEditar);
            em.getTransaction().commit();

            AlertaHelper.mostrar("Éxito", "El concurso ha sido modificado correctamente.", Alert.AlertType.INFORMATION);
            cerrarVentana();

        } catch (Exception e) {
            AlertaHelper.mostrar("Error de persistencia", "No se pudieron salvar los cambios: " + e.getMessage(), Alert.AlertType.ERROR);
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
}
