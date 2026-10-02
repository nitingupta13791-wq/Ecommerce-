package repository;

import java.util.List;

import entities.Product;
import jakarta.persistence.EntityManager;


public class ProductRepository {

    private EntityManager em;

    public ProductRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Product product) {
        em.persist(product);
    }

    public Product findById(int id) {
        return em.find(Product.class, id);
    }

    public List<Product> findAll() {
        return em.createQuery(
                "SELECT p FROM Product p",
                Product.class
        ).getResultList();
    }

    public void delete(Product product) {
        em.remove(product);
    }
    
   
}