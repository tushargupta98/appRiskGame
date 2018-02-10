package game.risk.model.valueobjects;

import java.util.ArrayList;

/**
 * Continent Class
 * @author Ziba Khaneshi
 * @version 1.0.0
 * @since 10-October-2017
 */
public class Continent {

	private String name;
	
	private int controlValue;
	
	private ArrayList<Country> listOfCountriesInContinent = new ArrayList<>();
	
	/**
	 * This is a Constructor for Continent Class
	 * This constructor sets name and control value of the class
	 * @param name of continent
	 * @param controlValue of continent
	 */
	public Continent(String name, int controlValue) {
		this.name = name;
		this.setControlValue(controlValue);
	}
	
	/**
	 * To get the name of the Continent
	 * @return name of continent
	 */
	public String getContinentName() {
		return name;
	}

	/**
	 * To get the control value of the Continent
	 * @return control value of the continent
	 */
	public int getControlValue() {
		return controlValue;
	}

	/**
	 * sets the control value of the continent
	 * @param controlValue
	 */
	public void setControlValue(int controlValue) {
		this.controlValue = controlValue;
	}

	/**
	 * To get the list of countries in the continent
	 * @return list of countries in the continent
	 */
	public ArrayList<Country> getListOfCountriesInContinent() {
		return listOfCountriesInContinent;
	}
	
	/**
	 * Add country to the continent
	 * @param country
	 */
	public void addCountryToContinent(Country country) {
		listOfCountriesInContinent.add(country);
	}

	/**
	 * Sets the list of countries in the continent
	 * @param listOfCountriesInContinent
	 */
	public void setListOfCountriesInContinent(ArrayList<Country> listOfCountriesInContinent) {
		this.listOfCountriesInContinent = listOfCountriesInContinent;
	}
	
}
