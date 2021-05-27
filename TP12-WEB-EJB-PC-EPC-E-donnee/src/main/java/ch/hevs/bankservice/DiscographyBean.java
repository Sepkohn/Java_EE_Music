package ch.hevs.bankservice;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ch.hevs.businessobject.Album;
import ch.hevs.businessobject.Artist;
import ch.hevs.businessobject.Song;

@Stateless
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
	public List<Album> getAlbums(String artistName) {
		return (List<Album>)em.createQuery("SELECT m.* FROM Music m, Artist a WHERE a.stageName=:artistName AND m.artist_id = m.id").setParameter("artistName", artistName).getResultList();
	}

	@Override
	public Album getAlbum(long id) {
		return (Album) em.createQuery("FROM Album a where a.id=:id").setParameter("id", id).getSingleResult();
	}

	@Override
	public List<Song> getSongsFromAlbum(String albumName) {
		return null;//return em.createQuery("FROM Song s ").getResultList(); //pas fini
	}

	@Override
	public Song getSong(long id) {
		return (Song) em.createQuery("FROM Song s where s.id=:id").setParameter("id", id).getSingleResult();
	}

	@Override
	public void addArtist(Artist artist) {
		Artist copyArtist = em.merge(artist);
	}

	@Override
	public void addAlbum(Album album, Artist artist) {
		Artist copyArtist = em.merge(artist);
		Album copyAlbum = em.merge(album);
		artist.addAlbum(album);
	}
	
	@Override
	public void addSongToAlbum(Song song, Album album) {
		Album copyAlbum = em.merge(album);
		Song copySong = em.merge(song);
		album.addSong(song);	
	}
	
	
}
