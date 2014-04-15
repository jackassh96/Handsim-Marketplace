package processing.data;

public class OfferHandler {
	
	
	/*
	 * Attributes
	 */
	private Offer[] offerList;

	
	/*
	 * Constructor
	 */
	public OfferHandler(Offer[] offerList) {
		this.offerList = offerList;
	}
	
	
	/**
	 * @param
	 * @return
	 * @ throws
	 * TODO Implement this SearchForID-Method correctly 
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
	public Offer[] getOfferList() {
		return offerList;
	}

	public void setOfferList(Offer[] offerList) {
		this.offerList = offerList;
	}



}
