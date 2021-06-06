package ch.hevs.businessobject;

import javax.persistence.Entity;
import javax.persistence.Inheritance;

@Entity
@Inheritance
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
	
	public Singer(String stageName, String genre, String realName, String realFirstname) {
		super(stageName, genre);
		// TODO Auto-generated constructor stub
		this.realName = realName;
		this.realFirstname = realFirstname;
	}
	public Singer() {
		
	}

}
