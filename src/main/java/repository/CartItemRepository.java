package repository;

import entities.CartItem;
import jakarta.persistence.EntityManager;

public class CartItemRepository {

    private EntityManager em;

    public CartItemRepository(EntityManager em) {
        this.em = em;
    }

    public void delete(CartItem cartItem) {
        em.remove(cartItem);
    }
    
    public void save(CartItem cartItem) {
        em.persist(cartItem);
    }
    
    public CartItem findById(int id) {
        return em.find(CartItem.class, id);
    }
}