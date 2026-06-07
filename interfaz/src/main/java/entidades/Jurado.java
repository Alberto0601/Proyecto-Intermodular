package entidades;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "jurado", schema = "ConcursoFotografia")
public class Jurado {
    @Id
    @Column(name = "id_usuario", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuarios;

    @Column(name = "email", nullable = false, length = 150)
    private String email;

    @Column(name = "telefono", length = 20)
    private String telefono;

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

    public Usuario getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuario usuarios) {
        this.usuarios = usuarios;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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