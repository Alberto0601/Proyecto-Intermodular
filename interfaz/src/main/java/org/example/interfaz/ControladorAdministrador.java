package org.example.interfaz;

import entidades.Concurso;
import entidades.Usuario;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class ControladorAdministrador {

    @FXML
    private void cerrar(ActionEvent evento) {//evento para cerrar sesión
        try {
            //cargamos el login
            FXMLLoader fxmlLoad = new FXMLLoader(getClass().getResource("/org/example/interfaz/hello-view.fxml"));//vuelvo hacia atrás cargando la interfaz anterior
            Parent root = fxmlLoad.load();

            //crear el nuevo escenario para login
            Stage interfazLogin = new Stage();//crear la interfaz vacia
            interfazLogin.setScene(new Scene(root));//crear el decorado y colocarlo

            //tamaños para la ventana que regresa
            interfazLogin.setWidth(650);
            interfazLogin.setHeight(650);

            //muestra la interfaz(se vuelve visible la interfaz)
            interfazLogin.show();

            Stage stageActual = (Stage) ((Node) evento.getSource()).getScene().getWindow();
            stageActual.close();

        } catch (IOException e) {
            System.err.println("Error al cerrar " + e.getMessage());
        }
    }

    @FXML
    private Label lblSaludo;

    private Usuario usuarioIniciado;

    /**
     *
     * @param usuario metodo para traer el objeto usuario a la interfaz de administrador
     */
    public void setUsuarioAdmin(Usuario usuario) {
        this.usuarioIniciado = usuario;

        if (usuarioIniciado != null) {//si usuario iniciado existe...
            lblSaludo.setText("Panel de Administrador: Hola " + usuarioIniciado.getNombre());
        }
    }

    /**
     * metodo para abrir el formulario de alta de los concursos
     */
    @FXML
    private void abrirFormularioAltaConcurso() {
        try {
            // Carga el FXML del formulario
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/interfaz/formulario-concurso.fxml"));
            Parent root = loader.load();

            // Crea una ventana emergente de tipo modal
            Stage stage = new Stage();
            stage.setTitle("Crear Nuevo Concurso");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.showAndWait(); // Muestra la ventana y espera a que termine

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar la vista del formulario: " + e.getMessage());
        }
    }

    /**
     * metodo para abrir el formulario de alta de participantes y jueces
     */
    @FXML
    private void abrirFormularioAltaUsuario() {
        try {
            // Carga el FXML del formulario
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/interfaz/formulario-usuario.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Crear Nuevo Usuario");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.showAndWait(); // Muestra la ventana y espera a que termine

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar la vista del formulario: " + e.getMessage());
        }
    }

    @FXML
    private void abrirModificarConcurso() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Modificar Concurso");
        dialog.setHeaderText("Buscar concurso por ID");
        dialog.setContentText("Introduce el ID numérico del concurso:");

        Optional<String> resultado = dialog.showAndWait();
        if (!resultado.isPresent() || resultado.get().trim().isEmpty()) {
            return;
        }

        Integer idBuscado;
        try {
            idBuscado = Integer.parseInt(resultado.get().trim());
        } catch (NumberFormatException e) {
            AlertaHelper.mostrar("Formato Erróneo", "El ID ingresado debe ser un número entero válido.", Alert.AlertType.ERROR);
            return;
        }

        Concurso concursoEncontrado = null;
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("concursoFotos");
             EntityManager em = emf.createEntityManager()) {

            concursoEncontrado = em.find(Concurso.class, idBuscado);

        } catch (Exception e) {
            AlertaHelper.mostrar("Error de Conexión", "No se pudo consultar el registro: " + e.getMessage(), Alert.AlertType.ERROR);
            return;
        }

        if (concursoEncontrado == null) {
            AlertaHelper.mostrar("No encontrado", "No se localizó ningún concurso con el ID: " + idBuscado, Alert.AlertType.WARNING);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/interfaz/formulario-modificar-concurso.fxml"));
            Parent root = loader.load();

            ControladorModificarConcurso controladorModif = loader.getController();
            controladorModif.cargarConcurso(concursoEncontrado);

            Stage stage = new Stage();
            stage.setTitle("Modificar Concurso #" + concursoEncontrado.getId());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            AlertaHelper.mostrar("Error de Interfaz", "Error al inicializar la pantalla de edición: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void abrirModificarJuez() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Modificar Juez");
        dialog.setHeaderText("Buscar el Juez por ID");
        dialog.setContentText("Introduce la ID del juez:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && !result.get().trim().isEmpty()) {
            try {
                Integer id = Integer.parseInt(result.get().trim());

                try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("concursoFotos");
                     EntityManager em = emf.createEntityManager()) {

                    Usuario u = em.find(Usuario.class, id);

                    // IMPORTANTE: Validamos que el usuario exista y que su rol sea Juez (ID 2)
                    if (u != null && u.getIdRol() != null && u.getIdRol().getId() == 2) {

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/interfaz/formulario-modificar-juez.fxml"));
                        Parent root = loader.load();

                        ControladorModificarJuez controller = loader.getController();
                        controller.cargarDatos(u);

                        Stage stage = new Stage();
                        stage.setTitle("Editando Juez: " + u.getNombre());
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setScene(new Scene(root));
                        stage.showAndWait();

                    } else {
                        AlertaHelper.mostrar("Aviso", "No se encontró un Juez con ese ID.", Alert.AlertType.WARNING);
                    }
                }
            } catch (NumberFormatException e) {
                AlertaHelper.mostrar("Error", "El ID debe ser un número.", Alert.AlertType.ERROR);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void abrirModificarParticipante() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Modificar Participante");
        dialog.setHeaderText("Buscar Participante por ID");
        dialog.setContentText("Introduce el ID del participante:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && !result.get().trim().isEmpty()) {
            try {
                Integer id = Integer.parseInt(result.get().trim());

                try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("concursoFotos");
                     EntityManager em = emf.createEntityManager()) {

                    Usuario u = em.find(Usuario.class, id);

                    // VALIDACIÓN ESPECÍFICA: Debe existir y su rol debe ser Participante (ID 3)
                    if (u != null && u.getIdRol() != null && u.getIdRol().getId() == 3) {

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/interfaz/formulario-modificar-participante.fxml"));
                        Parent root = loader.load();

                        ControladorModificarParticipante controller = loader.getController();
                        controller.cargarDatos(u);

                        Stage stage = new Stage();
                        stage.setTitle("Editando Participante: " + u.getNombre());
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setScene(new Scene(root));
                        stage.showAndWait();

                    } else {
                        AlertaHelper.mostrar("Aviso", "No se encontró un Participante con ese ID.", Alert.AlertType.WARNING);
                    }
                }
            } catch (NumberFormatException e) {
                AlertaHelper.mostrar("Error", "El ID debe ser un número.", Alert.AlertType.ERROR);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

