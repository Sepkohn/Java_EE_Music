package ch.hevs.businessobject;

import java.util.ArrayList;
import java.util.List;

public class Album extends Music{

	private List<Song> songs;
	private String label;
	
	public List<Song> getSongs() {
		return songs;
	}
	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	public Album(String name, int duration, int year, Artist artist,String label) {
		super(name, duration, year, artist);
		// TODO Auto-generated constructor stub
		this.label = label;
		this.songs = new ArrayList<Song>();
	}

	
	
	
}
