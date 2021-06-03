package ch.hevs.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.faces.event.ValueChangeEvent;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ch.hevs.bankservice.Bank;
import ch.hevs.bankservice.Discography;
import ch.hevs.businessobject.Account;
import ch.hevs.businessobject.Album;
import ch.hevs.businessobject.Artist;
import ch.hevs.businessobject.Band;
import ch.hevs.businessobject.Singer;
import ch.hevs.businessobject.Song;

@Stateless
public class DiscographyManagedBean {
	
	private List<String> artistNames;
	private List<String> albumNames;
	private List<String> songNames;
	private boolean albumLength;
	
	private String sourceArtistName;
	private String sourceAlbumName;
	private String sourceSongName;
	
	//Ajout Artist 
	private String newArtistName;
	private String newGenre;
	private String artistType;
	
	//Ajout Album
	private String nameMusic;
	private int duration;
	private int year;
	private String label;
	
	private String addingResult;
	
	private int numberOfSongs;
	
	
	private Discography disco;
	

	
	
	 @PostConstruct
	    public void initialize() throws NamingException {
	    	
	    	// use JNDI to inject reference to bank EJB
	    	InitialContext ctx = new InitialContext();
			disco = (Discography) ctx.lookup("java:global/TP12-WEB-EJB-PC-EPC-E-0.0.1-SNAPSHOT/DiscographyBean!ch.hevs.bankservice.Discography");    	
			
			
	    	// get clients
			artistList();
	
			//Pour vider les champs lorsque l'on retourne sur une page
			clearInputs();
			
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
    	
    	albumList();
    	songList();
    }
	
	public void updateSourceAlbum(ValueChangeEvent event) {
    	this.sourceAlbumName = (String)event.getNewValue();
    	
    	songList();
    }
	
	public void updateSourceSong(ValueChangeEvent event) {
    	this.sourceSongName = (String)event.getNewValue();
    }
	
	
	private void artistList() {
		List<Artist> artists = disco.getArtists();
		
		this.artistNames = new ArrayList<String>();
		for (Artist artist : artists) {
			this.artistNames.add(artist.getStageName());
		}
		this.sourceArtistName = artistNames.get(0);
		albumList();
	}
	private void albumList() {
		
		List<Album> albums = disco.getAlbums(sourceArtistName);
	    this.albumNames = new ArrayList<String>();
		for (Album album : albums) {
			this.albumNames.add(album.getName());
		}
		if(!albumNames.isEmpty()) {
			this.sourceAlbumName = albumNames.get(0);
			songList();
		}
		
	}
	
	private void songList() {
		List<Song> songs = disco.getSongsFromAlbum(this.sourceAlbumName, this.sourceArtistName);
	    this.songNames = new ArrayList<String>();
		for (Song song : songs) {
			this.songNames.add(song.getName());
		}
		if(!songNames.isEmpty()) {
			this.sourceSongName = songNames.get(0);
		}
	}
	
	public String toAlbums() {	
		return "welcomeAlbum";
	}
	
	public boolean getAlbumlength() {
			return !this.albumNames.isEmpty();
	}
	 
	
	public void updateSourceArtistNew(ValueChangeEvent event) {
    	this.sourceArtistName = (String)event.getNewValue();
    	
    	List<Album> albums = disco.getAlbums(this.sourceArtistName);
	    this.albumNames = new ArrayList<String>();
		for (Album album : albums) {
			this.albumNames.add(album.getName());
		}
    }
	
	public int getNumberOfSongs() {
		return disco.getNumberOfSongs(this.sourceArtistName);
	}
	
	
	//ADDING ------------------------------
	public String addArtist() {
		
		Artist artist;
		switch(this.artistType) {
		case("Singer"):
			artist = new Singer(newArtistName, newGenre);
			break;
		case("Band"):
			artist = new Band(newArtistName, newGenre);
			break;
		default : 
			artist = new Artist();
		}
		
		disco.addArtist(artist);
		
		artistList();
		
		clearInputs();
		
		this.addingResult = "Artiste";
		
		return "showAddingResult";
	}
	
	public String addAlbum() {

		Album album = new Album(nameMusic, duration, year, label);
		Artist artist = disco.getArtist(sourceArtistName);

		disco.addAlbum(album, artist);
		
		albumList();
		
		clearInputs();
		
		this.addingResult = "Album";
		
		return "showAddingResult";
	}
	
	public String addSong() {

		Song song = new Song(nameMusic, duration, year);
		Album album = disco.getAlbum(sourceAlbumName, sourceArtistName);
		song.addAlbum(album);

		disco.addSongToAlbum(song, album);
		
		songList();
		
		clearInputs();
		
		this.addingResult = "Chanson";
		
		return "showAddingResult";
	}
	
	private void clearInputs() {
		this.newArtistName = null;
		this.newGenre = null;
		this.nameMusic = null;
		this.duration = 0;
		this.year = 0;
		this.label = null;
		this.artistType = null;	
	}
	//--------------------

	
	
	//Getter Setter

	public String getNewArtistName() {
		return newArtistName;
	}


	public void setNewArtistName(String newArtistName) {
		this.newArtistName = newArtistName;
	}


	public String getNewGenre() {
		return newGenre;
	}


	public void setNewGenre(String newGenre) {
		this.newGenre = newGenre;
	}
	
	
	
	public String getNameMusic() {
		return nameMusic;
	}


	public void setNameMusic(String nameAlbum) {
		this.nameMusic = nameAlbum;
	}
	public int getDuration() {
		return duration;
	}


	public void setDuration(int duration) {
		this.duration = duration;
	}


	public int getYear() {
		return year;
	}


	public void setYear(int year) {
		this.year = year;
	}


	public String getLabel() {
		return label;
	}


	public void setLabel(String label) {
		this.label = label;
	}


	public String getArtistType() {
		return artistType;
	}


	public void setArtistType(String artistType) {
		this.artistType = artistType;
	}


	public String getAddingResult() {
		return addingResult;
	}

	public void setAddingResult(String addingResult) {
		this.addingResult = addingResult;
	}
	
	
	
	
}
