package game.risk.model.valueobjects;

import java.util.ArrayList;

public interface PlayerStrategy {

  public void attack();

  public void fortify();

  public void reinforcement();

  public void initialSoldierDistribution();

  public void removedLostCountry(Country country);

  public void addConqueredCountry(Country country);

  public void addNoOfArmyAlive();

  public ArrayList<Country> getListOfPlayersConqueredCountries();

  public void setListOfPlayersConqueredCountries(ArrayList<Country> conqueredCountries);

  public String getName();

  public void renew();

}
