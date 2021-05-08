package ch.hevs.businessobject;

public abstract class Music {
	
	private String name;
	private int duration;
	private int year;
	private Artist artist;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Artist getArtist() {
		return artist;
	}
	public void setArtist(Artist artist) {
		this.artist = artist;
	}
	
	public Music(String name, int duration, int year, Artist artist) {
		super();
		this.name = name;
		this.duration = duration;
		this.year = year;
		this.artist = artist;
	}


	
}
