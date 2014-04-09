package processing.data;

import java.util.ArrayList;

public class OfferHandler {
	private String offerHandlerID;
	private ArrayList<Offer> offerList;

	
	public OfferHandler(String offerHandlerID, ArrayList<Offer> offerList) {
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
		if (offerList.get(i) == null) return null;
		else return offerList.get(i);
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

	public ArrayList<Offer> getOfferList() {
		return offerList;
	}

	public void setOfferList(ArrayList<Offer> offerList) {
		this.offerList = offerList;
	}

}
