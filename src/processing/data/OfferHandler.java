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
	 * TODO Implement the Exception for the SearchForID-Method correctly
	 */
	public Offer SearchForID(String ID) {

		try {
			for (int i = 0; i < this.offerList.length; i++) {
				if (this.offerList[i].getOfferID() == ID) {
					return this.offerList[i];
				}
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

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
