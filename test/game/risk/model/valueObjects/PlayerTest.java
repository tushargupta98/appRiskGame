package game.risk.model.valueobjects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.awt.Color;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import game.risk.utils.GameInterface;
import game.risk.view.GameActionUI;

/**
 * Test class covering all the player related scenarios
 */
public class PlayerTest {

  Player player;

  /**
   * Method to create a player instance
   */
  @Before
  public void createPlayerInstance() {
    player = new Player("Karthik", Color.RED, "Red", 23);
  }

  Player player1, player2;
  Continent continent;
  Country country1, country2, country3, country4, country5;
  RiskCard riskCard1, riskCard2, riskCard3, riskCard4, riskCard5;

  /**
   * Method to initialize values for continent, country and player
   */
  public void setUpDummyGame() {
    continent = new Continent("Continent1", 3);
    Map.addContinent(continent);

    country1 = new Country("Country1", continent, null, 0, 0);
    Map.addCountry(country1);
    riskCard1 = new RiskCard(GameInterface.RISK_CARD_NO_OF_SOLDIER_ON_CARD_ARRAY[0], country1,
        GameInterface.RISK_CARD_NAMES[0]);
    continent.addCountryToContinent(country1);

    country2 = new Country("Country2", continent, null, 0, 0);
    Map.addCountry(country2);
    riskCard2 = new RiskCard(GameInterface.RISK_CARD_NO_OF_SOLDIER_ON_CARD_ARRAY[1], country2,
        GameInterface.RISK_CARD_NAMES[1]);
    continent.addCountryToContinent(country2);

    country3 = new Country("Country3", continent, null, 0, 0);
    Map.addCountry(country3);
    riskCard3 = new RiskCard(GameInterface.RISK_CARD_NO_OF_SOLDIER_ON_CARD_ARRAY[2], country3,
        GameInterface.RISK_CARD_NAMES[2]);
    continent.addCountryToContinent(country3);

    country4 = new Country("Country4", continent, null, 0, 0);
    Map.addCountry(country4);
    riskCard4 = new RiskCard(GameInterface.RISK_CARD_NO_OF_SOLDIER_ON_CARD_ARRAY[0], country4,
        GameInterface.RISK_CARD_NAMES[0]);
    continent.addCountryToContinent(country4);

    country5 = new Country("Country5", continent, null, 0, 0);
    Map.addCountry(country5);
    riskCard5 = new RiskCard(GameInterface.RISK_CARD_NO_OF_SOLDIER_ON_CARD_ARRAY[1], country5,
        GameInterface.RISK_CARD_NAMES[1]);
    continent.addCountryToContinent(country5);

    player1 = new Player("Player1", Color.BLUE, "Blue", 20);
    country1.setPlayer(player1);
    country2.setPlayer(player1);
    player1.addRiskCard(riskCard1);
    player1.addRiskCard(riskCard2);
    Map.addPlayers(player1);

    player2 = new Player("Player2", Color.RED, "Red", 20);
    country3.setPlayer(player2);
    country4.setPlayer(player2);
    country5.setPlayer(player2);
    player2.addRiskCard(riskCard3);
    player2.addRiskCard(riskCard4);
    player2.addRiskCard(riskCard5);
    Map.addPlayers(player2);
  }

  /**
   * Test Case 2: Calculation of reinforcement units by country/3 and Player with Turn
   */
  @Test
  public void calculateReinforcementUnitsTest1() {
    setUpDummyGame();
    player1.setTurn(true);
    GameActionUI.getGameActionView();
    player1.calculateReinforcementUnits();
    int expected1 = 20;
    int result1 = Map.playerWithTurn().getNoOfUnitUnassigned();
    assertEquals(expected1, result1);
  }

  /**
   * Test Case 3: Calculation of reinforcement units by country/3 and Player with Turn
   */
  @Test
  public void calculateReinforcementUnitsTest2() {
    setUpDummyGame();
    Map.getInstance().playerTurnChange();
    GameActionUI.getGameActionView();
    Map.playerWithTurn().calculateReinforcementUnits();
    int expected2 = 20;
    int result2 = Map.playerWithTurn().getNoOfUnitUnassigned();
    // System.out.println(Map.playerWithTurn().getName() + ": in junit " +
    // Map.playerWithTurn().getNoOfUnitUnassigned());
    assertEquals(expected2, result2);
  }

  /**
   * Test Case 4: Calculation of reinforcement unit for Matching Risk Cards
   */
  @Test
  public void calculateMatchingRiskCardTest() {
    setUpDummyGame();
    player1.setTurn(true);
    assertFalse(player1.calculateMatchingRiskCard());
  }

  /**
   * Test Case 5: Calculate of reinforcement unit for One Of Each
   */
  @Test
  public void calculateOneOfEachRiskCardTest() {
    setUpDummyGame();
    player2.setTurn(true);
    assertTrue(player2.calculateOneOfEachCard());
  }


  /**
   * Test Case 1: This test case checks for Adding number of unit/army alive.
   */
  @Test
  public void addNoOfArmyAliveTest() {
    player.addNoOfArmyAlive();
    int expectedValue = 1;
    int result = player.getNoOfUnitAlive();
    assertEquals(expectedValue, result);
    expectedValue = 22;
    result = player.getNoOfUnitUnassigned();
    assertEquals(expectedValue, result);
  }

  /**
   * Test for Continent Control Value Point
   */
  @Test
  public void getControlValuePointTest1() {
    Continent continent = new Continent("Dummy", 6);
    Map.addContinent(continent);
    Country country1 = new Country("Country1", continent, null, 0, 0);
    Map.addCountry(country1);
    Country country2 = new Country("Country2", continent, null, 0, 0);
    Map.addCountry(country2);
    Country country3 = new Country("Country3", continent, null, 0, 0);
    Map.addCountry(country3);
    country1.setPlayer(player);
    country2.setPlayer(player);
    country3.setPlayer(player);
    assertEquals(6, player.getControlValuePoint());
    System.out.println(" test 1 ends");
  }

  /**
   * Test for Continent Control Value Point
   */
  @Test
  public void getControlValuePointTest2() {
    Map.setContinents(new ArrayList<>());
    Continent continent1 = new Continent("Continent1", 6);
    Continent continent2 = new Continent("Continent2", 4);
    Map.addContinent(continent1);
    Map.addContinent(continent2);
    Country country1 = new Country("Country1", continent1, null, 0, 0);
    Map.addCountry(country1);
    Country country2 = new Country("Country2", continent1, null, 0, 0);
    Map.addCountry(country2);
    Country country3 = new Country("Country3", continent1, null, 0, 0);
    Map.addCountry(country3);
    Country country4 = new Country("Country4", continent2, null, 0, 0);
    Map.addCountry(country4);
    Country country5 = new Country("Country5", continent2, null, 0, 0);
    Map.addCountry(country5);
    country1.setPlayer(player);
    country2.setPlayer(player);
    country3.setPlayer(player);
    country4.setPlayer(player);
    country5.setPlayer(player);
    assertEquals(10, player.getControlValuePoint());
  }

  /**
   * Attacker Validation
   */
  @Test
  public void attackerValidationTest() {
    Map.setPlayers(new ArrayList<>());
    Player playerA = new Player("Player A", null, null, 5);
    Map.addPlayers(playerA);
    Player playerB = new Player("Player B", null, null, 5);
    Map.addPlayers(playerB);
    playerA.setAttacker(true);
    playerB.setAttacker(true);
    assertFalse(playerA.isAttacker());
    assertTrue(playerB.isAttacker());
  }

  /**
   * Defender Validation
   */
  @Test
  public void defenderValidationTest() {
    Map.setPlayers(new ArrayList<>());
    Player playerA = new Player("Player A", null, null, 5);
    Map.addPlayers(playerA);
    Player playerB = new Player("Player B", null, null, 5);
    Map.addPlayers(playerB);
    playerA.setDefender(true);
    playerB.setDefender(true);
    assertFalse(playerA.isDefender());
    assertTrue(playerB.isDefender());
  }

  /**
   * Checking for an eligible player
   */
  @Test
  public void isPlayerEligibleToAttackTest() {
    Player playerA = new Player("PlayerA", null, null, 3);
    Country country1 = new Country(null, null, null, 0, 0);
    country1.setSoilders(1);
    Country country2 = new Country(null, null, null, 0, 0);
    country2.setSoilders(1);
    Country country3 = new Country(null, null, null, 0, 0);
    country3.setSoilders(1);
    playerA.addConqueredCountry(country1);
    playerA.addConqueredCountry(country2);
    playerA.addConqueredCountry(country3);
    assertFalse(playerA.isPlayerEligibleToAttack());
  }

  /**
   * Checking for soldier fortification
   */
  @Test
  public void soldierFortifyTest1() {
    Map.setCountries(new ArrayList<>());
    Country country1 = new Country("Country 1", null, null, 0, 0);
    country1.setSoilders(4);
    Map.addCountry(country1);
    Country country2 = new Country("Country 2", null, null, 0, 0);
    country2.setSoilders(1);
    Map.addCountry(country2);
    Player player1 = new Player("Player1", null, null, 5);
    country1.setPlayer(player1);
    country2.setPlayer(player1);
    player1.soldierFortify(country1.getCountryName(), country2.getCountryName(), 2);
    assertEquals(2, country1.getSoilders());
  }

  /**
   * Checking for soldier fortification
   */
  @Test
  public void soldierFortifyTest2() {
    Map.setCountries(new ArrayList<>());
    Country country1 = new Country("Country 1", null, null, 0, 0);
    country1.setSoilders(4);
    Map.addCountry(country1);
    Country country2 = new Country("Country 2", null, null, 0, 0);
    country2.setSoilders(1);
    Map.addCountry(country2);
    Player player1 = new Player("Player1", null, null, 5);
    country1.setPlayer(player1);
    country2.setPlayer(player1);
    player1.soldierFortify(country1.getCountryName(), country2.getCountryName(), 2);
    assertEquals(3, country2.getSoilders());
  }

  /**
   * checking for correct game phase test
   */
  @Test
  public void correctStartGamePhaseTest() {
    Player player1 = new Player("Player1", null, null, 0);
    player1.setGamePlayStage(Map.gamePlayStage[0]);
    assertEquals(Map.gamePlayStage[0], player1.getGamePlayStage());
  }
}
