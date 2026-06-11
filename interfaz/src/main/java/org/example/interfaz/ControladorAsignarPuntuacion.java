package org.example.interfaz;

import entidades.Concurso;
import entidades.Jurado;
import entidades.Participante;
import entidades.Resultado;
import entidades.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ControladorAsignarPuntuacion {

    @FXML private ComboBox<Concurso> cmbConcursos;
    @FXML private ComboBox<Participante> cmbParticipantes;
    @FXML private TextField txtPuntuacion;

    private Usuario juezLogueado;
    private final ObservableList<Concurso> listaConcursosOb = FXCollections.observableArrayList();
    private final ObservableList<Participante> listaParticipantesOb = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configurarFormatosComboBox();
        cargarDatosIniciales();
    }

    public void setJuezLogueado(Usuario usuario) {
        this.juezLogueado = usuario;
    }

    /**
     * Carga todos los concursos y participantes de la base de datos usando JPA
     */
    private void cargarDatosIniciales() {
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("concursoFotos");
             EntityManager em = emf.createEntityManager()) {

            // Traemos los concursos activos o existentes
            List<Concurso> concursos = em.createQuery("SELECT c FROM Concurso c", Concurso.class).getResultList();
            listaConcursosOb.addAll(concursos);
            cmbConcursos.setItems(listaConcursosOb);

            // Traemos los participantes mapeados
            List<Participante> participantes = em.createQuery("SELECT p FROM Participante p", Participante.class).getResultList();
            listaParticipantesOb.addAll(participantes);
            cmbParticipantes.setItems(listaParticipantesOb);

        } catch (Exception e) {
            System.err.println("Error al precargar listas desplegables en el formulario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Procesa los objetos seleccionados y guarda la puntuación en la tabla resultados
     */
    @FXML
    private void guardarPuntuacion() {
        Concurso concursoSeleccionado = cmbConcursos.getSelectionModel().getSelectedItem();
        Participante participanteSeleccionado = cmbParticipantes.getSelectionModel().getSelectedItem();
        String strPuntuacion = txtPuntuacion.getText().trim();

        if (concursoSeleccionado == null || participanteSeleccionado == null || strPuntuacion.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos Vacíos", "Por favor, selecciona un concurso, un participante e introduce una puntuación.");
            return;
        }

        try {
            Integer puntuacion = Integer.parseInt(strPuntuacion);

            // CAMBIO: Ahora validamos que la nota esté entre 1 y 100
            if (puntuacion < 1 || puntuacion > 100) {
                mostrarAlerta(Alert.AlertType.WARNING, "Rango Incorrecto", "La nota debe ser un número entero entre 1 y 100.");
                return;
            }

            try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("concursoFotos");
                 EntityManager em = emf.createEntityManager()) {

                // Buscamos al Jurado basándonos en el usuario de la sesión activa
                Jurado juradoActivo = em.find(Jurado.class, juezLogueado.getId());

                if (juradoActivo == null) {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error de Sesión", "No se encontró un perfil de Jurado válido para el usuario actual.");
                    return;
                }

                // Comprobación antiduplicados (Evita el error 'unq_voto_juez')
                String jpqlCheck = "SELECT COUNT(r) FROM Resultado r WHERE " +
                        "r.idConcurso.id = :idConcurso AND " +
                        "r.idUsuarioParticipante.id = :idParticipante AND " +
                        "r.idUsuarioJurado.id = :idJuez";

                Long conteoVotos = em.createQuery(jpqlCheck, Long.class)
                        .setParameter("idConcurso", concursoSeleccionado.getId())
                        .setParameter("idParticipante", participanteSeleccionado.getId())
                        .setParameter("idJuez", juradoActivo.getId())
                        .getSingleResult();

                if (conteoVotos > 0) {
                    mostrarAlerta(Alert.AlertType.WARNING, "Calificación Ya Existente",
                            "Ya has asignado una puntuación a este participante en este concurso.\n" +
                                    "No se permiten calificaciones duplicadas.");
                    return;
                }

                em.getTransaction().begin();

                Concurso cManaged = em.merge(concursoSeleccionado);
                Participante pManaged = em.merge(participanteSeleccionado);

                Resultado resultado = new Resultado();
                resultado.setIdConcurso(cManaged);
                resultado.setIdUsuarioParticipante(pManaged);
                resultado.setIdUsuarioJurado(juradoActivo);
                resultado.setPuntuacion(puntuacion); // Se guarda el entero (hasta 100)
                resultado.setFechaRegistro(LocalDate.now());
                resultado.setHoraRegistro(LocalTime.now());

                em.persist(resultado);
                em.getTransaction().commit();

                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "¡Puntuación registrada con éxito!");
                cerrar();
            }

        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Formato Inválido", "La puntuación debe ser un número entero válido.");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error inesperado al guardar el resultado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void cancelar() {
        cerrar();
    }

    private void cerrar() {
        Stage stage = (Stage) txtPuntuacion.getScene().getWindow();
        stage.close();
    }

    /**
     * Cambia la apariencia visual interna del ComboBox para mostrar el nombre/email real del registro
     */
    private void configurarFormatosComboBox() {
        // Formato para los Concursos
        cmbConcursos.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Concurso item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText("[" + item.getId() + "] " + item.getNombre());
                }
            }
        });
        cmbConcursos.setConverter(new StringConverter<>() {
            @Override public String toString(Concurso object) { return object == null ? "" : "[" + object.getId() + "] " + object.getNombre(); }
            @Override public Concurso fromString(String string) { return null; }
        });

        // Formato para los Participantes (Usa el Email o el Nombre de su relación de Usuario)
        cmbParticipantes.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Participante item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText("ID: " + item.getId() + " - " + item.getEmail());
                }
            }
        });
        cmbParticipantes.setConverter(new StringConverter<>() {
            @Override public String toString(Participante object) { return object == null ? "" : "ID: " + object.getId() + " - " + object.getEmail(); }
            @Override public Participante fromString(String string) { return null; }
        });
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
