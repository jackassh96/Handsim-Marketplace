package processing.data;

import java.util.ArrayList;
import java.util.Date;

public class Assignment {
	private String assignmentID;
	private ArrayList<Position> positionList;
	private OfferHandler offerHandler;
	private String description;
	private Date dateOfCreation;
	private Date deadline;
	private String status;
	private String title;
	
	
	public Assignment() {
		// TODO Auto-generated constructor stub
	}


	//Getters and Setters
	public String getAssignmentID() {
		return assignmentID;
	}
	public void setAssignmentID(String assignmentID) {
		this.assignmentID = assignmentID;
	}
	public ArrayList<Position> getPositionList() {
		return positionList;
	}
	public void setPositionList(ArrayList<Position> positionList) {
		this.positionList = positionList;
	}
	public OfferHandler getOfferHandler() {
		return offerHandler;
	}
	public void setOfferHandler(OfferHandler offerHandler) {
		this.offerHandler = offerHandler;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDateOfCreation() {
		return dateOfCreation;
	}
	public void setDateOfCreation(Date dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

}
