package game.risk.model.valueobjects;

import java.util.ArrayList;
import javax.swing.JLabel;

/**
 * Country class
 * 
 * @author Ziba Khaneshi
 * @version 1.0.0
 * @since 10-October-2017
 *
 */
public class Country {

  private String name = "Default Country";

  private Continent continent;

  private ArrayList<Country> neighbooringCountries = new ArrayList<>();

  private ArrayList<String> neighbooringCountriesName = new ArrayList<>();

  private int xCoordiate;

  private int yCoordiate;

  private int soldiers = 0;

  private Player player = null;

  private PlayerStrategy playerStrategy = null;

  private JLabel pointInMapLabel = null;

  /**
   * This is a Constructor for Country class which sets name, continent, neighbooring countries and
   * xaxis and yaxis of the country.
   * 
   * @param name
   * @param continent
   * @param neighbooringCountriesName
   * @param xCoordinate
   * @param yCoordinate
   */
  public Country(String name, Continent continent, ArrayList<String> neighbooringCountriesName,
      int xCoordinate, int yCoordinate) {
    this.setCountryName(name);
    this.setContinent(continent);
    this.setNeighbooringCountriesName(neighbooringCountriesName);
    setCoordinate(xCoordinate, yCoordinate);
  }

  /**
   * Adds neighbooring country
   * 
   * @param country
   */
  public void addNeighboor(Country country) {
    neighbooringCountries.add(country);
  }

  /**
   * Removes neighbooring country
   * 
   * @param country
   */
  public void removeNeigboor(Country country) {
    neighbooringCountries.remove(country);
  }

  /**
   * Sets coordinate of the Country for GUI representation.
   * 
   * @param xCoordinate
   * @param yCoordinate
   */
  public void setCoordinate(int xCoordinate, int yCoordinate) {
    this.xCoordiate = xCoordinate;
    this.yCoordiate = yCoordinate;
  }

  /**
   * Sets Player who is occupying the country.
   * 
   * @param player
   */
  public void setPlayer(Player player) {
    this.player = player;
  }

  /**
   * Gets the coordinate of the country.
   * 
   * @return coordinate.
   */
  public int[] getCoordinate() {
    int[] coordinate = {xCoordiate, yCoordiate};
    return coordinate;
  }

  /**
   * Gets the number of soldiers stationed in the country.
   * 
   * @return number of soldiers.
   */
  public int getSoilders() {
    return soldiers;
  }

  /**
   * Sets the number of soldiers stationed in the country.
   * 
   * @param soldiers
   */
  public void setSoilders(int soldiers) {
    this.soldiers = soldiers;
  }

  /**
   * Adds soldiers to the country.
   */
  public void addSoilders() {
    this.soldiers++;
  }

  /**
   * Moves out number of soldiers out of country
   * 
   * @param noOfSoldier
   */
  public void moveOutSoldier(int noOfSoldier) {
    soldiers -= noOfSoldier;
  }

  /**
   * Moves in number of soldiers in the country
   * 
   * @param noOfSoldier
   */
  public void moveInSoldier(int noOfSoldier) {
    soldiers += noOfSoldier;
  }

  /**
   * Gets the Player occupying the country.
   * 
   * @return player.
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * Gets all the neighbooring country names.
   * 
   * @return list of the neighbooring country names.
   */
  public ArrayList<String> getNeighbooringCountriesName() {
    return neighbooringCountriesName;
  }

  /**
   * Sets list of all the neighbooring country names.
   * 
   * @param neighbooringCountriesName
   */
  public void setNeighbooringCountriesName(ArrayList<String> neighbooringCountriesName) {
    this.neighbooringCountriesName = neighbooringCountriesName;
  }

  /**
   * Get the country name.
   * 
   * @return country name.
   */
  public String getCountryName() {
    return name;
  }

  /**
   * Set country name.
   * 
   * @param name
   */
  public void setCountryName(String name) {
    this.name = name;
  }

  /**
   * Get all the neighbooring countries.
   * 
   * @return list of neighbooring countries.
   */
  public ArrayList<Country> getNeighbooringCountries() {
    return neighbooringCountries;
  }

  /**
   * Sets all the neighbooring Countries.
   * 
   * @param neighbooringCountries
   */
  public void setNeighbooringCountries(ArrayList<Country> neighbooringCountries) {
    this.neighbooringCountries = neighbooringCountries;
  }

  /**
   * Get the continent of the country.
   * 
   * @return continent.
   */
  public Continent getContinent() {
    return continent;
  }

  /**
   * Sets the continent of the country.
   * 
   * @param continent
   */
  public void setContinent(Continent continent) {
    this.continent = continent;
  }

  /**
   * Gets the Label expressing the number of soldier in the country on the GUI.
   * 
   * @return Label expressing the number of soldier in the country on the GUI.
   */
  public JLabel getPointInMapLabel() {
    return pointInMapLabel;
  }

  /**
   * Sets the Label expressing the number of soldier in the country on the GUI.
   * 
   * @param pointInMapLabel
   */
  public void setPointInMapLabel(JLabel pointInMapLabel) {
    this.pointInMapLabel = pointInMapLabel;
  }

  public PlayerStrategy getPlayerStrategy() {
    return playerStrategy;
  }

  public void setPlayerStrategy(PlayerStrategy playerStrategy) {
    this.playerStrategy = playerStrategy;
  }

}
