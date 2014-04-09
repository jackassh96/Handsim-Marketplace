package processing.data;

public class AssignmentHandler {
	private Assignment[] assignmentList;
	private String assignmentHandlerID;
	
	public AssignmentHandler(Assignment[] assignmentList,
			String assignmentHandlerID) {
		this.assignmentList = assignmentList;
		this.assignmentHandlerID = assignmentHandlerID;
	}

	/*
	 * Returns specified ID if it exists. 
	 */
	public Assignment SearchForID (String ID){
		
		int i;
		try{
			i = Integer.parseInt(ID);
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
		if (assignmentList[i] == null) return null;
		else return assignmentList[i];
	}
	
	/*
	 *Getters and Setters
	 */
	
	public String getAssignmentHandlerID() {
		return assignmentHandlerID;
	}
	public void setAssignmentHandlerID(String assignmentHandlerID) {
		this.assignmentHandlerID = assignmentHandlerID;
	}

	public Assignment[] getAssignmentList() {
		return assignmentList;
	}

	public void setAssignmentList(Assignment[] assignmentList) {
		this.assignmentList = assignmentList;
	}

}
