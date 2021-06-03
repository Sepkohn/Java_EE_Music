package ch.hevs.bankservice;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ch.hevs.businessobject.Album;
import ch.hevs.businessobject.Artist;
import ch.hevs.businessobject.Song;

@Stateless
//@RolesAllowed(value = {"visitor", "admin"})
public class DiscographyBean implements Discography{

	@PersistenceContext(name = "DiscoPU")
	private EntityManager em;
	
	
	public List<Artist> getArtists() {
	
		return em.createQuery("FROM Artist").getResultList();
	}

	/*@Override
	public Artist getArtist(Long id) {
		return (Artist) em.createQuery("FROM Artist a where a.id=:id").setParameter("id", id).getSingleResult();
	}*/
	
	
	@Override
	public Artist getArtist(String artistName) {
		return (Artist) em.createQuery("FROM Artist a where a.stageName=:artistName").setParameter("artistName", artistName).getSingleResult();
	}
	
	
	@Override
	public List<Album> getAlbums(String artistName) {
		return (List<Album>)em.createQuery("SELECT al FROM Album al, Artist a WHERE al.artist.id = a.id AND a.stageName=:artistName").setParameter("artistName", artistName).getResultList();
	}

	@Override
	public Album getAlbum(long id) {
		return (Album) em.createQuery("FROM Album a where a.id=:id").setParameter("id", id).getSingleResult();
	}

	@Override
	public List<Song> getSongsFromAlbum(String albumName) {
		return (List<Song>) em.createQuery("SELECT s FROM Album a, IN(a.songs) s WHERE a.name=:albumName").setParameter("albumName", albumName).getResultList(); //pas fini
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
