package game.risk.Helper;

import game.risk.model.valueobjects.Continent;
import game.risk.model.valueobjects.Country;
import game.risk.model.valueobjects.Map;

/**
 * This is the main Game Controller class.
 * 
 * @author Tushar Gupta
 * @version 1.0.0
 * @since 12-October-2017
 */
public class MapEditHelper {

  /**
   * Delete Continent
   * 
   * @param continentName
   */
  public static void deleteContinent(String continentName) {
    // Get the Continent
    Continent continentToDelete = Map.getContinent(continentName);
    // Delete all the countries from Continent
    for (int i = 0; i < continentToDelete.getListOfCountriesInContinent().size(); i++) {
      deleteCountry(continentToDelete.getListOfCountriesInContinent().get(i));
    }
    // Delete Continent from Map
    Map.removeContinent(continentToDelete);
    // Loose data
    continentToDelete = null;
  }

  /**
   * Delete Country
   * 
   * @param countryToDelete
   */
  public static void deleteCountry(Country countryToDelete) {
    // Delete Country from its neighbooring countries' list
    for (int i = 0; i < countryToDelete.getNeighbooringCountries().size(); i++) {
      countryToDelete.getNeighbooringCountries().get(i).removeNeigboor(countryToDelete);
    }
    // Delete Country from Map
    Map.removeCountry(countryToDelete);
    // Loose Data
    countryToDelete = null;
  }

  /**
   * Delete Country by name
   * 
   * @param countryName
   */
  public static void deleteCountry(String countryName) {
    // Get the Country
    Country countryToDelete = Map.getCountry(countryName);
    // Delete the Country
    deleteCountry(countryToDelete);
  }

}
