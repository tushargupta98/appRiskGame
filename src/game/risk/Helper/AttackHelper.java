package game.risk.Helper;

import java.util.ArrayList;
import game.risk.model.valueobjects.Country;
import game.risk.model.valueobjects.Map;
import game.risk.model.valueobjects.Player;
import game.risk.utils.GameInterface;

/**
 * This is the main Game Controller class.
 * 
 * @author Tushar Gupta
 * @version 1.0.0
 * @since 12-October-2017
 */
public class AttackHelper implements GameInterface {

  /**
   * Checks if the attacker won or not.
   * 
   * @param isWinner
   * @param attackerCountry
   * @param defenderCountry
   * @return
   */
  public static String isAttackerWon(ArrayList<Boolean> isWinner, String attackerCountry,
      String defenderCountry) {
    Country attacker = Map.getCountry(attackerCountry);
    Country defender = Map.getCountry(defenderCountry);
    for (int i = 0; i < isWinner.size(); i++) {
      if (isWinner.get(i)) {
        System.out.print("Attacker: " + attackerCountry + " killed one army of Defender: "
            + defenderCountry + " prev soldier " + defender.getSoilders());
        defender.getPlayer().killSoldierAtCountry(defenderCountry);
        System.out.println(" present soldier " + defender.getSoilders());
      } else {
        System.out.print("Defender: " + defenderCountry + " killed one army of Attacker: "
            + attackerCountry + " prev soldier " + attacker.getSoilders());
        attacker.getPlayer().killSoldierAtCountry(attackerCountry);
        System.out.println(" present soldier " + attacker.getSoilders());
      }
    }
    if (defender.getSoilders() < 1)
      return ATTACKER_WON_STATEMENT;
    else
      return NO_RESULT;
  }

  /**
   * Does the work after an attack win
   * 
   * @param attacker
   * @param defender
   * @param noOfSoldier
   * @return defeated Player
   */
  public static Player attackWon(Country attacker, Country defender, int noOfSoldier) {
    attacker.moveOutSoldier(noOfSoldier);
    if (Map.getRiskCardsOnStack().size() > 0) {
      attacker.getPlayer()
          .addRiskCard(Map.getRiskCardsOnStack().get(Map.getRiskCardsOnStack().size() - 1));
      Map.getRiskCardsOnStack().remove(Map.getRiskCardsOnStack().size() - 1);
    }
    defender.setSoilders(0);
    defender.moveInSoldier(noOfSoldier);
    Player defeatedPlayer = defender.getPlayer();
    defeatedPlayer.removedLostCountry(defender);
    defender.setPlayer(Map.playerWithTurn());
    Map.playerWithTurn().addConqueredCountry(defender);
    return defeatedPlayer;
  }

}
