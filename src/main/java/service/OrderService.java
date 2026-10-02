package service;



import java.util.List;

import entities.Cart;
import entities.CartItem;
import entities.Customer;
import entities.Order;
import entities.OrderItem;
import entities.Payment;
import entities.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import repository.CartItemRepository;
import repository.OrderItemRepository;
import repository.OrderRepository;
import repository.PaymentRepository;
import repository.CustomerRepository;

public class OrderService {

    private EntityManager em;

    
    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private PaymentRepository paymentRepository;

    
    private CartItemRepository cartItemRepository;
    private CustomerRepository customerRepository;
    public OrderService(EntityManager em) {

        this.em = em;

        this.orderRepository = new OrderRepository(em);
        this.orderItemRepository = new OrderItemRepository(em);
        this.paymentRepository = new PaymentRepository(em);
        this.cartItemRepository = new CartItemRepository(em);
        this.customerRepository = new CustomerRepository(em);
    }

    public void checkout(int customerId) {

        EntityTransaction et = em.getTransaction();

        try {

            et.begin();

            Customer customer = customerRepository.findById(customerId);

            if (customer == null) {
                System.out.println("Customer not found");
                et.rollback();
                return;
            }

            Cart cart = customer.getCart();

            if (cart == null || cart.getCartItems().isEmpty()) {
                System.out.println("Cart is empty");
                et.rollback();
                return;
            }

            double total = cart.getTotalAmount();

            Order order = new Order("PLACED");

            order.setCustomer(customer);
            order.setTotalAmount(total);

            orderRepository.save(order);
            for (CartItem cartItem : cart.getCartItems()) {

                Product product = cartItem.getProduct();

                int quantity = cartItem.getQuantity();

                if (product.getStock() < quantity) {

                    System.out.println(
                        "Not enough stock for "
                        + product.getName()
                    );

                    et.rollback();
                    return;
                }

                OrderItem orderItem = new OrderItem();

                orderItem.setQuantity(quantity);
                orderItem.setPrice(product.getPrice());
                orderItem.setProduct(product);
                orderItem.setOrder(order);
                order.getOrderItems().add(orderItem);

                orderItemRepository.save(orderItem);                
                // Reduce stock
                product.setStock(
                    product.getStock() - quantity
                );
            }

            // Remove cart items
            for (CartItem cartItem : cart.getCartItems()) {
            	cartItemRepository.delete(cartItem);
            }

            // Create payment
            Payment payment = new Payment(
                order.getTotalAmount(),
                "UPI",
                "SUCCESS"
            );

            payment.setOrder(order);
            

            paymentRepository.save(payment);

            et.commit();

            System.out.println("\n===== CHECKOUT SUCCESSFUL =====");
            System.out.println("Order ID: " + order.getId());
            System.out.println(
                "Total Amount: ₹" + order.getTotalAmount()
            );
            System.out.println(
                "Payment Status: " + payment.getStatus()
            );

        } catch (Exception e) {

            if (et.isActive()) {
                et.rollback();
            }

            e.printStackTrace();
        }
    
    }
    
    public void viewMyOrders(int customerId) {

        Customer customer =customerRepository.findById(customerId);

        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        if (customer.getOrders().isEmpty()) {
            System.out.println("You have no orders.");
            return;
        }

        System.out.println("\n===== MY ORDERS =====");

        for (Order order : customer.getOrders()) {

            System.out.println(
                "Order ID: " + order.getId()
                + " | Date: " + order.getOrderDate()
                + " | Status: " + order.getStatus()
                + " | Total: ₹" + order.getTotalAmount()
            );
        }
    }
    
    public void viewOrderDetails(int customerId, int orderId) {

        Customer customer = customerRepository.findById(customerId);

        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        Order order = orderRepository.findById(orderId);

        if (order == null) {
            System.out.println("Order not found.");
            return;
        }

        // Make sure this order belongs to the logged-in customer
        if (order.getCustomer().getId() != customerId) {
            System.out.println("This order does not belong to you.");
            return;
        }

        System.out.println("\n===== ORDER DETAILS =====");
        System.out.println("Order ID: " + order.getId());
        System.out.println("Order Date: " + order.getOrderDate());
        System.out.println("Status: " + order.getStatus());

        System.out.println("\n----- ITEMS -----");

        for (OrderItem item : order.getOrderItems()) {

            Product product = item.getProduct();

            System.out.println(
                "Product: " + product.getName()
                + " | Quantity: " + item.getQuantity()
                + " | Price: ₹" + item.getPrice()
                + " | Subtotal: ₹" +
                (item.getPrice() * item.getQuantity())
            );
        }

        System.out.println("----------------------------");
        System.out.println("Total Amount: ₹" + order.getTotalAmount());

        if (order.getPayment() != null) {
            System.out.println("Payment Method: "
                    + order.getPayment().getPaymentMethod());

            System.out.println("Payment Status: "
                    + order.getPayment().getStatus());
        }
    }
    
    public void viewAllOrders() {

        List<Order> orders = orderRepository.findAll();

        if (orders.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }

        System.out.println("\n===== ALL ORDERS =====");

        for (Order order : orders) {

            Customer customer = order.getCustomer();

            System.out.println("Order ID   : " + order.getId());

            if (customer != null) {
                System.out.println("Customer   : " + customer.getName());
                System.out.println("Email      : " + customer.getEmail());
            }

            System.out.println("Date       : " + order.getOrderDate());
            System.out.println("Status     : " + order.getStatus());
            System.out.println("Total      : ₹" + order.getTotalAmount());
            System.out.println("--------------------------");
        }
    }
    
    public void updateOrderStatus(int orderId, String newStatus) {

        EntityTransaction et = em.getTransaction();

        try {
            et.begin();

            Order order = orderRepository.findById(orderId);

            if (order == null) {
                System.out.println("Order not found.");
                et.rollback();
                return;
            }

            newStatus = newStatus.toUpperCase();

            if (!newStatus.equals("PLACED")
                    && !newStatus.equals("CONFIRMED")
                    && !newStatus.equals("SHIPPED")
                    && !newStatus.equals("DELIVERED")
                    && !newStatus.equals("CANCELLED")) {

                System.out.println("Invalid order status.");
                et.rollback();
                return;
            }

            order.setStatus(newStatus);

            et.commit();

            System.out.println("Order status updated successfully!");
            System.out.println("Order ID : " + order.getId());
            System.out.println("Status   : " + order.getStatus());

        } catch (Exception e) {

            if (et.isActive()) {
                et.rollback();
            }

            e.printStackTrace();
        }
    }
}
