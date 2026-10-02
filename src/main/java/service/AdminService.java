package service;

import entities.Admin;
import jakarta.persistence.EntityManager;
import repository.AdminRepository;

public class AdminService {


    private AdminRepository adminRepository;

    public AdminService(EntityManager em) {
        
        this.adminRepository = new AdminRepository(em);
    }

    public Admin login(String email, String password) {

        Admin admin = adminRepository.findByEmail(email);

        if (admin == null ||
            !admin.getPassword().equals(password)) {

            System.out.println("Invalid admin email or password.");
            return null;
        }

        System.out.println("\nAdmin login successful!");
        System.out.println("Welcome, " + admin.getName());

        return admin;
    }
}