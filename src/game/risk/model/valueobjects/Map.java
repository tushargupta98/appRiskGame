package game.risk.model.valueobjects;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Map class. This class keeps record of all the Countries, Continents and Players involve in the
 * game
 * 
 * @author Ziba Khaneshi
 * @version 1.0.0
 * @since 10-October-2017
 *
 */
public class Map extends Observable {

  private static String mapName = "no_name";
  private static String filePath = "no_file_path";
  private static String imagePath = "no_path";
  private static boolean gameEnded = false;

  /**
   * List of Game Play Stages
   */
  public static String[] gamePlayStage =
      {"Game Startup", "Reinforcements", "Attacking", "Fortification", "End Game"};

  private static ArrayList<Player> players = new ArrayList<>();
  private static ArrayList<PlayerStrategy> playersStrategy = new ArrayList<>();
  private static ArrayList<Continent> continents = new ArrayList<>();
  private static ArrayList<Country> countries = new ArrayList<>();
  private static ArrayList<RiskCard> riskCards = new ArrayList<>();
  private static ArrayList<RiskCard> riskCardsOnStack = new ArrayList<>();

  private static int noOfTurn = 0;

  private Map() {

  }

  public static void renewMap() {
    players = new ArrayList<>();
    continents = new ArrayList<>();
    countries = new ArrayList<>();
    riskCards = new ArrayList<>();
    riskCardsOnStack = new ArrayList<>();
    setNoOfTurn(0);
    mapName = "no_name";
    filePath = "no_file_path";
    gameEnded = false;
  }

  /**
   * Gets the Map Name.
   * 
   * @return map name.
   */
  public static String getMapName() {
    return mapName;
  }

  /**
   * Sets the Map name.
   * 
   * @param mapName
   */
  public static void setMapName(String mapName) {
    Map.mapName = mapName;
  }

  /**
   * Gets the Map file path.
   * 
   * @return file path.
   */
  public static String getFilePath() {
    return filePath;
  }

  /**
   * Sets the Map file path.
   * 
   * @param filePath
   */
  public static void setFilePath(String filePath) {
    Map.filePath = filePath;
  }

  /**
   * Sets both the Map name and File Path.
   * 
   * @param mapName
   * @param filePath
   */
  public static void setMapNameAndFilePath(String mapName, String filePath) {
    Map.setMapName(mapName);
    Map.setFilePath(filePath);
  }

  /**
   * Gets all the Players.
   * 
   * @return player list.
   */
  public static ArrayList<Player> getPlayerList() {
    return players;
  }

  /**
   * Adds player to the list.
   * 
   * @param player
   */
  public static void addPlayers(Player player) {
    players.add(player);
  }

  /**
   * Sets the list of players playing.
   * 
   * @param players
   */
  public static void setPlayers(ArrayList<Player> players) {
    Map.players = players;
  }

  /**
   * Get the list of all the Continents.
   * 
   * @return continent list.
   */
  public static ArrayList<Continent> getContinentList() {
    return continents;
  }

  /**
   * Adds Continent to the List.
   * 
   * @param continent
   */
  public static void addContinent(Continent continent) {
    continents.add(continent);
  }

  /**
   * Removes Continent from the List.
   * 
   * @param continent
   */
  public static void removeContinent(Continent continent) {
    continents.remove(continent);
  }

  /**
   * Sets the list of all the Continents.
   * 
   * @param continents
   */
  public static void setContinents(ArrayList<Continent> continents) {
    Map.continents = continents;
  }

  /**
   * Gets all the Countries.
   * 
   * @return county list.
   */
  public static ArrayList<Country> getCountryList() {
    return countries;
  }

  /**
   * Add country to the list.
   * 
   * @param country
   */
  public static void addCountry(Country country) {
    countries.add(country);
  }

  /**
   * Removes country from the list.
   * 
   * @param country
   */
  public static void removeCountry(Country country) {
    countries.remove(country);
  }

  /**
   * Get the Country it is called by name.
   * 
   * @param countryName
   * @return Country class.
   */
  public static Country getCountry(String countryName) {
    for (int i = 0; i < countries.size(); i++) {
      if (countries.get(i).getCountryName().equals(countryName))
        return countries.get(i);
    }
    return null;
  }

  /**
   * Sets the country list.
   * 
   * @param countries
   */
  public static void setCountries(ArrayList<Country> countries) {
    Map.countries = countries;
  }

  /**
   * Get the Continent it is called by name.
   * 
   * @param continentName
   * @return Continent class.
   */
  public static Continent getContinent(String continentName) {
    for (int i = 0; i < continents.size(); i++) {
      if (continents.get(i).getContinentName().equals(continentName))
        return continents.get(i);
    }
    return null;
  }

  /**
   * Get the player with turn.
   * 
   * @return player with turn.
   */
  public static Player playerWithTurn() {
    for (int i = 0; i < players.size(); i++)
      if (players.get(i).isTurn())
        return players.get(i);
    return null;
  }

  /**
   * Changes the player in turn to the next player.
   */
  public void playerTurnChange() {
    for (int i = 0; i < players.size(); i++) {
      if (players.get(i).isTurn()) {
        players.get(i).setTurn(false);
        nextNonDefeatedPlayer(i).setTurn(true);
        notifyGame();
        return;
      }
    }
    setNoOfTurn(getNoOfTurn() + 1);
  }

  private static Player nextNonDefeatedPlayer(int index) {
    for (int i = index + 1; i < getPlayerList().size(); i++) {
      if (!getPlayerList().get(i).isDefeated()) {
        return getPlayerList().get(i);
      }
    }
    for (int i = 0; i < index; i++) {
      if (!getPlayerList().get(i).isDefeated()) {
        return getPlayerList().get(i);
      }
    }
    return null;
  }

  /**
   * Gets the Risk Cards.
   * 
   * @return Risk Card list.
   */
  public static ArrayList<RiskCard> getRiskCards() {
    return riskCards;
  }

  /**
   * Set the list of Risk Cards.
   * 
   * @param riskCards
   */
  public static void setRiskCards(ArrayList<RiskCard> riskCards) {
    Map.riskCards = riskCards;
  }

  /**
   * Adds Risk Card.
   * 
   * @param riskCard
   */
  public static void addRiskCard(RiskCard riskCard) {
    riskCards.add(riskCard);
  }

  /**
   * Gets the List of Risk Cards on Stack.
   * 
   * @return risk card list on stack.
   */
  public static ArrayList<RiskCard> getRiskCardsOnStack() {
    return riskCardsOnStack;
  }

  /**
   * Sets the list of Risk Cards on Stack.
   * 
   * @param riskCardsOnStack
   */
  public static void setRiskCardsOnStack(ArrayList<RiskCard> riskCardsOnStack) {
    Map.riskCardsOnStack = riskCardsOnStack;
  }

  /**
   * Adds a Risk Card on Stack.
   * 
   * @param riskCard
   */
  public static void addRiskCardToStack(RiskCard riskCard) {
    riskCardsOnStack.add(riskCard);
  }

  /**
   * Gives a Risk Card from Stack
   * 
   * @return
   */
  public static RiskCard getOneRiskCardFromStack() {
    if (getRiskCardsOnStack().size() < 1)
      return null;
    RiskCard card = getRiskCardsOnStack().get(0);
    getRiskCardsOnStack().remove(0);
    return card;
  }

  /**
   * Gets the Risk Card with The Country name.
   * 
   * @param cardCountry
   * @return Risk Card with country name.
   */
  public static RiskCard getRiskCard(Country cardCountry) {
    for (int i = 0; i < riskCards.size(); i++) {
      if (riskCards.get(i).getCountry().getCountryName().equals(cardCountry.getCountryName()))
        return riskCards.get(i);
    }
    return null;
  }

  /**
   * Sends the Player of the Country.
   * 
   * @param country
   * @return
   */
  public static Player getPlayerOfCountry(Country country) {
    return country.getPlayer();
  }

  /**
   * Sends the Player Strategy of the Country.
   * 
   * @param country
   * @return
   */
  public static PlayerStrategy getPlayerStategyOfCountry(Country country) {
    return country.getPlayerStrategy();
  }


  /**
   * To Initiate all the list for Countries, Continents and Risk Cards.
   */
  public static void initiateMap() {
    continents = new ArrayList<>();
    countries = new ArrayList<>();
    riskCards = new ArrayList<>();
    riskCardsOnStack = new ArrayList<>();
  }

  /**
   * Says if the game ended or not.
   * 
   * @return
   */
  public static boolean isGameEnded() {
    return gameEnded;
  }

  /**
   * Ends game
   * 
   */
  public static void gameEnded() {
    Map.gameEnded = true;
    System.out.println("Game Ended!");
  }

  /**
   * To notify all the Observers
   */
  public void notifyGame() {
    setChanged();
    notifyObservers(this);
  }

  /**
   * Making Map singleton
   * 
   * @return
   */
  public static Map getInstance() {
    return new Map();
  }

  public static int getNoOfTurn() {
    return noOfTurn;
  }

  public static void setNoOfTurn(int noOfTurn) {
    Map.noOfTurn = noOfTurn;
  }

  public static ArrayList<PlayerStrategy> getPlayersStrategyList() {
    return playersStrategy;
  }

  public static void setPlayerStrategy(ArrayList<PlayerStrategy> playersStrategy) {
    Map.playersStrategy = playersStrategy;
  }

  public static void addPlayerStrategy(PlayerStrategy player) {
    playersStrategy.add(player);
  }

  public static String getImagePath() {
    return imagePath;
  }

  public static void setImagePath(String imagePath) {
    Map.imagePath = imagePath;
  }

}
