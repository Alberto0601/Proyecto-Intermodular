package org.example.interfaz; // Ajusta el paquete si lo pones en otro sitio

import javafx.scene.control.Alert;

public class AlertaHelper {
    public static void mostrar(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
