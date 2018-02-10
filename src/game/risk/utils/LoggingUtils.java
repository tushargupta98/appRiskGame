package game.risk.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * this class provides a static method accessible everywhere to write into logging window
 * 
 * @author Karthik
 * @version 1.0.0.0
 */
public class LoggingUtils {
  /*
   * this is a static method that takes the text and writes it into logging window file
   * 
   * @param is the text to be written
   */
  public static void Log(String line) throws IOException {
    String filename = "LoggingWindow.txt";
    FileWriter fw = new FileWriter(filename, true);
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Calendar cal = Calendar.getInstance();
    // appends the string to the file
    fw.write(dateFormat.format(cal.getTime()) + "\n");
    // appends the string to the file
    fw.write(line + "\n");
    fw.close();
  }
}
