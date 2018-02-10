package game.risk.view;

import java.util.Scanner;
import game.risk.controller.GameController;
import game.risk.utils.GameInterface;

/**
 * This class is a Game Map Setup View Class.
 * 
 * @author Chowdhury Farsad Aurangzeb
 * @version 1.0.0
 * @since 12-October-2017
 *
 */
public class GameInitialView implements GameInterface {

  /**
   * This is a Game Start View method which help in initial prompt of the game.
   */
  public static void gameStartView() {
    System.out.println("Welcome to Risk!");
    System.out.println();
    System.out.println();
    System.out.println("Select the Game Mode" + "\n 1) Enter 1 for Single Game Mode."
        + "\n 2) Enter 2 for Tournament Mode.");
    Scanner k = new Scanner(System.in);
    try {
      String numberEntered = "" + k.nextInt();
      switch (numberEntered) {
        case "1":
          // SingleGameSetupView sgView = new SingleGameSetupView();
          GameController.setTournamentMode(false);
          // sgView.initSingleGameMode();
          break;
        case "2":
          // TournamentSetupView tourView = new TournamentSetupView();
          // tourView.initTournamentMode();
          GameController.setTournamentMode(true);
          break;
        default:
          System.out.println("You have given a wrong integer!");
          gameStartView();
          break;
      }
    } catch (Exception e) {
      System.out.println("You have Enter a wrong Value!");
      e.printStackTrace();
    }
  }



}
