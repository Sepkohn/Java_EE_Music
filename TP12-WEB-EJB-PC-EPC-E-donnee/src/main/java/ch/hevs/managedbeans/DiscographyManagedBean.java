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
	
	private Artist artist;
	private Album album;
	private Song song;
	//private String sourceArtistName;
	//private String sourceAlbumName;
	//private String sourceSongName;
	
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
	
	/*public String getSourceArtistName() {
		return sourceArtistName;
	}
	public void setSourceArtistName(String sourceArtistName) {
		this.sourceArtistName = sourceArtistName;
	}*/
	
	
	/*public String getSourceAlbumName() {
		return sourceAlbumName;
	}
	public void setSourceAlbumName(String sourceAlbumName) {
		this.sourceAlbumName = sourceAlbumName;
	}*/


	/*public String getSourceSongName() {
		return sourceSongName;
	}
	public void setSourceSongName(String sourceSongName) {
		this.sourceSongName = sourceSongName;
	}*/


	public void updateSourceArtist(ValueChangeEvent event) {
    	String sourceArtistName = (String)event.getNewValue();
    	this.artist = disco.getArtist(sourceArtistName);
    	albumList();
    	songList();
    }
	
	public void updateSourceAlbum(ValueChangeEvent event) {
    	String sourceAlbumName = (String)event.getNewValue();
    	this.album = disco.getAlbum(sourceAlbumName, this.artist.getStageName());
    	
    	songList();
    }
	
	public void updateSourceSong(ValueChangeEvent event) {
    	String sourceSongName = (String)event.getNewValue();
    	this.song = disco.getSong(sourceSongName, this.album.getName());
    }
	
	
	private void artistList() {
		List<Artist> artists = disco.getArtists();
		
		this.artistNames = new ArrayList<String>();
		for (Artist artist : artists) {
			this.artistNames.add(artist.getStageName());
		}
		if(artist == null) {
			this.artist = artists.get(0);
		}
		//this.sourceArtistName = artist.getStageName();
		albumList();
	}
	private void albumList() {
		
		List<Album> albums = disco.getAlbums(this.artist.getStageName());
	    this.albumNames = new ArrayList<String>();
		for (Album album : albums) {
			this.albumNames.add(album.getName());
		}
		if(!albumNames.isEmpty()) {
			this.album = albums.get(0);
			songList();
		}
		
	}
	
	private void songList() {
		List<Song> songs = disco.getSongsFromAlbum(this.album.getName(), this.artist.getStageName());
	    this.songNames = new ArrayList<String>();
		for (Song song : songs) {
			this.songNames.add(song.getName());
		}
		if(!songNames.isEmpty()) {
			this.song = songs.get(0);
		}
	}
	
	public String toAlbums() {	
		return "welcomeAlbum";
	}
	
	public boolean getAlbumlength() {
			return !this.albumNames.isEmpty();
	}
	 
	
	/*public void updateSourceArtistNew(ValueChangeEvent event) {
    	this.sourceArtistName = (String)event.getNewValue();
    	
    	List<Album> albums = disco.getAlbums(this.sourceArtistName);
	    this.albumNames = new ArrayList<String>();
		for (Album album : albums) {
			this.albumNames.add(album.getName());
		}
    }*/
	
	public int getNumberOfSongs() {
		return disco.getNumberOfSongs(this.artist.getStageName());
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
		
		this.addingResult = "Artist";
		
		return "showAddingResult";
	}
	
	public String addAlbum() {

		Album album = new Album(nameMusic, duration, year, label);
		//Artist artist = disco.getArtist(sourceArtistName);

		disco.addAlbum(album, this.artist);
		
		albumList();
		
		clearInputs();
		
		this.addingResult = "Album";
		
		return "showAddingResult";
	}
	
	public String addSong() {

		Song song = new Song(nameMusic, duration, year);
		Album album = disco.getAlbum(this.album.getName(), this.artist.getStageName());
		song.addAlbum(album);

		disco.addSongToAlbum(song, album);
		
		songList();
		
		clearInputs();
		
		this.addingResult = "Song";
		
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
	
	public Artist getArtist() {
		return this.artist;
	}

	public Album getAlbum() {
		return album;
	}

	public Song getSong() {
		return song;
	}
	
	
	
	
	
	
}
