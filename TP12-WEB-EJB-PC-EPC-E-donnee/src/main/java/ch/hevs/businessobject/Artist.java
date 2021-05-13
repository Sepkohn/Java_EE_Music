package ch.hevs.businessobject;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Artist {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	
	private String stageName;
	private List<Album> albums;
	private List<Song> songs;
	private String origin;
	private String genre;
	
	
	public Artist() {
	
	}
	public String getStageName() {
		return stageName;
	}
	public void setStageName(String stageName) {
		this.stageName = stageName;
	}
	public List<Album> getAlbums() {
		return albums;
	}
	public void setAlbums(List<Album> albums) {
		this.albums = albums;
	}
	public List<Song> getSongs() {
		return songs;
	}
	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public Artist(String stageName, String origin, String genre) {
		super();
		this.stageName = stageName;
		this.origin = origin;
		this.genre = genre;
		this.albums = new ArrayList<Album>();
		this.songs = new ArrayList<Song>();
	}
}
