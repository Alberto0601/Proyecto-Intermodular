import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Principal {
    static void main() {
        try (EntityManagerFactory emf= Persistence.createEntityManagerFactory("concurso");
             EntityManager em= emf.createEntityManager()){
            System.out.println("funciona");

        }catch (Exception e){
            System.out.println("error"+e.getMessage());
        }
    }
}
