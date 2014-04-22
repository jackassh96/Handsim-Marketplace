package processing.data;

public class OfferHandler {

// Attributes
	 
	private Offer[] offerList;

	
// Constructor
	 
	public OfferHandler(Offer[] offerList) {
		this.offerList = offerList;
	}

	
// Public Methods
	
	/**
	 * @param ID
	 * @return 
	 * @throws 
	 * TODO Implement the Exception for the SearchForID-Method correctly --> Felix: welche exception soll da kommen? Die OfferList wird doch mit dem OfferHandler zusammen erstellt, da ist eigentlich kein Spielraum fuer Exceptions meine ich...
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

	
// Getters and Setters TODO Nicht genutzte Methoden eliminieren
	 
	public Offer[] getOfferList() {
		return offerList;
	}

	public void setOfferList(Offer[] offerList) {
		this.offerList = offerList;
	}

}
