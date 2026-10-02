package repository;

import entities.Payment;
import jakarta.persistence.EntityManager;

public class PaymentRepository {

    private EntityManager em;

    public PaymentRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Payment payment) {
        em.persist(payment);
    }
}