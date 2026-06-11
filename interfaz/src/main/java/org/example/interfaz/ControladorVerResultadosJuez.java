package org.example.interfaz;

import entidades.Resultado;
import entidades.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.util.List;

public class ControladorVerResultadosJuez {

    @FXML private TableView<Resultado> tablaResultadosJuez;
    @FXML private TableColumn<Resultado, Integer> colConcurso;
    @FXML private TableColumn<Resultado, String> colParticipante;
    @FXML private TableColumn<Resultado, Integer> colPuntuacion;
    @FXML private Button btnVolver;

    private Usuario juezLogueado;
    private final ObservableList<Resultado> listaResultadosJuezOb = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colConcurso.setCellValueFactory(celda -> new SimpleIntegerProperty(celda.getValue().getIdConcurso().getId()).asObject());

        colParticipante.setCellValueFactory(celda -> new SimpleStringProperty("Participante #" + celda.getValue().getIdUsuarioParticipante().getId()));

        colPuntuacion.setCellValueFactory(celda -> new SimpleIntegerProperty(celda.getValue().getPuntuacion()).asObject());
    }

    /**
     * Inyecta el usuario Juez activo y desencadena la carga desde JPA
     */
    public void setJuezLogueado(Usuario usuario) {
        this.juezLogueado = usuario;
        cargarCalificacionesEmitidas();
    }

    private void cargarCalificacionesEmitidas() {
        if (juezLogueado == null) return;

        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("concursoFotos");
             EntityManager em = emf.createEntityManager()) {

            String jpql = "SELECT r FROM Resultado r WHERE r.idUsuarioJurado.id = :idJuez";

            List<Resultado> resultados = em.createQuery(jpql, Resultado.class)
                    .setParameter("idJuez", juezLogueado.getId())
                    .getResultList();

            listaResultadosJuezOb.addAll(resultados);
            tablaResultadosJuez.setItems(listaResultadosJuezOb);

        } catch (Exception e) {
            System.err.println("Error al cargar calificaciones del juez con JPA: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void cerrarVentana() {
        Stage stage = (Stage) btnVolver.getScene().getWindow();
        stage.close();
    }
}
