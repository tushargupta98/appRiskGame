package game.risk.model.valueobjects;

import java.util.ArrayList;

/**
 * Created by TUSHAR on 11/19/2017.
 */
public class Tournament {

  private static int numOfGames;
  private static int numOfTurns;
  private static ArrayList<String> mapFilePaths = new ArrayList<>();
  private static ArrayList<String> mapNames = new ArrayList<>();
  private static ArrayList<String> results = new ArrayList<>();


  public static int getnumOfGames() {
    return numOfGames;
  }

  public static void setnumOfGames(int numOfGames) {
    Tournament.numOfGames = numOfGames;
  }

  public static int getnumOfTurns() {
    return numOfTurns;
  }

  public static void setNumOfTurns(int numOfTurns) {
    Tournament.numOfTurns = numOfTurns;
  }

  public static ArrayList<String> getMapFilePaths() {
    return mapFilePaths;
  }

  public static void setMapFilePaths(ArrayList<String> mapFilePaths) {
    Tournament.mapFilePaths = mapFilePaths;
  }

  public static void addMapFilePath(String filePath) {
    mapFilePaths.add(filePath);
  }

  public static ArrayList<String> getMapNames() {
    return mapNames;
  }

  public static void setMapNames(ArrayList<String> mapNames) {
    Tournament.mapNames = mapNames;
  }

  public static void addMapNames(String mapName) {
    mapNames.add(mapName);
  }

  public static ArrayList<String> getResult() {
    return results;
  }

  public static void setResult(ArrayList<String> result) {
    Tournament.results = result;
  }

  public static void addResult(String result) {
    results.add(result);
  }


}
