package entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime orderDate;

    private String status;
    
    private double totalAmount; 
    
    
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    
    
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();
    
    @OneToOne(mappedBy = "order")
    private Payment payment;
  
    public Order() {
    }

    public Order(String status) {
        this.status = status;
        this.orderDate = LocalDateTime.now();
    }
    
    

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public Customer getCustomer() {
	    return customer;
	}

	public void setCustomer(Customer customer) {
	    this.customer = customer;
	}
	
	
	
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	
	public Payment getPayment() {
	    return payment;
	}

	public void setPayment(Payment payment) {
	    this.payment = payment;
	}

	
	public double getTotalAmount() {
	    return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
	    this.totalAmount = totalAmount;
	}
	
	@Override
	public String toString() {
		return "Order [id=" + id + ", orderDate=" + orderDate + ", status=" + status + "]";
	}

}