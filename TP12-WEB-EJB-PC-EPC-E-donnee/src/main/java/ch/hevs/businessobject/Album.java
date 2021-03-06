package ch.hevs.businessobject;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
@Inheritance
public class Album extends Music{
	
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "albums",  cascade = CascadeType.ALL)
	private List<Song> songs;
	
	private String label;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Artist artist;
	
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
	
	public Artist getArtist() {
		return artist;
	}
	public void setArtist(Artist artist) {
		this.artist = artist;
	}
	
	public Album(String name, int year, String label) {
		super(name, 0, year);

		this.label = label;
		this.songs = new ArrayList<Song>();
	}
	
	public Album() {
		
	}
	
	public void addSong(Song song) {
		this.songs.add(song);	
	}
	public void removeSong(Song song) {
		this.songs.remove(song);	
	}
		
}
