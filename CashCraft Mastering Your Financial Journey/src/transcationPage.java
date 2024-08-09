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
import java.io.Serializable;

public class transcationPage extends JFrame implements ActionListener, Serializable {
    private static final long serialVersionUID = 1L;
    private JLabel bgJLabel;
    private String userId;
    private String username;
    private JTextField usernameJTextField;
    private JButton checkBalButton;
    private JButton depositButton;
    private JButton withdrawButton;
    private JButton transferButton;
    private JButton historyButton;
    private JButton logoutButton;
    private JButton accountDetailsButton;
    private final Font font = new Font("Arial Rounded MT Bold", 0, 25);
    private final Font font1 = new Font("Arial Rounded MT Bold", 0, 20);
    private Color color = new Color(100, 149, 237);
    private Color color1 = new Color(96, 192, 34);

    public transcationPage(String userId, String username) {
        this.userId = userId;
        this.username = username;
        setTitle("TRANSACTION PAGE");
        setBounds(485, 100, 410, 700);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        //background image
        this.bgJLabel = new JLabel(new ImageIcon(getClass().getResource("background.jpg")));
        this.bgJLabel.setBounds(0, 0, 410, 700);
        add(this.bgJLabel);
        String[] customerDetails = JdbcConnector.getCustomerDetails(this.userId);
        this.usernameJTextField = new JTextField("   Welcome back!  " + customerDetails[0] + " " + customerDetails[1]);
        this.usernameJTextField.setEditable(false);
        this.usernameJTextField.setBackground(this.color);
        this.usernameJTextField.setForeground(Color.WHITE);
        this.usernameJTextField.setBounds(15, 15, 360, 45);
        this.usernameJTextField.setFont(this.font1);
        this.bgJLabel.add(this.usernameJTextField);
        
        //account detail button
        this.accountDetailsButton = new JButton("Account Details");
        this.accountDetailsButton.setFont(font);
        this.accountDetailsButton.setBounds(60, 100, 250, 50);
        this.accountDetailsButton.setBackground(color1);
        this.accountDetailsButton.setForeground(Color.WHITE);
        this.accountDetailsButton.addActionListener(this);
        this.bgJLabel.add(accountDetailsButton);
        
        //check balance button
        this.checkBalButton = new JButton("  Check Balance ");
        this.checkBalButton.setFont(this.font);
        this.checkBalButton.setBounds(60, 180, 250, 50);
        this.checkBalButton.setBackground(color1);
        this.checkBalButton.setForeground(Color.WHITE);
        this.checkBalButton.addActionListener(this);
        this.bgJLabel.add(this.checkBalButton);
        
        
        //deposit button
        this.depositButton = new JButton("  Deposit ");
        this.depositButton.setFont(this.font);
        this.depositButton.setBounds(60, 260, 250, 50);
        this.depositButton.setBackground(color1);
        this.depositButton.setForeground(Color.WHITE);
        this.depositButton.addActionListener(this);
        this.bgJLabel.add(this.depositButton);
        
        //withdraw button
        this.withdrawButton = new JButton("  Withdraw ");
        this.withdrawButton.setFont(this.font);
        this.withdrawButton.setBounds(60, 340, 250, 50);
        this.withdrawButton.setBackground(color1);
        this.withdrawButton.setForeground(Color.WHITE);
        this.withdrawButton.addActionListener(this);
        this.bgJLabel.add(this.withdrawButton);
        
        //transfer button
        this.transferButton = new JButton("Transfer Amount");
        this.transferButton.setFont(this.font);
        this.transferButton.setBounds(60, 420, 250, 50);
        this.transferButton.setBackground(color1);
        this.transferButton.setForeground(Color.WHITE);
        this.transferButton.addActionListener(this);
        this.bgJLabel.add(this.transferButton);
        
        //account history button
        this.historyButton = new JButton("Account History");
        this.historyButton.setFont(this.font);
        this.historyButton.setBounds(60, 500, 250, 50);
        this.historyButton.setBackground(color1);
        this.historyButton.setForeground(Color.WHITE);
        this.historyButton.addActionListener(this);
        this.bgJLabel.add(this.historyButton);
        
        //log out button
        this.logoutButton = new JButton("Log out");
        this.logoutButton.setFont(this.font);
        this.logoutButton.setBounds(110, 580, 150, 50);
        this.logoutButton.setBackground(color1);
        this.logoutButton.setForeground(Color.WHITE);
        this.logoutButton.addActionListener(this);
        this.bgJLabel.add(this.logoutButton);
        
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	if (e.getSource() == accountDetailsButton) {
            new AccountDetailsPage(userId);
            setVisible(false);
            dispose();
        }
    	else if (e.getSource() == this.checkBalButton) {
            String balance = JdbcConnector.getBalance(this.userId);
            if (!balance.equals("offline")) {
                JOptionPane.showMessageDialog(null, "Your balance is: " + balance);
            } else {
                JOptionPane.showMessageDialog(null, "Database connection error. Please try again later.");
            }
        } else if (e.getSource() == this.depositButton) {
        	new deposit_button(userId,username);
            setVisible(false);
            this.dispose();
        } else if (e.getSource() == this.withdrawButton) {
            new withdraw_button(userId, username);
            setVisible(false);
            this.dispose(); 
        } else if (e.getSource() == this.transferButton) {
        	new transfer_amount(userId,username);
            setVisible(false);
            this.dispose(); 
        } else if (e.getSource() == this.historyButton) {
        	new account_history(userId, username);
            setVisible(false);
            this.dispose();
        } else if (e.getSource() == this.logoutButton) {
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                new Homepage();
                dispose();
            }
        }
    }

}
