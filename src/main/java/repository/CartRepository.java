package repository;

import entities.Cart;
import jakarta.persistence.EntityManager;

public class CartRepository {

    private EntityManager em;

    public CartRepository(EntityManager em) {
        this.em = em;
    }

    public Cart findById(int id) {
        return em.find(Cart.class, id);
    }
    
    
}