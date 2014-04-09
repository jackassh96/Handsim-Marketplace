package processing.data;

public class Position {
	private String positionID;
	private String description;
	private int amount;
	private ServiceGroup serviceGroup;
	
	
	public Position() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * Getters and Setters
	 */
	public String getPositionID() {
		return positionID;
	}


	public void setPositionID(String positionID) {
		this.positionID = positionID;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getAmount() {
		return amount;
	}


	public void setAmount(int amount) {
		this.amount = amount;
	}


	public ServiceGroup getServiceGroup() {
		return serviceGroup;
	}


	public void setServiceGroup(ServiceGroup serviceGroup) {
		this.serviceGroup = serviceGroup;
	}

}
