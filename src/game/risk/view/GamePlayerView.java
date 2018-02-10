package game.risk.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import game.risk.utils.GameInterface;

/**
 * This view class helps Select number of players and set their names.
 * @author Chowdhury Farsad Aurangzeb
 * @version 1.0.0
 * @since 12-October-2017
 *
 */
public class GamePlayerView implements GameInterface{

	/**
	 * this method helps to take the number of players
	 * @return the number of players inputed
	 */
	public static int getPlayerSelectNumber() {
		System.out.println();
		System.out.println("Please enter between " + MIN_NO_OF_PLAYERS + " and " + MAX_NO_OF_PLAYERS + 
				" to select the No of Player you will Play with.");
		boolean ifWrongInput = false;
		Scanner k = new Scanner(System.in);
		int input = 0;
		do {
			if(ifWrongInput) 
				System.out.println("Enter Number again between " + MIN_NO_OF_PLAYERS + " and " + MAX_NO_OF_PLAYERS + 
				".");
			input = k.nextInt();
			if(input < 3 || input > 5)
				ifWrongInput = true;
			else
				ifWrongInput = false;
		} while(ifWrongInput);
		return input;
	}
	
	/**
	 * This method helps to take the Name of the Player
	 * @return the name of the player
	 */
	public static String getPlayerName() {
		Scanner k = new Scanner(System.in);
		System.out.println();
		System.out.println("Please Enter Player Name (Insert One Word):");
		String name = k.next();
		return name;
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
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }


	  }
	
}
