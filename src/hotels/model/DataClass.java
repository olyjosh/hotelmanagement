package hotels.model;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import hotels.model.entity.Client;

/**
 *
 * @author olyjosh
 */
public class DataClass {

    EntityManager em = null;
    EntityManagerFactory emf = null;       

    public DataClass() {
        connectDatabase();
    }
        
    private void connectDatabase() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("Hospital.odb");
        }
        if (em == null) {
            em = emf.createEntityManager();
        }
    }
    
    public boolean regCustomer(String officerID, String firstName, String lastName, String otherName, String phone, String email, String address, String sex, String bloodGp, String dept, String dob, boolean hiv,String pass) {
        Client c = new Client(officerID, firstName, lastName, otherName, phone, email, address, sex, bloodGp, dept, dob, hiv,pass);     
        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();
        return true;
    }
    
    public Client staffLogin(String s, String q, String hashPass){
        try {
            return em.createQuery("SELECT  FROM Clients cu where cu."+q+"="+s, Client.class).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void getCustomer() {
        try {
            TypedQuery query = em.createQuery("SELECT cu FROM Clients cu where cu.id=1 ORDER BY cu.firstName", Client.class);
            List<Client> cus = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}