package entidades;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "resultados", schema = "ConcursoFotografia")
public class Resultado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resultado", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario_participante", nullable = false)
    private Participante idUsuarioParticipante;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_concurso", nullable = false)
    private Concurso idConcurso;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario_jurado", nullable = false)
    private Jurado idUsuarioJurado;

    @Column(name = "puntuacion", nullable = false)
    private Integer puntuacion;

    @ColumnDefault("(curdate())")
    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;

    @ColumnDefault("(curtime())")
    @Column(name = "hora_registro")
    private LocalTime horaRegistro;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Participante getIdUsuarioParticipante() {
        return idUsuarioParticipante;
    }

    public void setIdUsuarioParticipante(Participante idUsuarioParticipante) {
        this.idUsuarioParticipante = idUsuarioParticipante;
    }

    public Concurso getIdConcurso() {
        return idConcurso;
    }

    public void setIdConcurso(Concurso idConcurso) {
        this.idConcurso = idConcurso;
    }

    public Jurado getIdUsuarioJurado() {
        return idUsuarioJurado;
    }

    public void setIdUsuarioJurado(Jurado idUsuarioJurado) {
        this.idUsuarioJurado = idUsuarioJurado;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public LocalTime getHoraRegistro() {
        return horaRegistro;
    }

    public void setHoraRegistro(LocalTime horaRegistro) {
        this.horaRegistro = horaRegistro;
    }

}