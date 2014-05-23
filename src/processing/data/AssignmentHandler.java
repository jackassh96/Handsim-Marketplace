package processing.data;

public class AssignmentHandler {

	// Attributes
	private Assignment[] assignmentList;

	// Constructor
	public AssignmentHandler(Assignment[] assignmentList) {
		this.assignmentList = assignmentList;
	}

	// Public methods
	public Assignment SearchForID(String ID) {

		try {
			for (int i = 0; i < this.assignmentList.length; i++) {
				if (this.assignmentList[i].getAssignmentID() == ID) {
					return this.assignmentList[i];
				}
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	// Getters and Setters 
	public Assignment[] getAssignmentList() {
		return assignmentList;
	}

	public void setAssignmentList(Assignment[] assignmentList) {
		this.assignmentList = assignmentList;
	}

}
