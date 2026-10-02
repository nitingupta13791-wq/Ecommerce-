package repository;

import entities.Address;
import jakarta.persistence.EntityManager;

public class AddressRepository {

    private EntityManager em;

    public AddressRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Address address) {
        em.persist(address);
    }

    public Address findById(int id) {
        return em.find(Address.class, id);
    }
}