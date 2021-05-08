package ch.hevs.businessobject;

public class Singer extends Artist{
	
	
	private String realName;
	private String realFirstname;
	
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getRealFirstname() {
		return realFirstname;
	}
	public void setRealFirstname(String realFirstname) {
		this.realFirstname = realFirstname;
	}
	
	public Singer(String stageName, String origin, String genre) {
		super(stageName, origin, genre);
		// TODO Auto-generated constructor stub
	}

}
