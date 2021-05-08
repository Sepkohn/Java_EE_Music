package ch.hevs.businessobject;

import java.util.ArrayList;
import java.util.List;

public class Song extends Music{

	private List<Album> albums;

	public List<Album> getAlbums() {
		return albums;
	}

	public void setAlbums(List<Album> albums) {
		this.albums = albums;
	}
	
	public Song(String name, int duration, int year, Artist artist) {
		super(name, duration, year, artist);
		// TODO Auto-generated constructor stub
		this.albums = new ArrayList<Album>();
	}
}
