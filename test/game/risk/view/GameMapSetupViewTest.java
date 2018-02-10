package game.risk.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class created to check all the map validations
 */
public class GameMapSetupViewTest {

  ArrayList<String> map1;
  ArrayList<String> map2;
  ArrayList<String> map3;
  ArrayList<String> map5;
  ArrayList<String> map6;
  ArrayList<String> map7;
  ArrayList<String> map8;
  ArrayList<String> map9;
  ArrayList<String> map10;
  ArrayList<String> map11;

  /**
   * method loading all the .map files
   * 
   * @throws IOException
   */
  @Before
  public void setUpBeforeClass() throws IOException {
    map1 = GameMapSetupView.loadMap("Resources/Maps/Africa/Africa.map");
    map2 = GameMapSetupView.loadMap("Resources/Maps/InvalidMap2/invalidmap2.map");
    map3 = GameMapSetupView.loadMap("Resources/Maps/InvalidMap3/invalidmap3.map");
    map5 = GameMapSetupView.loadMap("Resources/Maps/InvalidMap5/invalidmap5.map");
    map6 = GameMapSetupView.loadMap("Resources/Maps/InvalidMap6/invalidmap6.map");
    map7 = GameMapSetupView.loadMap("Resources/Maps/InvalidMap7/invalidmap7.map");
    map8 = GameMapSetupView.loadMap("Resources/Maps/Africa2/Africa2.map");
    map9 = GameMapSetupView.loadMap("Resources/Maps/Twin Volcano/Twin Volcano.map");
    map10 = GameMapSetupView.loadMap("Resources/Maps/World/World.map");
    map11 = GameMapSetupView.loadMap("Resources/Maps/3D Cliff/3D Cliff.map");
  }

  /**
   * Test Case 6: Checking for Valid Map Folder
   */
  @Test
  public void isMapFileExistTest1() {
    boolean expected = true;
    boolean actual = GameMapSetupView.isMapFileExist("Resources/Maps/Africa", "Africa");
    assertEquals(expected, actual);
  }

  /**
   * Test Case 7: Checking for Invalid Map Folder
   */
  @Test
  public void isMapFileExistTest2() {
    boolean expected = false;
    boolean actual = GameMapSetupView.isMapFileExist("Resources/Maps/nothing", "nothing");
    assertEquals(expected, actual);
  }

  /**
   * Test Case 8: Verification of Valid Map Scenario
   */
  @Test
  public void verifyMapFileTest1() {
    assertTrue(GameMapSetupView.verifyMapFile(map1));
  }

  /**
   * Test Case 9: Verification of Invalid Map Scenario
   */
  @Test
  public void verifyMapFileTest2() {
    assertFalse(GameMapSetupView.verifyMapFile(map2));
  }

  /**
   * Test Case 10: Verification of Invalid Map Scenario
   */
  @Test
  public void verifyMapFileTest3() {
    assertFalse(GameMapSetupView.verifyMapFile(map3));
  }

  /**
   * Test Case 11: Verification of Invalid Map Scenario
   */
  @Test
  public void verifyMapFileTest5() {
    assertFalse(GameMapSetupView.verifyMapFile(map5));
  }

  /**
   * Test Case 12: Verification of Invalid Map Scenario
   */
  @Test
  public void verifyMapFileTest6() {
    assertFalse(GameMapSetupView.verifyMapFile(map6));
  }

  /**
   * Test Case 13: Verification of Invalid Map Scenario
   */
  @Test
  public void verifyMapFileTest7() {
    assertFalse(GameMapSetupView.verifyMapFile(map7));
  }

  /**
   * Test Case 14: Verification of Invalid Map Scenario for unconnected map
   */
  @Test
  public void verifyMapFileTest8() {
    assertFalse(GameMapSetupView.verifyMapConnected(map8));
  }

  /**
   * Test Case 15: Verification of Invalid Map Scenario for TwinVolcano.map
   */
  @Test
  public void verifyMapFileTest9() {
    assertFalse(GameMapSetupView.verifyMapFile(map9));
  }

  /**
   * Test Case 16: Verification of CheckingContinentsConnectivity(World.map)
   */
  @Test
  public void verifyMapFileTest10() {
    assertTrue(GameMapSetupView.verifyMapFile(map10));
  }

  /**
   * Test Case 17: Verification of Successful loading of 3dCliff.map
   */
  @Test
  public void verifyMapFileTest11() {
    assertFalse(GameMapSetupView.verifyMapFile(map11));
  }
}
