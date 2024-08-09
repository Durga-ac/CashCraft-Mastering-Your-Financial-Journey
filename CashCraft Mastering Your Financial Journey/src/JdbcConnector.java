import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;


public class JdbcConnector {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/cashcraft";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Work34#@me/$";
    
    public static String databasecheck_userid_password(String id, String pass) {
        try {
            String url = "jdbc:mysql://localhost:3306/cashcraft";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, "root", "Work34#@me/$");
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM cashcraft.account_details WHERE pin_id=? and password=?");
            pst.setInt(1, Integer.parseInt(id));
            pst.setString(2, pass);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Successfully signed in");
                return rs.getString(3); // Assuming the third column contains the user's name
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect details");
                return null; // or some other value indicating failure
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Invalid details or Empty fields");
            return null; // or some other value indicating failure
        }
    }


    public static String[] getCustomerDetails(String id) {
        try {
            String url = "jdbc:mysql://localhost:3306/cashcraft";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, "root", "Work34#@me/$");
            PreparedStatement pst = conn.prepareStatement("SELECT first_name, last_name FROM cashcraft.account_details WHERE pin_id=?");
            pst.setInt(1, Integer.parseInt(id));
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                return new String[]{firstName, lastName};
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        return new String[]{"", ""}; // Return empty strings if customer details not found
    }
    

    public static String getBalance(String userId) {
        try (Connection connection = connect()) {
            String query = "SELECT balance FROM cashcraft.account_details WHERE pin_id=?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, Integer.parseInt(userId));
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return String.valueOf(resultSet.getInt("balance"));
                    } else {
                        return "offline";
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "offline";
        }
    }

    public static boolean updateBalance(String userId, int amount) {
        try (Connection connection = connect()) {
            String query = "UPDATE cashcraft.account_details SET balance = balance + ? WHERE pin_id=?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, amount);
                statement.setInt(2, Integer.parseInt(userId));
                statement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    public static String[] getAccountDetails(String userId) {
        String[] userDetails = new String[7]; // Assuming there are 7 fields in the table
        String query = "SELECT user_id, first_name, last_name, email, dob, pin_id, balance FROM account_details WHERE pin_id=?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    userDetails[0] = resultSet.getString("user_id");
                    userDetails[1] = resultSet.getString("first_name");
                    userDetails[2] = resultSet.getString("last_name");
                    userDetails[3] = resultSet.getString("email");
                    userDetails[4] = resultSet.getString("dob");
                    userDetails[5] = resultSet.getString("pin_id");
                    userDetails[6] = resultSet.getString("balance");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception according to your needs
        }

        return userDetails;
    }

    
    
    public static boolean updateAccountDetails(String userId, String newFirstName, String newLastName, String newEmail, String newDob) {
        String updateQuery = "UPDATE account_details SET first_name=?, last_name=?, email=?, dob=? WHERE pin_id=?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, newFirstName);
            preparedStatement.setString(2, newLastName);
            preparedStatement.setString(3, newEmail);
            preparedStatement.setString(4, newDob);
            preparedStatement.setString(5, userId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean deleteAccount(String userId) {
        String url = "jdbc:mysql://localhost:3306/cashcraft";
        String username = "root";
        String dbPassword = "Work34#@me/$"; // Replace with your actual database password

        try (Connection connection = DriverManager.getConnection(url, username, dbPassword)) {
            String query = "DELETE FROM account_details WHERE pin_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, userId);

                int rowsAffected = statement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false; // Return false in case of any database error
        }
    }


    public static void insertTransactionRecord(int senderId, String transactionType, int amount, int receiverId) {
        try (Connection connection = connect()) {
            String query = "INSERT INTO Transactions (ACCNOSENDER, TYPETRANSCATION, AMOUNT, ACCNORECEIVER, TDATE, TTIME, TDT) VALUES (?, ?, ?, ?, CURDATE(), CURTIME(), NOW())";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, senderId);
                statement.setString(2, transactionType);
                statement.setInt(3, amount);
                statement.setInt(4, receiverId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
    
    public static String[][] getData(String accno, String string) {
        ArrayList<ArrayList<Object>> strings = new ArrayList<>();
        try {
          String url = "jdbc:mysql://localhost:3306/cashcraft";
          Class.forName("com.mysql.cj.jdbc.Driver");
          Connection conn = DriverManager.getConnection(url, "root", "Work34#@me/$");
          PreparedStatement to_get_rec = conn.prepareStatement("SELECT * FROM cashcraft.Transactions WHERE accNoSender=? AND typeTranscation=? ORDER BY no DESC");
          to_get_rec.setInt(1, Integer.parseInt(accno));
          to_get_rec.setString(2, string);
          ResultSet bal = to_get_rec.executeQuery();
          int j = -1;
          while (bal.next()) {
            j++;
            strings.add(j, new ArrayList<Object>());
            int x = bal.getInt(4);
            strings.get(j).add("                                              " + String.valueOf(x) + "/-");
            if (x > 1) {
              strings.get(j).add("        " + String.valueOf(String.valueOf(num_to_word.findnum(x)) + "rupees only/-"));
            } else {
              strings.get(j).add(String.valueOf(num_to_word.findnum(x)));
            } 
              strings.get(j).add("                                           " + String.valueOf(bal.getDate(6)));
              strings.get(j).add("                                           " + String.valueOf(bal.getTime(7)));
          } 
        } catch (ClassNotFoundException|java.sql.SQLException ex) {
          ex.printStackTrace();
          System.out.print("exception at retreving data from the table Transactions");
        } 
        String[][] data = new String[strings.size()][4];
        for (int i = 0; i < strings.size(); i++) {
          for (int j = 0; j < 4; j++)
            data[i][j] =(String) strings.get(i).get(j); 
        } 
        return data;
      }
      
      public static String[][] getData_transfer(String accno, String string) {
        ArrayList<ArrayList<Object>> strings = new ArrayList<>();
        try {
          String url = "jdbc:mysql://localhost:3306/cashcraft";
          Class.forName("com.mysql.cj.jdbc.Driver");
          Connection conn = DriverManager.getConnection(url, "root", "Work34#@me/$");
          PreparedStatement to_get_rec = conn.prepareStatement("SELECT * FROM cashcraft.Transactions WHERE accNoSender=? AND typeTranscation=? ORDER BY no DESC");
          to_get_rec.setInt(1, Integer.parseInt(accno));
          to_get_rec.setString(2, string);
          ResultSet bal = to_get_rec.executeQuery();
          int j = -1;
          while (bal.next()) {
            j++;
            strings.add(j, new ArrayList<Object>());
            strings.get(j).add("                                        " + String.valueOf(bal.getInt(5)));
            int x = bal.getInt(4);
            strings.get(j).add("                                               " + String.valueOf(x) + "/-");
            if (x > 1) {
              strings.get(j).add(" " + String.valueOf(String.valueOf(num_to_word.findnum(x)) + "rupees only/-"));
            } else {
              strings.get(j).add(String.valueOf(num_to_word.findnum(x)));
            } 
              strings.get(j).add("                              " + String.valueOf(bal.getTimestamp(8)));
          } 
        } catch (ClassNotFoundException|java.sql.SQLException ex) {
          ex.printStackTrace();
          System.out.print("exception at retreving data from the table Transactions");
        } 
        String[][] data = new String[strings.size()][4];
        for (int i = 0; i < strings.size(); i++) {
          for (int j = 0; j < 4; j++)
            data[i][j] = (String) strings.get(i).get(j); 
        } 
        return data;
      }
      
      public static String[][] getData_recent(String accno) {
        ArrayList<ArrayList<Object>> strings = new ArrayList<>();
        try {
          String url = "jdbc:mysql://localhost:3306/cashcraft";
          Class.forName("com.mysql.cj.jdbc.Driver");
          Connection conn = DriverManager.getConnection(url, "root", "Work34#@me/$");
          PreparedStatement to_get_rec = conn.prepareStatement("SELECT * FROM cashcraft.Transactions WHERE accNoSender=? ORDER BY no DESC");
          to_get_rec.setInt(1, Integer.parseInt(accno));
          ResultSet bal = to_get_rec.executeQuery();
          int j = -1;
          while (bal.next()) {
            j++;
            strings.add(j, new ArrayList<Object>());
            strings.get(j).add("                                 " + String.valueOf(bal.getString(3)));
            int x = bal.getInt(4);
            int y = bal.getInt(5);
            if (y == 0) {
               strings.get(j).add("                                           -");
            } else {
              strings.get(j).add("                                     " + y);
            } 
            strings.get(j).add("                                     " + String.valueOf(x) + "/-");
            if (x > 1) {
              strings.get(j).add(" " + String.valueOf(String.valueOf(num_to_word.findnum(x)) + "rupees only/-"));
            } else {
              strings.get(j).add(String.valueOf(num_to_word.findnum(x)));
            } 
            strings.get(j).add("                    " + String.valueOf(bal.getTimestamp(8)));
          } 
        } catch (ClassNotFoundException|java.sql.SQLException ex) {
          ex.printStackTrace();
        } 
        String[][] data = new String[strings.size()][5];
        for (int i = 0; i < strings.size(); i++) {
          for (int j = 0; j < 5; j++)
            data[i][j] = (String) strings.get(i).get(j); 
        } 
        return data;
      }
}
