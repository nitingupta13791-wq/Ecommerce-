package repository;

import java.util.List;

import entities.Order;
import jakarta.persistence.EntityManager;

public class OrderRepository {
	   private EntityManager em;

	    public OrderRepository(EntityManager em) {
	        this.em = em;
	    }

	    public void save(Order order) {
	        em.persist(order);
	    }

	    public Order findById(int id) {
	        return em.find(Order.class, id);
	    }

	    public List<Order> findAll() {
	        return em.createQuery(
	                "SELECT o FROM Order o",
	                Order.class
	        ).getResultList();
	    }

}
