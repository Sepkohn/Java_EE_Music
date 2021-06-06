package ch.hevs.businessobject;

import javax.persistence.Entity;
import javax.persistence.Inheritance;

@Entity
@Inheritance
public class Band extends Artist{

	public Band(String stageName, String genre) {
		super(stageName, genre);
		// TODO Auto-generated constructor stub
	}
	
	public Band() {
		
	}	
	
}
