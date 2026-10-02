package repository;

import entities.OrderItem;
import jakarta.persistence.EntityManager;


public class OrderItemRepository {

    private EntityManager em;

    public OrderItemRepository(EntityManager em) {
        this.em = em;
    }

    public void save(OrderItem orderItem) {
        em.persist(orderItem);
    }
}