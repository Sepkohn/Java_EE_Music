package ch.hevs.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.faces.event.ValueChangeEvent;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ch.hevs.Discoservice.Discography;
import ch.hevs.businessobject.Address;
import ch.hevs.businessobject.Album;
import ch.hevs.businessobject.Artist;
import ch.hevs.businessobject.Band;
import ch.hevs.businessobject.Singer;
import ch.hevs.businessobject.Song;

@Stateless
public class DiscographyManagedBean {
	
	//variable name
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
	
	//Add artist 
	private String newArtistName;
	private String newGenre;
	private String artistType;
	private String newArtistFirstname;
	private String newArtistLastname;
	
	private Address address;
	
	//Add Album
	private String nameMusic;
	private int duration;
	private int year;
	private String label;
	
	//Operation result
	private String addingResult;
	
	//tests on artist
	private int numberOfSongs;
	private boolean isSinger;
	
	//Bean
	private Discography disco;
	

	 @PostConstruct
	    public void initialize() throws NamingException {
	    	
	    	// use JNDI to inject reference
	    	InitialContext ctx = new InitialContext();
			disco = (Discography) ctx.lookup("java:global/TP12-WEB-EJB-PC-EPC-E-0.0.1-SNAPSHOT/DiscographyBean!ch.hevs.Discoservice.Discography");    	
			
			//variavbles initaîalization
			clearInputs();
			
			// get clients
			artistList();
			
	    }


	//Update an artist and his musics when selected in the scrolling menu
	public void updateSourceArtist(ValueChangeEvent event) {
    	String sourceArtistName = (String)event.getNewValue();
    	this.artist = disco.getArtist(sourceArtistName);
    	albumList();
    }
	
	//Update an album and its musics when selected in the scrolling menu
	public void updateSourceAlbum(ValueChangeEvent event) {
    	String sourceAlbumName = (String)event.getNewValue();
    	this.album = disco.getAlbum(sourceAlbumName, this.artist);
    	
    	songList();
    }
	
	//Update a song when selected in the scrolling menu
	public void updateSourceSong(ValueChangeEvent event) {
    	String sourceSongName = (String)event.getNewValue();
    	this.song = disco.getSong(sourceSongName, this.album);
    }
	
	//Update a song of an artist when selected in the scrolling Menu
	public void updateExistingSong(ValueChangeEvent event) {
    	String sourceSongName = (String)event.getNewValue();
    	this.song = disco.getSong(sourceSongName);
    }
	
	
	//Get all artists in database and set the first result as default
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
	
	//Get all albums from an artist in database and set the first result as default
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
	
	//Get all songs of an album in database and set the first result as default
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
	
	//Get all songs from an artist, except those from actual album in database and set the first result as default
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
	
	
	//reinitialize the main variables
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
	
	//calculate the number of songs that have an artist
	public int getNumberOfSongs() {
		return disco.getNumberOfSongs(this.artist);
	}
	
	//set the artistType with radio button
	public void typeChange(ValueChangeEvent e) {
		this.artistType = e.getNewValue().toString();
	}
	
	
	//TESTS------------------------------------------------------------------
	
	//return if there is an album in the list
	public boolean getAlbumlength() {
			return !this.albumNames.isEmpty();
	}
	
	//return if there is a song in the list
	public boolean getSonglength() {
		return !this.songNames.isEmpty();
	}
	
	//return if there is an artist in the list
	public boolean getArtistLength() {
		return !this.artistNames.isEmpty();	
	}
	
	//return if there is a song in the list
	public boolean getExistingSongLength() {
		return !this.existingSongNames.isEmpty();	
	}
	
	//return if the artist is a singer
		public boolean getIsSinger() {
			return this.artist.getClass().equals(Singer.class);
		}

	//ADDING -------------------------------------------------
	
	//Add an artist to database
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
		
		this.addingResult = "Artist added";
		
		return "showResult";
	}
	
	//Add an album to database
	public String addAlbum() {

		Album album = new Album(nameMusic, year, label);
		Artist artist = disco.getArtist(this.artist.getId());

		disco.addAlbum(album, artist);
		
		albumList();
		
		clearInputs();
		
		this.addingResult = "Album added";
		
		return "showResult";
	}
	
	//Add a song to database
	public String addSong() {

		Song song = new Song(nameMusic, duration, album.getYear());
		Album album = disco.getAlbum(this.album.getName(), this.artist);
		album.setDuration(album.getDuration()+duration);
		song.addAlbum(album);

		disco.addSongToAlbum(song, album);
		
		albumList();
		
		clearInputs();
		
		this.addingResult = "Song added";
		
		return "showResult";
	}
	
	//Update an existing song to database
	public String addExistingSong() {
		
		Song song = disco.getSong(this.existingSong.getName());
		Album album = disco.getAlbum(this.album.getName(), this.artist);
		album.setDuration(album.getDuration()+song.getDuration());
		song.addAlbum(album);
		
		disco.addSongToAlbum(song, album);
		
		albumList();
		
		clearInputs();
		
		this.addingResult = "Existing Song added";
		
		return "showResult";
	}

	
	//DELETING--------------------------------------
	
	//Delete an artist an his musics from database
	public String deleteArtist() {
	
	Artist artist = disco.getArtist(this.artist.getStageName());
	disco.deleteArtist(artist);
	System.out.println(artist.getId());
	
	
	clearInputs();
	
	artistList();
	
	this.addingResult = "Artist deleted";
	
	return "showResult";
	}

	
	//Delete a song from the database
	public String deleteSong() {
	
		disco.deleteSongToAlbum(this.song, this.album);

		
		clearInputs();
		
		songList();
		this.addingResult = "Song deleted";
	
		return "showResult";
	}
	
	//Delete an album an its musics from database
	public String deleteAlbum() {
		
		disco.deleteAlbum(this.album, this.artist);
		
		clearInputs();
		
		albumList();
		
		this.addingResult = "Album deleted";
	
		return "showResult";
	}
	
	
	//GETTER AND SETTER-----------------------------------------------------
	
	public List<String> getArtistNames() {
		return artistNames;
	}

	
	public List<String> getAlbumNames() {
		return albumNames;
	}

	
	public List<String> getSongNames() {
		return songNames;
	}

	
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
	public void setArtistType(String type) {
		this.artistType = type;
	}


	public String getAddingResult() {
		return addingResult;
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
	public void setNewArtistFirstname(String newArtistFirstname) {
		this.newArtistFirstname = newArtistFirstname;
	}
	

	public String getNewArtistLastname() {
		return newArtistLastname;
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
	

	
	
	
	
	
	
	
	
}
