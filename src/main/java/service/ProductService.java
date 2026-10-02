package service;

import java.time.LocalDateTime;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import entities.Category;
import entities.Product;
import repository.CategoryRepository;
import repository.ProductRepository;

public class ProductService {

    private EntityManager em;
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public ProductService(EntityManager em) {
        this.em = em;
        this.productRepository = new ProductRepository(em);
        this.categoryRepository = new CategoryRepository(em);
    }

    public void addProduct(Product product) {

        EntityTransaction et = em.getTransaction();

        try {
            et.begin();

            productRepository.save(product);

            et.commit();

            System.out.println("Product added successfully!");

        } catch (Exception e) {

            if (et.isActive()) {
                et.rollback();
            }

            e.printStackTrace();
        }
    }

    public Product getProduct(int id) {

        return productRepository.findById(id);
    }

    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }

    public void deleteProduct(int id) {

        EntityTransaction et = em.getTransaction();

        try {
            et.begin();

            Product product = productRepository.findById(id);

            if (product == null) {
                System.out.println("Product not found");
                et.rollback();
                return;
            }

            productRepository.delete(product);

            et.commit();

            System.out.println("Product deleted successfully!");

        } catch (Exception e) {

            if (et.isActive()) {
                et.rollback();
            }

            e.printStackTrace();
        }
    }

    public void viewProducts() {

        List<Product> products =
                productRepository.findAll();

        System.out.println("\n===== PRODUCTS =====");

        for (Product p : products) {

            System.out.println("ID       : " + p.getId());
            System.out.println("Name     : " + p.getName());
            System.out.println("Brand    : " + p.getBrand());
            System.out.println("Price    : ₹" + p.getPrice());
            System.out.println("Stock    : " + p.getStock());

            if (p.getCategory() != null) {
                System.out.println(
                    "Category : " +
                    p.getCategory().getName()
                );
            }

            System.out.println("----------------------");
        }
    }
    
    
    public void addProduct(String name,
            String description,
            String brand,
            double price,
            int stock,
            int categoryId) {

EntityTransaction et = em.getTransaction();

try {
et.begin();

Category category = categoryRepository.findById(categoryId);

if (category == null) {
 System.out.println("Category not found");
 et.rollback();
 return;
}

Product product = new Product();

product.setName(name);
product.setDescription(description);
product.setBrand(brand);
product.setPrice(price);
product.setStock(stock);
product.setCreatedAt(LocalDateTime.now());
product.setCategory(category);

productRepository.save(product);

et.commit();

System.out.println("Product added successfully!");
System.out.println("Product ID: " + product.getId());

} catch (Exception e) {

if (et.isActive()) {
 et.rollback();
}

e.printStackTrace();
}
}
    
    public void updateProduct(int productId,
            double newPrice,
            int newStock) {

EntityTransaction et = em.getTransaction();

try {
et.begin();

Product product = productRepository.findById(productId);

if (product == null) {
System.out.println("Product not found");
et.rollback();
return;
}

if (newPrice <= 0) {
System.out.println("Price must be greater than 0");
et.rollback();
return;
}

if (newStock < 0) {
System.out.println("Stock cannot be negative");
et.rollback();
return;
}

product.setPrice(newPrice);
product.setStock(newStock);

et.commit();

System.out.println("Product updated successfully!");

} catch (Exception e) {

if (et.isActive()) {
et.rollback();
}

e.printStackTrace();
}
}
}