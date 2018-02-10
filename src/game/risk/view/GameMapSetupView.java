package game.risk.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import game.risk.controller.GameController;
import game.risk.model.valueobjects.Map;
import game.risk.utils.GameInterface;

/**
 * This class is a Game Map Setup View Class.
 * 
 * @author Chowdhury Farsad Aurangzeb
 * @version 1.0.0
 * @since 12-October-2017
 *
 */
public class GameMapSetupView implements GameInterface {

  private static JFrame mapEditFrame;
  private static boolean isMapEditFrameOn = false;

  /**
   * This is a Game Start View method which help in initial prompt of the game.
   */
  public static void gameStartView() {
    // System.out.println("Welcome to Risk!");
    System.out.println();
    System.out.println();
    if (!GameController.isTournamentMode()) {
      System.out.println("Do you want to play with Existing Map or Create New Map?"
          + "\n 1) Enter 1 to Play with Existing Map." + "\n 2) Enter 2 to create New Map."
          + "\n 3) Enter 3 to Edit Existing Map" + "\n 4) Enter 4 to play with saved game.");
      Scanner k = new Scanner(System.in);
      try {
        String numberEntered = "" + k.nextInt();
        switch (numberEntered) {
          case "1":
            openExistingMap();
            break;
          case "2":
            createNewMap();
            break;
          case "3":
            editExistingMap();
            break;
          case "4":
            openSavedGame();
            break;
          default:
            System.out.println("You have given a wrong integer!");
            gameStartView();
            break;
        }
      } catch (Exception e) {
        System.out.println("You have Enter a wrong Value!");
        e.printStackTrace();
        System.out.println();
        System.out.println();
        gameStartView();
      }
    } else {
      openExistingMap();
    }
  }

  private static void openExistingMap() {
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
      System.out.println(
          "  " + (i + 1) + ") Press " + (i + 1) + " to play in " + mapFile.get(i).getName() + ".");
    }
    Scanner k = new Scanner(System.in);
    try {
      int mapSelect = k.nextInt();
      if (mapSelect - 1 < mapFile.size()
          && isMapFileExist(mapFile.get(mapSelect - 1).getAbsolutePath(),
              mapFile.get(mapSelect - 1).getName())
          && verifyMapFile(loadMap(mapFile.get(mapSelect - 1).getAbsolutePath() + "/"
              + mapFile.get(mapSelect - 1).getName() + ".map"))) {
        Map.setFilePath(mapFile.get(mapSelect - 1).getAbsolutePath() + "/");
        Map.setMapName(mapFile.get(mapSelect - 1).getName());
      } else {
        System.out.println("You have given a wrong integer or your Map file is not Valid!");
        openExistingMap();
      }
    } catch (Exception e) {
      System.out.println("You have Enter a wrong Value!");
      System.out.println();
      System.out.println();
      openExistingMap();
    }
  }

  private static void createNewMap() {
    System.out.println("Please Enter a Name for the New Map:");
    Scanner k = new Scanner(System.in);
    String mapName = k.next();
    File newMapFolder = new File(MAP_DIRECTORY + "/" + mapName);
    newMapFolder.mkdirs();
    System.out.println("Write you Map below and if you finish writing please write EXIT:");
    ArrayList<String> map = new ArrayList<>();
    Scanner writer = new Scanner(System.in);
    String line = "";
    while (!(line = writer.nextLine()).equals("EXIT")) {
      map.add(line);
    }
    if (verifyMapFile(map)) {
      GameController
          .createFileAndWriteInFile(MAP_DIRECTORY + "/" + mapName + "/" + mapName + ".map", map);
      Map.setFilePath(MAP_DIRECTORY + "/" + mapName + "/");
      Map.setMapName(mapName);
      System.out.println();
      System.out
          .println("Please enter Image URL and if there is no Image file to Upload then write NO:");
      Scanner imageURL = new Scanner(System.in);
      String url = imageURL.next();
      if (!url.equals("NO")) {
        File srcImage = new File(url);
        File newImage = new File(MAP_DIRECTORY + "/" + mapName + "/" + mapName + ".bmp");
        try {
          System.out.println("SRC Image Path: " + srcImage.toPath());
          System.out.println("Saved Image Path: " + newImage.toPath());
          Files.copy(srcImage.toPath(), newImage.toPath());
        } catch (IOException e) {
          e.printStackTrace();
        }
      } else {
        File srcImage = new File(DEFAULT_IMAGE_FILE_PATH);
        File newImage = new File(MAP_DIRECTORY + "/" + mapName + "/" + mapName + ".bmp");
        try {
          System.out.println("SRC Image Path: " + srcImage.toPath());
          System.out.println("Saved Image Path: " + newImage.toPath());
          Files.copy(srcImage.toPath(), newImage.toPath());
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } else {
      System.out.println();
      System.out.println("You have Inserted a Wrong format of Map File.");
      gameStartView();
    }


  }

  private static void editExistingMap() {
    System.out.println();
    System.out.println();
    System.out.println(
        "How do you want to Edit the Map:\n  1) With Text Editor (Game Pro)\n  2) With User Friendly Prompt (Game Dummies)");
    Scanner inputForEditMap = new Scanner(System.in);
    try {
      int input = inputForEditMap.nextInt();
      switch (input) {
        case 1:
          editExistingMapWithTextEditor();
          break;
        case 2:
          editExistingMapWithConsole();
          break;
      }
    } catch (Exception e) {
      System.out.println("You have input a wrong value.");
      openExistingMap();
    }
  }

  private static void openSavedGame() {
    System.out.println();
    System.out.println();
    File mapDirectory = new File("Resources/Saved");
    File[] fileList = mapDirectory.listFiles();
    ArrayList<File> mapFile = new ArrayList<>();
    System.out.println("Please Choose which Map you want to play on:");
    for (int i = 0; i < fileList.length; i++) {
      if (!".DS_Store".equals(fileList[i].getName())) {
        mapFile.add(fileList[i]);
      }
    }
    for (int i = 0; i < mapFile.size(); i++) {
      System.out.println(
          "  " + (i + 1) + ") Press " + (i + 1) + " to play in " + mapFile.get(i).getName() + ".");
    }
    Scanner k = new Scanner(System.in);
    try {
      int mapSelect = k.nextInt();
      Map.setFilePath(mapFile.get(mapSelect - 1).getAbsolutePath() + "/");
      Map.setMapName(mapFile.get(mapSelect - 1).getName());
      // Remove (Saved) from string
      String path = mapFile.get(mapSelect - 1).getName();
      path = path.substring(0, path.length() - 7);
      Map.setImagePath(MAP_DIRECTORY + "/" + path + "/" + path + ".bmp");
      GameController.setSaveMode(true);

    } catch (Exception e) {
      System.out.println("You have Enter a wrong Value!");
      System.out.println();
      System.out.println();
      // openSavedGame();
      System.out.println("Stop");
    }
  }

  private static void editExistingMapWithConsole() {
    System.out.println();
    System.out.println();
    File mapDirectory = new File(MAP_DIRECTORY);
    File[] fileList = mapDirectory.listFiles();
    ArrayList<File> mapFile = new ArrayList<>();
    System.out.println("Please Choose which Map you want to Edit:");
    for (int i = 0; i < fileList.length; i++) {
      if (!".DS_Store".equals(fileList[i].getName())) {
        mapFile.add(fileList[i]);
      }
    }
    for (int i = 0; i < mapFile.size(); i++) {
      System.out.println(
          "  " + (i + 1) + ") Press " + (i + 1) + " to play in " + mapFile.get(i).getName() + ".");
    }
    Scanner k = new Scanner(System.in);
    try {
      int mapSelect = k.nextInt();
      if (mapSelect - 1 < mapFile.size()
          && isMapFileExist(mapFile.get(mapSelect - 1).getAbsolutePath(),
              mapFile.get(mapSelect - 1).getName())
          && verifyMapFile(loadMap(mapFile.get(mapSelect - 1).getAbsolutePath() + "/"
              + mapFile.get(mapSelect - 1).getName() + ".map"))) {
        String filePath = mapFile.get(mapSelect - 1).getAbsolutePath() + "/"
            + mapFile.get(mapSelect - 1).getName() + ".map";
        GameController.createComponents(filePath);
        boolean changeMade = false;
        // 1. Print out All the countries and continents
        System.out.println("List of all the Continents:");
        for (int i = 0; i < Map.getContinentList().size(); i++) {
          System.out.println((i + 1) + ") " + Map.getContinentList().get(i).getContinentName());
        }
        System.out.println();
        System.out.println("List of all the Countries/Territories:");
        for (int i = 0; i < Map.getCountryList().size(); i++) {
          System.out.println((i + 1) + ") " + Map.getCountryList().get(i).getCountryName());
        }
        System.out.println();
        // 2. Prompt Delete <Continent/Country> <Name of Continent/Country>
        System.out.println("To Delete Continent write Delete Continent <Contient Name>");
        System.out.println("To Delete Country write Delete Country <Country Name>");
        System.out.println("If finish type EXIT");
        Scanner deleteScn = new Scanner(System.in);
        String deleteStatement;
        do {
          System.out.println("Command Here:");
          deleteStatement = deleteScn.nextLine();
          if (deleteStatement.length() >= 16) {
            if (deleteStatement.substring(0, 16).equals("Delete Continent")) {
              // 3. if Delete Continent <Name of Continent>
              GameController
                  .deleteContinent(deleteStatement.substring(17, deleteStatement.length()));
              changeMade = true;
            } else if (deleteStatement.substring(0, 14).equals("Delete Country")) {
              // 4. if Delete Country <Name of Country>
              GameController.deleteCountry(deleteStatement.substring(15, deleteStatement.length()));
              changeMade = true;
            }
          }
        } while (!deleteStatement.equals("EXIT"));

        // Write in Text Editor.
        if (changeMade) {
          GameController.deleteAndCreateFileAndWriteInFile(filePath,
              GameController.mapComponentToList());
        }
        Map.setFilePath(MAP_DIRECTORY + "/" + mapFile.get(mapSelect - 1).getName() + "/");
        Map.setMapName(mapFile.get(mapSelect - 1).getName());
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("You have input a wrong value.");
      openExistingMap();
    }
  }

  private static void editExistingMapWithTextEditor() {
    System.out.println();
    System.out.println();
    File mapDirectory = new File(MAP_DIRECTORY);
    File[] fileList = mapDirectory.listFiles();
    ArrayList<File> mapFile = new ArrayList<>();
    System.out.println("Please Choose which Map you want to Edit:");
    for (int i = 0; i < fileList.length; i++) {
      if (!".DS_Store".equals(fileList[i].getName())) {
        mapFile.add(fileList[i]);
      }
    }
    for (int i = 0; i < mapFile.size(); i++) {
      System.out.println(
          "  " + (i + 1) + ") Press " + (i + 1) + " to play in " + mapFile.get(i).getName() + ".");
    }
    Scanner k = new Scanner(System.in);
    try {
      int mapSelect = k.nextInt();
      if (mapSelect - 1 < mapFile.size()
          && isMapFileExist(mapFile.get(mapSelect - 1).getAbsolutePath(),
              mapFile.get(mapSelect - 1).getName())
          && verifyMapFile(loadMap(mapFile.get(mapSelect - 1).getAbsolutePath() + "/"
              + mapFile.get(mapSelect - 1).getName() + ".map"))) {
        String filePath = mapFile.get(mapSelect - 1).getAbsolutePath() + "/"
            + mapFile.get(mapSelect - 1).getName() + ".map";
        // Loading the map in ArrayList
        ArrayList<String> map = loadMap(filePath);
        // Loading it in JTextArea
        String mapLines = "";
        for (int i = 0; i < map.size(); i++) {
          mapLines = mapLines + map.get(i) + "\n";
        }
        JTextArea mapTextArea = new JTextArea(mapLines);
        // Posting in JFrame
        mapEditFrame = new JFrame("Map Edit");
        JScrollPane mapEditScrollPane = new JScrollPane(mapTextArea);
        mapEditScrollPane.setBounds(20, 20, FRAME_WIDTH - 40, FRAME_HEIGHT - 120);
        JButton mapEditSaveButton = new JButton("Save and Exit");
        mapEditSaveButton.addActionListener(new ActionListener() {

          @Override
          public void actionPerformed(ActionEvent e) {
            String s[] = mapTextArea.getText().split("\\r?\\n");
            ArrayList<String> editedMapList = new ArrayList<>(Arrays.asList(s));
            if (verifyMapFile(editedMapList)) {
              GameController.deleteAndCreateFileAndWriteInFile(filePath, editedMapList);
              Map.setFilePath(MAP_DIRECTORY + "/" + mapFile.get(mapSelect - 1).getName() + "/");
              Map.setMapName(mapFile.get(mapSelect - 1).getName());
              GameController.createComponents(Map.getFilePath() + Map.getMapName() + ".map");
              isMapEditFrameOn = true;
              mapEditFrame.dispose();
            } else {
              System.out.println("You have Inserted a Wrong format of Map File.");
              isMapEditFrameOn = false;
              mapEditFrame.dispose();
              gameStartView();
            }
          }
        });
        mapEditSaveButton.setBounds(
            mapEditScrollPane.getX() + mapEditScrollPane.getWidth() / 2 - (150 / 2),
            mapEditScrollPane.getHeight() + 20, 150, 35);
        JPanel mapEditPanel = new JPanel(null);
        mapEditPanel.add(mapEditScrollPane);
        mapEditFrame.add(mapEditSaveButton);
        mapEditFrame.add(mapEditPanel);
        mapEditFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        mapEditFrame.setVisible(true);
        mapEditFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Saving if from JtextArea and save it on file
      } else {
        System.out.println("You have given a wrong integer or your Map file is not Valid!");
        openExistingMap();
      }
    } catch (Exception e) {
      System.out.println("You have Enter a wrong Value!");
      System.out.println();
      System.out.println();
      openExistingMap();
    }
  }

  /**
   * This method loads the Map file
   * 
   * @param filePath takes a path of map file as String
   * @return boolean if the map is loaded or not.
   * @throws IOException
   */
  public static ArrayList<String> loadMap(String filePath) throws IOException {
    ArrayList<String> mapFile = new ArrayList<>();

    BufferedReader br = new BufferedReader(new FileReader(filePath));
    String line = "";
    while ((line = br.readLine()) != null) {
      mapFile.add(line);
    }
    br.close();
    return mapFile;
  }

  public static boolean isMapFileExist(String filePath, String folderName) {
    File mapDirectory = new File(filePath);
    File[] fileList = mapDirectory.listFiles();

    // Checking for .map file
    boolean isMapFileExist = false;
    for (int i = 0; i < fileList.length; i++) {
      String fileName = fileList[i].getName();
      if (fileName.equals(folderName + ".map")) {
        isMapFileExist = true;
        break;
      }
    }
    if (!isMapFileExist)
      return false;

    // Checking for .bmp file
    boolean isBmpFileExist = false;
    for (int i = 0; i < fileList.length; i++) {
      String fileName = fileList[i].getName();
      if (fileName.equals(folderName + ".bmp")) {
        isBmpFileExist = true;
        break;
      }
    }
    if (isBmpFileExist)
      return true;

    return false;
  }

  /**
   * This method verifies a Map file.
   * 
   * @param mapFile takes map data as a arraylist
   * @return boolean if a map is in a correct format or not.
   */
  public static boolean verifyMapFile(ArrayList<String> mapFile) {
    try {
      if (mapFile == null)
        return false;
      if (mapFile.size() < 5) {
        System.out.println("Verification failed in verification 0");
        return false;
      }
      if (!verifyMap1(mapFile))
        return false;
      if (!verifyMap2(mapFile))
        return false;
      if (!verifyMap3(mapFile))
        return false;
      if (!verifyMap5(mapFile))
        return false;
      if (!verifyMap6(mapFile))
        return false;
      if (verifyMapAtleastOneContinentInTerritory(mapFile) && verifyMapConnected(mapFile)) {

        return true;
      } else {
        // System.out.println("Fail");
        return false;
      }
    } catch (Exception e) {
      if (isMapEditFrameOn) {
        isMapEditFrameOn = false;
        mapEditFrame.setVisible(false);
        mapEditFrame.dispose();
        mapEditFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mapEditFrame.setDefaultCloseOperation(JFrame.ABORT);
      }
      return false;
    }
  }

  private static boolean verifyMap1(ArrayList<String> arr) {

    if (arr.get(2).contentEquals("[Territories]")) {
      System.out.println("Verification failed in verification 1");
      return false;
    }
    return true;
  }


  private static boolean verifyMap2(ArrayList<String> arr) {

    if ((arr.get(arr.size() - 1).contentEquals("[Territories]"))) {
      System.out.println("Verification failed in verification 2");
      return false;
    }

    return true;
  }

  private static boolean verifyMap3(ArrayList<String> arr) {

    if (arr.get(2).endsWith("=") || arr.get(3).endsWith("=")) {
      System.out.println("Verification failed in verification 3");
      return false;
    }

    return true;
  }

  // Every Country has to have a Continent
  private static boolean verifyMap5(ArrayList<String> arr) {
    int startIndexForContinent = arr.indexOf("[Continents]") + 1;
    int startIndexForTerritory = arr.indexOf("[Territories]") + 1;
    ArrayList<String> continents = new ArrayList<>();
    for (int i = startIndexForContinent; i < startIndexForTerritory - 1; i++) {
      String[] line = arr.get(i).split("=");
      continents.add(line[0]);
    }

    for (int i = startIndexForTerritory; i < arr.size(); i++) {
      if (!arr.get(i).equals("")) {
        String[] line = arr.get(i).split(",");
        if (continents.indexOf(line[3]) == -1) {
          System.out.println(line[3]);
          System.out.println("Verification failed in verification 5");
          return false;
        }
      }
    }
    return true;
  }

  private static boolean verifyMap6(ArrayList<String> arr) {
    if (!((arr.contains("[Map]")) && (arr.contains("[Continents]"))
        && (arr.contains("[Territories]")))) {
      System.out.println("Verification failed in verification 6");
      return false;
    }
    return true;
  }

  /**
   * Verifies proper connection for the whole map.
   * 
   * @param arr
   * @return
   */
  public static boolean verifyMapConnected(ArrayList<String> arr) {
    String mainCountry;
    int startIndexForTerritory = arr.indexOf("[Territories]") + 1;
    // int endIndexForTerritory = arr.lastIndexOf(arr.get(arr.size() - 1));

    // All territories in [Territories]
    ArrayList<String> territory = new ArrayList<String>();
    for (int i = startIndexForTerritory; i < arr.size(); i++) {
      if (!arr.get(i).equals("")) {
        territory.add(arr.get(i));
      }
    }

    // Access the arrayList between start and end territories, first element into mainCountry,
    // and line[4], line[5], line[6] into neibhouringCountries array.
    for (int i = 0; i < territory.size(); i++) {
      String[] line = territory.get(i).split(",");
      for (int j = 4; j < line.length; j++) {
      }
      mainCountry = line[0];
      for (int j = 4; j < line.length; j++) {
        String neibhouringCountry = line[j];
        // for each string in neibhouringCountries, access the corresponding line in territory
        // arraylist and
        // check if the mainCountry value is present or not.
        for (int k = 0; k < territory.size(); k++) {
          String[] lands = territory.get(k).split(",");
          for (int l = 0; l < lands.length; l++) {
          }
          if (neibhouringCountry.equals(lands[0])) {
            boolean isThere = false;
            for (int l = 4; l < lands.length; l++) {
              // System.out.println("Before " + lands[0] + " " + mainCountry);
              if (lands[l].equals(mainCountry)) {
                // System.out.println("Yes " + lands[0] + " " + mainCountry);
                isThere = true;
                // break;
              }
            }
            if (!isThere) {
              System.out.println("Failing from Connected Map " + lands[0] + "  " + mainCountry);
              return false;
            }
          }
        }
      }

    }
    return true;
  }

  /**
   * Verifies at least one continent in territory
   * 
   * @param arr
   * @return
   */
  public static boolean verifyMapAtleastOneContinentInTerritory(ArrayList<String> arr) {
    int startIndexForTerritory = arr.indexOf("[Territories]") + 1;
    int endIndexForTerritory = arr.lastIndexOf(arr.get(arr.size() - 1));
    ArrayList<String> territory = new ArrayList<String>();
    for (int i = startIndexForTerritory; i < endIndexForTerritory - 1; i++) {
      if (!arr.get(i).equals("")) {
        String[] line = arr.get(i).split(",");
        territory.add(line[3]);
        String[] territoryNames = new String[territory.size()];
        territoryNames = territory.toArray(territoryNames);
        if (arr.get(startIndexForTerritory) != null) {
          if (territory.indexOf(line[3]) == -1) {
            System.out.println("Fail in Continent");
            return false;
          }
        }
      }
    }
    return true;
  }

}
