package game.risk.view;

import java.awt.Color;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import game.risk.model.valueobjects.Map;
import game.risk.utils.GameInterface;

/**
 * 
 * @author chowdhuryfarsadaurangzeb
 *
 */
public class PhaseView implements GameInterface, Observer {

  private static JLabel phaseViewLabel = new JLabel();
  private static JLabel playersTurnInPhaseLabel = new JLabel("");
  private static JLabel playerGamePhaseLabel = new JLabel("");

  private PhaseView() {

  }

  /**
   * Adding Phase View Label
   */
  public static JLabel getPhaseViewLabel() {
    phaseViewLabel.removeAll();
    phaseViewLabel = null;
    phaseViewLabel = new JLabel();
    phaseViewLabel.setBorder(
        BorderFactory.createTitledBorder(null, "Phase View", TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.BOLD, 12), Color.BLACK));
    // Player Name
    playersTurnInPhaseLabel = new JLabel(
        "Player " + Map.playerWithTurn().getName() + ": " + Map.playerWithTurn().getColorName());
    // playersTurnInPhaseLabel.setBorder(new TitledBorder("Player's Turn"));
    playersTurnInPhaseLabel.setBounds(LABEL_GAP, LABEL_GAP - 10, LABEL_WIDTH, LABEL_HEIGHT);
    // Player Game Phase
    playerGamePhaseLabel = new JLabel(Map.playerWithTurn().getGamePlayStage());
    playerGamePhaseLabel.setBounds(
        playersTurnInPhaseLabel.getX() + playersTurnInPhaseLabel.getWidth() + LABEL_GAP,
        playersTurnInPhaseLabel.getY(), playersTurnInPhaseLabel.getWidth(),
        playersTurnInPhaseLabel.getHeight());
    // Adding all Components
    phaseViewLabel.add(playersTurnInPhaseLabel);
    phaseViewLabel.add(playerGamePhaseLabel);
    return phaseViewLabel;
  }

  /**
   * Update method for Observer
   */
  @Override
  public void update(Observable o, Object arg) {
    playerGamePhaseLabel.setText(Map.playerWithTurn().getGamePlayStage());
    playersTurnInPhaseLabel.setText(
        "Player " + Map.playerWithTurn().getName() + ": " + Map.playerWithTurn().getColorName());
  }

  /**
   * Making Phase View Singleton
   * 
   * @return
   */
  public static PhaseView getInstance() {
    return new PhaseView();
  }

}
