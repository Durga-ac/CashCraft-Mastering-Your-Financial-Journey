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

public class CreateAccountPage implements ActionListener {
    private JFrame frame;
    private JTextField firstNameField, lastNameField, emailField, dobField;
    private JPasswordField passwordField;
    private JLabel userIdLabel;

    ImageIcon image1;
    Color color = new Color(96, 192, 34);
    final Font font1 = new Font("Arial Rounded MT Bold", 0, 30);
    final Font font = new Font("Arial Rounded MT Bold", 0, 20);

    public CreateAccountPage() {

        frame = new JFrame("Cashcraft");
        frame.setBounds(450, 150, 765, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Adding background image
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("background.jpg"));
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 765, 450);
        frame.add(backgroundLabel);

        //page header title
        JLabel titleLabel = new JLabel("Create Your Account");
        titleLabel.setBounds(250, 30, 320, 50);
        titleLabel.setFont(this.font1);
        titleLabel.setForeground(Color.WHITE); // Set text color to white
        backgroundLabel.add(titleLabel);

        //first name label
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setBounds(150, 100, 400, 30);
        firstNameLabel.setForeground(Color.WHITE); // Set text color to white
        firstNameLabel.setFont(this.font);
        backgroundLabel.add(firstNameLabel);

        //first name textfield
        firstNameField = new JTextField();
        firstNameField.setBounds(320, 100, 240, 30);
        firstNameField.setFont(this.font);
        backgroundLabel.add(firstNameField);

        //last name label
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setBounds(150, 150, 400, 30);
        lastNameLabel.setForeground(Color.WHITE); // Set text color to white
        lastNameLabel.setFont(this.font);
        backgroundLabel.add(lastNameLabel);

        //last name textfield
        lastNameField = new JTextField();
        lastNameField.setBounds(320, 150, 240, 30);
        lastNameField.setFont(this.font);
        backgroundLabel.add(lastNameField);

        //email label
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(150, 200, 400, 30);
        emailLabel.setForeground(Color.WHITE); // Set text color to white
        emailLabel.setFont(this.font);
        backgroundLabel.add(emailLabel);

        //email textfield
        emailField = new JTextField();
        emailField.setBounds(320, 200, 240, 30);
        emailField.setFont(this.font);
        backgroundLabel.add(emailField);

        //dob label
        JLabel dobLabel = new JLabel("Date of Birth:");
        dobLabel.setBounds(150, 250, 400, 30);
        dobLabel.setForeground(Color.WHITE); // Set text color to white
        dobLabel.setFont(this.font);
        backgroundLabel.add(dobLabel);

        //dob textfield
        dobField = new JTextField();
        dobField.setBounds(320, 250, 240, 30);
        dobField.setFont(this.font);
        backgroundLabel.add(dobField);

        //password label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(150, 300, 400, 30);
        passwordLabel.setForeground(Color.WHITE); // Set text color to white
        passwordLabel.setFont(this.font);
        backgroundLabel.add(passwordLabel);

        //password textfield
        passwordField = new JPasswordField();
        passwordField.setBounds(320, 300, 240, 30);
        passwordField.setFont(this.font);
        backgroundLabel.add(passwordField);

        //submit button
        JButton createAccountButton = new JButton("Submit");
        createAccountButton.setBounds(280, 350, 150, 40);
        createAccountButton.setBackground(color);
        createAccountButton.setForeground(Color.WHITE);
        createAccountButton.setFont(this.font1);
        createAccountButton.addActionListener(this);
        backgroundLabel.add(createAccountButton);

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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Submit")) {
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String email = emailField.getText().trim();
            String dob = dobField.getText().trim(); // Date of Birth
            char[] passwordChars = passwordField.getPassword();
            String password = new String(passwordChars);

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || dob.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill out all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid email address.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!isValidDateFormat(dob)) {
                JOptionPane.showMessageDialog(frame, "Please enter the date of birth in YYYY/MM/DD format.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int pinId = generateUniquePinId();
                boolean success = storeUserData(firstName, lastName, email, dob, password, pinId);

                if (success) {
                    JOptionPane.showMessageDialog(frame, "Account created successfully! PIN ID: " + pinId + "\nPlease keep your PIN ID safe.");
                 // Close the current frame
                    frame.dispose();

                    // Open the user_log_in page
                    new user_log_in();
                    userIdLabel.setText("Account created successfully! PIN ID: " + pinId);
                } else {
                    userIdLabel.setText("Account creation failed. Please try again.");
                }
            }
        } else if (e.getActionCommand().equals("<")) {
            frame.setVisible(false);
            new Homepage(); // Navigate back to the homepage
        }
    }

    private boolean isValidEmail(String email) {
        // Simple email validation using a regular expression
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
    
    private boolean isValidDateFormat(String dob) {
        // Simple date format validation using a regular expression (YYYY/mm/dd)
        String dateRegex = "^\\d{4}/\\d{2}/\\d{2}$";
        return dob.matches(dateRegex);
    }
    
	private boolean isUserExists(String email) {
        String url = "jdbc:mysql://localhost:3306/cashcraft";
        String username = "root";
        String dbPassword = "Work34#@me/$"; // Replace with your actual database password

        try (Connection connection = DriverManager.getConnection(url, username, dbPassword)) {
            String query = "SELECT * FROM account_details WHERE email = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, email);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next(); // If the result set is not empty, the user exists
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false; // Return false in case of any database error
        }
    }

    private boolean isPinIdExists(int pinId) {
        String url = "jdbc:mysql://localhost:3306/cashcraft";
        String username = "root";
        String dbPassword = "Work34#@me/$"; // Replace with your actual database password

        try (Connection connection = DriverManager.getConnection(url, username, dbPassword)) {
            String query = "SELECT * FROM account_details WHERE pin_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, pinId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next(); // If the result set is not empty, the PIN_ID exists
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false; // Return false in case of any database error
        }
    }

    private boolean storeUserData(String firstName, String lastName, String email, String dob, String password, int pinId) {
        String url = "jdbc:mysql://localhost:3306/cashcraft";
        String username = "root";
        String dbPassword = "Work34#@me/$"; // Replace with your actual database password

        try (Connection connection = DriverManager.getConnection(url, username, dbPassword)) {
            // Check if the email or pinId already exists
            if (isUserExists(email)) {
            	JOptionPane.showMessageDialog(frame, "User with this email id already exists. Please change your email.", "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println("User with this email id already exists.");
                return false;
            }

            if (isPinIdExists(pinId)) {
            	System.out.println("PIN ID already exists");
                return false;
            }

            // Store user data in account_details table
            String userInsertQuery = "INSERT INTO account_details (first_name, last_name, email, dob, password, pin_id, balance) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement userStatement = connection.prepareStatement(userInsertQuery)) {
                userStatement.setString(1, firstName);
                userStatement.setString(2, lastName);
                userStatement.setString(3, email);
                userStatement.setString(4, dob);
                userStatement.setString(5, password);
                userStatement.setInt(6, pinId);
                userStatement.setInt(7, 0); // Initial balance set to 0 for a new account

                int rowsAffected = userStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("User data inserted successfully");
                    return true;
                } else {
                    System.out.println("User data insertion failed");
                    return false;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false; // Return false in case of any database error
        }
    }

    private int generateUniquePinId() {
        int pinId;
        // Keep generating a new PIN_ID until it is unique
        do {
            pinId = 1000000 + (int) (Math.random() * 9000000);
        } while (isPinIdExists(pinId)); // Check if the generated PIN_ID already exists in the database
        return pinId;
    }

    public static void main(String[] args) {
        new CreateAccountPage();
    }
}
