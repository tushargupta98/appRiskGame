package game.risk.Helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * This is a helper class for Dice Roll
 * 
 * @author chowdhuryfarsadaurangzeb
 *
 */
public class DiceHelper {

  /**
   * Compares the Dice rolls of Attacker and Defender and declares is Attacker is the winner.
   * 
   * @param attackerDiceRolls
   * @param defenderDiceRolls
   * @return
   */
  public static ArrayList<Boolean> diceRollCompare(ArrayList<Integer> attackerDiceRolls,
      ArrayList<Integer> defenderDiceRolls) {
    ArrayList<Boolean> isWinner = new ArrayList<>();
    Collections.sort(attackerDiceRolls, Collections.reverseOrder());
    Collections.sort(defenderDiceRolls, Collections.reverseOrder());
    if (attackerDiceRolls.get(0) > defenderDiceRolls.get(0)) {
      isWinner.add(true);
    } else {
      isWinner.add(false);
    }
    if (attackerDiceRolls.size() > 1 && defenderDiceRolls.size() > 1) {
      if (attackerDiceRolls.get(1) > defenderDiceRolls.get(1))
        isWinner.add(true);
      else
        isWinner.add(false);
    }
    return isWinner;
  }

  /**
   * Rolls Dice and gives out random value between 1 - 6
   * 
   * @return
   */
  public static int diceRoll() {
    return new Random().nextInt(6) + 1;
  }

}
