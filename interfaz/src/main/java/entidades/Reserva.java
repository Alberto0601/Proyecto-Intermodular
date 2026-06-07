package entidades;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "reservas", schema = "ConcursoFotografia")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_estudio", nullable = false)
    private Estudio idEstudio;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario_participante", nullable = false)
    private Participante idUsuarioParticipante;

    @Column(name = "fecha_inicio", nullable = false)
    private Instant fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private Instant fechaFin;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Estudio getIdEstudio() {
        return idEstudio;
    }

    public void setIdEstudio(Estudio idEstudio) {
        this.idEstudio = idEstudio;
    }

    public Participante getIdUsuarioParticipante() {
        return idUsuarioParticipante;
    }

    public void setIdUsuarioParticipante(Participante idUsuarioParticipante) {
        this.idUsuarioParticipante = idUsuarioParticipante;
    }

    public Instant getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Instant fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Instant getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Instant fechaFin) {
        this.fechaFin = fechaFin;
    }

}