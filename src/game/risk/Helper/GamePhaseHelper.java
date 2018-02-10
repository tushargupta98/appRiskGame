package game.risk.Helper;

import game.risk.model.valueobjects.Map;

/**
 * This is the main Game Controller class.
 * 
 * @author Tushar Gupta
 * @version 1.0.0
 * @since 12-October-2017
 */
public class GamePhaseHelper {

  /**
   * Tells if the Game has ended or not.
   * 
   * @return
   */
  public static boolean isGameEnded() {
    for (int i = 0; i < Map.getPlayerList().size(); i++) {
      if (Map.getPlayerList().get(i).getListOfPlayersConqueredCountries().size() == Map
          .getCountryList().size()) {
        Map.gameEnded();
        return Map.isGameEnded();
      }
    }
    return false;
  }

}
