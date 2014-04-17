package processing.data;

import processing.Controller;
import processing.helper.DatumFull;

public class Offer {

//Attributes
	
	private String offerID;
	private double price;
	private String amountOfTimeNeeded;
	private String status;
	private Company company;
	private DatumFull date;
	private String description;

//Constructor
	/**
	 * TODO @Exception
	 * @param data
	 * @throws Exception
	 */
	public Offer(String[] data) throws Exception{
		this.offerID = data[0];
		this.price = Double.parseDouble(data[3]);
		this.amountOfTimeNeeded = data[4];
		this.status = data[7];
		this.company = Controller.getInstance().searchForCompany(data[2]);
		String[] dateData = data[6].split(".");
		this.date = new DatumFull(dateData[2], dateData[1], dateData[0]);
		this.description = data[5];
	}
	
	public Offer(String offerID, double price, String amountOfTimeNeeded,
			String status, Company company, DatumFull date, String description) {
		this.offerID = offerID;
		this.price = price;
		this.amountOfTimeNeeded = amountOfTimeNeeded;
		this.status = status;
		this.company = company;
		this.date = date;
		this.description = description;
	}

//Getters and Setters TODO Nicht genutzte Methoden eliminieren
	 
	public String getOfferID() {
		return offerID;
	}

	public void setOfferID(String offerID) {
		this.offerID = offerID;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getAmountOfTimeNeeded() {
		return amountOfTimeNeeded;
	}

	public void setAmountOfTimeNeeded(String amountOfTimeNeeded) {
		this.amountOfTimeNeeded = amountOfTimeNeeded;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public DatumFull getDate() {
		return date;
	}

	public void setDate(DatumFull date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
