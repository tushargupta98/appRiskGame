package game.risk.model.valueobjects;

/**
 * This is a Risk Card class
 * @author Ziba Khaneshi
 * @version 1.0.0
 * @since 10-October-2017
 *
 */
public class RiskCard {

	private int noOFSoldier;
	
	private Country country;
	
	private String cardName;
	
	/**
	 * This is a constructor of Risk Card Class which sets number of soldier, country and card name.
	 * @param noOfSoldier
	 * @param country
	 * @param cardName
	 */
	public RiskCard(int noOfSoldier, Country country, String cardName) {
		setNoOFSoldier(noOfSoldier);
		setCountry(country);
		setCardName(cardName);
	}

	/**
	 * Gets the number of soldiers.
	 * @return number of soldiers.
	 */
	public int getNoOFSoldier() {
		return noOFSoldier;
	}

	/**
	 * Sets the number of soldiers.
	 * @param noOFSoldier
	 */
	public void setNoOFSoldier(int noOFSoldier) {
		this.noOFSoldier = noOFSoldier;
	}

	/**
	 * Gets the country.
	 * @return country
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * Sets the Country of the risk card.
	 * @param country
	 */
	public void setCountry(Country country) {
		this.country = country;
	}

	/**
	 * Gets the name of the risk card.
	 * @return
	 */
	public String getCardName() {
		return cardName;
	}

	/**
	 * Sets the name of the Risk Card.
	 * @param cardName
	 */
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	
}
