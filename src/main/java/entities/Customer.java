package entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
@Entity
public class Customer {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String email;
	private String password;
	private long phone;
	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDateTime  createdAt;
	
	


	@OneToOne
	@JoinColumn(name = "address_id")
	private Address address;
	
	

	@OneToMany(mappedBy = "customer")
	private List<Review> reviews = new ArrayList<>();
	
	@OneToMany(mappedBy = "customer")
	private List<Order> orders = new ArrayList<>();
	



	public Customer() {
		
	}
	
	
	@OneToOne
	@JoinColumn(name = "cart_id")
	private Cart cart;
	
	

	public Customer(String name, String email, String password, long phone) {
		
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;

	}

	
	
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	
	public List<Review> getReviews() {
		return reviews;
	}



	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
	
	public Cart getCart() {
	    return cart;
	}

	public void setCart(Cart cart) {
	    this.cart = cart;
	}
	
	public List<Order> getOrders() {
		return orders;
	}


	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
	
	@Override
	public String toString() {
		return "Customer [name=" + name + ", email=" + email + ", password=" + password + ", phone=" + phone
				+ ", createdAt=" + createdAt + "]";
	}
	
	
}
	


