package ch.hevs.businessobject;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

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
