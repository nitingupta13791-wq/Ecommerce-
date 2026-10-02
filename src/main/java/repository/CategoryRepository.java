package repository;

import java.util.List;


import entities.Category;
import jakarta.persistence.EntityManager;

public class CategoryRepository {

    private EntityManager em;

    public CategoryRepository(EntityManager em) {
        this.em = em;
    }

    public List<Category> findAll() {
        return em.createQuery(
            "SELECT c FROM Category c",
            Category.class
        ).getResultList();
    }

    public Category findById(int id) {
        return em.find(Category.class, id);
    }
    
    public void save(Category category) {
        em.persist(category);
    }
    public Category findByName(String name) {
        return em.createQuery(
            "SELECT c FROM Category c WHERE c.name = :name",
            Category.class
        )
        .setParameter("name", name)
        .getResultStream()
        .findFirst()
        .orElse(null);
    }
}