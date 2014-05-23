package processing.data;

public class Company {

	//Attributes
	private String companyID;
	private String name;
	private String street;
	private String number;
	private int postCode;
	private String owner;
	private String phone;
	private String eMail;
	private String description;

	
	//Constructor	
	public Company(String[] data) throws NumberFormatException{
		try{
			this.companyID = data[0];
			this.name = data[1];
			this.street = data[2];
			this.number = data[3];
			this.postCode = Integer.parseInt(data[4]);
			this.owner = data[5];
			this.phone = data[6];
			this.eMail = data[7];
			this.description = data[8];
		}catch(NumberFormatException e){
			throw new NumberFormatException("String im Array kann nicht in Integer umgewandelt werden. Datenkonsistenz checken" + e.getMessage());
		}

	}

	
	//Getters and Setters
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
}
