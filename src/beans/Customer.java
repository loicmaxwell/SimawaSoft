package beans;

public class Customer {
	private int id_customer;
	private int id_card;
	private String documentType;
	private String firstname;
	private String lastname;
	private String email;
	private int phone;
	private String birthday;
	
	public Customer() {
		// TODO Auto-generated constructor stub
	}	

	public Customer(int id_card, String documentType, String firstname, String lastname) {
		super();
		this.id_card = id_card;
		this.documentType = documentType;
		this.firstname = firstname;
		this.lastname = lastname;
	}



	public int getId_customer() {
		return id_customer;
	}

	public void setId_customer(int id_customer) {
		this.id_customer = id_customer;
	}

	public int getId_card() {
		return id_card;
	}

	public void setId_card(int id_card) {
		this.id_card = id_card;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
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

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	
}
