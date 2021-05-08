package ch.hevs.businessobject;

import java.util.List;

public class BandMember {

	private String name;
	private String firstname;
	private String role;
	private List<Band> bands;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public List<Band> getBands() {
		return bands;
	}
	public void setBands(List<Band> bands) {
		this.bands = bands;
	}
}
