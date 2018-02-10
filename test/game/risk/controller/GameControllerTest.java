package game.risk.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import org.junit.Test;
import game.risk.view.GameMapSetupView;

public class GameControllerTest {

  /**
   * Test case for Saved Map
   * 
   * @throws IOException
   */
  @Test
  public void setUpSavedGame1Test() throws IOException {
    String filepath = "Resources/Saved/Africa2(Saved)/Africa2(Saved).map";
    GameMapSetupView.loadMap(filepath);
    GameController.setSaveMode(true);
    GameController.createComponents(filepath);
    assertTrue(GameController.isSaveMode());
  }

  /**
   * Test case for New Map
   * 
   * @throws IOException
   */
  @Test
  public void setUpNewGame1Test() throws IOException {
    String filepath = "Resources/Maps/Africa2/Africa2.map";
    GameMapSetupView.loadMap(filepath);
    GameController.setSaveMode(false);
    GameController.createComponents(filepath);
    assertFalse(GameController.isSaveMode());
  }

  /**
   * Test case for Saved Map
   * 
   * @throws IOException
   */
  @Test
  public void setUpSavedGame2Test() throws IOException {
    String filepath = "Resources/Saved/Asia(Saved)/Asia(Saved).map";
    GameMapSetupView.loadMap(filepath);
    GameController.setSaveMode(true);
    GameController.createComponents(filepath);
    assertTrue(GameController.isSaveMode());
  }

  /**
   * Test case for New Map
   * 
   * @throws IOException
   */
  @Test
  public void setUpNewGame2Test() throws IOException {
    String filepath = "Resources/Maps/Asia/Asia.map";
    GameMapSetupView.loadMap(filepath);
    GameController.setSaveMode(false);
    GameController.createComponents(filepath);
    assertFalse(GameController.isSaveMode());
  }

  /**
   * Test case for is in Tournament Mode
   */
  @Test
  public void isTournamentTest() {
    GameController.setTournamentMode(true);
    assertTrue(GameController.isTournamentMode());
  }


}
