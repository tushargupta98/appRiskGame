package game.risk.model.valueobjects;

import java.awt.Color;
import java.util.ArrayList;
import game.risk.Helper.PlayerStatusHelper;
import game.risk.utils.GameInterface;
import game.risk.view.GameActionUI;

/**
 * This is a Player Class.
 * 
 * @author Ziba Khaneshi
 * @version 1.0.0
 * @since 10-October-2017
 *
 */
public class Player implements GameInterface, PlayerStrategy {

  private int noOfUnassignedArmy = 0;
  private int noOfArmyAlive = 0;
  private int riskCardTurnCount = 1;

  private String name = "Default Player";
  private String colorName = "Blue";
  private String gamePlayStage = null;

  private Color color = Color.BLUE;

  private boolean turn = false;
  private boolean calculateReinforcement = false;
  private boolean isAttacker = false;
  private boolean isDefender = false;
  private boolean playerAttackWin = false;
  private boolean isDefeated = false;

  private ArrayList<RiskCard> riskCards = new ArrayList<>();
  private ArrayList<Country> listOfCountiresPlayerConquered = new ArrayList<>();

  /**
   * This is a constructor of Player Class which sets name, color, color name and initial units.
   * 
   * @param playerName this is a int
   * @param playerColor this is a int
   * @param playerColorName this is a int
   * @param noOfInitialAmry this is a int
   */
  public Player(String playerName, Color playerColor, String playerColorName, int noOfInitialAmry) {
    setName(playerName);
    setColor(playerColor);
    addUnassignedArmy(noOfInitialAmry);
    setGamePlayStage(Map.gamePlayStage[0]);
    setColorName(playerColorName);
  }

  /**
   * Player Constructor
   */
  public Player() {

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
   * Sets number of alive army.
   * 
   * @param aliveArmy
   */
  public void setNoOfAliveArmy(int aliveArmy) {
    noOfArmyAlive = aliveArmy;
  }

  /**
   * Decreasing number of soldier by killing.
   */
  public void killArmy() {
    this.noOfArmyAlive--;
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
   * Sets player color.
   * 
   * @return player color.
   */
  public Color getColor() {
    return color;
  }

  /**
   * Sets player color.
   * 
   * @param color color
   */
  public void setColor(Color color) {
    this.color = color;
  }

  /**
   * Checks is player in turn.
   * 
   * @return boolean
   */
  public boolean isTurn() {
    return turn;
  }

  /**
   * Sets player turn.
   * 
   * @param turn boolean
   */
  public void setTurn(boolean turn) {
    this.turn = turn;
  }

  /**
   * Get the game phase of player.
   * 
   * @return string
   */
  public String getGamePlayStage() {
    return gamePlayStage;
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
   * Checks if reinforcement calculation is needed.
   * 
   * @return boolean
   */
  public boolean isCalculateReinforcement() {
    return calculateReinforcement;
  }

  /**
   * Sets if reinforcement calculation is required.
   * 
   * @param calculateReinforcement boolean
   */
  public void setCalculateReinforcement(boolean calculateReinforcement) {
    this.calculateReinforcement = calculateReinforcement;
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
   * Get player color name.
   * 
   * @return color name.
   */
  public String getColorName() {
    return colorName;
  }

  /**
   * Sets the color name of the player.
   * 
   * @param colorName string
   */
  public void setColorName(String colorName) {
    this.colorName = colorName;
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
   * This is method for calculating the reinforcement units. This method includes calculation of
   * both all the countries/3 and risk cards.
   */
  public void calculateReinforcementUnits() {
    setGamePlayStage(Map.gamePlayStage[1]);
    // Calculate for total country occupied by 3
    addUnassignedArmy((listOfCountiresPlayerConquered.size()) / 3);
    // Calculate for Continent Control Value Point
    addUnassignedArmy(getControlValuePoint());
    // Calculate for Risk Cards
    // For Matching Card
    calculateMatchingRiskCard();

    // For 1 of each Card
    calculateOneOfEachCard();
    // Refresh Data
    GameActionUI.refreshData();
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
        GameActionUI.switchTurnInMatchingRiskCard(true);
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
      for (int j = 0; j < Map.playerWithTurn().getRiskCards().size(); j++) {
        if (RISK_CARD_NAMES[i].equals(Map.playerWithTurn().getRiskCards().get(j).getCardName())) {
          count++;
          break;
        }
      }
    }
    if (count == 3) {
      GameActionUI.switchTurnInOneOfEachRiskCard(true);
      return true;
    }
    return false;
  }

  /**
   * Gives list of Country name player conquered.
   * 
   * @return string []
   */
  public String[] listOfCountriesPlayerConqueredInStringArray() {
    return arrayListToStringArray(listOfCountiresPlayerConquered);
  }

  /**
   * Gives list of Neighbooring Country Names of same player.
   * 
   * @param countryName string
   * @return string []
   */
  public String[] listOfNeighbooringSamePlayerCountries(String countryName) {
    Country country = Map.getCountry(countryName);
    ArrayList<Country> neighbooringCountriesOfSamePlayer = new ArrayList<>();
    for (int i = 0; i < country.getNeighbooringCountries().size(); i++) {
      if (country.getNeighbooringCountries().get(i).getPlayer().equals(country.getPlayer())) {
        neighbooringCountriesOfSamePlayer.add(country.getNeighbooringCountries().get(i));
      }
    }
    return arrayListToStringArray(neighbooringCountriesOfSamePlayer);
  }

  /**
   * Fortification of Soldier from One neighbooring country to another.
   * 
   * @param soldierSendFromCountryName string
   * @param soldierSendToCountryName string
   * @param noOfSoldier int
   */
  public void soldierFortify(String soldierSendFromCountryName, String soldierSendToCountryName,
      int noOfSoldier) {
    Map.getCountry(soldierSendFromCountryName).moveOutSoldier(noOfSoldier);
    Map.getCountry(soldierSendToCountryName).moveInSoldier(noOfSoldier);
  }

  /**
   * Countries which can attack.
   * 
   * @return list of countries which can attack.
   */
  public String[] getAttackerCountries() {
    ArrayList<Country> attackerCountryList = new ArrayList<>();
    for (int i = 0; i < listOfCountiresPlayerConquered.size(); i++) {
      if (listOfCountiresPlayerConquered.get(i).getSoilders() > 1) {
        attackerCountryList.add(listOfCountiresPlayerConquered.get(i));
      }
    }
    return arrayListToStringArray(attackerCountryList);
  }

  /**
   * Countries that the player can attack.
   * 
   * @param countryName string
   * @return string array
   */
  public String[] getAttackableCountries(String countryName) {
    ArrayList<Country> attackableCountryList = new ArrayList<>();
    Country country = Map.getCountry(countryName);
    if (!country.getPlayer().equals(this))
      return null;
    for (int i = 0; i < country.getNeighbooringCountries().size(); i++) {
      if (!this.equals(country.getNeighbooringCountries().get(i).getPlayer())) {
        attackableCountryList.add(country.getNeighbooringCountries().get(i));
      }
    }
    return arrayListToStringArray(attackableCountryList);
  }

  /**
   * This method tells how many dice this player can roll on this country.
   * 
   * @param countryName this is a string country name
   * @return int
   */
  public int noOfDicePlayerCanRollOnThisCountry(String countryName) {
    Country country = Map.getCountry(countryName);
    if (isAttacker) {
      if (country.getSoilders() < 2)
        return 0;
      else if (country.getSoilders() < 3)
        return 1;
      else if (country.getSoilders() < 4)
        return 2;
      else if (country.getSoilders() > 3)
        return 3;
    } else if (isDefender) {
      if (country.getSoilders() < 2)
        return 1;
      else if (country.getSoilders() > 1)
        return 2;
    }
    return -1;
  }

  private String[] arrayListToStringArray(ArrayList<Country> arrayList) {
    String[] stringArray = new String[arrayList.size()];
    for (int i = 0; i < stringArray.length; i++) {
      stringArray[i] = arrayList.get(i).getCountryName();
    }
    return stringArray;
  }

  /**
   * Tells if this player is the Attacker or not.
   * 
   * @return boolean
   */
  public boolean isAttacker() {
    return isAttacker;
  }

  /**
   * This method helps set if this class is the attacker or not.
   * 
   * @param isAttacker boolean
   */
  public void setAttacker(boolean isAttacker) {
    if (isAttacker)
      PlayerStatusHelper.eraseAttacker();
    this.isAttacker = isAttacker;
  }

  /**
   * Tells if this player is the defender or not.
   * 
   * @return boolean
   */
  public boolean isDefender() {
    return isDefender;
  }

  /**
   * Sets if this player is the defender or not.
   * 
   * @param isDefender boolean
   */
  public void setDefender(boolean isDefender) {
    if (isDefender)
      PlayerStatusHelper.eraseDefender();
    this.isDefender = isDefender;
  }

  /**
   * This method tells if this player is eligible to attack or not.
   * 
   * @return boolean
   */
  public boolean isPlayerEligibleToAttack() {
    for (int i = 0; i < getListOfPlayersConqueredCountries().size(); i++) {
      if (getListOfPlayersConqueredCountries().get(i).getSoilders() > 1) {
        if (getAttackableCountries(
            getListOfPlayersConqueredCountries().get(i).getCountryName()).length > 0) {
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
      if (listOfNeighbooringSamePlayerCountries(
          getListOfPlayersConqueredCountries().get(i).getCountryName()).length > 0
          && getListOfPlayersConqueredCountries().get(i).getSoilders() > 1) {
        return true;
      }
    }
    return false;
  }

  /**
   * Checks if the Player is a Winner or not.
   * 
   * @return boolean
   */
  public boolean isPlayerWinner() {
    return Map.getCountryList().size() == listOfCountiresPlayerConquered.size();
  }

  /**
   * Kills Soldier of a particular country of player
   * 
   * @param countryName countryname
   */
  public void killSoldierAtCountry(String countryName) {
    Country country = Map.getCountry(countryName);
    killArmy();
    country.moveOutSoldier(1);
  }

  /**
   * Tells if the player won the attack.
   * 
   * @return boolean
   */
  public boolean isPlayerAttackWin() {
    return playerAttackWin;
  }

  /**
   * sets if the player has won the attack for not.
   * 
   * @param playerAttackWin this is a int
   */
  public void setPlayerAttackWin(boolean playerAttackWin) {
    this.playerAttackWin = playerAttackWin;
  }

  /**
   * Tells if a player is defeated or not.
   * 
   * @return boolean
   */
  public boolean isDefeated() {
    return isDefeated;
  }

  /**
   * Makes player defeated.
   */
  public void setDefeated() {
    this.isDefeated = true;
  }

  @Override
  public void attack() {

  }

  @Override
  public void fortify() {

  }

  @Override
  public void reinforcement() {
    addNoOfArmyAlive();
  }

  @Override
  public void initialSoldierDistribution() {
    addNoOfArmyAlive();
  }

  @Override
  public void setListOfPlayersConqueredCountries(ArrayList<Country> conqueredCountries) {
    listOfCountiresPlayerConquered = conqueredCountries;
  }

  @Override
  public void renew() {

  }


}
