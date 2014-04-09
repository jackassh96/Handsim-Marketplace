package processing.data;

import java.util.ArrayList;

public class AssignmentHandler {
	private ArrayList<Assignment> assignmentList;
	private String assignmentHandlerID;
	
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
		if (assignmentList.get(i) == null) return null;
		else return assignmentList.get(i);
	}
	
	public AssignmentHandler() {
		// TODO Auto-generated constructor stub
	}
	
	/*
	 *Getters and Setters
	 */
	public ArrayList<Assignment> getAssignmentList() {
		return assignmentList;
	}
	public void setAssignmentList(ArrayList<Assignment> assignmentList) {
		this.assignmentList = assignmentList;
	}
	public String getAssignmentHandlerID() {
		return assignmentHandlerID;
	}
	public void setAssignmentHandlerID(String assignmentHandlerID) {
		this.assignmentHandlerID = assignmentHandlerID;
	}

}
