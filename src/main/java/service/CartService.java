package service;


import entities.Cart;
import entities.CartItem;
import entities.Customer;
import entities.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import repository.CartItemRepository;

import repository.CustomerRepository;
import repository.ProductRepository;

public class CartService {
	
	private CartItemRepository cartItemRepository;
	private CustomerRepository customerRepository;
	private ProductRepository productRepository;

    private EntityManager em;

    public CartService(EntityManager em) {
     
    	  this.em = em;
    	    this.cartItemRepository = new CartItemRepository(em);
    	    this.customerRepository = new CustomerRepository(em);
    	    this.productRepository = new ProductRepository(em);    }

    public void addToCart(int customerId, int productId, int quantity) {

        EntityTransaction et = em.getTransaction();

        try {
            et.begin();

            Customer customer = customerRepository.findById(customerId);
            Product product = productRepository.findById(productId);
            if (customer == null) {
                System.out.println("Customer not found");
                et.rollback();
                return;
            }

            if (product == null) {
                System.out.println("Product not found");
                et.rollback();
                return;
            }

            if (quantity <= 0) {
                System.out.println("Quantity must be greater than 0");
                et.rollback();
                return;
            }

            Cart cart = customer.getCart();

            if (cart == null) {
                System.out.println("Cart not found");
                et.rollback();
                return;
            }

            if (product.getStock() < quantity) {
                System.out.println("Not enough stock");
                et.rollback();
                return;
            }

            CartItem existingItem = null;

            for (CartItem item : cart.getCartItems()) {

                if (item.getProduct().getId() == productId) {
                    existingItem = item;
                    break;
                }
            }

            if (existingItem != null) {

                existingItem.setQuantity(
                    existingItem.getQuantity() + quantity
                );

            } else {

                CartItem item = new CartItem();

                item.setQuantity(quantity);
                item.setCart(cart);
                item.setProduct(product);

                cart.getCartItems().add(item);

                cartItemRepository.save(item);
            }

            et.commit();

            System.out.println(
                "Product added to cart successfully!"
            );

        } catch (Exception e) {

            if (et.isActive()) {
                et.rollback();
            }

            e.printStackTrace();
        }
    }
    
    public void viewCart(int customerId) {

    	Customer customer = customerRepository.findById(customerId);
    	
        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        Cart cart = customer.getCart();

        if (cart == null || cart.getCartItems().isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        System.out.println("\n===== MY CART =====");

        for (CartItem item : cart.getCartItems()) {
            Product product = item.getProduct();

            System.out.println(
                "Cart Item ID: " + item.getId()
                + " | Product: " + product.getName()
                + " | Price: ₹" + product.getPrice()
                + " | Quantity: " + item.getQuantity()
                + " | Subtotal: ₹" +
                (product.getPrice() * item.getQuantity())
            );
        }

        System.out.println("----------------------------");
        System.out.println("Total: ₹" + cart.getTotalAmount());
    }
    
    public void updateCartQuantity(int customerId, int cartItemId, int newQuantity) {

        EntityTransaction et = em.getTransaction();

        try {
            et.begin();

            Customer customer = customerRepository.findById(customerId);
            
            if (customer == null) {
                System.out.println("Customer not found.");
                et.rollback();
                return;
            }

            Cart cart = customer.getCart();

            if (cart == null) {
                System.out.println("Cart not found.");
                et.rollback();
                return;
            }

            CartItem item = cartItemRepository.findById(cartItemId);
            
            if (item == null) {
                System.out.println("Cart item not found.");
                et.rollback();
                return;
            }

            // Make sure this cart item belongs to this customer's cart
            if (item.getCart().getId() != cart.getId()) {
                System.out.println("This cart item does not belong to your cart.");
                et.rollback();
                return;
            }

            if (newQuantity <= 0) {
                System.out.println("Quantity must be greater than 0.");
                et.rollback();
                return;
            }

            Product product = item.getProduct();

            if (newQuantity > product.getStock()) {
                System.out.println("Not enough stock available.");
                et.rollback();
                return;
            }

            item.setQuantity(newQuantity);

            et.commit();

            System.out.println("Cart quantity updated successfully!");

        } catch (Exception e) {

            if (et.isActive()) {
                et.rollback();
            }

            e.printStackTrace();
        }
    }
    
    
    public void removeFromCart(int customerId, int cartItemId) {

        EntityTransaction et = em.getTransaction();

        try {
            et.begin();

            Customer customer = customerRepository.findById(customerId);

            if (customer == null) {
                System.out.println("Customer not found.");
                et.rollback();
                return;
            }

            Cart cart = customer.getCart();

            if (cart == null) {
                System.out.println("Cart not found.");
                et.rollback();
                return;
            }

            CartItem item = cartItemRepository.findById(cartItemId);

            if (item == null) {
                System.out.println("Cart item not found.");
                et.rollback();
                return;
            }

            // Check ownership
            if (item.getCart().getId() != cart.getId()) {
                System.out.println("This cart item does not belong to your cart.");
                et.rollback();
                return;
            }

            cartItemRepository.delete(item);

            et.commit();

            System.out.println("Product removed from cart successfully!");

        } catch (Exception e) {

            if (et.isActive()) {
                et.rollback();
            }

            e.printStackTrace();
        }
    }
}