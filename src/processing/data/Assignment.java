package processing.data;

import processing.helper.DatumFull;

public class Assignment {

//Attributes
	
	private String assignmentID;
	private Position[] positionList;
	private String description;
	private String dateOfCreation; 
	private String deadline;
	private String status;
	private String title;
	private String dueDate;

/**
 * Constructor for an Assignment from the DB
 * TODO @Exception
 * @param assignmentData
 * @param positionData
 * @param offerData
 * @throws Exception 
 */
//	public Assignment(String[] assignmentData, HashMap<String,String[]> positionData, HashMap<String,String[]> offerData) throws Exception{
//		this.assignmentID = assignmentData[0];
//		this.description = assignmentData[1];
//		String[] dateData = assignmentData[2].split(".");
//		this.dateOfCreation = new DatumFull();// dateData[2], dateData[1], dateData[0] TODO Specify exception type in DatumFull, implement here and in Controller.importAssignments
//		dateData = assignmentData[3].split(".");
//		this.deadline = new DatumFull(); //dateData[2], dateData[1], dateData[0]
//		this.status = assignmentData[4];
//		this.title = assignmentData[5];
//		
//		//Creation of the offerHandler and its offerList
//		this.offerHandler = new OfferHandler(new Offer[1]); 
//		//for loop for parsing the HashMap
//		for (int j = 0; j < offerData.size(); j++) {
//			Offer[] temporaryOfferList = new Offer[j];
//			Offer temporaryOffer;
//			//gets Array from HashMap which represents a data record for an offer
//			String [] offerBuff = offerData.get(String.valueOf(j));
//			//creates object out of new data record
//			temporaryOffer = new Offer(offerBuff);
//			//add new object to offerList
//			for (int i = 0; i < j; i++){
//				temporaryOfferList[i] = this.offerHandler.getOfferList()[i];
//			}
//			System.out.println(j);
//			temporaryOfferList[j] = temporaryOffer;
//			this.offerHandler.setOfferList(temporaryOfferList);
//		}
//		
//		//Creation of the positionList
//		this.positionList = new TreeItem[1]; 
//		//for loop for parsing the HashMap
//		for (int j = 0; j < positionData.size(); j++) {
//			TreeItem[] temporaryPositionList = new TreeItem[j];
//			TreeItem temporaryPosition;
//			//gets Array from HashMap which represents a data record for a position
//			String [] positionBuff = positionData.get(String.valueOf(j));
//			// creates TreeItem object out of new data record
//			temporaryPosition = new TreeItem(Controller.getInstance().searchForCategory(positionBuff[1]), 0);
//			temporaryPosition.setText(new String[] {positionBuff[3],positionBuff[2],positionBuff[0],positionBuff[1]});
//			//add new object to positionList
//			for (int i = 0; i < j; i++){
//				temporaryPositionList[i] = this.positionList[i];
//			}
//			temporaryPositionList[j] = temporaryPosition;
//			this.positionList = temporaryPositionList;
//		}
//		
//	}

/**
 * TODO
 * @param assignmentID
 * @param positionList
 * @param offerHandler
 * @param description
 * @param dateOfCreation
 * @param deadline
 * @param status
 * @param title
 */
	public Assignment(String assignmentID, Position[] positionList, String description, String dateOfCreation,
			String deadline, String status, String title, String dueDate) {

		this.assignmentID = assignmentID;
		this.setPositionList(positionList);
		this.description = description;
		this.dateOfCreation = dateOfCreation;
		this.deadline = deadline;
		this.status = status;
		this.title = title;
		this.setDueDate(dueDate);
	}

// Getters and Setters
	public String getAssignmentID() {
		return assignmentID;
	}

	public void setAssignmentID(String assignmentID) {
		this.assignmentID = assignmentID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDateOfCreation() {
		return dateOfCreation;
	}

	public void setDateOfCreation(String dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
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

	public Position[] getPositionList() {
		return positionList;
	}

	public void setPositionList(Position[] positionList) {
		this.positionList = positionList;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

}
