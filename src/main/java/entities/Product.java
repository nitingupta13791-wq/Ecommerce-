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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String  name;
	private String description;
	private double price;
	private int stock;
	private String brand;
	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDateTime  createdAt;
	
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@OneToMany(mappedBy = "product")
	private List<Review> reviews = new ArrayList<>();
	
	
	@OneToMany(mappedBy = "product")
	private List<CartItem> cartItems = new ArrayList<>();
	
	@OneToMany(mappedBy = "product")
	private List<OrderItem> orderItems = new ArrayList<>();
	
public Product() {
	
}

public Product(String name, String description, double price, int stock, String brand) {
	
	this.name = name;
	this.description = description;
	this.price = price;
	this.stock = stock;
	this.brand = brand;

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

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public double getPrice() {
	return price;
}

public void setPrice(double price) {
	this.price = price;
}

public int getStock() {
	return stock;
}

public void setStock(int stock) {
	this.stock = stock;
}

public String getBrand() {
	return brand;
}

public void setBrand(String brand) {
	this.brand = brand;
}

public LocalDateTime getCreatedAt() {
	return createdAt;
}

public void setCreatedAt(LocalDateTime createdAt) {
	this.createdAt = createdAt;
}



public Category getCategory() {
    return category;
}

public void setCategory(Category category) {
    this.category = category;
}


public List<Review> getReviews() {
	return reviews;
}

public void setReviews(List<Review> reviews) {
	this.reviews = reviews;
}


public List<CartItem> getCartItems() {
    return cartItems;
}

public void setCartItems(List<CartItem> cartItems) {
    this.cartItems = cartItems;
}




public List<OrderItem> getOrderItems() {
	return orderItems;
}

public void setOrderItems(List<OrderItem> orderItems) {
	this.orderItems = orderItems;
}

@Override
public String toString() {
	return "Product [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price + ", stock="
			+ stock + ", brand=" + brand + ", createdAt=" + createdAt + "]";
}


}

