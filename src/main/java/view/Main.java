package view;

import java.util.List;



import java.util.Scanner;

import entities.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import jakarta.persistence.Persistence;

import service.AdminService;
import service.CartService;
import service.CategoryService;
import service.CustomerService;
import service.OrderService;
import service.ProductService;

public class Main {
	
           //Customer Menu
	
	public static void customerMenu(
	        Customer customer,
	        ProductService productService,
	        CartService cartService,
	        OrderService orderService) {

           Scanner sc= new Scanner(System.in);

	    while (true) {

	        System.out.println("\n===== CUSTOMER MENU =====");
	        System.out.println("1. View Products");
	        System.out.println("2. Add to Cart");
	        System.out.println("3. View Cart");
	        System.out.println("4. Update Cart Quantity");
	        System.out.println("5. Remove from Cart");
	        System.out.println("6. Checkout");
	        System.out.println("7. My Orders");
	        System.out.println("8. Order Details");
	        System.out.println("9. Logout");

	        System.out.print("Enter choice: ");
	        int choice = sc.nextInt();

	        switch (choice) {

	        case 1: {
	            productService.viewProducts();
	            break;
	        }

	        case 2: {

	            System.out.print("Enter Product ID: ");
	            int productId = sc.nextInt();

	            System.out.print("Enter Quantity: ");
	            int quantity = sc.nextInt();

	            cartService.addToCart(
	                customer.getId(),
	                productId,
	                quantity
	            );

	            break;
	        }

	        case 3: {
	            cartService.viewCart(customer.getId());
	            break;
	        }

	        case 4: {
	            System.out.print("Enter Cart Item ID: ");
	            int cartItemId = sc.nextInt();

	            System.out.print("Enter New Quantity: ");
	            int newQuantity = sc.nextInt();

	            cartService.updateCartQuantity(
	                customer.getId(),
	                cartItemId,
	                newQuantity
	            );

	            break;
	        }

	        case 5: {
	            System.out.print("Enter Cart Item ID: ");
	            int cartItemId = sc.nextInt();

	            cartService.removeFromCart(
	                customer.getId(),
	                cartItemId
	            );

	            break;
	        }

	        case 6:
	            orderService.checkout(customer.getId());
	            break;

	        case 7:
	            orderService.viewMyOrders(customer.getId());
	            break;

	        case 8: {
	            System.out.print("Enter Order ID: ");
	            int orderId = sc.nextInt();

	            orderService.viewOrderDetails(
	                customer.getId(),
	                orderId
	            );

	            break;
	        }

	        case 9:
	            System.out.println("Logged out successfully!");
	            return;

	        default:
	            System.out.println("Invalid choice!");
	        }
	    }
	}
	

	
                      //Admin manu
	
	
	
	
	
	public static void adminMenu(

	        ProductService productService,
	        OrderService orderService,
	        CategoryService categoryService) {

	    Scanner sc = new Scanner(System.in);

	    while (true) {

	        System.out.println("\n===== ADMIN MENU =====");
	        System.out.println("1. View Products");
	        System.out.println("2. Add Product");
	        System.out.println("3. Update Product");
	        System.out.println("4. Delete Product");
	        System.out.println("5. View All Orders");
	        System.out.println("6. Update Order Status");
	        System.out.println("7. Logout");

	        System.out.print("Enter choice: ");
	        int choice = sc.nextInt();
	        sc.nextLine();

	        switch (choice) {

	        case 1:
	        	productService.viewProducts();
	        	break;

	        case 2: {

	            List<Category> categories =
	                    categoryService.getAllCategories();

	            System.out.println("\n===== AVAILABLE CATEGORIES =====");

	            for (int i = 0; i < categories.size(); i++) {

	                Category category = categories.get(i);

	                System.out.println(
	                    (i + 1) + ". " + category.getName()
	                );
	            }

	            System.out.println(
	                (categories.size() + 1) + ". Add New Category"
	            );

	            System.out.print("\nEnter Product Name: ");
	            String name = sc.nextLine();

	            System.out.print("Enter Description: ");
	            String description = sc.nextLine();

	            System.out.print("Enter Brand: ");
	            String brand = sc.nextLine();

	            System.out.print("Enter Price: ");
	            double price = sc.nextDouble();

	            System.out.print("Enter Stock: ");
	            int stock = sc.nextInt();

	            System.out.print("Select Category: ");
	            int categoryChoice = sc.nextInt();
	            sc.nextLine();

	            int categoryId;

	            if (categoryChoice == categories.size() + 1) {

	                System.out.print("Enter New Category Name: ");
	                String newCategoryName = sc.nextLine();

	                System.out.print("Enter Category Description: ");
	                String newCategoryDescription = sc.nextLine();

	                Category newCategory = new Category();
	                newCategory.setName(newCategoryName);
	                newCategory.setDescription(newCategoryDescription);

	                Category selectedCategory =
	                    categoryService.addCategory(newCategory);

	                categoryId = selectedCategory.getId();

	            } else {

	                Category selectedCategory =
	                    categories.get(categoryChoice - 1);

	                categoryId = selectedCategory.getId();
	            }

	            productService.addProduct(
	                name,
	                description,
	                brand,
	                price,
	                stock,
	                categoryId
	            );
	            break;
	        }
	      

	        case 3: {

	            System.out.print("Enter Product ID: ");
	            int productId = sc.nextInt();

	            System.out.print("Enter New Price: ");
	            double newPrice = sc.nextDouble();

	            System.out.print("Enter New Stock: ");
	            int newStock = sc.nextInt();
	            sc.nextLine();

	            productService.updateProduct(
	                productId,
	                newPrice,
	                newStock
	            );

	            break;
	        }

	        case 4: {

	            System.out.print("Enter Product ID: ");
	            int productId = sc.nextInt();
	            sc.nextLine();

	            productService.deleteProduct(productId);

	            break;
	        }

	        case 5:
	            orderService.viewAllOrders();
	            break;

	        case 6: {

	            System.out.print("Enter Order ID: ");
	            int orderId = sc.nextInt();
	            sc.nextLine();

	            System.out.print(
	                "Enter Status (PLACED/CONFIRMED/SHIPPED/DELIVERED/CANCELLED): "
	            );

	            String status = sc.nextLine();

	            orderService.updateOrderStatus(
	                orderId,
	                status
	            );

	            break;
	        }

	        case 7:

	            System.out.println("Admin logged out!");
	            return;

	        default:

	            System.out.println("Invalid choice!");
	        }
	    }
	}
	
	
	
	public static void main(String[] args) {

	    EntityManagerFactory emf =
	            Persistence.createEntityManagerFactory("ecommerce");

	    EntityManager em = emf.createEntityManager();

	    ProductService productService =
	            new ProductService(em);

	    OrderService orderService =
	            new OrderService(em);

	    CartService cartService =
	            new CartService(em);

	    CustomerService customerService =
	            new CustomerService(em);
	    
	    
	    AdminService adminService = new AdminService(em);
	    
	    CategoryService categoryService = new CategoryService(em);

	    Scanner sc = new Scanner(System.in);

	    while (true) {

	        System.out.println("\n===== E-COMMERCE APPLICATION =====");
	        System.out.println("1. Customer");
	        System.out.println("2. Admin");
	        System.out.println("3. Exit");

	        System.out.print("Enter choice: ");
	        int choice = sc.nextInt();
	        sc.nextLine();

	        switch (choice) {
	        case 1: {

	            System.out.println("\n===== CUSTOMER =====");
	            System.out.println("1. Login");
	            System.out.println("2. Register");

	            System.out.print("Enter choice: ");
	            int customerChoice = sc.nextInt();
	            sc.nextLine();

	            if (customerChoice == 1) {

	                System.out.print("Enter Email: ");
	                String email = sc.nextLine();

	                System.out.print("Enter Password: ");
	                String password = sc.nextLine();

	                Customer customer =
	                        customerService.login(email, password);

	                if (customer != null) {
	                    customerMenu(
	                        
	                        customer,
	                        productService,
	                        cartService,
	                        orderService
	                    );
	                }

	            } else if (customerChoice == 2) {

	                System.out.print("Enter Name: ");
	                String name = sc.nextLine();

	                System.out.print("Enter Email: ");
	                String email = sc.nextLine();

	                System.out.print("Enter Password: ");
	                String password = sc.nextLine();

	                System.out.print("Enter Phone: ");
	                long phone = sc.nextLong();
	                sc.nextLine();

	                System.out.println("\n===== ADDRESS DETAILS =====");

	                System.out.print("Enter Street: ");
	                String street = sc.nextLine();

	                System.out.print("Enter City: ");
	                String city = sc.nextLine();

	                System.out.print("Enter State: ");
	                String state = sc.nextLine();

	                System.out.print("Enter Pincode: ");
	                long pincode = sc.nextLong();
	                sc.nextLine();

	                System.out.print("Enter Country: ");
	                String country = sc.nextLine();

	                customerService.register(
	                    name,
	                    email,
	                    password,
	                    phone,
	                    street,
	                    city,
	                    state,
	                    pincode,
	                    country
	                );

	            } else {

	                System.out.println("Invalid choice!");
	            }

	            break;
	        }

	        case 2: {

	            System.out.println("\n===== ADMIN LOGIN =====");

	            System.out.print("Enter Email: ");
	            String email = sc.nextLine();

	            System.out.print("Enter Password: ");
	            String password = sc.nextLine();

	            Admin admin = adminService.login(email, password);

	            if (admin != null) {
	                adminMenu(
	                    
	                    productService,
	                    orderService,
	                    categoryService
	                );
	            }

	            break;
	        }
	            case 3:
	                System.out.println("Application closed.");
	                sc.close();
	                em.close();
	                emf.close();
	                return;

	            default:
	                System.out.println("Invalid choice!");
	        }
	    }
	}
    }
