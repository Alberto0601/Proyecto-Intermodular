package entidades;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "fotos", schema = "ConcursoFotografia")
public class Foto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_foto", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_participante", nullable = false)
    private Participante idParticipante;

    @Column(name = "nombre_foto", length = 150)
    private String nombreFoto;

    @Column(name = "imagen", nullable = false)
    private byte[] imagen;

    @ColumnDefault("(curdate())")
    @Column(name = "fecha_subida")
    private LocalDate fechaSubida;

    @ColumnDefault("(curtime())")
    @Column(name = "hora_subida")
    private LocalTime horaSubida;

    public Foto() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Participante getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(Participante idParticipante) {
        this.idParticipante = idParticipante;
    }

    public String getNombreFoto() {
        return nombreFoto;
    }

    public void setNombreFoto(String nombreFoto) {
        this.nombreFoto = nombreFoto;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public LocalDate getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(LocalDate fechaSubida) {
        this.fechaSubida = fechaSubida;
    }

    public LocalTime getHoraSubida() {
        return horaSubida;
    }

    public void setHoraSubida(LocalTime horaSubida) {
        this.horaSubida = horaSubida;
    }

}