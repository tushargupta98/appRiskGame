package game.risk.model.Helper;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import game.risk.Helper.GamePhaseHelper;
import game.risk.model.valueobjects.Country;
import game.risk.model.valueobjects.Map;
import game.risk.model.valueobjects.Player;

/**
 * Test class covering all the Game Phase test scenarios
 * 
 */
public class GamePhaseHelperTest {

  Country country1;
  Country country2;

  Player player1;
  Player player2;

  /**
   * Setup method to initialize all the Map and player objects
   */
  @Before
  public void setUp() {
    Map.setCountries(new ArrayList<>());
    Map.setPlayers(new ArrayList<>());
    country1 = new Country(null, null, null, 0, 0);
    country2 = new Country(null, null, null, 0, 0);
    player1 = new Player(null, null, null, 0);
    Map.addPlayers(player1);
    player2 = new Player(null, null, null, 0);
    Map.addPlayers(player2);
    Map.addCountry(country1);
    Map.addCountry(country2);
    country1.setPlayer(player1);
    player1.addConqueredCountry(country1);
    country2.setPlayer(player1);
    player1.addConqueredCountry(country2);
  }

  /**
   * Checks if Game is ended or not.
   */
  @Test
  public void isGameEndedTest() {
    assertTrue(GamePhaseHelper.isGameEnded());
  }

}
