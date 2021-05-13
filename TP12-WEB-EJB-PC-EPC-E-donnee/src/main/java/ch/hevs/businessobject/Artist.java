package ch.hevs.businessobject;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
@Entity
public class Artist {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	
	private String stageName;
	
	@OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
	private List<Album> albums;
	@OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
	private List<Song> songs;
	
	@Embedded
	private Address address;
	
	private String genre;
	
	
	public Artist() {
	
	}
	
	public long getId() {
		return id;
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
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public Artist(String stageName, String genre) {
		super();
		this.stageName = stageName;
		this.genre = genre;
		this.albums = new ArrayList<Album>();
		this.songs = new ArrayList<Song>();
	}
	
	public void addAlbum(Album album) {
		album.setArtist(this);
		this.albums.add(album);
	}
	
	public void addSong(Song song) {
		song.setArtist(this);
		this.songs.add(song);
	}
	
	@Override
	public String toString() {
		String result = id + "-" + stageName;
		return result;
	}
}
