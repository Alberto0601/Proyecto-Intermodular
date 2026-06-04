package org.example.interfazlogin;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class HelloController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblMensaje;

    @FXML
    protected void iniciarSesion() {

        String usuario = txtUsuario.getText();
        String password = txtPassword.getText();

        if (usuario.equals("admin") && password.equals("1234")) {

            lblMensaje.setText("Inicio de sesión correcto");
            lblMensaje.setStyle("-fx-text-fill: #16A34A;");

        } else {

            lblMensaje.setText("Usuario o contraseña incorrectos");
            lblMensaje.setStyle("-fx-text-fill: #DC2626;");
        }
    }
}
