package org.example.interfaz;

import entidades.Role;
import entidades.Usuario;
import excepciones.UsuariosException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; //

public class ControladorFormularioUsuario {

    @FXML
    private TextField txtNombre;
    @FXML
    private PasswordField txtPass;
    @FXML
    private ComboBox<Role> comboRol;
    @FXML
    private CheckBox chkActivo;
    @FXML
    private Button btnCancelar;

    /**
     *Inicializa la ventana del formulario para usuarios
     */
    @FXML
    public void initialize() {
        // Configuramos el ComboBox para que muestre la propiedad 'nombreRol'
        comboRol.setCellFactory(param -> new ListCell<Role>() {
            @Override
            protected void updateItem(Role item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNombreRol());
                }
            }
        });
        comboRol.setButtonCell(comboRol.getCellFactory().call(null));
    }

    /**
     * metodo que guarda un nuevo usuario en la BBDD
     * @throws UsuariosException lanza mi excepción personalizada en caso de que haya campos vacios
     */
    @FXML
    private void guardarUsuario()throws UsuariosException {
        // Validación de campos vacíos obligatorios
        if (txtNombre.getText().trim().isEmpty() || txtPass.getText().trim().isEmpty() || comboRol.getValue() == null) {
            mostrarAlertaUsu("Campos incompletos", "Por favor, rellene todos los campos obligatorios.", Alert.AlertType.WARNING);
            throw new UsuariosException();
        }

        // Bloque de persistencia JPA
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("concursoFotos");
             EntityManager em = emf.createEntityManager()) {

            Usuario u = new Usuario();
            u.setNombre(txtNombre.getText().trim());
            u.setActivo(chkActivo.isSelected());


            String passwordPlano = txtPass.getText().trim();
            String passwordHasheado = hashPassword(passwordPlano);
            u.setPass(passwordHasheado);

            em.getTransaction().begin();

            Role rolSeleccionado = comboRol.getValue();
            // Busca el registro real usando el ID de la clave primaria mapeada
            Role rolAsignado = em.find(Role.class, rolSeleccionado.getId());

            if (rolAsignado == null) {
                mostrarAlertaUsu("Error de Rol", "El rol seleccionado no existe en la base de datos.", Alert.AlertType.ERROR);
                em.getTransaction().rollback();
                return;
            }
            // Vinculamos el objeto Rol encontrado a la entidad Usuario
            u.setIdRol(rolAsignado);

            em.persist(u);
            em.getTransaction().commit();

            mostrarAlertaUsu("Éxito", "¡El usuario se ha registrado correctamente!", Alert.AlertType.INFORMATION);
            cerrarVentana();

        } catch (Exception e) {
            mostrarAlertaUsu("Error", "No se pudo guardar el usuario: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     *
     * @param passwordPlano contraseña en texto plano
     * @return contraseña hasheada en formato String
     */
    private String hashPassword(String passwordPlano) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); //
        return encoder.encode(passwordPlano);
    }

    @FXML
    private void cancelar() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlertaUsu(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}