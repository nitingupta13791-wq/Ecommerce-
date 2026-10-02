package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CartItem {
	
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;

	    private int quantity;

	    @ManyToOne
	    @JoinColumn(name = "cart_id")
	    private Cart cart;

	    @ManyToOne
	    @JoinColumn(name = "product_id")
	    private Product product;

	    public CartItem() {
	    }

	    public CartItem(int quantity) {
	        this.quantity = quantity;
	    }
	    
	    
	    
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getQuantity() {
			return quantity;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
		
	

		public Cart getCart() {
			return cart;
		}

		public void setCart(Cart cart) {
			this.cart = cart;
		}

		public Product getProduct() {
			return product;
		}

		public void setProduct(Product product) {
			this.product = product;
		}

		@Override
		public String toString() {
			return "CartItem [id=" + id + ", quantity=" + quantity + ", cart=" + cart + ", product=" + product + "]";
		}
	    

}
