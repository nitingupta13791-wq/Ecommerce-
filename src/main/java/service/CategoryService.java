package service;

import java.util.List;


import entities.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import repository.CategoryRepository;

public class CategoryService {

    private CategoryRepository categoryRepository;

    private EntityManager em;
    
    public CategoryService(EntityManager em) {
    	 this.em = em;
        this.categoryRepository = new CategoryRepository(em);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    
    
    public Category addCategory(Category category) {

        EntityTransaction et = em.getTransaction();

        try {
            et.begin();

            Category existingCategory =
                    categoryRepository.findByName(category.getName());

            if (existingCategory != null) {
                System.out.println("Category already exists. Using existing category.");

                et.commit();

                return existingCategory;
            }

            categoryRepository.save(category);

            et.commit();

            System.out.println("Category added successfully!");

            return category;

        } catch (Exception e) {

            if (et.isActive()) {
                et.rollback();
            }

            e.printStackTrace();
            return null;
        }
    }
}