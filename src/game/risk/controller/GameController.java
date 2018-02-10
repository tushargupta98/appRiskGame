package game.risk.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.SwingUtilities;
import game.risk.Helper.MapCreateHelper;
import game.risk.Helper.MapEditHelper;
import game.risk.Helper.ReinforcementHelper;
import game.risk.model.valueobjects.Continent;
import game.risk.model.valueobjects.Country;
import game.risk.model.valueobjects.Map;
import game.risk.model.valueobjects.Player;
import game.risk.model.valueobjects.RiskCard;
import game.risk.model.valueobjects.Tournament;
import game.risk.utils.GameInterface;
import game.risk.view.GameActionUI;
import game.risk.view.GameInitialView;
import game.risk.view.GameMapSetupView;
import game.risk.view.GamePlayerView;
import game.risk.view.PhaseView;
import game.risk.view.PlayerWorldDominationView;
import game.risk.view.TournamentSetupView;

/**
 * This is the main Game Controller class.
 * 
 * @author Tushar Gupta
 * @version 1.0.0
 * @since 12-October-2017
 */
public class GameController implements GameInterface {

  static int territoriesIndex;
  static int continentsIndex;
  static int playerIndex;
  private static boolean saveMode = false;
  private static boolean tournamentMode = false;

  /**
   * method used to read map
   * 
   * @param file
   * @return
   */
  private static ArrayList<String> readMap(String file) {
    ArrayList<String> mapData = null;
    try {
      BufferedReader br = new BufferedReader(new FileReader(file));
      // Read file and load it to list
      mapData = new ArrayList<String>();
      String line = "";
      int indexNo = 0;
      while ((line = br.readLine()) != null) {
        if (line.contains("[Continents]"))
          continentsIndex = indexNo;
        if (line.contains("[Territories]"))
          territoriesIndex = indexNo;
        if (line.contains("[Player]"))
          playerIndex = indexNo;
        mapData.add(line);
        indexNo++;
      }
    } catch (IOException e) {

      e.printStackTrace();
    }
    return mapData;
  }

  /**
   * This method creates all the Country, Continent and Risk Card objects. This method also connects
   * all the objects with each other.
   * 
   * @param file
   */
  public static void createComponents(String file) {
    Map.initiateMap();
    ArrayList<String> mapData = readMap(file);
    // Read Continent
    for (int i = continentsIndex + 1; !mapData.get(i).equals("")
        && !mapData.get(i).contains("[Territories]"); i++) {
      String data = mapData.get(i);
      int equalIndex = data.indexOf("=");
      String continentName = data.substring(0, equalIndex);
      int point = Integer.parseInt("" + data.substring(equalIndex + 1));
      Map.addContinent(new Continent(continentName, point));
    }

    // Read Territories
    int cardCount = 0;
    for (int i = territoriesIndex + 1; i < mapData.size()
        && !mapData.get(i).equals("[Player]"); i++) {
      String data = mapData.get(i);
      String territoryName = "Default Territory";
      if (!(data.equals(""))) {
        int commaIndex = data.indexOf(",");
        int xCoordinate = -1, yCoordinate = -1;
        ArrayList<String> neighbooringCountries = new ArrayList<>();
        String word = "";
        int count = 1;
        int continentIndex = -1;
        for (int j = 0; j < data.length(); j++) {
          if (data.charAt(j) == ',') {
            if (count == 1) {
              commaIndex = j;
              territoryName = word;
              word = "";
              count++;
            } else if (count == 2) {
              commaIndex = j;
              xCoordinate = Integer.parseInt(word);
              word = "";
              count++;
            } else if (count == 3) {
              commaIndex = j;
              yCoordinate = Integer.parseInt(word);
              word = "";
              count++;
            } else if (count == 4) {
              commaIndex = j;
              for (int k = 0; k < Map.getContinentList().size(); k++) {
                if (Map.getContinentList().get(k).getContinentName().equals(word)) {
                  continentIndex = k;
                  word = "";
                  break;
                }
              }
              count++;
            } else {
              commaIndex = j;
              neighbooringCountries.add(word);
              word = "";
            }
          } else if (j == (data.length() - 1)) {
            word = word + data.charAt(j);
            neighbooringCountries.add(word);
          } else {
            word = word + data.charAt(j);
          }
        }
        Country newCountry = new Country(territoryName, Map.getContinentList().get(continentIndex),
            neighbooringCountries, xCoordinate, yCoordinate);
        Map.addCountry(newCountry);
        for (int j = 0; j < Map.getContinentList().size(); j++) {
          if (Map.getContinentList().get(j).getContinentName()
              // Default Territory
              .equals(newCountry.getContinent().getContinentName())) {
            Map.getContinentList().get(j).addCountryToContinent(newCountry);
            break;
          }
        }
        // Create Risk Card
        RiskCard newRiskCard = new RiskCard(RISK_CARD_NO_OF_SOLDIER_ON_CARD_ARRAY[cardCount],
            newCountry, RISK_CARD_NAMES[cardCount]);
        if (cardCount == 2)
          cardCount = 0;
        else
          cardCount++;
        Map.addRiskCard(newRiskCard);
      }
    }
    setNeigbooringCountry();
    // If Saved Game create Player.
    if (isSaveMode()) {
      boolean isSotrue = true;
      for (int i = playerIndex + 1; i < mapData.size(); i++) {
        String[] line = mapData.get(i).split(",");
        Player player = new Player();
        if (isSotrue) {
          player.setTurn(true);
          isSotrue = false;
        }
        // setting the values of player
        player.setName(line[0]);
        for (int j = 0; j < COLOR_NAME_ARRAY.length; j++) {
          if (line[1].equals(COLOR_NAME_ARRAY[j])) {
            player.setColorName(line[1]);
            player.setColor(COLOR_ARRAY[j]);
            break;
          }
        }
        // Adding the players onto map
        player.setNoOfAliveArmy(Integer.parseInt(line[2]));
        for (int j = 3; j < line.length; j++) {
          String[] countryLine = line[j].split("-");
          Country country = Map.getCountry(countryLine[0]);
          int soldiers = Integer.parseInt(countryLine[1]);
          country.setPlayer(player);
          country.setSoilders(soldiers);
          player.addConqueredCountry(country);
          player.addRiskCard(Map.getRiskCard(country));
        }
        Map.addPlayers(player);
      }
    }


  }

  /**
   * This method connects all the neighbooring countries to each other.
   */
  public static void setNeigbooringCountry() {
    for (int i = 0; i < Map.getCountryList().size(); i++) {
      Country tempCountry = Map.getCountryList().get(i);
      for (int j = 0; j < tempCountry.getNeighbooringCountriesName().size(); j++) {
        for (int j2 = 0; j2 < Map.getCountryList().size(); j2++) {
          if (tempCountry.getNeighbooringCountriesName().get(j)
              .equals(Map.getCountryList().get(j2).getCountryName())) {
            tempCountry.addNeighboor(Map.getCountryList().get(j2));
          }
        }
      }
    }
  }

  /**
   * Turn in Matching card action
   */
  public static void turnInMatchingCardAction() {
    ReinforcementHelper.turnInMatchingCardAction();
  }

  /**
   * Turn in one of each card action
   */
  public static void turnInOneOfEachCardAction() {
    ReinforcementHelper.turnInOneOfEachCardAction();
  }

  /**
   * Delete Continent
   * 
   * @param continentName
   */
  public static void deleteContinent(String continentName) {
    MapEditHelper.deleteContinent(continentName);
  }

  /**
   * Delete Country
   * 
   * @param countryName
   */
  public static void deleteCountry(String countryName) {
    MapEditHelper.deleteCountry(countryName);
  }

  /**
   * Create File and Write in File
   * 
   * @param filePath
   * @param map
   */
  public static void createFileAndWriteInFile(String filePath, ArrayList<String> map) {
    MapCreateHelper.createFileAndWriteInFile(filePath, map);
  }

  /**
   * Delete and Create new file and write in file.
   * 
   * @param filePath
   * @param editedMapList
   */
  public static void deleteAndCreateFileAndWriteInFile(String filePath,
      ArrayList<String> editedMapList) {
    MapCreateHelper.deleteAndCreateFileAndWriteInFile(filePath, editedMapList);
  }

  /**
   * Map Component To List
   * 
   * @return
   */
  public static ArrayList<String> mapComponentToList() {
    return MapCreateHelper.mapComponentToList();
  }

  /**
   * It makes components for saved files.
   */
  public static void savingAllComponents() {

  }

  /**
   * This is a main method for GameController class
   * 
   * @param args
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {
    GameInitialView.gameStartView();
    if (!isTournamentMode()) {
      // Create Map instance
      Map.renewMap();
      Map mapObject = Map.getInstance();
      for (int i = 0; i < args.length; i++) {
        Map.getPlayersStrategyList().get(i).setListOfPlayersConqueredCountries(new ArrayList<>());
      }
      // add Phase View and Player World Domination class as Observer to Map
      mapObject.addObserver(PhaseView.getInstance());
      mapObject.addObserver(PlayerWorldDominationView.getIntance());
      // Game Map Setup View
      GameMapSetupView.gameStartView();
      if (!Map.getFilePath().equals("no_file_path")) {
        createComponents(Map.getFilePath() + Map.getMapName() + ".map");
      }

      if (!isSaveMode()) {
        // Game Player Selection View
        int noOfPlayers = GamePlayerView.getPlayerSelectNumber();
        int noOfArmy = 0;
        if (noOfPlayers == 3)
          noOfArmy = TOTAL_NO_OF_UNIT_FOR_3_PLAYERS_EACH;
        else if (noOfPlayers == 4)
          noOfArmy = TOTAL_NO_OF_UNIT_FOR_4_PLAYERS_EACH;
        else
          noOfArmy = TOTAL_NO_OF_UNIT_FOR_5_PLAYERS_EACH;

        for (int i = 0; i < noOfPlayers; i++) {
          String playerName = GamePlayerView.getPlayerName();
          Player newPlayer = new Player(playerName, COLOR_ARRAY[i], COLOR_NAME_ARRAY[i], noOfArmy);
          newPlayer.setGamePlayStage(Map.gamePlayStage[0]);
          if (i == 0)
            newPlayer.setTurn(true);
          Map.addPlayers(newPlayer);
        }
        // Assign Countries to Players Randomly
        int noOfUnoccupiedCountries = Map.getCountryList().size();
        int playerIndex = 0;
        while (noOfUnoccupiedCountries > 0) {
          if (playerIndex == Map.getPlayerList().size()) {
            playerIndex = 0;
          }
          int random = -1;
          do {
            random = new Random().nextInt(Map.getCountryList().size());
          } while (Map.getCountryList().get(random).getPlayer() != null);
          // Adding Player to Country
          Map.getCountryList().get(random).setPlayer(Map.getPlayerList().get(playerIndex));
          // Adding Country to Player
          Map.getPlayerList().get(playerIndex)
              .addConqueredCountry(Map.getCountryList().get(random));
          // Adding Risk Card to Player
          Map.getCountryList().get(random).getPlayer()
              .addRiskCard(Map.getRiskCard(Map.getCountryList().get(random)));
          Map.getCountryList().get(random).addSoilders();
          Map.getPlayerList().get(playerIndex).addNoOfArmyAlive();
          noOfUnoccupiedCountries--;
          playerIndex++;
        }
      }

      SwingUtilities.invokeLater(new GameActionUI());
    } else if (isTournamentMode()) {
      setTournamentMode(true);
      // Playing Tournament
      TournamentSetupView.tournamentGameViewStart();
      // Number of Game Loop
      for (int p = 0; p < Tournament.getnumOfGames(); p++) {
        // Number of Map Loop
        for (int h = 0; h < Tournament.getMapFilePaths().size(); h++) {
          Map.renewMap();
          Map.setFilePath(Tournament.getMapFilePaths().get(h));
          Map.setMapName(Tournament.getMapNames().get(h));
          createComponents(Map.getFilePath() + Map.getMapName() + ".map");
          for (int i = 0; i < Map.getRiskCards().size(); i++) {
            Map.addRiskCardToStack(Map.getRiskCards().get(i));
          }
          // Renew Player
          for (int i = 0; i < Map.getPlayersStrategyList().size(); i++) {
            Map.getPlayersStrategyList().get(i).renew();
          }
          // Assign Countries to Players Randomly
          int noOfUnoccupiedCountries = Map.getCountryList().size();
          int playerIndex = 0;
          while (noOfUnoccupiedCountries > 0) {
            if (playerIndex == Map.getPlayersStrategyList().size()) {
              playerIndex = 0;
            }
            int random = -1;
            do {
              random = new Random().nextInt(Map.getCountryList().size());
            } while (Map.getCountryList().get(random).getPlayerStrategy() != null);
            // Adding Player to Country
            Map.getCountryList().get(random)
                .setPlayerStrategy(Map.getPlayersStrategyList().get(playerIndex));
            // Adding Country to Player
            Map.getPlayersStrategyList().get(playerIndex)
                .addConqueredCountry(Map.getCountryList().get(random));
            // Adding 1 soldier to country
            Map.getCountryList().get(random).addSoilders();
            Map.getPlayersStrategyList().get(playerIndex).addNoOfArmyAlive();
            noOfUnoccupiedCountries--;
            playerIndex++;
          }
          // Adding initial soldier distribution
          for (int i = 0; i < 23; i++) {
            for (int j = 0; j < Map.getPlayersStrategyList().size(); j++) {
              Map.getPlayersStrategyList().get(j).initialSoldierDistribution();
            }
          }
          // Tournament phase
          boolean goOn = true;
          for (int i = 0; i < Map.getPlayersStrategyList().size(); i++) {
            int percent =
                ((Map.getPlayersStrategyList().get(i).getListOfPlayersConqueredCountries().size()
                    * 100) / Map.getCountryList().size());
            if (percent > 100)
              percent = 100;
            System.out.println(Map.getPlayersStrategyList().get(i).getName() + " conquered "
                + percent + "% of the world.");
          }
          for (int i = 0; i < Tournament.getnumOfTurns() && goOn; i++) {
            for (int j = 0; j < Map.getPlayersStrategyList().size() && goOn; j++, i++) {
              if (Map.getPlayersStrategyList().get(j).getListOfPlayersConqueredCountries()
                  .size() > 0) {
                Map.getPlayersStrategyList().get(j).reinforcement();
                Map.getPlayersStrategyList().get(j).attack();
                // Checking for all loser
                int count = 0;
                for (int j2 = 0; j2 < Map.getPlayersStrategyList().size(); j2++) {
                  if (Map.getPlayersStrategyList().get(j2).getListOfPlayersConqueredCountries()
                      .size() < 1) {
                    count++;
                  }
                }
                if (count == Map.getPlayersStrategyList().size() - 1) {
                  System.out.println(Map.getPlayersStrategyList().get(j).getName() + " Won!!");
                  // Save result
                  Tournament.addResult("Game " + (p + 1) + " Map " + (h + 1) + " Winner: "
                      + Map.getPlayersStrategyList().get(j).getName());
                  goOn = false;
                } else {
                  Map.getPlayersStrategyList().get(j).fortify();
                }
                for (int b = 0; b < Map.getPlayersStrategyList().size(); b++) {
                  int percent =
                      ((Map.getPlayersStrategyList().get(b).getListOfPlayersConqueredCountries()
                          .size() * 100) / Map.getCountryList().size());
                  if (percent > 100)
                    percent = 100;
                  System.out.println(Map.getPlayersStrategyList().get(b).getName() + " conquered "
                      + percent + "% of the world.");
                }
              } else {
                System.out
                    .println(Map.getPlayersStrategyList().get(j).getName() + " is out of Game.");
              }
            }
          }
          if (goOn) {
            System.out.println("Draw");
            Tournament.addResult("Game " + (p + 1) + " Map " + (h + 1) + " Draw!");
          }
        }
      }
      for (int j = 0; j < Tournament.getResult().size(); j++) {
        System.out.println(Tournament.getResult().get(j));
      }
    }
  }

  /**
   * Checking for isSave mode or not
   * 
   * @return
   */
  public static boolean isSaveMode() {
    return saveMode;
  }

  /**
   * Assigning the save mode to the player
   * 
   * @param saveMode
   */
  public static void setSaveMode(boolean saveMode) {
    GameController.saveMode = saveMode;
  }

  /**
   * checking for tournament mode
   * 
   * @return
   */
  public static boolean isTournamentMode() {
    return tournamentMode;
  }

  /**
   * Assigning the tournament mode to the player
   * 
   * @param tournamentMode
   */
  public static void setTournamentMode(boolean tournamentMode) {
    GameController.tournamentMode = tournamentMode;
  }
}
