package entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles", schema = "ConcursoFotografia")
public class Role {
    @Id
    @Column(name = "id_rol", nullable = false)
    private Integer id;

    @Column(name = "nombre_rol", nullable = false, length = 50)
    private String nombreRol;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

}