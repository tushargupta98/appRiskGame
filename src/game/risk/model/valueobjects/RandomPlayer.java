package game.risk.model.valueobjects;

import java.util.ArrayList;
import java.util.Random;
import game.risk.Helper.DiceHelper;
import game.risk.utils.GameInterface;

public class RandomPlayer implements PlayerStrategy, GameInterface {

  private Map mapObject;

  private int noOfUnassignedArmy = 0;
  private int noOfArmyAlive = 0;
  private int riskCardTurnCount = 1;

  private String gamePlayStage = null;
  private String name = "Random Player";
  private String colorName = "Red";

  private ArrayList<RiskCard> riskCards = new ArrayList<>();
  private ArrayList<Country> listOfCountiresPlayerConquered = new ArrayList<>();

  public RandomPlayer(int noOfArmy) {
    addUnassignedArmy(noOfArmy);
  }

  /**
   * Attack other countries.
   */
  @Override
  public void attack() {
    System.out.println(getName() + " is in Attacking Phase.");
    if (!isPlayerEligibleToAttack()) {
      System.out.println(getName() + " is not eligible to Attack.");
      return;
    }

    // Select a Random number for random number of times
    int noOfAttack = new Random().nextInt(15);

    // in the loop
    for (int p = 0; p < noOfAttack && isPlayerEligibleToAttack(); p++) {
      Country country = getListOfPlayersConqueredCountries()
          .get(new Random().nextInt(getListOfPlayersConqueredCountries().size()));
      if (isCountryAttacker(country)) {
        Country attackableCountry = nextAttackableCountry(country);
        System.out.println(
            country.getCountryName() + " will attack " + attackableCountry.getCountryName());
        int noOfDiceOfAttacker = 0;
        if (country.getSoilders() > 3)
          noOfDiceOfAttacker = 3;
        else if (country.getSoilders() > 2)
          noOfDiceOfAttacker = 2;
        else
          noOfDiceOfAttacker = 1;
        int attackerDiceRoll = 1;
        for (int i = 0; i < noOfDiceOfAttacker; i++) {
          int diceRoll = DiceHelper.diceRoll();
          if (diceRoll > attackerDiceRoll)
            attackerDiceRoll = diceRoll;
        }
        int defenderDiceRoll = DiceHelper.diceRoll();
        if (attackerDiceRoll > defenderDiceRoll) {
          System.out.println(country.getCountryName() + " killed 1 soldier of "
              + attackableCountry.getCountryName());
          if (attackableCountry.getSoilders() < 1) {
            System.out.println(country.getCountryName() + " won.");
            attackableCountry.setSoilders(noOfDiceOfAttacker);
            attackableCountry.getPlayerStrategy().removedLostCountry(attackableCountry);
            attackableCountry.setPlayerStrategy(this);
            country.moveOutSoldier(noOfDiceOfAttacker);
            addConqueredCountry(attackableCountry);
            riskCards.add(Map.getOneRiskCardFromStack());
          } else {
            System.out.println(" but didt win.");
            attackableCountry.moveOutSoldier(1);
          }
        } else {
          System.out.println(country.getCountryName() + " lost.");
          country.moveOutSoldier(1);
          killArmy();
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
    if (!isPlayerEligibleToFortify()) {
      System.out.println(getName() + " is not eligible to fortify.");
      return;
    }
    // Find a country with largest soldier and is able to attack
    Country country = null;
    do {
      country = getListOfPlayersConqueredCountries()
          .get(new Random().nextInt(getListOfPlayersConqueredCountries().size()));
    } while (!isCountryAttacker(country));



    if (country != null) {
      // Find list of neighbooring countries of same player
      ArrayList<Country> neighbooringCountries = listOfNeighbooringSamePlayerCountries(country);
      /*
       * Fortify Soldier from second largest soldier containing country to largest soldier
       * containing country
       */
      if (neighbooringCountries != null && neighbooringCountries.size() > 0) {
        // Find the random neighbooring country
        Country neighbooringCountry =
            neighbooringCountries.get(new Random().nextInt(neighbooringCountries.size()));
        if (neighbooringCountry.getSoilders() - 1 < 1)
          return;
        System.out.println(getName() + " decides to fortify.");

        // Fortify all the soldier to the main country
        int noOfSoldier = new Random().nextInt(neighbooringCountry.getSoilders() - 1);
        System.out.println(country.getCountryName() + " moves in " + (noOfSoldier)
            + " soldier from " + neighbooringCountry.getCountryName());
        country.moveInSoldier(noOfSoldier);
        neighbooringCountry.moveOutSoldier(noOfSoldier);

      }
    } else {
      System.out.println("This this player not able to fortify");
      // Say something in the log about this this player not able to fortify
    }
  }

  /**
   * Adds reinforcements in countries.
   */
  @Override
  public void reinforcement() {
    System.out.println(getName() + " is in Reinforcement phase.");
    // Set Reinforcement Phase
    // setGamePlayStage(Map.gamePlayStage[1]);
    // Calculate reinforcement soldier
    // i. Calculate for number of Countries.
    addUnassignedArmy((listOfCountiresPlayerConquered.size()) / 3);
    // ii. Calculate for Continent value point
    int controlValuePoint = 0;
    for (int i = 0; i < Map.getContinentList().size(); i++) {
      int count = 0;
      for (int j = 0; j < Map.getContinentList().get(i).getListOfCountriesInContinent()
          .size(); j++) {
        // System.out.println("Continent " + Map.getContinentList().get(i).getContinentName());
        // System.out.println("Country " + Map.getContinentList().get(i)
        // .getListOfCountriesInContinent().get(j).getCountryName());
        // System.out.println("first " +
        // Map.getContinentList().get(i).getListOfCountriesInContinent()
        // .get(j).getPlayerStrategy().getName());
        // System.out.println("second " + getName());
        if (Map.getContinentList().get(i).getListOfCountriesInContinent().get(j).getPlayerStrategy()
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
    addUnassignedArmy(controlValuePoint);
    // iii. Calculate for Risk Card
    if (getRiskCards().size() > 2) {
      // a. if Matching Risk Card
      if (calculateMatchingRiskCard()) {
        boolean isContinue = true;
        for (int i = 0; i < RISK_CARD_NAMES.length && isContinue; i++) {
          int count = 0;
          ArrayList<RiskCard> cardHolder = new ArrayList<>();
          ArrayList<Integer> cardIndexHolder = new ArrayList<>();
          for (int j = 0; j < getRiskCards().size(); j++) {
            if (getRiskCards().get(j).getCardName().equals(RISK_CARD_NAMES[i])) {
              cardHolder.add(getRiskCards().get(j));
              cardIndexHolder.add(j);
              count++;
              if (count == 3) {
                for (int k = 0; k < cardHolder.size(); k++) {
                  getRiskCards().remove(cardHolder.get(k));
                  Map.addRiskCardToStack(cardHolder.get(k));
                  isContinue = false;
                }
                break;
              }
            }
          }
        }
        int newArmyNo = getRiskCardTurnCount() * 5;
        addUnassignedArmy(newArmyNo);
        incrementRiskCardTurnCount();
      }
      // b. else One of Each Risk Card
      else if (calculateOneOfEachCard()) {
        for (int i = 0; i < RISK_CARD_NAMES.length; i++) {
          for (int j = 0; j < getRiskCards().size(); j++) {
            if (getRiskCards().get(j).getCardName().equals(RISK_CARD_NAMES[i])) {
              Map.addRiskCardToStack(getRiskCards().get(j));
              getRiskCards().remove(j);
              break;
            }
          }
        }
        int newArmyNo = getRiskCardTurnCount() * 5;
        addUnassignedArmy(newArmyNo);
        incrementRiskCardTurnCount();
      }
    }
    if (getListOfPlayersConqueredCountries() != null
        && getListOfPlayersConqueredCountries().size() > 0) {
      // Reinforcing soldier to random country
      for (int i = 0; i < noOfUnassignedArmy; i++) {
        getListOfPlayersConqueredCountries()
            .get(new Random().nextInt(getListOfPlayersConqueredCountries().size())).addSoilders();
      }
      noOfArmyAlive = noOfArmyAlive + getNoOfUnitUnassigned();
      noOfUnassignedArmy = 0;
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
        // GameActionUI.switchTurnInMatchingRiskCard(true);
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
      // GameActionUI.switchTurnInOneOfEachRiskCard(true);
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


  @Override
  public void setListOfPlayersConqueredCountries(ArrayList<Country> conqueredCountries) {
    listOfCountiresPlayerConquered = conqueredCountries;
  }

  @Override
  public void renew() {
    riskCards = new ArrayList<>();
    listOfCountiresPlayerConquered = new ArrayList<>();
    noOfArmyAlive = 0;
    noOfUnassignedArmy = 0;
    riskCardTurnCount = 1;
  }


}
