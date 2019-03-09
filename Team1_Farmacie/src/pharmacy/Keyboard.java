
package pharmacy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;

public class Keyboard implements Serializable {
  private static final long serialVersionUID = 1L;
  BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

  public String readLine() {
    try {
      return bufferedReader.readLine();
    } catch (IOException e) {
      System.out.println("Invalid input.");
      return null;
    }
  }

  public int readInt() {
    try {
      return Integer.parseInt(readLine());
    } catch (NumberFormatException e) {
      System.out.println("Invalid number.");
      return 0;
    }
  }

  public double readDouble() {
    try {
      return Double.parseDouble(readLine());
    } catch (NumberFormatException e) {
      System.out.println("Invalid number.");
      return 0;
    }
  }
}
