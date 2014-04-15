package processing.data;

public class Company {

	/*
	 * Attributes
	 */
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

	/*
	 * Constructor
	 */
	public Company(String[] data) {
		this.companyID = data[1];
		this.name = data[2];
		this.street = data[3];
		this.number = data[4];
		this.postCode = Integer.parseInt(data[5]);
		this.city = data[6];
		this.owner = data[7];
		this.phone = data[8];
		this.eMail = data[9];
		this.description = data[10];
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
