package org.example.interfaz;

import javafx.fxml.FXML;

public class ControladorAdministrador {
    @FXML
    private void darAltaConcurso() {

        System.out.println("Dar de alta el concurso");
        //metodo de un insert de un concurso
    }

    @FXML
    private void modificarConcurso() {

        System.out.println("Modificar el concurso");
        //metodo que es un update de la tabla concursos
    }

    @FXML
    private void darBajaConcurso() {

        System.out.println("Dar de baja el concurso");
        //metodo que es un remove de la tabla concursos
    }

    //cad metodo te lleva a una interfaz nueva y ahí es donde se hacen las operaciones
}

