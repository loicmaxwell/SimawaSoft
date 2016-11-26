package beans;

public class Customer {
	private int id_customer;
	private String id_card;
	private String documentType;
	private String firstname;
	private String lastname;
	private String email;
	private String phone;
	private String birthdate;
	
	public Customer() {
		// TODO Auto-generated constructor stub
	}	

	public Customer(String id_card, String documentType, String firstname, String lastname) {
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

	public String getId_card() {
		return id_card;
	}

	public void setId_card(String id_card) {
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	
	// used for context menu in selectCustomerController
	public Customer getCustomer() {
		return new Customer(this.id_card, this.documentType, this.firstname, this.lastname);
	}
}
