package game.risk.utils;

import java.awt.Color;

/**
 * This Interface contains all the final values used by the Game GUIs.
 * 
 * @author Tushar Gupta
 * @version 1.0.0
 * @since 12-October-2017
 *
 */
public interface GameInterface {

  /**
   * Name of the Game displayed on Frame.
   */
  public final static String FRAME_TITLE = "Risk Game Application";

  /**
   * Frame width
   */
  public final static int FRAME_WIDTH = 1200;

  /**
   * Frame Height
   */
  public final static int FRAME_HEIGHT = 700;

  /**
   * Label width
   */
  public final static int LABEL_WIDTH = 252;

  /**
   * Label height
   */
  public final static int LABEL_HEIGHT = 52;

  /**
   * Label Gap
   */
  public final static int LABEL_GAP = 20;

  /**
   * Map Directory
   */
  public final static String MAP_DIRECTORY = "Resources/Maps";

  /**
   * Default image directory
   */
  public final static String DEFAULT_IMAGE_FILE_PATH = "Resources/Image/default.bmp";

  /**
   * Maximum number of players can play
   */
  public final static int MAX_NO_OF_PLAYERS = 5;

  /**
   * Minimum number of players can play
   */
  public final static int MIN_NO_OF_PLAYERS = 3;

  /**
   * Total number of units for each player with total 3 players
   */
  public final static int TOTAL_NO_OF_UNIT_FOR_3_PLAYERS_EACH = 35;

  /**
   * Total number of units for each player with total 4 players
   */
  public final static int TOTAL_NO_OF_UNIT_FOR_4_PLAYERS_EACH = 30;

  /**
   * Total number of units for each player with total 5 players
   */
  public final static int TOTAL_NO_OF_UNIT_FOR_5_PLAYERS_EACH = 25;

  /**
   * Players' different colors
   */
  public final static Color[] COLOR_ARRAY =
      {Color.BLUE, Color.RED, Color.GREEN, Color.ORANGE, Color.BLACK};

  /**
   * Players' different color names
   */
  public final static String[] COLOR_NAME_ARRAY = {"Blue", "Red", "Green", "Orange", "Black"};

  /**
   * Different risk card names
   */
  public final static String[] RISK_CARD_NAMES = {"Soldier", "Cavalry", "Canon"};

  /**
   * Numbers in different Risk cards
   */
  public final static int[] RISK_CARD_NO_OF_SOLDIER_ON_CARD_ARRAY = {1, 3, 5};

  /**
   * Dice's Six Sides
   */
  public final static String[] DICE_SIDES =
      {"     *", "     **", "     ***", "     ****", "     *****", "     ******"};

  /**
   * Attacker Won Statement
   */
  public final static String ATTACKER_WON_STATEMENT = "Attacker Won!";

  /**
   * Attacker Lose StatEment
   */
  public final static String ATTACKER_LOSE_STATEMENT = "Attacker Defeated!";

  /**
   * Nobody Won
   */
  public final static String NO_RESULT = "No Result!";

  /**
   * File path for saved files
   */
  public final static String FILE_PATH_SAVED = "Resources/Saved/";
}
