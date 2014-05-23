package processing.data;

public class Position {
	
//Attributes
	
	private String positionID;
	private String category_ID;
	private String assignment_ID;
	private String description;
	private String amount;
	

//Constructor	
	
	public Position(String category_ID, String assignment_ID, String description, String amount) {
		this.category_ID = category_ID;
		this.assignment_ID = assignment_ID;
		this.description = description;
		this.amount = amount;
	}

	
//Getters and Setters	

	public String getPositionID() {
		return positionID;
	}


	public void setPositionID(String positionID) {
		this.positionID = positionID;
	}


	public String getCategory_ID() {
		return category_ID;
	}


	public void setCategory_ID(String category_ID) {
		this.category_ID = category_ID;
	}


	public String getAssignment_ID() {
		return assignment_ID;
	}


	public void setAssignment_ID(String assignment_ID) {
		this.assignment_ID = assignment_ID;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getAmount() {
		return amount;
	}


	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	

}
