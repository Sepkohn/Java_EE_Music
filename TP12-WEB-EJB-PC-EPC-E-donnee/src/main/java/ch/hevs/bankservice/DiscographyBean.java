package ch.hevs.bankservice;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ch.hevs.businessobject.Album;
import ch.hevs.businessobject.Artist;
import ch.hevs.businessobject.Song;

import javax.persistence.Query;

@Stateless
//@RolesAllowed(value = {"visitor", "admin"})
public class DiscographyBean implements Discography{

	@PersistenceContext(name = "DiscoPU")
	private EntityManager em;
	
	
	public List<Artist> getArtists() {
		return em.createQuery("FROM Artist").getResultList();
	}

	@Override
	public Artist getArtist(Long id) {
		return (Artist) em.createQuery("FROM Artist a where a.id=:id").setParameter("id", id).getSingleResult();
	}
	
	
	@Override
	public Artist getArtist(String artistName) {
		return (Artist) em.createQuery("FROM Artist a where a.stageName=:artistName").setParameter("artistName", artistName).getSingleResult();
	}
	
	
	@Override
	public List<Album> getAlbums(String artistName) {
		return (List<Album>)em.createQuery("SELECT al FROM Album al, Artist a WHERE al.artist.id = a.id AND a.stageName=:artistName").setParameter("artistName", artistName).getResultList();
	}

	@Override
	public Album getAlbum(String albumName, String artistName) {
		Query query = em.createQuery("SELECT a FROM Album a, Artist ar where a.name=:albumName AND ar.stageName=:artistName AND a.artist.id = ar.id");
		query.setParameter("albumName", albumName);
		query.setParameter("artistName", artistName);
		return (Album) query.getSingleResult();
	}

	@Override
	public List<Song> getSongsFromAlbum(String albumName, String artistName) {
		Query query = em.createQuery("SELECT s FROM Artist ar, Album a, IN(a.songs) s where a.name=:albumName AND ar.stageName=:artistName AND a.artist.id = ar.id");
		query.setParameter("albumName", albumName);
		query.setParameter("artistName", artistName);
		return (List<Song>) query.getResultList();
	}

	@Override
	public Song getSong(String songName, String albumName) {
		Query query = em.createQuery("SELECT s FROM Song s, Album a, IN(s.albums) al where s.name =:songName AND a.name =:albumName AND al.id = a.id");
		query.setParameter("songName", songName);
		query.setParameter("albumName", albumName);
		return (Song) query.getSingleResult();
	}

	@Override
	public void addArtist(Artist artist) {
		Artist copyArtist = em.merge(artist);
	}

	@Override
	public void addAlbum(Album album, Artist artist) {
		artist.addAlbum(album);
		Artist copyArtist = em.merge(artist);
	}
	
	@Override
	public void addSongToAlbum(Song song, Album album) {
		album.addSong(song);
		Album copyAlbum = em.merge(album);
			
	}

	@Override
	public int getNumberOfSongs(Artist artist) {
		
		return Math.toIntExact((long) em.createQuery("SELECT DISTINCT COUNT(s.id) FROM Artist a, IN(a.albums) al, IN(al.songs) s WHERE a.id =:id").setParameter("id", artist.getId()).getSingleResult());
	}

	@Override
	public void deleteArtist(Artist artist) {
				
		Query query = em.createQuery("DELETE FROM Artist a WHERE a.id = :artist_id");
		query.setParameter("artist_id", artist.getId()).executeUpdate();
		
	}

	@Override
	public void deleteSongToAlbum(Song song, Album album) {
		album.removeSong(song);
		Query query = em.createQuery("DELETE FROM Song s WHERE s.id = :song_id");
		query.setParameter("song_id", song.getId());
		query.executeUpdate();
		
	}
	
	@Override
	public void deleteAlbum(Album album, Artist artist) {

		for(Song s : album.getSongs()) {
			album.removeSong(s);
		}
		artist.removeAlbum(album);
		Query query = em.createQuery("DELETE FROM Album a WHERE a.id =:album_id");
		query.setParameter("album_id", album.getId());
		query.executeUpdate();
	
	}


	
	
	
	
}
