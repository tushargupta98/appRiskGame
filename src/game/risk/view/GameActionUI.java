package game.risk.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import game.risk.Helper.AttackHelper;
import game.risk.Helper.DiceHelper;
import game.risk.Helper.GamePhaseHelper;
import game.risk.Helper.PlayerStatusHelper;
import game.risk.controller.GameController;
import game.risk.model.valueobjects.Country;
import game.risk.model.valueobjects.Map;
import game.risk.model.valueobjects.Player;
import game.risk.utils.GameInterface;

/**
 * This is the main Game Action UI. This view contains the option of Distribution of Soldier,
 * Reinforcement, Attacking, Dice and Fortification option.
 * 
 * @author Chowdhury Farsad Aurangzeb
 * @version 1.0.0
 * @since 12-October-2017
 *
 */
public class GameActionUI implements GameInterface, Runnable {

  /*
   * public static void logPropeties() { LoggingUtils.getLogProperties(); }
   */

  private static JPanel gameActionPanel = new JPanel(null);
  private static JFrame gameFrame = null;
  private static boolean firstTurn = true;

  // Map Label
  private static JLabel mapLabel = new JLabel();
  private static JScrollPane mapScrollPane = null;

  // Reinforcement Label
  private static JLabel reinforcementLabel = new JLabel();
  private static JLabel playersTurnLabel = new JLabel("Default");
  private static JLabel unassignedUnit = new JLabel("0");
  private static JComboBox<String> addUnitToCountryComboBox = new JComboBox<>();
  private static JComboBox<String> listOfRiskCards = new JComboBox<>();
  private static JButton turnInMatchingRiskCard = new JButton("Matching Cards");
  private static JButton turnInOneOfEachRiskCard = new JButton("1 of each Cards");
  private static JButton addUnitButton = new JButton("Add Unit");

  // Phase View Label
  private static JLabel phaseViewLabel = new JLabel();

  // Player World Domination Label
  private static JLabel playerWorldDominationViewLabel = new JLabel();

  // Attacking and Dice Roll Label
  private static JLabel attackingAndDiceLabel = new JLabel();
  private static JLabel attackerDice1 = new JLabel();
  private static JLabel attackerDice2 = new JLabel();
  private static JLabel attackerDice3 = new JLabel();
  private static JLabel defenderDice1 = new JLabel();
  private static JLabel defenderDice2 = new JLabel();
  private static JLabel attackStatusLabel = new JLabel();
  private static JButton rollDiceButton = new JButton();
  private static JButton soldierTransferButton = new JButton();
  private static JButton doneAttackButton = new JButton();
  private static JComboBox<String> attackerCountriesComboBox = new JComboBox<>();
  private static JComboBox<String> attackableCountriesComboBox = new JComboBox<>();
  private static JComboBox<String> attackerDiceChoice = new JComboBox<>();
  private static JComboBox<String> defenderDiceChoice = new JComboBox<>();
  private static JComboBox<String> noOfSoldierTranfer = new JComboBox<>();

  // Fortification Label
  private static JLabel fortificationLabel = new JLabel();
  private static JComboBox<String> soldierMoveFromComboBox = new JComboBox<>();
  private static JComboBox<String> soldierMoveToComboBox = new JComboBox<>();
  private static JComboBox<String> noOfSoldierMoveComboBox = new JComboBox<>();
  private static JButton fortificationMoveButton = new JButton("Move");

  // Save Game Button
  private static JLabel saveButtonLabel = new JLabel();
  private static JButton saveButton = new JButton("Save Game");


  private static Map mapObject;

  /**
   * This is a load game method which is initially run for to create initial panel.
   */
  public static void loadGameActionPanel() {
    try {
      gameActionPanel.removeAll();
      gameActionPanel = new JPanel(null);
      loadingMapLabel();
      loadingReinforcementLabel();
      loadingPhaseViewLabel();
      loadingPlayerWorldDominationViewLabel();
      loadingAttackingAndDiceLabel();
      loadingFortificationLabel();
      loadingSaveGameButton();
      gameActionPanel.repaint();
      gameActionPanel.revalidate();
    } catch (Exception e) {
      loadGameActionPanel();
    }
  }

  /**
   * Refreshes Data in View.
   */
  public static void refreshData() {
    mapObject.notifyGame();
    for (int i = 0; i < Map.getCountryList().size(); i++) {
      Map.getCountryList().get(i).getPointInMapLabel()
          .setText("" + Map.getCountryList().get(i).getSoilders());
    }
    playersTurnLabel
        .setText(Map.playerWithTurn().getName() + " (" + Map.playerWithTurn().getColorName() + ")");
    unassignedUnit.setText("" + Map.playerWithTurn().getNoOfUnitUnassigned());
    String[] countryNameList =
        new String[Map.playerWithTurn().getListOfPlayersConqueredCountries().size()];
    for (int i = 0; i < Map.playerWithTurn().getListOfPlayersConqueredCountries().size(); i++) {
      countryNameList[i] =
          Map.playerWithTurn().getListOfPlayersConqueredCountries().get(i).getCountryName();
    }
    addUnitToCountryComboBox.setModel(new DefaultComboBoxModel<>(countryNameList));
    soldierMoveFromComboBox.setModel(new DefaultComboBoxModel<>(countryNameList));
    soldierMoveToComboBox.setModel(new DefaultComboBoxModel<>(new String[0]));
    noOfSoldierMoveComboBox.setModel(new DefaultComboBoxModel<>(new String[0]));
    uppwd();
    String[] riskCardList = new String[Map.playerWithTurn().getRiskCards().size()];
    for (int p = 0; p < riskCardList.length; p++) {
      riskCardList[p] = Map.playerWithTurn().getRiskCards().get(p).getCountry().getCountryName()
          + " " + Map.playerWithTurn().getRiskCards().get(p).getCardName();
    }
    uppv();
    listOfRiskCards.setModel(new DefaultComboBoxModel<>(riskCardList));
    if (Map.playerWithTurn().getGamePlayStage().equals(Map.gamePlayStage[2])) {
      attackerCountriesComboBox
          .setModel(new DefaultComboBoxModel<>(Map.playerWithTurn().getAttackerCountries()));
      // Refreshing all the point labels
      for (int i = 0; i < Map.getCountryList().size(); i++) {
        Country tempCountry = Map.getCountryList().get(i);
        tempCountry.getPointInMapLabel().setText("" + tempCountry.getSoilders());
        tempCountry.getPointInMapLabel().setForeground(tempCountry.getPlayer().getColor());
      }
    }
  }

  /**
   * This is a game action view method which initials frame of the action gui.
   */
  public static void getGameActionView() {
    mapObject = Map.getInstance();
    gameFrame = new JFrame(FRAME_TITLE);
    loadGameActionPanel();
    gameFrame.add(gameActionPanel);
    gameFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    gameFrame.setVisible(true);
    gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  /*
   * Adding Map Image Label
   */
  private static void loadingMapLabel() throws IOException {
    log_writer("In Load Map Label");
    File imageFile = null;
    if (!GameController.isSaveMode()) {
      imageFile = new File(Map.getFilePath() + Map.getMapName() + ".bmp");
    } else {
      System.out.println(Map.getImagePath());
      imageFile = new File(Map.getImagePath());
    }
    Image image;
    ImageIcon icon = null;
    try {
      image = ImageIO.read(imageFile);
      icon = new ImageIcon(image);
    } catch (IOException e) {
      e.printStackTrace();
    }
    mapLabel = new JLabel(icon);
    for (int i = 0; i < Map.getCountryList().size(); i++) {
      Country tempCountry = Map.getCountryList().get(i);
      int[] coordinate = tempCountry.getCoordinate();
      tempCountry.setPointInMapLabel(new JLabel("" + tempCountry.getSoilders()));
      tempCountry.getPointInMapLabel().setFont(new Font("Courier", Font.BOLD, 20));
      tempCountry.getPointInMapLabel().setForeground(tempCountry.getPlayer().getColor());
      tempCountry.getPointInMapLabel().setBounds(coordinate[0], coordinate[1], 25, 25);
      mapLabel.add(tempCountry.getPointInMapLabel());
    }
    mapScrollPane = new JScrollPane(mapLabel);
    mapScrollPane.setBounds(10, 10, 600, 450);
    mapScrollPane.setBorder(new TitledBorder("Map"));
    gameActionPanel.add(mapScrollPane);
  }

  /*
   * Adding Reinforcement Label
   */
  private static void loadingReinforcementLabel() throws Exception {
    log_writer("In Reinforcement Label");
    // Delete Everything in Label
    reinforcementLabel.removeAll();
    reinforcementLabel = null;
    reinforcementLabel = new JLabel();
    reinforcementLabel.setBorder(BorderFactory.createTitledBorder(null, Map.gamePlayStage[0],
        TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
        new Font("SansSerif", Font.PLAIN, 12), Color.RED));
    reinforcementLabel.setBounds(mapScrollPane.getX(),
        mapScrollPane.getY() + mapScrollPane.getHeight() + 10, mapScrollPane.getWidth(),
        FRAME_HEIGHT - mapScrollPane.getHeight() - 60);

    // Recreate every components in Label
    playersTurnLabel = new JLabel(
        Map.playerWithTurn().getName() + " (" + Map.playerWithTurn().getColorName() + ")");
    playersTurnLabel.setBorder(new TitledBorder("Player's Turn"));
    playersTurnLabel.setBounds(5, 20, reinforcementLabel.getWidth() / 2 - 8,
        reinforcementLabel.getHeight() / 3 - 15);

    // listOfCountriesPlayerOccupied(Map.playerWithTurn());
    String[] countryNameList = Map.playerWithTurn().listOfCountriesPlayerConqueredInStringArray();
    addUnitToCountryComboBox = new JComboBox<>(countryNameList);
    addUnitToCountryComboBox.setBorder(new TitledBorder("Add Unit To Country"));
    addUnitToCountryComboBox.setBounds(playersTurnLabel.getX() + playersTurnLabel.getWidth() + 3,
        playersTurnLabel.getY(), playersTurnLabel.getWidth(), playersTurnLabel.getHeight());

    String[] riskCardList = new String[Map.playerWithTurn().getRiskCards().size()];
    for (int i = 0; i < riskCardList.length; i++) {
      riskCardList[i] = Map.playerWithTurn().getRiskCards().get(i).getCountry().getCountryName()
          + " " + Map.playerWithTurn().getRiskCards().get(i).getCardName();
    }

    listOfRiskCards = new JComboBox<>(riskCardList);
    listOfRiskCards.setBorder(new TitledBorder("Player's Risk Cards"));
    listOfRiskCards.setBounds(playersTurnLabel.getX(),
        playersTurnLabel.getY() + playersTurnLabel.getHeight() + 5, playersTurnLabel.getWidth(),
        playersTurnLabel.getHeight());

    turnInMatchingRiskCard.setBounds(addUnitToCountryComboBox.getX(), listOfRiskCards.getY(),
        addUnitToCountryComboBox.getWidth() / 2 - 2, listOfRiskCards.getHeight());
    turnInMatchingRiskCard.setEnabled(false);
    turnInMatchingRiskCard.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        GameController.turnInMatchingCardAction();
        String[] riskCardList = new String[Map.playerWithTurn().getRiskCards().size()];
        for (int p = 0; p < riskCardList.length; p++) {
          riskCardList[p] = Map.playerWithTurn().getRiskCards().get(p).getCountry().getCountryName()
              + " " + Map.playerWithTurn().getRiskCards().get(p).getCardName();
        }
        listOfRiskCards.setModel(new DefaultComboBoxModel<>(riskCardList));
        unassignedUnit.setText("" + Map.playerWithTurn().getNoOfUnitUnassigned());
        turnInMatchingRiskCard.setEnabled(false);
        turnInOneOfEachRiskCard.setEnabled(false);
      }
    });

    turnInOneOfEachRiskCard.setBounds(
        turnInMatchingRiskCard.getX() + turnInMatchingRiskCard.getWidth() + 2,
        turnInMatchingRiskCard.getY(), turnInMatchingRiskCard.getWidth(),
        turnInMatchingRiskCard.getHeight());
    turnInOneOfEachRiskCard.setEnabled(false);
    turnInOneOfEachRiskCard.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        GameController.turnInOneOfEachCardAction();
        String[] riskCardList = new String[Map.playerWithTurn().getRiskCards().size()];
        for (int p = 0; p < riskCardList.length; p++) {
          riskCardList[p] = Map.playerWithTurn().getRiskCards().get(p).getCountry().getCountryName()
              + " " + Map.playerWithTurn().getRiskCards().get(p).getCardName();
        }
        listOfRiskCards.setModel(new DefaultComboBoxModel<>(riskCardList));
        unassignedUnit.setText("" + Map.playerWithTurn().getNoOfUnitUnassigned());
        turnInOneOfEachRiskCard.setEnabled(false);
        turnInMatchingRiskCard.setEnabled(false);
      }
    });

    unassignedUnit = new JLabel("" + Map.playerWithTurn().getNoOfUnitUnassigned());
    unassignedUnit.setBorder(new TitledBorder("Unassigned Unit"));
    unassignedUnit.setBounds(listOfRiskCards.getX(),
        listOfRiskCards.getY() + playersTurnLabel.getHeight() + 5, playersTurnLabel.getWidth(),
        playersTurnLabel.getHeight());

    addUnitButton.setBounds(addUnitToCountryComboBox.getX(), unassignedUnit.getY(),
        unassignedUnit.getWidth(), unassignedUnit.getHeight());

    addUnitButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        addUnitToCountry(addUnitToCountryComboBox.getSelectedItem().toString());
        if (firstTurn) {
          mapObject.playerTurnChange();
          checkFirstRound();
        }
        refreshData();
      }
    });

    // Add all components in Label
    reinforcementLabel.add(playersTurnLabel);
    reinforcementLabel.add(unassignedUnit);
    reinforcementLabel.add(addUnitToCountryComboBox);
    reinforcementLabel.add(addUnitButton);
    reinforcementLabel.add(listOfRiskCards);
    reinforcementLabel.add(turnInMatchingRiskCard);
    reinforcementLabel.add(turnInOneOfEachRiskCard);

    // Add reinforcement Label to gameAction Panel
    gameActionPanel.add(reinforcementLabel);
  }


  /*
   * Adding Phase View Label
   */
  private static void loadingPhaseViewLabel() throws IOException {
    log_writer("In pahase view label Label");
    phaseViewLabel = PhaseView.getPhaseViewLabel();
    phaseViewLabel.setBounds(mapScrollPane.getX() + mapScrollPane.getWidth() + 20,
        mapScrollPane.getY(), FRAME_WIDTH - mapScrollPane.getY() - mapScrollPane.getWidth() - 40,
        60);
    gameActionPanel.add(phaseViewLabel);
  }

  /*
   * Adding Player World Domination View Label
   */

  private static void loadingPlayerWorldDominationViewLabel() throws IOException {
    log_writer("In Player World Domination Label");
    // Delete Everything in Label
    playerWorldDominationViewLabel = PlayerWorldDominationView.getPLayerWorldDominationViewLabel();
    playerWorldDominationViewLabel.setBounds(phaseViewLabel.getX(),
        phaseViewLabel.getY() + 10 + phaseViewLabel.getHeight(), phaseViewLabel.getWidth(),
        phaseViewLabel.getHeight());
    gameActionPanel.add(playerWorldDominationViewLabel);
  }

  /*
   * Adding Attacking and Dice Label
   */
  private static void loadingAttackingAndDiceLabel() throws IOException {

    log_writer("In Attacking Label");
    // Delete Everything in Label
    attackingAndDiceLabel.removeAll();
    attackingAndDiceLabel = null;
    attackingAndDiceLabel = new JLabel();
    attackingAndDiceLabel.setBorder(BorderFactory.createTitledBorder(null,
        "Attacking and Dice Roll", TitledBorder.DEFAULT_JUSTIFICATION,
        TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.PLAIN, 12), Color.BLACK));
    attackingAndDiceLabel.setBounds(playerWorldDominationViewLabel.getX(),
        playerWorldDominationViewLabel.getY() + 10 + playerWorldDominationViewLabel.getHeight(),
        playerWorldDominationViewLabel.getWidth(), 300);

    // Attacker Countries
    attackerCountriesComboBox = new JComboBox<>(Map.playerWithTurn().getAttackerCountries());
    attackerCountriesComboBox.setBounds(LABEL_GAP, LABEL_GAP, LABEL_WIDTH, LABEL_HEIGHT);
    attackerCountriesComboBox.setBorder(new TitledBorder("Attack From"));
    attackerCountriesComboBox.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        if (Map.playerWithTurn().getGamePlayStage().equals(Map.gamePlayStage[2])) {
          attackableCountriesComboBox.setModel(new DefaultComboBoxModel<>(
              Map.playerWithTurn().getAttackableCountries(attackerCountriesComboBox
                  .getItemAt(attackerCountriesComboBox.getSelectedIndex()))));
        }
      }
    });
    attackingAndDiceLabel.add(attackerCountriesComboBox);

    // Attackable Countries
    attackableCountriesComboBox = new JComboBox<>();
    attackableCountriesComboBox.setBounds(
        attackerCountriesComboBox.getX() + attackerCountriesComboBox.getWidth() + 10,
        attackerCountriesComboBox.getY(), attackerCountriesComboBox.getWidth(),
        attackerCountriesComboBox.getHeight());
    attackableCountriesComboBox.setBorder(new TitledBorder("Attack Country"));
    attackableCountriesComboBox.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        if (Map.playerWithTurn().getGamePlayStage().equals(Map.gamePlayStage[2])) {
          // Setting Defender true
          // Values for Attacker Dice Choice
          String[] nos = new String[Map.playerWithTurn().noOfDicePlayerCanRollOnThisCountry(
              attackerCountriesComboBox.getSelectedItem().toString())];
          for (int i = 0; i < nos.length; i++) {
            nos[i] = "" + (i + 1);
          }
          attackerDiceChoice.setModel(new DefaultComboBoxModel<>(nos));

          // Get the Defender Player and set it Defender
          Player defenderPlayer = Map.getPlayerOfCountry(
              Map.getCountry(attackableCountriesComboBox.getSelectedItem().toString()));
          defenderPlayer.setDefender(true);

          // Values for Defender Dice Choice
          nos = new String[defenderPlayer.noOfDicePlayerCanRollOnThisCountry(
              attackableCountriesComboBox.getSelectedItem().toString())];
          for (int i = 0; i < nos.length; i++) {
            nos[i] = "" + (i + 1);
          }
          defenderDiceChoice.setModel(new DefaultComboBoxModel<>(nos));
        }
      }
    });
    attackingAndDiceLabel.add(attackableCountriesComboBox);

    // Attacker Dice Choices
    attackerDiceChoice = new JComboBox<>();
    attackerDiceChoice.setBorder(new TitledBorder("Attacker Dice"));
    attackerDiceChoice.setBounds(attackerCountriesComboBox.getX(),
        attackableCountriesComboBox.getY() + attackableCountriesComboBox.getHeight() + LABEL_GAP,
        (LABEL_WIDTH / 2) - 5, LABEL_HEIGHT);
    attackingAndDiceLabel.add(attackerDiceChoice);

    // Dice 1
    attackerDice1 = new JLabel("");
    attackerDice1.setBorder(new TitledBorder("Dice 1"));
    attackerDice1.setBounds(attackerDiceChoice.getX() + attackerDiceChoice.getWidth() + 10,
        attackerDiceChoice.getY(), attackerDiceChoice.getWidth(), attackerDiceChoice.getHeight());
    attackingAndDiceLabel.add(attackerDice1);

    // Dice 2
    attackerDice2 = new JLabel("");
    attackerDice2.setBorder(new TitledBorder("Dice 2"));
    attackerDice2.setBounds(attackerDice1.getX() + attackerDice1.getWidth() + 10,
        attackerDice1.getY(), attackerDice1.getWidth(), attackerDice1.getHeight());
    attackingAndDiceLabel.add(attackerDice2);

    // Dice 3
    attackerDice3 = new JLabel("");
    attackerDice3.setBorder(new TitledBorder("Dice 3"));
    attackerDice3.setBounds(attackerDice2.getX() + attackerDice2.getWidth() + 10,
        attackerDice2.getY(), attackerDice2.getWidth(), attackerDice2.getHeight());
    attackingAndDiceLabel.add(attackerDice3);

    // Defender Dice Choices
    defenderDiceChoice = new JComboBox<>();
    defenderDiceChoice.setBorder(new TitledBorder("Defender Dice"));
    defenderDiceChoice.setBounds(attackerCountriesComboBox.getX(),
        attackerDiceChoice.getY() + attackerDiceChoice.getHeight() + LABEL_GAP,
        attackerDiceChoice.getWidth(), attackerDiceChoice.getHeight());
    attackingAndDiceLabel.add(defenderDiceChoice);

    // Dice 1
    defenderDice1 = new JLabel("");
    defenderDice1.setBorder(new TitledBorder("Dice 1"));
    defenderDice1.setBounds(defenderDiceChoice.getX() + defenderDiceChoice.getWidth() + 10,
        defenderDiceChoice.getY(), attackerDice3.getWidth(), attackerDice3.getHeight());
    attackingAndDiceLabel.add(defenderDice1);

    // Dice 2
    defenderDice2 = new JLabel("");
    defenderDice2.setBorder(new TitledBorder("Dice 2"));
    defenderDice2.setBounds(defenderDice1.getX() + defenderDice1.getWidth() + 10,
        defenderDice1.getY(), defenderDice1.getWidth(), defenderDice1.getHeight());
    attackingAndDiceLabel.add(defenderDice2);

    // Dice Roll Button
    rollDiceButton = new JButton("Roll The Dice");
    rollDiceButton.setBounds(defenderDice2.getX() + defenderDice2.getWidth() + 10,
        defenderDice2.getY(), defenderDice2.getWidth(), defenderDice2.getHeight());
    rollDiceButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        if (Map.playerWithTurn().getGamePlayStage().equals(Map.gamePlayStage[2])
            && attackerDiceChoice.getSelectedIndex() > -1
            && defenderDiceChoice.getSelectedIndex() > -1) {
          ArrayList<Integer> attackerDiceRolls = new ArrayList<>();
          ArrayList<Integer> defenderDiceRolls = new ArrayList<>();
          // For attacker Dice Rolls
          int diceSide = DiceHelper.diceRoll();
          attackerDice1.setText(DICE_SIDES[diceSide - 1]);
          attackerDiceRolls.add(diceSide);
          if (Integer.parseInt(attackerDiceChoice.getSelectedItem().toString()) > 1) {
            diceSide = DiceHelper.diceRoll();
            attackerDice2.setText(DICE_SIDES[diceSide - 1]);
            attackerDiceRolls.add(diceSide);
            if (Integer.parseInt(attackerDiceChoice.getSelectedItem().toString()) > 2) {
              diceSide = DiceHelper.diceRoll();
              attackerDice3.setText(DICE_SIDES[diceSide - 1]);
              attackerDiceRolls.add(diceSide);
            }
          }

          // For defender Dice Rolls
          diceSide = DiceHelper.diceRoll();
          defenderDice1.setText(DICE_SIDES[diceSide - 1]);
          defenderDiceRolls.add(diceSide);
          if (Integer.parseInt(defenderDiceChoice.getSelectedItem().toString()) > 1) {
            diceSide = DiceHelper.diceRoll();
            defenderDice2.setText(DICE_SIDES[diceSide - 1]);
            defenderDiceRolls.add(diceSide);
          }

          // Update attack status
          attackStatusLabel.setText(AttackHelper.isAttackerWon(
              DiceHelper.diceRollCompare(attackerDiceRolls, defenderDiceRolls),
              attackerCountriesComboBox.getSelectedItem().toString(),
              attackableCountriesComboBox.getSelectedItem().toString()));
          // checks if attacker won
          if (attackStatusLabel.getText().equals(ATTACKER_WON_STATEMENT)) {
            Country attacker =
                Map.getCountry(attackerCountriesComboBox.getSelectedItem().toString());
            Country defender =
                Map.getCountry(attackableCountriesComboBox.getSelectedItem().toString());
            // Move at least the amount of soldier the attacker attacked with (no of dice in play of
            // attacker) and set defender country to attacker player.
            Player defeatedPlayer = AttackHelper.attackWon(attacker, defender,
                Integer.parseInt(attackerDiceChoice.getSelectedItem().toString()));
            System.out.println(defeatedPlayer.getName() + " lost " + defender.getCountryName()
                + " and " + defender.getPlayer().getName() + " conquered it.");
            int soldierInAttackerCountry = Map
                .getCountry(attackerCountriesComboBox.getSelectedItem().toString()).getSoilders();
            String[] soldierNo = new String[soldierInAttackerCountry - 1];
            for (int i = 0; i < soldierNo.length; i++) {
              soldierNo[i] = "" + (i + 1);
            }
            noOfSoldierTranfer.setModel(new DefaultComboBoxModel<>(soldierNo));

            // Checks if the defender player is out of game
            PlayerStatusHelper.isPlayerDefeated(defeatedPlayer);
            // Checks if the game is over
            if (GamePhaseHelper.isGameEnded()) {
              gameFrame.dispose();
            }
          }
          refreshData();
        }
      }
    });

    attackingAndDiceLabel.add(rollDiceButton);

    // Attack Status
    attackStatusLabel = new JLabel("");
    attackStatusLabel.setBorder(new TitledBorder("Attack status"));
    attackStatusLabel.setBounds(defenderDiceChoice.getX(),
        defenderDiceChoice.getY() + LABEL_GAP + defenderDiceChoice.getHeight(),
        defenderDiceChoice.getWidth(), defenderDiceChoice.getHeight());
    attackingAndDiceLabel.add(attackStatusLabel);

    // Soldier Transfer Choice
    noOfSoldierTranfer = new JComboBox<>();
    noOfSoldierTranfer.setBorder(new TitledBorder("No. of Soldier Tranfer"));
    noOfSoldierTranfer.setBounds(attackStatusLabel.getX() + attackStatusLabel.getWidth() + 10,
        attackStatusLabel.getY(), attackStatusLabel.getWidth(), attackStatusLabel.getHeight());
    attackingAndDiceLabel.add(noOfSoldierTranfer);

    // Transfer Button
    soldierTransferButton = new JButton("Transfer");
    soldierTransferButton.setBounds(noOfSoldierTranfer.getX() + 10 + noOfSoldierTranfer.getWidth(),
        noOfSoldierTranfer.getY(), noOfSoldierTranfer.getWidth(), noOfSoldierTranfer.getHeight());
    soldierTransferButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        if (noOfSoldierTranfer.getSelectedIndex() > -1
            && Map.playerWithTurn().getGamePlayStage().equals(Map.gamePlayStage[2])) {
          int noOfSoldierTransfers =
              Integer.parseInt(noOfSoldierTranfer.getSelectedItem().toString());
          Country attacker = Map.getCountry(attackerCountriesComboBox.getSelectedItem().toString());
          Country defender =
              Map.getCountry(attackableCountriesComboBox.getSelectedItem().toString());
          attacker.moveOutSoldier(noOfSoldierTransfers);
          defender.moveInSoldier(noOfSoldierTransfers);
          refreshData();
        }
      }
    });
    attackingAndDiceLabel.add(soldierTransferButton);

    // Done Attack Button
    doneAttackButton = new JButton("Done Attack");
    doneAttackButton.setBounds(soldierTransferButton.getX() + 10 + soldierTransferButton.getWidth(),
        soldierTransferButton.getY(), soldierTransferButton.getWidth(),
        soldierTransferButton.getHeight());
    doneAttackButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        PlayerStatusHelper.eraseAttacker();
        PlayerStatusHelper.eraseDefender();
        Map.playerWithTurn().setPlayerAttackWin(false);
        attackStatusLabel.setText("");
        // soldierTransferButton.setEnabled(false);
        // noOfSoldierMoveComboBox.setEnabled(false);
        if (Map.playerWithTurn().isPlayerEligibleToFortify()) {
          Map.playerWithTurn().setGamePlayStage(Map.gamePlayStage[3]);
          fortificationLabel.setBorder(BorderFactory.createTitledBorder(null, "Fortification",
              TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
              new Font("SansSerif", Font.PLAIN, 12), Color.RED));
          attackingAndDiceLabel.setBorder(BorderFactory.createTitledBorder(null,
              "Attacking and Dice Roll", TitledBorder.DEFAULT_JUSTIFICATION,
              TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.PLAIN, 12), Color.BLACK));
        } else {
          mapObject.playerTurnChange();
          Map.playerWithTurn().setGamePlayStage(Map.gamePlayStage[1]);
          Map.playerWithTurn().calculateReinforcementUnits();
          //
          attackingAndDiceLabel.setBorder(BorderFactory.createTitledBorder(null,
              "Attacking and Dice Roll", TitledBorder.DEFAULT_JUSTIFICATION,
              TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.PLAIN, 12), Color.BLACK));
          Map.playerWithTurn().setGamePlayStage(Map.gamePlayStage[1]);
          reinforcementLabel.setBorder(BorderFactory.createTitledBorder(null, "Reinforcements",
              TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
              new Font("SansSerif", Font.PLAIN, 12), Color.RED));
        }
        refreshData();
      }
    });
    attackingAndDiceLabel.add(doneAttackButton);

    gameActionPanel.add(attackingAndDiceLabel);
  }

  /*
   * Adding Fortification Label
   */
  private static void loadingFortificationLabel() throws IOException {
    log_writer("In Fortification Label");
    // Delete Everything in Label
    fortificationLabel.removeAll();
    fortificationLabel = null;
    fortificationLabel = new JLabel();
    fortificationLabel.setBorder(
        BorderFactory.createTitledBorder(null, "Fortification", TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.PLAIN, 12), Color.BLACK));
    fortificationLabel.setBounds(playerWorldDominationViewLabel.getX(),
        attackingAndDiceLabel.getY() + 10 + attackingAndDiceLabel.getHeight(),
        playerWorldDominationViewLabel.getWidth(), 140);
    // Recreate every components in Label
    soldierMoveFromComboBox =
        new JComboBox<>(Map.playerWithTurn().listOfCountriesPlayerConqueredInStringArray());
    soldierMoveFromComboBox.setBorder(new TitledBorder("Soldier Move From"));
    soldierMoveFromComboBox.setBounds(LABEL_GAP, LABEL_GAP, LABEL_WIDTH, LABEL_HEIGHT);
    soldierMoveFromComboBox.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        soldierMoveToComboBox.setModel(
            new DefaultComboBoxModel<>(Map.playerWithTurn().listOfNeighbooringSamePlayerCountries(
                soldierMoveFromComboBox.getSelectedItem().toString())));
        String[] noOfSoldierAddOption = new String[Map
            .getCountry(soldierMoveFromComboBox.getSelectedItem().toString()).getSoilders()];
        for (int i = 0; i < noOfSoldierAddOption.length; i++) {
          noOfSoldierAddOption[i] = "" + i;
        }
        noOfSoldierMoveComboBox.setModel(new DefaultComboBoxModel<>(noOfSoldierAddOption));
      }
    });

    soldierMoveToComboBox = new JComboBox<>();
    soldierMoveToComboBox.setBorder(new TitledBorder("Soldier Move To"));
    soldierMoveToComboBox.setBounds(
        soldierMoveFromComboBox.getX() + soldierMoveFromComboBox.getWidth() + 10,
        soldierMoveFromComboBox.getY(), soldierMoveFromComboBox.getWidth(),
        soldierMoveFromComboBox.getHeight());

    noOfSoldierMoveComboBox.setBounds(soldierMoveFromComboBox.getX(),
        soldierMoveFromComboBox.getHeight() + soldierMoveFromComboBox.getY() + 7,
        soldierMoveFromComboBox.getWidth(), soldierMoveFromComboBox.getHeight());
    noOfSoldierMoveComboBox.setBorder(new TitledBorder("No Of Soldier Move"));

    fortificationMoveButton.setBounds(soldierMoveToComboBox.getX(), noOfSoldierMoveComboBox.getY(),
        soldierMoveToComboBox.getWidth(), soldierMoveToComboBox.getHeight());
    fortificationMoveButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        if (noOfSoldierMoveComboBox.getSelectedIndex() > 0
            && Map.playerWithTurn().getGamePlayStage().equals(Map.gamePlayStage[3])) {
          //
          Map.playerWithTurn().soldierFortify(soldierMoveFromComboBox.getSelectedItem().toString(),
              soldierMoveToComboBox.getSelectedItem().toString(),
              noOfSoldierMoveComboBox.getSelectedIndex());
          //
          mapObject.playerTurnChange();
          Map.playerWithTurn().calculateReinforcementUnits();
          //
          fortificationLabel.setBorder(BorderFactory.createTitledBorder(null, "Fortification",
              TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
              new Font("SansSerif", Font.PLAIN, 12), Color.BLACK));
          Map.playerWithTurn().setGamePlayStage(Map.gamePlayStage[1]);
          reinforcementLabel.setBorder(BorderFactory.createTitledBorder(null, "Reinforcements",
              TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
              new Font("SansSerif", Font.PLAIN, 12), Color.RED));
        } else if (noOfSoldierMoveComboBox.getSelectedIndex() < 1
            && Map.playerWithTurn().getGamePlayStage().equals(Map.gamePlayStage[3])) {
          //
          mapObject.playerTurnChange();
          Map.playerWithTurn().setGamePlayStage(Map.gamePlayStage[1]);
          Map.playerWithTurn().calculateReinforcementUnits();
          //
          fortificationLabel.setBorder(BorderFactory.createTitledBorder(null, "Fortification",
              TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
              new Font("SansSerif", Font.PLAIN, 12), Color.BLACK));
          Map.playerWithTurn().setGamePlayStage(Map.gamePlayStage[1]);
          reinforcementLabel.setBorder(BorderFactory.createTitledBorder(null, "Reinforcements",
              TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
              new Font("SansSerif", Font.PLAIN, 12), Color.RED));
        }
      }
    });

    // Add all components in Label
    fortificationLabel.add(soldierMoveFromComboBox);
    fortificationLabel.add(soldierMoveToComboBox);
    fortificationLabel.add(noOfSoldierMoveComboBox);
    fortificationLabel.add(fortificationMoveButton);
    // Adding Label to Panel
    gameActionPanel.add(fortificationLabel);
  }

  private static void loadingSaveGameButton() {
    saveButtonLabel.removeAll();
    saveButtonLabel = null;
    saveButtonLabel = new JLabel();
    saveButtonLabel
        .setBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.PLAIN, 12), Color.BLACK));
    saveButtonLabel.setBounds(playerWorldDominationViewLabel.getX(),
        fortificationLabel.getY() + 10 + fortificationLabel.getHeight(),
        playerWorldDominationViewLabel.getWidth(), playerWorldDominationViewLabel.getHeight() - 13);

    saveButton = new JButton("Save Game");
    int buttonHeight = 25;
    int buttonWidth = 100;
    saveButton.setBounds(saveButtonLabel.getWidth() / 2 - buttonWidth / 2,
        saveButtonLabel.getHeight() / 2 - buttonHeight / 2, buttonWidth, buttonHeight);
    saveButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        GameController.setSaveMode(true);
        // File newMapFolder = new File(FILE_PATH_SAVED + Map.getMapName() + "(Saved)");
        // newMapFolder.mkdirs();
        GameController.deleteAndCreateFileAndWriteInFile(
            FILE_PATH_SAVED + Map.getMapName() + "(Saved)/" + Map.getMapName() + "(Saved).map",
            GameController.mapComponentToList());
        gameFrame.dispose();
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      }
    });
    saveButtonLabel.add(saveButton);

    gameActionPanel.add(saveButtonLabel);
  }

  private static void uppv() {
    PhaseView.getInstance().update(null, null);
  }

  private static void uppwd() {
    PlayerWorldDominationView.getIntance().update(null, null);
  }

  private static boolean addUnitToCountry(String countryName) {
    for (int i = 0; i < Map.playerWithTurn().getListOfPlayersConqueredCountries().size(); i++) {
      if (Map.playerWithTurn().getListOfPlayersConqueredCountries().get(i).getCountryName()
          .equals(countryName)
          && Map.playerWithTurn().getListOfPlayersConqueredCountries().get(i).getPlayer()
              .getNoOfUnitUnassigned() > 0) {
        Map.playerWithTurn().getListOfPlayersConqueredCountries().get(i).addSoilders();
        Map.playerWithTurn().getListOfPlayersConqueredCountries().get(i).getPlayer()
            .addNoOfArmyAlive();
        if (Map.playerWithTurn().getNoOfUnitUnassigned() == 0 && firstTurn == false) {
          if (Map.playerWithTurn().isPlayerEligibleToAttack()) {
            Map.playerWithTurn().setGamePlayStage(Map.gamePlayStage[2]);
            // Set attacker
            Map.playerWithTurn().setAttacker(true);
            attackingAndDiceLabel.setBorder(BorderFactory.createTitledBorder(null,
                "Attacking and Dice Roll", TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.PLAIN, 12), Color.RED));
            reinforcementLabel.setBorder(BorderFactory.createTitledBorder(null, "Reinforcements",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                new Font("SansSerif", Font.PLAIN, 12), Color.BLACK));
            turnInMatchingRiskCard.setEnabled(false);
            turnInOneOfEachRiskCard.setEnabled(false);
          } else if (Map.playerWithTurn().isPlayerEligibleToFortify()) {
            Map.playerWithTurn().setGamePlayStage(Map.gamePlayStage[3]);
            fortificationLabel.setBorder(BorderFactory.createTitledBorder(null, "Fortification",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                new Font("SansSerif", Font.PLAIN, 12), Color.RED));
            reinforcementLabel.setBorder(BorderFactory.createTitledBorder(null, "Reinforcements",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                new Font("SansSerif", Font.PLAIN, 12), Color.BLACK));
            turnInMatchingRiskCard.setEnabled(false);
            turnInOneOfEachRiskCard.setEnabled(false);
          } else {
            mapObject.playerTurnChange();
            Map.playerWithTurn().setGamePlayStage(Map.gamePlayStage[1]);
            Map.playerWithTurn().calculateReinforcementUnits();
            Map.playerWithTurn().setGamePlayStage(Map.gamePlayStage[1]);
          }
        }
        return true;
      }
    }
    return false;
  }

  private static void checkFirstRound() {
    boolean allZero = true;
    for (int i = 0; i < Map.getPlayerList().size(); i++) {
      if (Map.getPlayerList().get(i).getNoOfUnitUnassigned() > 0) {
        allZero = false;
        return;
      }
    }
    if (allZero) {
      firstTurn = false;
      reinforcementLabel.setBorder(BorderFactory.createTitledBorder(null, "Reinforcement",
          TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
          new Font("SansSerif", Font.PLAIN, 12), Color.RED));
      Map.playerWithTurn().calculateReinforcementUnits();
      refreshData();
    }
  }

  /**
   * Switches On/Off for Turn in one of each Risk Card
   * 
   * @param turn
   */
  public static void switchTurnInOneOfEachRiskCard(boolean turn) {
    turnInOneOfEachRiskCard.setEnabled(turn);
  }

  /**
   * Switches On/Off for Turn in Matching risk card
   * 
   * @param turn
   */
  public static void switchTurnInMatchingRiskCard(boolean turn) {
    turnInMatchingRiskCard.setEnabled(turn);
  }

  /**
   * This is a run method to run this class as a Java Thread.
   */
  @Override
  public void run() {
    getGameActionView();
  }

  /**
   * logger window
   * 
   * @param str string
   * @throws IOException exception
   */
  public static void log_writer(String str) throws IOException {
    try {
      File file = new File("/Users/chowdhuryfarsadaurangzeb/Desktop/Log/log.txt");
      FileWriter fw = new FileWriter(file, true);
      PrintWriter pw = new PrintWriter(fw);
      pw.println(str);
      pw.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }


  }

}
