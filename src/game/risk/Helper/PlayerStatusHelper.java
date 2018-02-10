package game.risk.Helper;

import game.risk.model.valueobjects.Map;
import game.risk.model.valueobjects.Player;

/**
 * This is the main Game Controller class.
 * 
 * @author Tushar Gupta
 * @version 1.0.0
 * @since 12-October-2017
 */
public class PlayerStatusHelper {

  /**
   * Erases attacker status of any player
   */
  public static void eraseDefender() {
    for (int j = 0; j < Map.getPlayerList().size(); j++) {
      Map.getPlayerList().get(j).setDefender(false);
    }
  }

  /**
   * Erases defender status of any player
   */
  public static void eraseAttacker() {
    for (int j = 0; j < Map.getPlayerList().size(); j++) {
      Map.getPlayerList().get(j).setAttacker(false);
    }
  }

  /**
   * Checks if a Player is defeated and sets it defeated.
   * 
   * @param player
   * @return
   */
  public static boolean isPlayerDefeated(Player player) {
    if (player.getListOfPlayersConqueredCountries().size() < 1)
      player.setDefeated();

    return player.isDefeated();
  }

}
