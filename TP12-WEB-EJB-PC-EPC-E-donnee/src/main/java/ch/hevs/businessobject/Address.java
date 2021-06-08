package ch.hevs.businessobject;
import javax.persistence.Embeddable;

@Embeddable
public class Address {
	
	private int postalCode;
	private String street;
	private String city;



	public int getPostalCode() {
		return postalCode;
	}
	public String getStreet() {
		return street;
	}
	public String getCity() {
		return city;
	}
	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public void setCity(String city) {
		this.city = city;
	}
	// constructors
	public Address() {
	}
	public Address(int postalCode, String street, String city) {
	this.postalCode = postalCode;
	this.street = street;
	this.city = city;
	}

 
}
