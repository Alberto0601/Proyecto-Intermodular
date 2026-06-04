package org.example.interfazlogin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader =
                new FXMLLoader(getClass().getResource("hello-view.fxml"));

        Scene scene = new Scene(loader.load(), 500, 500); //tamaño ventana

        stage.setTitle("Inicio de sesión");//mensaje de arriba a la izquierda
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
//solo lanza el diseño

