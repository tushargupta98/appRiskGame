package game.risk.model.Helper;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import game.risk.Helper.AttackHelper;
import game.risk.model.valueobjects.Country;
import game.risk.model.valueobjects.Map;
import game.risk.model.valueobjects.Player;
import game.risk.model.valueobjects.RiskCard;

/**
 * Test class covering all the Attack test scenarios
 * 
 */
public class AttackHelperTest {

  Player attackerPlayer;
  Player defenderPlayer;
  Country attacker;
  Country defender;
  RiskCard risk1;
  RiskCard risk2;

  /**
   * Startup method to initialize all the Map objects
   * 
   */
  @Before
  public void startup() {
    Map.setCountries(new ArrayList<>());
    Map.setPlayers(new ArrayList<>());
    Map.setRiskCardsOnStack(new ArrayList<>());
    Map.setRiskCards(new ArrayList<>());
    attackerPlayer = new Player(null, null, null, 5);
    attackerPlayer.setTurn(true);
    attackerPlayer.setRiskCards(new ArrayList<>());
    Map.addPlayers(attackerPlayer);
    defenderPlayer = new Player(null, null, null, 0);
    defenderPlayer.setRiskCards(new ArrayList<>());
    Map.addPlayers(defenderPlayer);
    attacker = new Country("Attacker", null, null, 0, 0);
    Map.addCountry(attacker);
    attacker.setPlayer(attackerPlayer);
    attacker.setSoilders(5);
    defender = new Country("Defender", null, null, 0, 0);
    Map.addCountry(defender);
    defender.setPlayer(defenderPlayer);
    risk1 = new RiskCard(0, null, null);
    risk2 = new RiskCard(0, null, null);
    Map.setRiskCardsOnStack(new ArrayList<>());
    Map.addRiskCardToStack(risk1);
    Map.addRiskCardToStack(risk2);
    AttackHelper.attackWon(attacker, defender, 3);
  }

  /**
   * Defender's all soldier is dead and Attacker's army took over (Attacker attacked by 3 army)
   * 
   */
  @Test
  public void attackWonTest1() {
    assertEquals(3, defender.getSoilders());
  }

  /**
   * Attacker shifts correct amount of soldiers
   * 
   */
  @Test
  public void attackWonTest2() {
    assertEquals(2, attacker.getSoilders());
  }

  /**
   * Gets Risk Card after attack win
   * 
   */
  @Test
  public void attackWonTest3() {
    assertEquals(1, attackerPlayer.getRiskCards().size());
  }

}
