package ch.hevs.businessobject;

import javax.persistence.Entity;

@Entity
public class Singer extends Artist{
	
	
	private String realName;
	private String realFirstname;
	
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	public String getRealFirstname() {
		return realFirstname;
	}
	public void setRealFirstname(String realFirstname) {
		this.realFirstname = realFirstname;
	}
	
	public Singer(String stageName, String genre) {
		super(stageName, genre);
		// TODO Auto-generated constructor stub
	}
	public Singer() {
		
	}

}
