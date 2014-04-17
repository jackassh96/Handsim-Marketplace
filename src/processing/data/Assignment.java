package processing.data;

import org.eclipse.swt.widgets.TreeItem;

import processing.Controller;

import java.util.Date;
import java.util.HashMap;

public class Assignment {

//Attributes
	
	private String assignmentID;
	private TreeItem[] positionList;
	private OfferHandler offerHandler;
	private String description;
	private Date dateOfCreation;
	private Date deadline;
	private String status;
	private String title;

/**
 * TODO finish this constructor
 * Constructor for an Assignment from the DB
 * @param assignmentData
 * @param positionData
 * @param offerData
 */
	
	public Assignment(String[] assignmentData, HashMap<String,String[]> positionData, HashMap<String,String[]> offerData){
		this.assignmentID = assignmentData[0];
		this.description = assignmentData[1];
		/**
		 * TODO form Date out of complete String. Ask Ben for String composition in DB
		 * this.dateOfCreation = assignmentData[2];
		 * this.deadline = assignmentData[3];
		 */
		this.status = assignmentData[4];
		this.title = assignmentData[5];
		
		//Creation of the offerHandler and its offerList
		this.offerHandler = new OfferHandler(new Offer[1]); 
		//for loop for parsing the HashMap
		for (int j = 0; j < offerData.size(); j++) {
			Offer[] temporaryOfferList = new Offer[j];
			Offer temporaryOffer;
			//gets Array from HashMap which represents a data record for an offer
			String [] offerBuff = offerData.get(String.valueOf(j));
			//creates object out of new data record
			temporaryOffer = new Offer(offerBuff);
			//add new object to offerList
			for (int i = 0; i < j; i++){
				temporaryOfferList[i] = this.offerHandler.getOfferList()[i];
			}
			temporaryOfferList[j] = temporaryOffer;
			this.offerHandler.setOfferList(temporaryOfferList);
		}
		
		//Creation of the positionList
		this.positionList = new TreeItem[1]; 
		//for loop for parsing the HashMap
		for (int j = 0; j < positionData.size(); j++) {
			TreeItem[] temporaryPositionList = new TreeItem[j];
			TreeItem temporaryPosition;
			//gets Array from HashMap which represents a data record for a position
			String [] positionBuff = positionData.get(String.valueOf(j));
			// creates TreeItem object out of new data record
			temporaryPosition = new TreeItem(Controller.getInstance().searchForCategory(positionBuff[1]), 0);
			temporaryPosition.setText(new String[] {positionBuff[3],positionBuff[2],positionBuff[0],positionBuff[1]});
			//add new object to positionList
			for (int i = 0; i < j; i++){
				temporaryPositionList[i] = this.positionList[i];
			}
			temporaryPositionList[j] = temporaryPosition;
			this.positionList = temporaryPositionList;
		}
		
	}
	
	public Assignment(String assignmentID, TreeItem[] positionList,
			OfferHandler offerHandler, String description, Date dateOfCreation,
			Date deadline, String status, String title) {

		this.assignmentID = assignmentID;
		this.positionList = positionList;
		this.offerHandler = offerHandler;
		this.description = description;
		this.dateOfCreation = dateOfCreation;
		this.deadline = deadline;
		this.status = status;
		this.title = title;
	}

// Getters and Setters TODO nicht genutzte Methoden eliminieren
	public String getAssignmentID() {
		return assignmentID;
	}

	public void setAssignmentID(String assignmentID) {
		this.assignmentID = assignmentID;
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

	public TreeItem[] getPositionList() {
		return positionList;
	}

	public void setPositionList(TreeItem[] positionList) {
		this.positionList = positionList;
	}

}
