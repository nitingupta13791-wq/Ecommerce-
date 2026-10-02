package service;

import java.time.LocalDateTime;

import entities.Address;
import entities.Cart;
import entities.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import repository.AddressRepository;
import repository.CustomerRepository;

public class CustomerService {

    private EntityManager em;
    private CustomerRepository customerRepository;
    
    private AddressRepository addressRepository;

    public CustomerService(EntityManager em) {
        this.em = em;
        this.customerRepository = new CustomerRepository(em);
        this.addressRepository = new AddressRepository(em);
    }

    public Customer register(String name, String email,
            String password, long phone,
            String street, String city,
            String state, long pincode,
            String country) {

        EntityTransaction et = em.getTransaction();

        try {
            et.begin();

            Customer existingCustomer =
                    customerRepository.findByEmail(email);

            if (existingCustomer != null) {
                System.out.println("Email already registered.");
                et.rollback();
                return null;
            }

            Customer customer = new Customer();

            customer.setName(name);
            customer.setEmail(email);
            customer.setPassword(password);
            customer.setPhone(phone);
            customer.setCreatedAt(LocalDateTime.now());
            
            Address address = new Address(
            	    street,
            	    city,
            	    state,
            	    pincode,
            	    country
            	);

            	addressRepository.save(address);

            	customer.setAddress(address);

            Cart cart = new Cart();
            em.persist(cart);

            customer.setCart(cart);

            customerRepository.save(customer);

            et.commit();

            System.out.println("\nRegistration successful!");
            System.out.println("Customer ID: " + customer.getId());

            return customer;

        } catch (Exception e) {

            if (et.isActive()) {
                et.rollback();
            }

            e.printStackTrace();
            return null;
        }
    }

    public Customer login(String email, String password) {

        Customer customer =
                customerRepository.findByEmail(email);

        if (customer == null ||
            !customer.getPassword().equals(password)) {

            System.out.println("Invalid email or password.");
            return null;
        }

        System.out.println("\nLogin successful!");
        System.out.println("Welcome, " + customer.getName());

        return customer;
    }
}