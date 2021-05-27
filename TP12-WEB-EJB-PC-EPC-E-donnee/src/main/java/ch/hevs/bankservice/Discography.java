package ch.hevs.bankservice;

import java.util.List;

import javax.ejb.Local;

import ch.hevs.businessobject.Album;
import ch.hevs.businessobject.Artist;
import ch.hevs.businessobject.Song;

@Local
public interface Discography {
	
	List<Artist> getArtists();
	
	Artist getArtist(Long id);
	
	List<Album> getAlbums(String artistName);
	
	Album getAlbum(long id);
	
	List<Song> getSongsFromAlbum(Long albumId);
	List<Song> getSongsFromArtist(String artistName);
	
	Song getSong(long id);
	
	void addArtist(Artist artist);
	void addAlbum(Album album, Artist artist);
	void addSongToAlbum(Song song, Album album);
	
	
}
