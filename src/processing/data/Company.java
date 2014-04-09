package processing.data;

public class Company {
	private String companyID;
	private String name;
	private String street;
	private String number;
	private int postCode;
	private String city;
	private String owner;
	private String phone;
	private String eMail;
	private String description;

public Company(String companyID, String name, String street, String number,
			int postCode, String city, String owner, String phone,
			String eMail, String description) {
		this.companyID = companyID;
		this.name = name;
		this.street = street;
		this.number = number;
		this.postCode = postCode;
		this.city = city;
		this.owner = owner;
		this.phone = phone;
		this.eMail = eMail;
		this.description = description;
	}


/*
 * Getters and Setters
 */
	public String getCompanyID() {
		return companyID;
	}


	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getStreet() {
		return street;
	}


	public void setStreet(String street) {
		this.street = street;
	}


	public String getNumber() {
		return number;
	}


	public void setNumber(String number) {
		this.number = number;
	}


	public int getPostCode() {
		return postCode;
	}


	public void setPostCode(int postCode) {
		this.postCode = postCode;
	}


	public String getOwner() {
		return owner;
	}


	public void setOwner(String owner) {
		this.owner = owner;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getEMail() {
		return eMail;
	}


	public void setEMail(String eMail) {
		this.eMail = eMail;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
