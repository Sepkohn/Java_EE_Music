package ch.hevs.businessobject;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

public class Band extends Artist{

	private List<BandMember> members;
	
	public Band(String stageName, String origin, String genre) {
		super(stageName, origin, genre);
		// TODO Auto-generated constructor stub
		this.members = new ArrayList<BandMember>();
	}
	
}
