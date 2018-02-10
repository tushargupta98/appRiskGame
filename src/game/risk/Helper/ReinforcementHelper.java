package game.risk.Helper;

import java.util.ArrayList;
import game.risk.model.valueobjects.Map;
import game.risk.model.valueobjects.RiskCard;
import game.risk.utils.GameInterface;

/**
 * This is the main Game Controller class.
 * 
 * @author Tushar Gupta
 * @version 1.0.0
 * @since 12-October-2017
 */
public class ReinforcementHelper implements GameInterface {

  /**
   * Turn in matching card action
   */
  public static void turnInMatchingCardAction() {
    boolean isContinue = true;
    for (int i = 0; i < RISK_CARD_NAMES.length && isContinue; i++) {
      int count = 0;
      ArrayList<RiskCard> cardHolder = new ArrayList<>();
      ArrayList<Integer> cardIndexHolder = new ArrayList<>();
      for (int j = 0; j < Map.playerWithTurn().getRiskCards().size(); j++) {
        if (Map.playerWithTurn().getRiskCards().get(j).getCardName().equals(RISK_CARD_NAMES[i])) {
          cardHolder.add(Map.playerWithTurn().getRiskCards().get(j));
          cardIndexHolder.add(j);
          count++;
          if (count == 3) {
            for (int k = 0; k < cardHolder.size(); k++) {
              Map.playerWithTurn().getRiskCards().remove(cardHolder.get(k));
              Map.addRiskCardToStack(cardHolder.get(k));
              isContinue = false;
            }
            break;
          }
        }
      }
    }
    int newArmyNo = Map.playerWithTurn().getRiskCardTurnCount() * 5;
    Map.playerWithTurn().addUnassignedArmy(newArmyNo);
    Map.playerWithTurn().incrementRiskCardTurnCount();
  }

  /**
   * Turn in one of each card action
   */
  public static void turnInOneOfEachCardAction() {
    for (int i = 0; i < RISK_CARD_NAMES.length; i++) {
      for (int j = 0; j < Map.playerWithTurn().getRiskCards().size(); j++) {
        if (Map.playerWithTurn().getRiskCards().get(j).getCardName().equals(RISK_CARD_NAMES[i])) {
          Map.addRiskCardToStack(Map.playerWithTurn().getRiskCards().get(j));
          Map.playerWithTurn().getRiskCards().remove(j);
          break;
        }
      }
    }
    int newArmyNo = Map.playerWithTurn().getRiskCardTurnCount() * 5;
    Map.playerWithTurn().addUnassignedArmy(newArmyNo);
    Map.playerWithTurn().incrementRiskCardTurnCount();
  }
}
