package repository;

import entities.Customer;
import jakarta.persistence.EntityManager;

public class CustomerRepository {

    private EntityManager em;

    public CustomerRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Customer customer) {
        em.persist(customer);
    }

    public Customer findById(int id) {
        return em.find(Customer.class, id);
    }
    
    public Customer findByEmail(String email) {

        return em.createQuery(
            "SELECT c FROM Customer c WHERE c.email = :email",
            Customer.class
        )
        .setParameter("email", email)
        .getResultStream()
        .findFirst()
        .orElse(null);
    }
}
