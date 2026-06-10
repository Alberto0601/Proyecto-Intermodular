package org.example.interfaz;

import entidades.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ControladorModificarJuez {

    @FXML private TextField txtNombre;
    @FXML private PasswordField txtPass;
    @FXML private CheckBox chkActivo;
    @FXML private Button btnCancelar;

    private Integer idUsuarioActual;

    public void cargarDatos(Usuario u) {
        this.idUsuarioActual = u.getId();
        txtNombre.setText(u.getNombre());
        chkActivo.setSelected(u.getActivo() != null && u.getActivo());
    }

    @FXML
    private void guardarCambios() {
        if (txtNombre.getText().trim().isEmpty()) {
            AlertaHelper.mostrar("Error", "El nombre no puede estar vacío.", Alert.AlertType.WARNING);
            return;
        }
        //JPA
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("concursoFotos");
             EntityManager em = emf.createEntityManager()) {

            em.getTransaction().begin();
            Usuario u = em.find(Usuario.class, idUsuarioActual);

            if (u != null) {
                u.setNombre(txtNombre.getText().trim());
                u.setActivo(chkActivo.isSelected());

                // Solo actualizamos la contraseña si se ha escrito algo en el campo
                if (!txtPass.getText().trim().isEmpty()) {
                    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                    u.setPass(encoder.encode(txtPass.getText().trim()));
                }

                em.merge(u);
                em.getTransaction().commit();
                AlertaHelper.mostrar("Éxito", "Juez actualizado correctamente.", Alert.AlertType.INFORMATION);
                cerrar();
            }
        } catch (Exception e) {
            AlertaHelper.mostrar("Error", "No se pudo actualizar: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML private void cancelar() { cerrar(); }

    private void cerrar() {
        ((Stage) btnCancelar.getScene().getWindow()).close();
    }
}