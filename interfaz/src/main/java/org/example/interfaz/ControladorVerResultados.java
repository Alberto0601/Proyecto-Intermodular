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

public class ControladorVerResultados {

    @FXML private TableView<Resultado> tablaResultados;
    @FXML private TableColumn<Resultado, Integer> colConcurso;
    @FXML private TableColumn<Resultado, String> colJurado;
    @FXML private TableColumn<Resultado, Integer> colPuntuacion;
    @FXML private Button btnVolver;

    private Usuario usuarioLogueado;
    private final ObservableList<Resultado> listaResultadosOb = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configuramos cómo se va a pintar cada columna usando expresiones lambda sencillas
        colConcurso.setCellValueFactory(celda -> new SimpleIntegerProperty(celda.getValue().getIdConcurso().getId()).asObject());

        colJurado.setCellValueFactory(celda -> new SimpleStringProperty("Jurado #" + celda.getValue().getIdUsuarioJurado().getId()));

        colPuntuacion.setCellValueFactory(celda -> new SimpleIntegerProperty(celda.getValue().getPuntuacion()).asObject());
    }

    /**
     * Recibe el usuario participante, carga sus notas desde JPA y las renderiza en la tabla.
     */
    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;
        cargarResultadosBBDD();
    }

    private void cargarResultadosBBDD() {
        if (usuarioLogueado == null) return;

        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("concursoFotos");
             EntityManager em = emf.createEntityManager()) {

            // Consulta JPQL para traer los resultados que pertenezcan a este participante
            String jpql = "SELECT r FROM Resultado r WHERE r.idUsuarioParticipante.id = :idParticipante";

            List<Resultado> resultados = em.createQuery(jpql, Resultado.class)
                    .setParameter("idParticipante", usuarioLogueado.getId())
                    .getResultList();

            // Pasamos los objetos recuperados a la lista que escucha la tabla de JavaFX
            listaResultadosOb.addAll(resultados);
            tablaResultados.setItems(listaResultadosOb);

        } catch (Exception e) {
            System.err.println("Error al cargar resultados en la tabla modal: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void cerrarVentana() {
        Stage stage = (Stage) btnVolver.getScene().getWindow();
        stage.close();
    }
}
