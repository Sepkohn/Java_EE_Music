package ch.hevs.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ValueChangeEvent;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ch.hevs.bankservice.Bank;
import ch.hevs.bankservice.Discography;
import ch.hevs.businessobject.Account;
import ch.hevs.businessobject.Album;
import ch.hevs.businessobject.Artist;
import ch.hevs.businessobject.Song;

public class DiscographyManagedBean {
	
	private List<Artist> artists;
	private String artistName; //test
	private List<String> artistNames;
	private List<Album> albums;
	private List<String> albumNames;
	private List<Song> songs;
	private List<String> songNames;
	
	private String sourceArtistName;
	
	private Discography disco;
	
	
	 @PostConstruct
	    public void initialize() throws NamingException {
	    	
	    	// use JNDI to inject reference to bank EJB
	    	InitialContext ctx = new InitialContext();
			disco = (Discography) ctx.lookup("java:global/TP12-WEB-EJB-PC-EPC-E-0.0.1-SNAPSHOT/DiscographyBean!ch.hevs.bankservice.Discography");    	
			
			
	    	// get clients
			this.artists = disco.getArtists();
			
			this.artistNames = new ArrayList<String>();
			for (Artist artist : this.artists) {
				this.artistNames.add(artist.getStageName());
			}
			
			this.albums = new ArrayList<Album>();
			this.songs = new ArrayList<Song>();
			
	    }


	public List<Artist> getArtists() {
		return artists;
	}
	public void setArtists(List<Artist> artists) {
		this.artists = artists;
	}
	
	public String getArtistName() {
		return artistName;
	}

	public List<String> getArtistNames() {
		return artistNames;
	}
	public void setArtistNames(List<String> artistNames) {
		this.artistNames = artistNames;
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


	public String getSourceArtistName() {
		return sourceArtistName;
	}
	public void setSourceArtistName(String sourceArtistName) {
		this.sourceArtistName = sourceArtistName;
	}
	
	public void updateSourceArtist(ValueChangeEvent event) {
    	this.sourceArtistName = (String)event.getNewValue();
    	
	    disco.getAlbums(this.sourceArtistName);
	    this.albumNames = new ArrayList<String>();
		for (Album album : albums) {
			this.albumNames.add(album.getName());
		}
		
		List<Song> songs = disco.getSongsFromArtist(this.sourceArtistName);
	    this.songNames = new ArrayList<String>();
		for (Song song : songs) {
			this.songNames.add(song.getName());
		}
    }
	
	 
	 

}
