package entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    
    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems = new ArrayList<>();

    public Cart() {
    }
    
    

	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	@Override
	public String toString() {
		return "Cart [id=" + id + "]";
	}
	
	
	public List<CartItem> getCartItems() {
	    return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
	    this.cartItems = cartItems;
	}
	
	
	
	// methods
	
	public double getTotalAmount() {

	    double total = 0;

	    for (CartItem item : cartItems) {
	        total += item.getProduct().getPrice() * item.getQuantity();
	    }

	    return total;
	}
    
    
}
