package org.example.interfaz;

import entidades.Concurso;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControladorBajaConcurso {

    @FXML private TextField txtNombre;
    @FXML private TextField txtEstadoActual;
    @FXML private TextField txtFechaFin;
    @FXML private Button btnCancelar;

    private Integer idConcursoActual;

    public void cargarConcurso(Concurso concurso) {
        if (concurso == null) return;

        this.idConcursoActual = concurso.getId();
        txtNombre.setText(concurso.getNombre());
        txtEstadoActual.setText(concurso.getEstado());
        txtFechaFin.setText(concurso.getFechaFin() != null ? concurso.getFechaFin().toString() : "No definida");
    }

    @FXML
    private void confirmarBaja() {
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("concursoFotos");
             EntityManager em = emf.createEntityManager()) {

            em.getTransaction().begin();
            Concurso concurso = em.find(Concurso.class, idConcursoActual);

            if (concurso == null) {
                AlertaHelper.mostrar("Error", "El concurso ya no existe en el sistema.", Alert.AlertType.ERROR);
                em.getTransaction().rollback();
                return;
            }

            concurso.setEstado("Finalizado");

            em.merge(concurso);
            em.getTransaction().commit();

            AlertaHelper.mostrar("Éxito", "El concurso ha sido dado de baja exitosamente.", Alert.AlertType.INFORMATION);
            cerrarVentana();

        } catch (Exception e) {
            AlertaHelper.mostrar("Error", "No se pudo tramitar la baja: " + e.getMessage(), Alert.AlertType.ERROR);
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
