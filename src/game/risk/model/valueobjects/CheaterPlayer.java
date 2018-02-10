package game.risk.model.valueobjects;

import java.util.ArrayList;
import java.util.Random;
import game.risk.utils.GameInterface;

public class CheaterPlayer implements PlayerStrategy, GameInterface {
  private Map mapObject;
  private int noOfUnassignedArmy = 0;
  private int noOfArmyAlive = 0;
  private int riskCardTurnCount = 1;
  private String gamePlayStage = null;
  private String name = "Cheater Player";
  private String colorName = "Red";
  private ArrayList<RiskCard> riskCards = new ArrayList<>();
  private ArrayList<Country> listOfCountiresPlayerConquered = new ArrayList<>();

  /**
   * 
   * @param noOfArmy
   */
  public CheaterPlayer(int noOfArmy) {
    addUnassignedArmy(noOfArmy);
  }

  /**
   * Attack other countries.
   */
  @Override
  public void attack() {
    System.out.println(getName() + " is in Attacking Phase.");
    for (int j = 0; j < getListOfPlayersConqueredCountries().size(); j++) {
      Country attackerCountry = getListOfPlayersConqueredCountries().get(j);

      // Attacks and conquers all the neighbooring countries.
      for (int i = 0; i < attackerCountry.getNeighbooringCountries().size(); i++) {
        if (!this.equals(attackerCountry.getNeighbooringCountries().get(i).getPlayerStrategy())) {
          System.out.println(attackerCountry.getCountryName() + " attacks and conquers "
              + attackerCountry.getNeighbooringCountries().get(i).getCountryName());
          attackerCountry.getNeighbooringCountries().get(i).getPlayerStrategy()
              .removedLostCountry(attackerCountry.getNeighbooringCountries().get(i));
          attackerCountry.getNeighbooringCountries().get(i).setPlayerStrategy(this);
          addConqueredCountry(attackerCountry.getNeighbooringCountries().get(i));
          riskCards.add(Map.getOneRiskCardFromStack());
        }

      }
    }
  }

  /**
   * Fortifies soldiers to countries.
   */
  @Override
  public void fortify() {
    System.out.println(getName() + " is in Fortification");
    // Doubles the soldier of all the countries with neighboor to other country
    for (int i = 0; i < getListOfPlayersConqueredCountries().size(); i++) {
      boolean goOn = true;
      for (int j =
          0; j < getListOfPlayersConqueredCountries().get(i).getNeighbooringCountries().size()
              && goOn; j++) {
        if (!getListOfPlayersConqueredCountries().get(i).getNeighbooringCountries().get(j)
            .getPlayerStrategy().equals(this)) {
          System.out.println(getName() + " fortifies and doubles the number of soldiers in "
              + getListOfPlayersConqueredCountries().get(i).getCountryName());
          getListOfPlayersConqueredCountries().get(i)
              .moveInSoldier(getListOfPlayersConqueredCountries().get(i).getSoilders());
          goOn = false;
        }
      }
    }
  }

  /**
   * Adds reinforcements in countries.
   */
  @Override
  public void reinforcement() {
    System.out.println(getName() + " is in Reinforcement phase.");

    for (int i = 0; i < getListOfPlayersConqueredCountries().size(); i++) {
      System.out.println(getName() + " doubles the soldier in "
          + getListOfPlayersConqueredCountries().get(i).getCountryName());
      getListOfPlayersConqueredCountries().get(i)
          .moveInSoldier(getListOfPlayersConqueredCountries().get(i).getSoilders());
    }
  }

  /**
   * Adds initial soldiers in countries.
   */
  @Override
  public void initialSoldierDistribution() {
    System.out.println(getName() + " is distributing soldiers.");
    if (getNoOfUnitUnassigned() > 0) {
      int randomIndex = new Random().nextInt(getListOfPlayersConqueredCountries().size());
      getListOfPlayersConqueredCountries().get(randomIndex).addSoilders();
      addNoOfArmyAlive();
    }
  }

  /**
   * This method counts the Matching Risk Cards.
   * 
   * @return boolean if there are at least 3 matching risk cards or not.
   */
  public boolean calculateMatchingRiskCard() {
    for (int i = 0; i < getRiskCards().size() - 2; i++) {
      RiskCard card = getRiskCards().get(i);
      int count = 1;
      for (int j = i + 1; j < getRiskCards().size(); j++) {
        if (card.getCardName().equals(getRiskCards().get(j).getCardName()))
          count++;
      }
      if (count > 2) {
        return true;
      }
    }
    return false;
  }

  /**
   * This method identifies if the player has On of Each Risk Cards.
   * 
   * @return boolean if there are at least One of Each Risk Cards.
   */
  public boolean calculateOneOfEachCard() {
    int count = 0;
    for (int i = 0; i < RISK_CARD_NAMES.length; i++) {
      for (int j = 0; j < getRiskCards().size(); j++) {
        if (RISK_CARD_NAMES[i].equals(getRiskCards().get(j).getCardName())) {
          count++;
          break;
        }
      }
    }
    if (count == 3) {
      return true;
    }
    return false;
  }

  /**
   * Adds a new country/territory after conquering
   * 
   * @param country country
   */
  public void addConqueredCountry(Country country) {
    listOfCountiresPlayerConquered.add(country);
    Map.getInstance().notifyGame();
  }

  /**
   * Removes a existing country after player get defeated and looses this country.
   * 
   * @param country Country
   */
  public void removedLostCountry(Country country) {
    listOfCountiresPlayerConquered.remove(country);
    Map.getInstance().notifyGame();
  }

  /**
   * Returns a list of Countries the player has conquered.
   * 
   * @return arraylist country
   */
  public ArrayList<Country> getListOfPlayersConqueredCountries() {
    return listOfCountiresPlayerConquered;
  }

  /**
   * Get all the Risk Cards player holding.
   * 
   * @return risk card list.
   */
  public ArrayList<RiskCard> getRiskCards() {
    return riskCards;
  }

  /**
   * Set a list of Risk Cards player holding.
   * 
   * @param riskCards arraylist
   */
  public void setRiskCards(ArrayList<RiskCard> riskCards) {
    this.riskCards = riskCards;
  }

  /**
   * Adds risk card holding by player.
   * 
   * @param riskCard risk card
   */
  public void addRiskCard(RiskCard riskCard) {
    riskCards.add(riskCard);
  }

  /**
   * Gets the number of units unassigned.
   * 
   * @return number of units unassigned.
   */
  public int getNoOfUnitUnassigned() {
    return noOfUnassignedArmy;
  }

  /**
   * Adds the number of units unassigned.
   * 
   * @param noOfInitialAmry int
   */
  public void addUnassignedArmy(int noOfInitialAmry) {
    this.noOfUnassignedArmy += noOfInitialAmry;
  }

  /**
   * Gets the number of units alive.
   * 
   * @return int
   */
  public int getNoOfUnitAlive() {
    return noOfArmyAlive;
  }

  /**
   * Adds number of units alive by decreasing number of unit unassigned.
   */
  public void addNoOfArmyAlive() {
    this.noOfArmyAlive++;
    this.noOfUnassignedArmy--;
  }

  /**
   * Decreasing number of soldier by killing.
   */
  public void killArmy() {
    this.noOfArmyAlive--;
  }

  /**
   * Sets game player stage.
   * 
   * @param gamePlayStage string
   */
  public void setGamePlayStage(String gamePlayStage) {
    this.gamePlayStage = gamePlayStage;
    Map.getInstance().notifyGame();
  }

  /**
   * Calculates if a Player gets control value point or not.
   * 
   * @return int
   */
  public int getControlValuePoint() {
    int controlValuePoint = 0;
    for (int i = 0; i < Map.getContinentList().size(); i++) {
      int count = 0;
      for (int j = 0; j < Map.getContinentList().get(i).getListOfCountriesInContinent()
          .size(); j++) {
        // comparing if all the countries hold the same player or not
        if (Map.getContinentList().get(i).getListOfCountriesInContinent().get(j).getPlayer()
            .getName().equals(getName())) {
          count++;
        } else if (count > 0 || (count == 0 && j > 0)) {
          break;
        }
      }
      if (count == Map.getContinentList().get(i).getListOfCountriesInContinent().size()) {
        controlValuePoint = controlValuePoint + Map.getContinentList().get(i).getControlValue();
      }
    }
    return controlValuePoint;
  }

  /**
   * Get Player name.
   * 
   * @return player name.
   */
  public String getName() {
    return name;
  }

  /**
   * Sets player name.
   * 
   * @param name string
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the risk card turn count.
   * 
   * @return int
   */
  public int getRiskCardTurnCount() {
    return riskCardTurnCount;
  }

  /**
   * Sets risk card turn count.
   * 
   * @param riskCardTurnCount int
   */
  public void setRiskCardTurnCount(int riskCardTurnCount) {
    this.riskCardTurnCount = riskCardTurnCount;
  }

  /**
   * Increments Risk Card Turn Count.
   */
  public void incrementRiskCardTurnCount() {
    riskCardTurnCount++;
  }

  /**
   * Gives list of Neighbooring Country Names of same player.
   * 
   * @param country
   * @return arraylist of country
   */
  public ArrayList<Country> listOfNeighbooringSamePlayerCountries(Country country) {
    ArrayList<Country> neighbooringCountriesOfSamePlayer = new ArrayList<>();
    for (int i = 0; i < country.getNeighbooringCountries().size(); i++) {
      if (country.getNeighbooringCountries().get(i).getPlayerStrategy()
          .equals(country.getPlayerStrategy())) {
        neighbooringCountriesOfSamePlayer.add(country.getNeighbooringCountries().get(i));
      }
    }
    return neighbooringCountriesOfSamePlayer;
  }

  /**
   * Tells if Country can attack other country or not.
   * 
   * @param country
   * @return
   */
  public boolean isCountryAttacker(Country country) {
    if (country.getSoilders() < 2)
      return false;
    for (int i = 0; i < country.getNeighbooringCountries().size(); i++) {
      if (!country.getNeighbooringCountries().get(i).getPlayerStrategy().equals(this))
        return true;
    }
    return false;
  }

  /**
   * It sends the next attackable country
   * 
   * @param country
   * @return
   */
  public Country nextAttackableCountry(Country country) {
    for (int i = 0; i < country.getNeighbooringCountries().size(); i++) {
      if (!country.getNeighbooringCountries().get(i).getPlayerStrategy()
          .equals(country.getPlayerStrategy())) {
        return country.getNeighbooringCountries().get(i);
      }
    }
    return null;
  }

  /**
   * This method tells if this player is eligible to attack or not.
   * 
   * @return boolean
   */
  public boolean isPlayerEligibleToAttack() {
    for (int i = 0; i < getListOfPlayersConqueredCountries().size(); i++) {
      if (getListOfPlayersConqueredCountries().get(i).getSoilders() > 1) {
        if (getAttackableCountries(getListOfPlayersConqueredCountries().get(i)).size() > 0) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * This method tells if this player is eligible to fortify or not.
   * 
   * @return boolean
   */
  public boolean isPlayerEligibleToFortify() {
    for (int i = 0; i < getListOfPlayersConqueredCountries().size(); i++) {
      if (listOfNeighbooringSamePlayerCountries(getListOfPlayersConqueredCountries().get(i))
          .size() > 0 && getListOfPlayersConqueredCountries().get(i).getSoilders() > 1) {
        return true;
      }
    }
    return false;
  }

  /**
   * Countries that the player can attack.
   * 
   * @param countryName string
   * @return string array
   */
  public ArrayList<Country> getAttackableCountries(Country country) {
    ArrayList<Country> attackableCountryList = new ArrayList<>();
    if (!country.getPlayerStrategy().equals(this))
      return null;
    for (int i = 0; i < country.getNeighbooringCountries().size(); i++) {
      if (!this.equals(country.getNeighbooringCountries().get(i).getPlayerStrategy())) {
        attackableCountryList.add(country.getNeighbooringCountries().get(i));
      }
    }
    return attackableCountryList;
  }

  /**
   * method to assign the countries
   */
  @Override
  public void setListOfPlayersConqueredCountries(ArrayList<Country> conqueredCountries) {
    listOfCountiresPlayerConquered = conqueredCountries;
  }

  /**
   * method to renew all the components of the player
   */
  @Override
  public void renew() {
    riskCards = new ArrayList<>();
    listOfCountiresPlayerConquered = new ArrayList<>();
    noOfArmyAlive = 0;
    noOfUnassignedArmy = 0;
    riskCardTurnCount = 1;
  }



}
