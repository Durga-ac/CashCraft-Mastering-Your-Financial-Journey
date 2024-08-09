import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class AccountDetailsPage extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private String userId, username;
    private JTextField userIdField, firstNameField, lastNameField, emailField, dobField, balanceField;
    private JButton editButton, updateButton, uneditButton, backButton, deleteButton;
    private String originalFirstName, originalLastName, originalEmail, originalDob;
    
    ImageIcon image1;
    Color color = new Color(96, 192, 34);
    final Font font1 = new Font("Arial Rounded MT Bold", 0, 30);
    final Font font = new Font("Arial Rounded MT Bold", 0, 20);

    public AccountDetailsPage(String userId) {
        this.userId = userId;
        setTitle("Account Details");
        setBounds(450, 150, 765, 550);
        setLayout(null);
        setResizable(false);
        
        // Create a JLabel to hold the background image
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("background.jpg"));
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 765, 550);
        setContentPane(backgroundLabel);

        // Fetch user details from the database based on userId
        String[] userDetails = JdbcConnector.getAccountDetails(userId);
        String firstName = userDetails[1];
        String lastName = userDetails[2];
        String email = userDetails[3];
        String dob = userDetails[4];
        String pinId = userDetails[5];
        String balance = userDetails[6];
        

        //page header
        JLabel titleLabel = new JLabel("Account Details");
        titleLabel.setBounds(250, 30, 320, 50);
        titleLabel.setFont(this.font1);
        titleLabel.setForeground(Color.WHITE); // Set text color to white
        add(titleLabel);

        JLabel userIdLabel = new JLabel("PIN ID:");
        userIdLabel.setBounds(150, 100, 400, 30);
        userIdLabel.setForeground(Color.WHITE); // Set text color to white
        userIdLabel.setFont(this.font);
        add(userIdLabel);

        userIdField = new JTextField(pinId);
        userIdField.setBounds(320, 100, 240, 30);
        userIdField.setBackground(Color.BLACK);
        userIdField.setForeground(Color.WHITE); // Set text color to white
        userIdField.setFont(this.font);
        userIdField.setEditable(false); // User ID is non-editable
        userIdField.setBorder(null); // Remove the border
        add(userIdField);

        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setBounds(150, 150, 400, 30);
        firstNameLabel.setForeground(Color.WHITE); // Set text color to white
        firstNameLabel.setFont(this.font);
        add(firstNameLabel);

        firstNameField = new JTextField(firstName);
        firstNameField.setBounds(320, 150, 240, 30);
        firstNameField.setFont(this.font);
        firstNameField.setEditable(false); // Initial state: non-editable
        add(firstNameField);

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setBounds(150, 200, 400, 30);
        lastNameLabel.setForeground(Color.WHITE); // Set text color to white
        lastNameLabel.setFont(this.font);
        add(lastNameLabel);

        lastNameField = new JTextField(lastName);
        lastNameField.setBounds(320, 200, 240, 30);
        lastNameField.setFont(this.font);
        lastNameField.setEditable(false); // Initial state: non-editable
        add(lastNameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(150, 250, 400, 30);
        emailLabel.setForeground(Color.WHITE); // Set text color to white
        emailLabel.setFont(this.font);
        add(emailLabel);

        emailField = new JTextField(email);
        emailField.setBounds(320, 250, 240, 30);
        emailField.setFont(this.font);
        emailField.setEditable(false); // Initial state: non-editable
        add(emailField);

        JLabel dobLabel = new JLabel("Date of Birth:");
        dobLabel.setBounds(150, 300, 400, 30);
        dobLabel.setForeground(Color.WHITE); // Set text color to white
        dobLabel.setFont(this.font);
        add(dobLabel);

        dobField = new JTextField(dob);
        dobField.setBounds(320, 300, 240, 30);
        dobField.setFont(this.font);
        dobField.setEditable(false); // Initial state: non-editable
        add(dobField);

        JLabel balanceLabel = new JLabel("Balance:");
        balanceLabel.setBounds(150, 350, 400, 30);
        balanceLabel.setForeground(Color.WHITE); // Set text color to white
        balanceLabel.setFont(this.font);
        add(balanceLabel);

        balanceField = new JTextField(balance);
        balanceField.setBounds(320, 350, 240, 30);
        balanceField.setBackground(Color.BLACK);
        balanceField.setForeground(Color.WHITE); // Set text color to white
        balanceField.setFont(this.font);
        balanceField.setEditable(false); // Balance is non-editable
        balanceField.setBorder(null); // Remove the border
        add(balanceField);

        editButton = new JButton("Edit");
        editButton.setBounds(150, 420, 80, 40);
        editButton.setBackground(color);
        editButton.setForeground(Color.WHITE);
        setFont(this.font);
        editButton.addActionListener(this);
        add(editButton);

        updateButton = new JButton("Update");
        updateButton.setBounds(250, 420, 80, 40);
        updateButton.setBackground(color);
        updateButton.setForeground(Color.WHITE);
        setFont(this.font);
        updateButton.addActionListener(this);
        updateButton.setEnabled(false); // Initially disabled until Edit button is clicked
        add(updateButton);

        uneditButton = new JButton("Unedit");
        uneditButton.setBounds(350, 420, 80, 40);
        uneditButton.setBackground(color);
        uneditButton.setForeground(Color.WHITE);
        setFont(this.font);
        uneditButton.addActionListener(this);
        uneditButton.setEnabled(false); // Initially disabled until Edit button is clicked
        add(uneditButton);
        
        deleteButton = new JButton("Delete Account");
        deleteButton.setBounds(450, 420, 120, 40);
        deleteButton.setBackground(color);
        deleteButton.setForeground(Color.WHITE);
        setFont(this.font);
        deleteButton.addActionListener(this);
        add(deleteButton);

       
        
        backButton = new JButton("<");
        backButton.setBackground(color);
        backButton.setForeground(Color.WHITE);
        backButton.setBorderPainted(false);
        backButton.setBounds(0, 0, 60, 40);
        backButton.setFont(this.font1);
        backButton.addActionListener(this);
        add(backButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == editButton) {
            // Enable fields for editing
            originalFirstName = firstNameField.getText().trim();
            originalLastName = lastNameField.getText().trim();
            originalEmail = emailField.getText().trim();
            originalDob = dobField.getText().trim();

            firstNameField.setEditable(true);
            lastNameField.setEditable(true);
            emailField.setEditable(true);
            dobField.setEditable(true);
            editButton.setEnabled(false); // Disable the Edit button
            uneditButton.setEnabled(true); // Enable the Unedit button
            updateButton.setEnabled(true); // Enable the Update button
        } else if (e.getSource() == uneditButton) {
            // Disable fields and buttons after unediting
            firstNameField.setText(originalFirstName);
            lastNameField.setText(originalLastName);
            emailField.setText(originalEmail);
            dobField.setText(originalDob);

            firstNameField.setEditable(false);
            lastNameField.setEditable(false);
            emailField.setEditable(false);
            dobField.setEditable(false);
            editButton.setEnabled(true); // Enable the Edit button
            uneditButton.setEnabled(false); // Disable the Unedit button
            updateButton.setEnabled(false); // Disable the Update button
        } else if (e.getSource() == updateButton) {
            // Confirmation dialog
            int option = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to update the details?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                // Get updated details from the fields
                String newFirstName = firstNameField.getText().trim();
                String newLastName = lastNameField.getText().trim();
                String newEmail = emailField.getText().trim();
                String newDob = dobField.getText().trim();

                // Perform the update operation in the database using userId and new values
                boolean success = JdbcConnector.updateAccountDetails(userId, newFirstName, newLastName, newEmail, newDob);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Details updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update details. Please try again.");
                }

                // Disable fields and buttons after saving
                firstNameField.setEditable(false);
                lastNameField.setEditable(false);
                emailField.setEditable(false);
                dobField.setEditable(false);
                editButton.setEnabled(true); // Enable the Edit button
                uneditButton.setEnabled(false); // Disable the Unedit button
                updateButton.setEnabled(false); // Disable the Update button
            }
        }else if (e.getSource() == deleteButton) {
            // Confirmation dialog for delete
            int option = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete your account?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                // Perform the delete operation in the database using userId
                boolean success = JdbcConnector.deleteAccount(userId);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Account deleted successfully!");
                    // Implement any additional actions after successful deletion
                    // For example, navigate back to the homepage
                    new Homepage();
                    dispose(); // Close the current frame
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete account. Please try again.");
                }
            }
        } else if (e.getSource() == backButton) {
            // Handle back button click (navigate back to transaction page)
            new transcationPage(userId, username);
            dispose(); // Close the current frame
            // Implement code to navigate back to the transaction page here
        }
    }

    public static void main(String[] args) {
        // Example usage:
        //new AccountDetailsPage("8222856");
    }
}
