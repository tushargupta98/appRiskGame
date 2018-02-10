package game.risk.Helper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import game.risk.controller.GameController;
import game.risk.model.valueobjects.Map;

/**
 * This is the main Game Controller class.
 * 
 * @author Tushar Gupta
 * @version 1.0.0
 * @since 12-October-2017
 */
public class MapCreateHelper {

  /**
   * Create file and write in file.
   * 
   * @param filePath
   * @param map
   */
  public static void createFileAndWriteInFile(String filePath, ArrayList<String> map) {
    File newMapFile = new File(filePath);
    try {
      newMapFile.createNewFile();
      FileWriter fileWriter = new FileWriter(newMapFile);
      String mapLines = "";
      for (int i = 0; i < map.size(); i++) {
        mapLines = mapLines + map.get(i) + "\n";
      }
      fileWriter.write(mapLines);
      fileWriter.flush();
      fileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Delete and create file and write in file.
   * 
   * @param filePath
   * @param editedMapList
   */
  public static void deleteAndCreateFileAndWriteInFile(String filePath,
      ArrayList<String> editedMapList) {
    File oldMapFile = new File(filePath);
    oldMapFile.delete();
    createFileAndWriteInFile(filePath, editedMapList);
  }

  /**
   * Map components to List
   * 
   * @return
   */
  public static ArrayList<String> mapComponentToList() {
    ArrayList<String> map = new ArrayList<>();
    map.add("[Map]");
    map.add("author=Farsad, Karthik, Tushar and Ziba");
    map.add("warn=yes");
    map.add("image=" + Map.getMapName() + ".bmp");
    map.add("wrap=no");
    map.add("scroll=horizontal");
    map.add("");

    // Adding Continents
    map.add("[Continents]");
    for (int i = 0; i < Map.getContinentList().size(); i++) {
      String line = Map.getContinentList().get(i).getContinentName() + "="
          + Map.getContinentList().get(i).getControlValue();
      map.add(line);
    }

    map.add("");

    // Adding Territories
    map.add("[Territories]");
    for (int i = 0; i < Map.getCountryList().size(); i++) {
      int[] coor = Map.getCountryList().get(i).getCoordinate();
      String line = Map.getCountryList().get(i).getCountryName() + "," + coor[0] + "," + coor[1]
          + "," + Map.getCountryList().get(i).getContinent().getContinentName();
      for (int j = 0; j < Map.getCountryList().get(i).getNeighbooringCountries().size(); j++) {
        line = line + ","
            + Map.getCountryList().get(i).getNeighbooringCountries().get(j).getCountryName();
      }
      map.add(line);
    }

    map.add("");

    // Add Player if in Save Mode
    if (GameController.isSaveMode()) {
      map.add("[Player]");
      for (int i = 0; i < Map.getPlayerList().size(); i++) {
        // Adding all the countries of the player in one line.
        String countries = "";
        for (int j = 0; j < Map.getPlayerList().get(i).getListOfPlayersConqueredCountries()
            .size(); j++) {
          countries = countries + ","
              + Map.getPlayerList().get(i).getListOfPlayersConqueredCountries().get(j)
                  .getCountryName()
              + "-" + Map.getPlayerList().get(i).getListOfPlayersConqueredCountries().get(j)
                  .getSoilders();
        }
        // We have to do it in one line.
        map.add(
            Map.getPlayerList().get(i).getName() + "," + Map.getPlayerList().get(i).getColorName()
                + "," + Map.getPlayerList().get(i).getNoOfUnitAlive() + countries);
      }
    }

    return map;
  }

}
