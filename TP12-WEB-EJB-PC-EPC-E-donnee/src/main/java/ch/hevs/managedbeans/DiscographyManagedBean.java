package ch.hevs.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.faces.event.ValueChangeEvent;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ch.hevs.bankservice.Bank;
import ch.hevs.bankservice.Discography;
import ch.hevs.businessobject.Account;
import ch.hevs.businessobject.Album;
import ch.hevs.businessobject.Artist;
import ch.hevs.businessobject.Song;

@Stateful
public class DiscographyManagedBean {
	
	private List<Artist> artists;
	private List<String> artistNames;
	private List<Album> albums;
	private List<String> albumNames;
	private List<Song> songs;
	private List<String> songNames;
	private boolean albumLength;
	
	private String sourceArtistName;
	private String sourceAlbumName;
	private String sourceSongName;
	
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
			this.sourceArtistName = artistNames.get(0);
			
			albums = disco.getAlbums(sourceArtistName);
		    this.albumNames = new ArrayList<String>();
			for (Album album : albums) {
				this.albumNames.add(album.getName());
			}
			this.sourceAlbumName = albumNames.get(0);
			
			songs = disco.getSongsFromAlbum(this.sourceAlbumName);
		    this.songNames = new ArrayList<String>();
			for (Song song : songs) {
				this.songNames.add(song.getName());
			}
			this.sourceSongName = songNames.get(0);
			
	    }


	public List<Artist> getArtists() {
		return artists;
	}
	public void setArtists(List<Artist> artists) {
		this.artists = artists;
	}

	public List<String> getArtistNames() {
		return artistNames;
	}
	public void setArtistNames(List<String> artistNames) {
		this.artistNames = artistNames;
	}
	
	public List<String> getAlbumNames() {
		return albumNames;
	}
	public void setAlbumNames(List<String> albumNames) {
		this.albumNames = albumNames;
	}


	public List<String> getSongNames() {
		return songNames;
	}
	public void setSongNames(List<String> songNames) {
		this.songNames = songNames;
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
	
	
	public String getSourceAlbumName() {
		return sourceAlbumName;
	}
	public void setSourceAlbumName(String sourceAlbumName) {
		this.sourceAlbumName = sourceAlbumName;
	}


	public String getSourceSongName() {
		return sourceSongName;
	}
	public void setSourceSongName(String sourceSongName) {
		this.sourceSongName = sourceSongName;
	}


	public void updateSourceArtist(ValueChangeEvent event) {
    	this.sourceArtistName = (String)event.getNewValue();
    	
    	List<Album> albums = disco.getAlbums(this.sourceArtistName);
	    this.albumNames = new ArrayList<String>();
		for (Album album : albums) {
			this.albumNames.add(album.getName());
		}
    }
	
	public void updateSourceAlbum(ValueChangeEvent event) {
    	this.sourceAlbumName = (String)event.getNewValue();
    	
    	List<Song> songs = disco.getSongsFromAlbum(this.sourceAlbumName);
	    this.songNames = new ArrayList<String>();
		for (Song song : songs) {
			this.songNames.add(song.getName());
		}
    }
	
	public void updateSourceSong(ValueChangeEvent event) {
    	this.sourceSongName = (String)event.getNewValue();
    }
	
	public String toAlbums() {	
		return "welcomeAlbum";
	}
	
	public boolean getAlbumlength() {
			return !this.albumNames.isEmpty();
	}
	 

}
