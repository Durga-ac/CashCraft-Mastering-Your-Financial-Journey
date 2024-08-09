import java.awt.Color;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class withdraw_button implements ActionListener {
    JFrame obJFrame = null;
    String id;
    String username;
    JTextField moneydepField;
    JButton withdrawButton;
    JButton backJButton;
    JLabel bgJLabel;
    
    Color color = new Color(96, 192, 34);
    final Font font1 = new Font("Arial Rounded MT Bold", 0, 30);
    final Font font = new Font("Arial Rounded MT Bold", 0, 20);

    public withdraw_button(String id, String username) {
        this.obJFrame = new JFrame("Withdraw");
        this.id = id;
        this.username = username;
        this.obJFrame.setBounds(485, 100, 410, 600);
        this.obJFrame.setLayout((LayoutManager) null);
        this.obJFrame.setDefaultCloseOperation(3);
        this.obJFrame.setResizable(false);
        
        //background image
        this.bgJLabel = new JLabel(new ImageIcon(getClass().getResource("background.jpg")));
        this.bgJLabel.setBounds(0, 0, 410, 600);
        this.obJFrame.add(this.bgJLabel);
        
        //page header
        JLabel titleLabel = new JLabel("Withdraw Amount");
        titleLabel.setBounds(80, 70, 250, 50);
        titleLabel.setFont(this.font1);
        titleLabel.setForeground(Color.WHITE); // Set text color to white
        this.bgJLabel.add(titleLabel);
        
        //withdraw textfield
        this.moneydepField = new JTextField();
        this.moneydepField.setBounds(60, 160, 250, 50);
        this.moneydepField.setFont(this.font);
        this.bgJLabel.add(this.moneydepField);
        
        //withdrwa button
        this.withdrawButton = new JButton(" Withdraw ");
        this.withdrawButton.setBackground(this.color);
        this.withdrawButton.setForeground(Color.WHITE);
        this.withdrawButton.setBounds(80, 260, 200, 70);
        this.withdrawButton.setFont(this.font);
        this.withdrawButton.setVisible(true);
        this.withdrawButton.addActionListener(this);
        this.bgJLabel.add(this.withdrawButton);
        
        //
        this.backJButton = new JButton("<");
        this.backJButton.setBackground(this.color);
        this.backJButton.setForeground(Color.WHITE);
        this.backJButton.setBounds(0, 0, 60, 40);
        this.backJButton.setFont(this.font1);
        this.bgJLabel.add(this.backJButton);
        this.backJButton.addActionListener(this);
        this.obJFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
    	if (e.getSource() == this.withdrawButton) {
    	    String withdrawAmount = this.moneydepField.getText();
    	    if (withdrawAmount != null && !withdrawAmount.trim().isEmpty()) {
    	        try {
    	            int withdraw = Integer.parseInt(withdrawAmount);
    	            if (withdraw > 0) {
    	                String balance = JdbcConnector.getBalance(this.id);
    	                if (!balance.equals("offline")) {
    	                    int currentBalance = Integer.parseInt(balance);
    	                    if (currentBalance >= withdraw) {
    	                        boolean success = JdbcConnector.updateBalance(this.id, -withdraw);
    	                        if (success) {
    	                            JdbcConnector.insertTransactionRecord(Integer.parseInt(this.id), "Withdraw", withdraw, Integer.parseInt(this.id));
    	                            String amountrem = JdbcConnector.getBalance(this.id);
    	                            JOptionPane.showMessageDialog(null, "Withdrawal successful! Your balance is " + amountrem + ".");
    	                            this.obJFrame.dispose();
    	                            new transcationPage(id, username);
    	                        } else {
    	                            JOptionPane.showMessageDialog(null, "Failed to withdraw. Please try again later.");
    	                        }
    	                    } else {
    	                        JOptionPane.showMessageDialog(null, "Insufficient balance.");
    	                    }
    	                } else {
    	                    JOptionPane.showMessageDialog(null, "Database connection error. Please try again later.");
    	                }
    	            } else {
    	                JOptionPane.showMessageDialog(null, "Invalid withdrawal amount. Please enter a positive amount.");
    	            }
    	        } catch (NumberFormatException ex) {
    	            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
    	        }
    	    } else {
    	        JOptionPane.showMessageDialog(null, "Please enter withdrawal amount.");
    	    }
    	} else if (e.getSource() == this.backJButton) {
            new transcationPage(id, username);
            obJFrame.setVisible(false);
            this.obJFrame.dispose();
        }
    }

    public static void main(String[] args) {
        // Main method
    }
}
