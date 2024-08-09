import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;

public class forget_password extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L; // Unique identifier for the class
	private JFrame frame;
    private JTextField pinField, emailField;
    private JPasswordField passwordField;
    private JButton resetButton;

    ImageIcon image1;
    final Font font1 = new Font("Arial Rounded MT Bold", 0, 30);
    final Font font = new Font("Arial Rounded MT Bold", 0, 20);

    public forget_password() {
        frame = new JFrame("Forget Password");
        frame.setBounds(450, 150, 765, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        Color color = new Color(96, 192, 34);

        // Adding background image
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("background.jpg"));
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 765, 450);
        frame.add(backgroundLabel);

        //page header title
        JLabel titleLabel = new JLabel("Reset Password");
        titleLabel.setBounds(250, 30, 320, 50);
        titleLabel.setFont(this.font1);
        titleLabel.setForeground(Color.WHITE); // Set text color to white
        backgroundLabel.add(titleLabel);

        //PIN id label
        JLabel pinLabel = new JLabel("Pin Number:");
        pinLabel.setBounds(150, 100, 400, 30);
        pinLabel.setForeground(Color.WHITE); // Set text color to white
        pinLabel.setFont(this.font);
        backgroundLabel.add(pinLabel);

        //PIN id textfield
        pinField = new JTextField();
        pinField.setBounds(320, 100, 240, 30);
        pinField.setFont(this.font);
        backgroundLabel.add(pinField);

        //email label
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(150, 150, 400, 30);
        emailLabel.setForeground(Color.WHITE); // Set text color to white
        emailLabel.setFont(this.font);
        backgroundLabel.add(emailLabel);

        //email textfield
        emailField = new JTextField();
        emailField.setBounds(320, 150, 240, 30);
        emailField.setFont(this.font);
        backgroundLabel.add(emailField);

        //password label
        JLabel passwordLabel = new JLabel("New Password:");
        passwordLabel.setBounds(150, 200, 400, 30);
        passwordLabel.setForeground(Color.WHITE); // Set text color to white
        passwordLabel.setFont(this.font);
        backgroundLabel.add(passwordLabel);

        //password textfield
        passwordField = new JPasswordField();
        passwordField.setBounds(320, 200, 240, 30);
        passwordField.setFont(this.font);
        backgroundLabel.add(passwordField);

        //reset button
        resetButton = new JButton("Reset"); // Assigning to class-level variable
        resetButton.setBounds(280, 250, 150, 40);
        resetButton.setBackground(color);
        resetButton.setForeground(Color.WHITE);
        resetButton.setFont(this.font1);
        resetButton.addActionListener(this);
        backgroundLabel.add(resetButton);

        JButton backButton = new JButton("<");
        backButton.setBackground(color);
        backButton.setForeground(Color.WHITE);
        backButton.setBorderPainted(false);
        backButton.setBounds(0, 0, 60, 40);
        backButton.setFont(this.font1);
        backButton.addActionListener(this);
        backgroundLabel.add(backButton);

        frame.setVisible(true);
    }

 // Inside the actionPerformed method for the Reset button
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetButton) {
            String pin = pinField.getText();
            String email = emailField.getText();
            String newPassword = new String(passwordField.getPassword());

            if (pin.isEmpty() || email.isEmpty() || newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields are required.");
            } else {
                if (isValidCredentials(pin, email)) {
                    if (updatePassword(pin, newPassword)) {
                        JOptionPane.showMessageDialog(frame, "Password updated successfully!");
                        // Close the forget_password dialog
                        frame.dispose();
                        // Open the user_log_in page
                        new user_log_in();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Password update failed. Please try again.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid credentials. Please enter valid PIN and email.");
                }
            }
        }else if (e.getActionCommand().equals("<")) {
            frame.setVisible(false);
            new user_log_in(); // Navigate back to the homepage
        }

    }

    // Method to validate entered PIN and email from the database
    private boolean isValidCredentials(String pin, String email) {
    	String url = "jdbc:mysql://localhost:3306/cashcraft";
        String username = "root";
        String dbPassword = "Work34#@me/$"; // Replace with your actual database password
        // Use PreparedStatement to check if PIN and email match in the database
        try (Connection connection = DriverManager.getConnection(url, username, dbPassword)) {
            String query = "SELECT * FROM account_details WHERE pin_id = ? AND email = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, pin);
            statement.setString(2, email);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next(); // If a matching entry is found, return true
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Method to update the password in the database
    private boolean updatePassword(String pin, String newPassword) {
    	String url = "jdbc:mysql://localhost:3306/cashcraft";
        String username = "root";
        String dbPassword = "Work34#@me/$"; // Replace with your actual database password
        try (Connection connection = DriverManager.getConnection(url, username, dbPassword)) {
            String query = "UPDATE account_details SET password = ? WHERE pin_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newPassword);
            statement.setString(2, pin);

            int updatedRows = statement.executeUpdate();
            return updatedRows > 0; // If update was successful, return true
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    public static void main(String[] args) {
        new forget_password();
    }
}
