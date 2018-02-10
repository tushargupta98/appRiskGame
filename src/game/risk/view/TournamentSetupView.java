package game.risk.view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import game.risk.model.valueobjects.AggressivePlayer;
import game.risk.model.valueobjects.BenevolentPlayer;
import game.risk.model.valueobjects.CheaterPlayer;
import game.risk.model.valueobjects.Map;
import game.risk.model.valueobjects.RandomPlayer;
import game.risk.model.valueobjects.Tournament;
import game.risk.utils.GameInterface;

/**
 * Created by TUSHAR on 11/19/2017.
 */
public class TournamentSetupView implements GameInterface {

  public static void tournamentGameViewStart() throws IOException {
    selectMap();
    selectPlayer();
    selectNoOfGames();
    selectNoOfTurns();
  }

  public static void selectMap() throws IOException {
    System.out.println("Enter Total Number of Map (between 1 and 5):");
    Scanner k = new Scanner(System.in);
    int noOfMap = 0;
    do {
      noOfMap = k.nextInt();
      if (noOfMap < 1 && noOfMap > 5)
        System.out.println("You have to give the number between 1 to 5");
    } while (noOfMap < 1 && noOfMap > 5);
    for (int p = 0; p < noOfMap; p++) {
      //
      System.out.println();
      System.out.println();
      File mapDirectory = new File(MAP_DIRECTORY);
      File[] fileList = mapDirectory.listFiles();
      ArrayList<File> mapFile = new ArrayList<>();
      System.out.println("Please Choose which Map you want to play on:");
      for (int i = 0; i < fileList.length; i++) {
        if (!".DS_Store".equals(fileList[i].getName())) {
          mapFile.add(fileList[i]);
        }
      }
      for (int i = 0; i < mapFile.size(); i++) {
        System.out.println("  " + (i + 1) + ") Press " + (i + 1) + " to play in "
            + mapFile.get(i).getName() + ".");
      }
      // Scanner kd = new Scanner(System.in);

      int mapSelect = k.nextInt();
      if (mapSelect - 1 < mapFile.size()
          && GameMapSetupView.isMapFileExist(mapFile.get(mapSelect - 1).getAbsolutePath(),
              mapFile.get(mapSelect - 1).getName())
          && GameMapSetupView
              .verifyMapFile(GameMapSetupView.loadMap(mapFile.get(mapSelect - 1).getAbsolutePath()
                  + "/" + mapFile.get(mapSelect - 1).getName() + ".map"))) {
        // Map.setFilePath(mapFile.get(mapSelect - 1).getAbsolutePath() + "/");
        Tournament.addMapFilePath(mapFile.get(mapSelect - 1).getAbsolutePath() + "/");
        // Map.setMapName(mapFile.get(mapSelect - 1).getName());
        Tournament.addMapNames(mapFile.get(mapSelect - 1).getName());
      } else {
        System.out.println("You have given a wrong integer or your Map file is not Valid!");
        // openExistingMap();
      }

      //
    }
  }


  public static void selectPlayer() {
    System.out.println("Enter Total Number of Players (between 2 and 4)");
    Scanner k = new Scanner(System.in);
    int noOfSoldier = 0;
    do {
      noOfSoldier = k.nextInt();
      if (noOfSoldier < 2 && noOfSoldier > 4)
        System.out.println("You have to give the number between 2 to 4");
    } while (noOfSoldier < 2 && noOfSoldier > 4);
    System.out.println("1. Aggressive Player");
    System.out.println("2. Benevolent Player");
    System.out.println("3. Random Player");
    System.out.println("4. Cheater Player");
    System.out.println("Choose The player Type From the List Above:\n");
    for (int i = 0; i < noOfSoldier; i++) {
      System.out.println("Select Next Player:");
      int select = k.nextInt();
      if (select == 1)
        Map.addPlayerStrategy(new AggressivePlayer(23));
      if (select == 2)
        Map.addPlayerStrategy(new BenevolentPlayer(23));
      if (select == 3)
        Map.addPlayerStrategy(new RandomPlayer(23));
      if (select == 4)
        Map.addPlayerStrategy(new CheaterPlayer(23));
    }
    System.out.println();
  }

  public static void selectNoOfGames() {
    System.out.println("Enter Total Number of Game (between 1 and 5):");
    Scanner k = new Scanner(System.in);
    String line;
    int turn = 0;
    do {
      line = k.next();
      turn = Integer.parseInt(line);
      if (turn < 1 && turn > 5)
        System.out.println("You have to give the number between 1 to 5");
    } while (turn < 1 && turn > 5);
    Tournament.setnumOfGames(turn);
  }

  public static void selectNoOfTurns() {
    System.out.println("Enter Total Number of Turns (between 10 and 50):");
    Scanner k = new Scanner(System.in);
    String line;
    int turn = 0;
    do {
      line = k.next();
      turn = Integer.parseInt(line);
      if (turn < 10 && turn > 50)
        System.out.println("You have to give the turn between 10 to 50");
    } while (turn < 10 && turn > 50);
    Tournament.setNumOfTurns(turn);
  }



}
