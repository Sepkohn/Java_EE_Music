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

	@ManyToMany
	private List<BandMember> members;
	
	public Band(String stageName, String genre) {
		//super(stageName, genre);
		// TODO Auto-generated constructor stub
		this.members = new ArrayList<BandMember>();
	}
	
	public Band() {
		
	}
	
	public void addMember(BandMember member) {
		member.addBand(this);
		this.members.add(member);
	}
	
}
