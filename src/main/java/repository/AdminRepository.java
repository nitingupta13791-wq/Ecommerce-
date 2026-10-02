package repository;

import entities.Admin;
import jakarta.persistence.EntityManager;

public class AdminRepository {

    private EntityManager em;

    public AdminRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Admin admin) {
        em.persist(admin);
    }

    public Admin findById(int id) {
        return em.find(Admin.class, id);
    }

    public Admin findByEmail(String email) {

        return em.createQuery(
            "SELECT a FROM Admin a WHERE a.email = :email",
            Admin.class
        )
        .setParameter("email", email)
        .getResultStream()
        .findFirst()
        .orElse(null);
    }
}