package ch.hevs.Discoservice;

import java.util.List;
import javax.ejb.Local;
import ch.hevs.businessobject.Album;
import ch.hevs.businessobject.Artist;
import ch.hevs.businessobject.Song;

@Local
public interface Discography {
	
	List<Artist> getArtists();
	
	Artist getArtist(Long id);
	Artist getArtist(String artistName);
	List<Album> getAlbums(Artist artist);
	
	Album getAlbum(String albumName, Artist artist);
	
	List<Song> getSongsFromAlbum(Album album, Artist artist);
	List<Song> getSongsFromArtist(Artist artist, Album album);
	
	Song getSong(String songName, Album album);
	Song getSong(String songName);
	
	void addArtist(Artist artist);
	void addAlbum(Album album, Artist artist);
	void addSongToAlbum(Song song, Album album);

	int getNumberOfSongs(Artist artist);
	
	void deleteArtist(Artist artist);
	void deleteAlbum(Album album, Artist artist);
	void deleteSongToAlbum(Song song, Album album);

	
}
