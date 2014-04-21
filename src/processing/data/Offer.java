package processing.data;

import processing.Controller;
import processing.helper.DatumFull;

public class Offer {

//Attributes
	
	private String offerID;
	private String assignmentID;//Felix: Getter / Setter
	private String companyID; //Felix: Getter / Setter
	private double price;
	private String amountOfTimeNeeded;
	private String description;
	private String date;
	private String status;
	

//Constructor
	/**
	 * @Exception TODO 
	 * @param data
	 * @throws Exception
	 */
	public Offer(String[] data) throws Exception{
		this.offerID = data[0];
		this.assignmentID = data[1];
		this.companyID = data[2];
		this.price = Double.parseDouble(data[3]);
		this.amountOfTimeNeeded = data[4];
		this.description = data[5];
		this.date = data[6];
		this.status = data[7];
	}
	
	public Offer(String offerID, String companyID, double price, String amountOfTimeNeeded,
			String status, String date, String description, String state) {
		this.offerID = offerID;
		this.companyID = companyID;
		this.price = price;
		this.amountOfTimeNeeded = amountOfTimeNeeded;
		this.status = state;
		this.date = date;
		this.description = description;
		this.status = status;
	}

//Getters and Setters
	 
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
