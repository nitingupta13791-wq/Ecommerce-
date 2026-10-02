package entities;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String street;
	private String city;
    private String state;
	private long pincode;
	private String country;
	
	
	public Address() {
		
	}


	public Address(String street, String city, String state, long pincode, String country) {
		
		this.street = street;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
		this.country = country;
	}


	public String getStreet() {
		return street;
	}


	public void setStreet(String street) {
		this.street = street;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public long getPincode() {
		return pincode;
	}


	public void setPincode(long pincode) {
		this.pincode = pincode;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	@Override
	public String toString() {
		return "Address [id=" + id + ", street=" + street + ", city=" + city + ", state=" + state + ", pincode="
				+ pincode + ", country=" + country + "]";
	}
	
	
	

}
