package servicios;

import entidades.Usuario;
import excepciones.CredencialesInvalidasException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ServicioUsuarios {

    //Constantes atributos
    private final EntityManager em;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); //encriptador

    public ServicioUsuarios(EntityManager em) { //constructor de la clase
        this.em = em;
    }

    //metodo

    public Usuario login(String nombre, String passwordPlana){
        try {
            TypedQuery<Usuario> query = em.createQuery(
                    "SELECT u FROM Usuario u WHERE u.nombre = :nombre", Usuario.class
            );
            query.setParameter("nombre", nombre);

            Usuario usuario = query.getSingleResult();

            if (encoder.matches(passwordPlana,usuario.getPass())) {
                return usuario;
            }else  {
                System.out.println("Contraseña incorrecta");
                throw new CredencialesInvalidasException();
            }

        }catch (NoResultException e){
            System.err.println("No existe el usuario en la BBDD");
            return null;
        }catch (CredencialesInvalidasException e){
            System.err.println("Error durante el inicio de sesión: "+e.getMessage());
            return null;
        }//mirar pq no me lanza la excepción
    }
}

