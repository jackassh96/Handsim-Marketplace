package processing.data;

import java.util.Date;

public class Offer {

	/*
	 * Attributes
	 */
	private String offerID;
	private double price;
	private String amountOfTimeNeeded;
	private String status;
	private Company company;
	private Date date;
	private String description;

	/*
	 * Constructor
	 */
	public Offer(String offerID, double price, String amountOfTimeNeeded,
			String status, Company company, Date date, String description) {
		this.offerID = offerID;
		this.price = price;
		this.amountOfTimeNeeded = amountOfTimeNeeded;
		this.status = status;
		this.company = company;
		this.date = date;
		this.description = description;
	}

	/*
	 * Getters and Setters
	 */
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
