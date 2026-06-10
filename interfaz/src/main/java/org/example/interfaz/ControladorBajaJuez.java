package org.example.interfaz;

import entidades.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControladorBajaJuez {

    @FXML private TextField txtNombre;
    @FXML private TextField txtEstado;
    @FXML private Button btnCancelar;

    private Integer idUsuarioActual;

    public void cargarDatos(Usuario u) {
        if (u == null) return;
        this.idUsuarioActual = u.getId();
        txtNombre.setText(u.getNombre());
        txtEstado.setText((u.getActivo() != null && u.getActivo()) ? "Activo" : "Inactivo");
    }

    @FXML
    private void confirmarBaja() {
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("concursoFotos");
             EntityManager em = emf.createEntityManager()) {

            em.getTransaction().begin();
            Usuario u = em.find(Usuario.class, idUsuarioActual);

            if (u != null) {
                // Baja lógica asignando false al estado activo
                u.setActivo(false);

                em.merge(u);
                em.getTransaction().commit();

                AlertaHelper.mostrar("Éxito", "El juez ha sido desactivado correctamente.", Alert.AlertType.INFORMATION);
                cerrar();
            } else {
                AlertaHelper.mostrar("Error", "El usuario ya no existe en el sistema.", Alert.AlertType.ERROR);
                em.getTransaction().rollback();
            }
        } catch (Exception e) {
            AlertaHelper.mostrar("Error", "No se pudo procesar la baja: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void cancelar() {
        cerrar();
    }

    private void cerrar() {
        ((Stage) btnCancelar.getScene().getWindow()).close();
    }
}
