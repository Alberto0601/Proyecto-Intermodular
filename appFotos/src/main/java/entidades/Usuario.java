package entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios", schema = "ConcursoFotografia")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", nullable = false)
    private Integer id;

    @Column(name = "rol")
    private Integer rol;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "`contraseña`", length = 100)
    private String contraseña;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRol() {
        return rol;
    }

    public void setRol(Integer rol) {
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

}