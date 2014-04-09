package processing.data;

public class OfferHandler {
	private String offerHandlerID;
	private Offer[] offerList;

	
	public OfferHandler(String offerHandlerID, Offer[] offerList) {
		this.offerHandlerID = offerHandlerID;
		this.offerList = offerList;
	}

	/*
	 * Returns specified ID if it exists. 
	 */
	public Offer SearchForID (String ID){
		
		int i;
		try{
			i = Integer.parseInt(ID);
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
		if (offerList[i] == null) return null;
		else return offerList[i];
	}

	/*
	 * Getters and Setters
	 */
	public String getOfferHandlerID() {
		return offerHandlerID;
	}

	public void setOfferHandlerID(String offerHandlerID) {
		this.offerHandlerID = offerHandlerID;
	}

	public Offer[] getOfferList() {
		return offerList;
	}

	public void setOfferList(Offer[] offerList) {
		this.offerList = offerList;
	}



}
