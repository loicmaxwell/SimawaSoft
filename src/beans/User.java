package beans;

public class User {
	private int id_user;
	private String firstname;
	private String lastname;
	private String email;
	private int phone;
	private String login;
	private String profile;
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	public User(String firstname, String lastname, String email, String profile) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.profile = profile;
	}
	
	public int getId_user() {
		return id_user;
	}
	public void setId_user(int id_user) {
		this.id_user = id_user;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getPhone() {
		return phone;
	}
	public void setPhone(int phone) {
		this.phone = phone;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	
	
}
