import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class tablesView {
  public static void withdraw_sheet(String accno) {
    JFrame obJFrame = new JFrame("WITHDRAW HISTORY");
    obJFrame.setBackground(new Color(145, 142, 255));
    obJFrame.setBounds(0, 0, 1365, 725);
    obJFrame.setDefaultCloseOperation(2);
    new Font("Arial Rounded MT Bold", 0, 15);
    String[] coloumnames = { "Amount", "Amount In Words", "DATE", "TIME" };
    String[][] data = JdbcConnector.getData(accno, "Withdraw");
    JTable table = new JTable((Object[][])data, (Object[])coloumnames);
    table.setEnabled(false);
    table.setBackground(new Color(234, 212, 224));
    table.setRowHeight(40);
    JScrollPane sPane = new JScrollPane(table);
    obJFrame.add(sPane);
    obJFrame.setVisible(true);
  }
  
  public static void deposit_sheet(String accno) {
    JFrame obJFrame = new JFrame("DEPOSIT HISTORY");
    obJFrame.setBackground(new Color(145, 142, 255));
    obJFrame.setBounds(0, 0, 1365, 725);
    obJFrame.setDefaultCloseOperation(2);
    new Font("Arial Rounded MT Bold", 0, 15);
    String[] coloumnames = { "Amount", "Amount In Words", "DATE", "TIME" };
    String[][] data = JdbcConnector.getData(accno, "Deposit");
    JTable table = new JTable((Object[][])data, (Object[])coloumnames);
    table.setEnabled(false);
    table.setBackground(new Color(234, 212, 224));
    table.setRowHeight(40);
    JScrollPane sPane = new JScrollPane(table);
    obJFrame.add(sPane);
    obJFrame.setVisible(true);
  }
  
  public static void transfer_sheet(String accno) {
    JFrame obJFrame = new JFrame("TRANSFER HISTORY");
    obJFrame.setBackground(new Color(145, 142, 255));
    obJFrame.setBounds(0, 0, 1365, 725);
    obJFrame.setDefaultCloseOperation(2);
    new Font("Arial Rounded MT Bold", 0, 15);
    String[] coloumnames = { "Receivers Account No.", "Amount", "Amount In Words", "DATE & TIME" };
    String[][] data = JdbcConnector.getData_transfer(accno, "Transfer");
    JTable table = new JTable((Object[][])data, (Object[])coloumnames);
    table.setEnabled(false);
    table.setBackground(new Color(234, 212, 224));
    table.setRowHeight(40);
    JScrollPane sPane = new JScrollPane(table);
    obJFrame.add(sPane);
    obJFrame.setVisible(true);
  }
  
  public static void recent_sheet(String accno) {
    JFrame obJFrame = new JFrame("RECENT HISTORY");
    obJFrame.setBackground(new Color(145, 142, 255));
    obJFrame.setBounds(0, 0, 1365, 725);
    obJFrame.setDefaultCloseOperation(2);
    obJFrame.setLocationRelativeTo((Component)null);
    new Font("Arial Rounded MT Bold", 0, 15);
    String[] coloumnames = { "Transcation", "Receivers Account No.", "Amount", "Amount In Words", "DATE & TIME" };
    String[][] data = JdbcConnector.getData_recent(accno);
    JTable table = new JTable((Object[][])data, (Object[])coloumnames);
    table.setEnabled(false);
    table.setBackground(new Color(234, 212, 224));
    table.setRowHeight(40);
    JScrollPane sPane = new JScrollPane(table);
    obJFrame.add(sPane);
    obJFrame.setVisible(true);
  }
  
  public static void main(String[] args) {}
}