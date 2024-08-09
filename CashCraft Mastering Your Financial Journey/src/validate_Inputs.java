import javax.swing.JOptionPane;

public class validate_Inputs {
  public static boolean validate_amount(String amount) {
    try {
      if (Integer.parseInt(amount) >= 1 && Integer.parseInt(amount) <= Integer.MAX_VALUE)
        return true; 
      throw new RuntimeException();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Enter the correct amount");
      return false;
    } 
  }
  
  public static boolean validate_accno(String acc) {
    try {
      if (Integer.parseInt(acc) >= 1 && Integer.parseInt(acc) <= Integer.MAX_VALUE)
        return true; 
      throw new RuntimeException();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Enter the correct account number");
      return false;
    } 
  }
  
  public static void main(String[] args) {}
}
