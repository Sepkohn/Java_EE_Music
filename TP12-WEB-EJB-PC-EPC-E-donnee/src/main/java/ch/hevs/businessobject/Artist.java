package ch.hevs.businessobject;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.DiscriminatorOptions;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Artist {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	
	@JoinColumn(nullable = false)
	private String stageName;
	
	@OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
	private List<Album> albums;
	
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
	}
	
	public void addAlbum(Album album) {
		album.setArtist(this);
		this.albums.add(album);
	}
	
	
	@Override
	public String toString() {
		String result = id + "-" + stageName;
		return result;
	}
}
