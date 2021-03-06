package ch.hevs.businessobject;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.ManyToMany;

@Entity
@Inheritance
public class Song extends Music{
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Album> albums;

	public List<Album> getAlbums() {
		return albums;
	}

	public void setAlbums(List<Album> albums) {
		this.albums = albums;
	}
	
	public Song(String name, int duration, int year) {
		super(name, duration, year);
		
		this.albums = new ArrayList<Album>();
	}
	public Song() {
		
	}
	
	public void addAlbum(Album album) {
		album.addSong(this);
		this.albums.add(album);
	}
	
}
