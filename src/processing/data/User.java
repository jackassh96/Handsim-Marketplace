package processing.data;

public class User {

// Attributes
	
	private String userID;
	private String passwd;
	private String firstName;
	private String lastName;
	private String street;
	private String number;
	private int postCode;
	private String city;
	private String company;
	private String phone;
	private String eMail;
	private String gender;

	
// Constructor
	
	/**
	 * Creates User object from string array coming from the data base.
	 * @param data Data coming from the data base
	 * @throws ArrayIndexOutOfBoundsException Exception that is thrown when the array is too short. If this exception occurs, it should be made sure that the array is properly extracted from the data base.
	 */
	public User(String[] data) throws ArrayIndexOutOfBoundsException{
		this.userID = data[0];
		this.passwd = data[1];
		this.firstName = data[2];
		this.lastName = data[3];
		this.street = data[4];
		this.number = data[5];
		this.postCode = Integer.parseInt(data[6]);
		this.city = data[7];
		this.company = data[8];
		this.phone = data[9];
		this.eMail = data[10];
		this.gender = data[11];
	}

	
// Public Methods
	/**
	 * @return Returns a String array of all Attributes. Used to save the object to the data base
	 */
	public String[] toStringArray() {
		String[] s = new String[12];
		s[0] = this.userID;
		s[1] = this.passwd;
		s[2] = this.firstName;
		s[3] = this.lastName;
		s[4] = this.street;
		s[5] = this.number;
		s[6] = String.valueOf(this.postCode);
		s[7] = this.city;
		s[8] = this.company;
		s[9] = this.phone;
		s[10] = this.eMail;
		s[11] = this.gender;
		
		return s;
	}
	
	//Getters and Setters (needed for test classes and controller) 
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
