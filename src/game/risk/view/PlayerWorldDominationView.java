package game.risk.view;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import game.risk.model.valueobjects.Map;

/**
 * 
 * @author chowdhuryfarsadaurangzeb
 *
 */
public class PlayerWorldDominationView implements Observer {

  private static JLabel playerWorldDominationViewLabel = new JLabel();
  private static ArrayList<JLabel> playerNamesWithPerc = new ArrayList<>();

  private PlayerWorldDominationView() {

  }

  /**
   * Gets Player World Domination View Label
   * 
   * @return
   */
  public static JLabel getPLayerWorldDominationViewLabel() {
    playerWorldDominationViewLabel.removeAll();
    playerWorldDominationViewLabel = null;
    playerWorldDominationViewLabel = new JLabel();
    playerWorldDominationViewLabel.setBorder(BorderFactory.createTitledBorder(null,
        "Player World Domination View", TitledBorder.DEFAULT_JUSTIFICATION,
        TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.BOLD, 12), Color.BLACK));

    // Player Names and their Percentage of World Domination
    for (int i = 0; i < Map.getPlayerList().size(); i++) {
      playerNamesWithPerc.add(new JLabel(Map.getPlayerList().get(i).getName() + ": "
          + (100 * Map.getPlayerList().get(i).getListOfPlayersConqueredCountries().size())
              / Map.getCountryList().size()
          + "%"));
    }
    int x = 10;
    int y = 12;
    int w = 550 / 5 - 20;
    int h = 40;
    for (int i = 0; i < playerNamesWithPerc.size(); i++) {
      playerNamesWithPerc.get(i).setBounds(x, y, w, h);
      x = x + w + 10;
      playerWorldDominationViewLabel.add(playerNamesWithPerc.get(i));
    }
    return playerWorldDominationViewLabel;
  }

  /**
   * Making PlayerWorldDominationView Singleton
   * 
   * @return
   */
  public static PlayerWorldDominationView getIntance() {
    return new PlayerWorldDominationView();
  }

  /**
   * Update method for Observer
   */
  @Override
  public void update(Observable o, Object arg) {
    for (int i = 0; i < Map.getPlayerList().size(); i++) {
      playerNamesWithPerc.get(i)
          .setText(Map.getPlayerList().get(i).getName() + ": "
              + (100 * Map.getPlayerList().get(i).getListOfPlayersConqueredCountries().size())
                  / Map.getCountryList().size()
              + "%");
    }
  }

}
