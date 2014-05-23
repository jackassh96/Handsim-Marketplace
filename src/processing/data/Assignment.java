package processing.data;

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


	//Constructor for an Assignment from the DB
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
