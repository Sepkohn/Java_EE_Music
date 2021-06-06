package ch.hevs.managedbeans;

import java.awt.Window;
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
import ch.hevs.businessobject.Address;
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
	private List<String> existingSongNames;
	private List<Song> existingSongs;
	
	//get lengths
	private boolean artistLength;
	private boolean albumLength;
	private boolean songLength;
	private boolean existingSongsLength;
	
	private Artist artist;
	private Album album;
	private Song song;
	private Song existingSong;
	
	//Ajout Artist 
	private String newArtistName;
	private String newGenre;
	private String artistType;
	private String newArtistFirstname;
	private String newArtistLastname;
	
	private Address address;
	
	//Ajout Album
	private String nameMusic;
	private int duration;
	private int year;
	private String label;
	
	private String addingResult;
	
	private int numberOfSongs;
	private boolean isSinger;
	
	
	private Discography disco;
	

	
	
	 @PostConstruct
	    public void initialize() throws NamingException {
	    	
	    	// use JNDI to inject reference to bank EJB
	    	InitialContext ctx = new InitialContext();
			disco = (Discography) ctx.lookup("java:global/TP12-WEB-EJB-PC-EPC-E-0.0.1-SNAPSHOT/DiscographyBean!ch.hevs.bankservice.Discography");    	
			
			//Pour vider les champs lorsque l'on retourne sur une page
			clearInputs();
			
			// get clients
			artistList();
			
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

	public void updateSourceArtist(ValueChangeEvent event) {
    	String sourceArtistName = (String)event.getNewValue();
    	this.artist = disco.getArtist(sourceArtistName);
    	albumList();
    	songList();
    }
	
	public void updateSourceAlbum(ValueChangeEvent event) {
    	String sourceAlbumName = (String)event.getNewValue();
    	this.album = disco.getAlbum(sourceAlbumName, this.artist);
    	
    	songList();
    }
	
	public void updateSourceSong(ValueChangeEvent event) {
    	String sourceSongName = (String)event.getNewValue();
    	this.song = disco.getSong(sourceSongName, this.album);
    }
	
	public void updateExistingSong(ValueChangeEvent event) {
    	String sourceSongName = (String)event.getNewValue();
    	this.song = disco.getSong(sourceSongName);
    }
	
	
	private void artistList() {
		List<Artist> artists = disco.getArtists();
		
		this.artistNames = new ArrayList<String>();
		for (Artist artist : artists) {
			this.artistNames.add(artist.getStageName());
		}
		if(!artists.isEmpty()) {
			this.artist = artists.get(0);		
			albumList();
		}
	}
	private void albumList() {
		
		List<Album> albums = disco.getAlbums(this.artist);
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
		List<Song> songs = disco.getSongsFromAlbum(this.album, this.artist);
	    this.songNames = new ArrayList<String>();
		for (Song song : songs) {
			this.songNames.add(song.getName());
		}
		if(!songNames.isEmpty()) {
			this.song = songs.get(0);
		}
		existingSongList();
	}
	
	private void existingSongList() {
		this.existingSongs = disco.getSongsFromArtist(this.artist,  this.album);
		this.existingSongNames = new ArrayList<String>();
		for (Song song : existingSongs) {
			this.existingSongNames.add(song.getName());
		}
		if(!existingSongs.isEmpty()) {
			this.existingSong = existingSongs.get(0);
		}
	}
	
	
	
	public String toAlbums() {	
		return "welcomeAlbum";
	}
	
	public boolean getAlbumlength() {
			return !this.albumNames.isEmpty();
	}
	
	public boolean getSonglength() {
		return !this.songNames.isEmpty();
}
	 
	
	public int getNumberOfSongs() {
		return disco.getNumberOfSongs(this.artist);
	}
	
	
	//ADDING -------------------------------------------------
	public String addArtist() {
		
		Artist artist;
		switch(this.artistType) {
		case("Singer"):
			artist = new Singer(newArtistName, newGenre, newArtistLastname, newArtistFirstname);	
			break;
		case("Band"):
			artist = new Band(newArtistName, newGenre);
			break;
		default : 
			artist = new Artist();
		}
		artist.setAddress(this.address);
		disco.addArtist(artist);
		
		artistList();
		
		clearInputs();
		
		this.addingResult = "Artist";
		
		return "showAddingResult";
	}
	
	public String addAlbum() {

		Album album = new Album(nameMusic, year, label);
		Artist artist = disco.getArtist(this.artist.getId());

		disco.addAlbum(album, artist);
		
		albumList();
		
		clearInputs();
		
		this.addingResult = "Album";
		
		return "showAddingResult";
	}
	
	public String addSong() {

		Song song = new Song(nameMusic, duration, album.getYear());
		Album album = disco.getAlbum(this.album.getName(), this.artist);
		album.setDuration(album.getDuration()+duration);
		song.addAlbum(album);

		disco.addSongToAlbum(song, album);
		
		albumList();
		
		clearInputs();
		
		this.addingResult = "Song";
		
		return "showAddingResult";
	}
	
	public String addExistingSong() {
		
		Song song = disco.getSong(this.existingSong.getName());
		Album album = disco.getAlbum(this.album.getName(), this.artist);
		album.setDuration(album.getDuration()+song.getDuration());
		song.addAlbum(album);
		
		disco.addSongToAlbum(song, album);
		
		albumList();
		
		clearInputs();
		
		this.addingResult = "Existing Song";
		
		return "showAddingResult";
	}
	
	public void typeChange(ValueChangeEvent e) {
		this.artistType = e.getNewValue().toString();
	}
	
	private void clearInputs() {
		this.newArtistName = null;
		this.newGenre = null;
		this.nameMusic = null;
		this.duration = 0;
		this.year = 0;
		this.label = null;
		this.artistType = "Singer";	
		this.address = new Address();
		this.newArtistFirstname = null;
		this.newArtistLastname = null;
	}
	
	public boolean getIsSinger() {
		return this.artist.getClass().equals(Singer.class);
	}
	//--------------------------------------
	
	public String deleteArtist() {
	
	Artist artist = disco.getArtist(this.artist.getStageName());
	disco.deleteArtist(artist);
	System.out.println(artist.getId());
	
	
	clearInputs();
	
	artistList();
	
	this.addingResult = "Delete Artiste";
	
	return "showAddingResult";
	}

	
	public String deleteSong() {
	
		disco.deleteSongToAlbum(this.song, this.album);

		
		clearInputs();
		
		songList();
		this.addingResult = "Delete Song";
	
		return "showAddingResult";
	}
	
	public String deleteAlbum() {
		
		disco.deleteAlbum(this.album, this.artist);
		
		clearInputs();
		
		albumList();
		
		this.addingResult = "Delete Album";
	
		return "showAddingResult";
	}
	
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
		return artist;
	}

	public Album getAlbum() {
		return album;
	}

	public Song getSong() {
		return song;
	}
	
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}

	public String getNewArtistFirstname() {
		return newArtistFirstname;
	}

	public String getNewArtistLastname() {
		return newArtistLastname;
	}

	public void setNewArtistFirstname(String newArtistFirstname) {
		this.newArtistFirstname = newArtistFirstname;
	}

	public void setNewArtistLastname(String newArtistLastname) {
		this.newArtistLastname = newArtistLastname;
	}

	public List<String> getExistingSongNames() {
		return existingSongNames;
	}

	public Song getExistingSong() {
		return existingSong;
	}

	public void setExistingSong(Song existingSong) {
		this.existingSong = existingSong;
	}
	
	public boolean getArtistLength() {
		return !this.artistNames.isEmpty();	
	}
	
	public boolean getExistingSongLength() {
		return !this.existingSongNames.isEmpty();	
	}
	
	
	
	
	
	
	
	
	
}
