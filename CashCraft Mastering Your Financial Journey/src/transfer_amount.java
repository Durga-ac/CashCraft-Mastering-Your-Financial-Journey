// Import statements
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

public class transfer_amount implements ActionListener {
    JFrame obJFrame = null;
    String id;
    String username;
    JTextField enteramo;
    JTextField moneydepField;
    JTextField accno;
    JTextField amo;
    JButton depButton;
    JButton backJButton;
    JLabel bgJLabel;
    JLabel transamt;
    
    Color color = new Color(96, 192, 34);
    final Font font = new Font("Arial Rounded MT Bold", 0, 20);
    final Font font1 = new Font("Arial Rounded MT Bold", 0, 30);

    public transfer_amount(String id, String username) {
        this.id = id;
        this.username = username;
        this.obJFrame = new JFrame("Transfer Amount");
        this.obJFrame.setBounds(450, 150, 765, 450);
        this.obJFrame.setLayout(null);
        this.obJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.obJFrame.setResizable(false);
        
        //background image
        this.bgJLabel = new JLabel(new ImageIcon(getClass().getResource("background.jpg")));
        this.bgJLabel.setBounds(0, 0, 765, 450);
        this.obJFrame.add(this.bgJLabel);
       
        
        //header title
        this.transamt = new JLabel("Transfer Amount");
        this.transamt.setForeground(Color.WHITE);
        this.transamt.setBounds(300, 30, 300, 50);
        this.transamt.setFont(this.font1);
        this.bgJLabel.add(this.transamt);
        
        //pin no label
        this.enteramo = new JTextField("  PIN Number :");
        this.enteramo.setEditable(false);
        this.enteramo.setBackground(this.color);
        this.enteramo.setForeground(Color.WHITE);
        this.enteramo.setBounds(80, 120, 300, 60);
        this.enteramo.setFont(this.font1);
        this.bgJLabel.add(this.enteramo);
        
        //pin no textfield
        this.accno = new JTextField();
        this.accno.setBounds(420, 120, 240, 60);
        this.accno.setFont(this.font);
        this.bgJLabel.add(this.accno);
        
        
        //tranfer amount label
        this.moneydepField = new JTextField("  Amount : ");
        this.moneydepField.setEditable(false);
        this.moneydepField.setBackground(this.color);
        this.moneydepField.setForeground(Color.WHITE);
        this.moneydepField.setBounds(80, 200, 300, 60);
        this.moneydepField.setFont(this.font1);
        this.bgJLabel.add(this.moneydepField);

        //tranfer amount textfield
        this.amo = new JTextField();
        this.amo.setBounds(420, 200, 240, 60);
        this.amo.setFont(this.font);
        this.bgJLabel.add(this.amo);

        //transfer button
        this.depButton = new JButton(" Transfer ");
        this.depButton.setBackground(this.color);
        this.depButton.setForeground(Color.WHITE);
        this.depButton.setBounds(280, 300, 250, 50);
        this.depButton.setFont(this.font1);
        this.depButton.addActionListener(this);
        this.bgJLabel.add(this.depButton);

        //back button
        this.backJButton = new JButton("<");
        this.backJButton.setBackground(this.color);
        this.backJButton.setForeground(Color.WHITE);
        this.backJButton.setBounds(0, 0, 60, 40);
        this.backJButton.setFont(this.font1);
        this.backJButton.addActionListener(this);
        this.bgJLabel.add(this.backJButton);

        this.obJFrame.setVisible(true);
    }

    public static void main(String[] args) {
        // Entry point of the application
        // You can instantiate and initialize other classes or frames here if needed
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.depButton) {
            String receiverId = this.accno.getText();
            String transferAmount = this.amo.getText();

            boolean isValidAccNumber = validate_Inputs.validate_accno(receiverId);
            boolean isValidAmount = validate_Inputs.validate_amount(transferAmount);

            if (isValidAccNumber && isValidAmount) {
                int transfer = Integer.parseInt(transferAmount);

                // Get receiver details
                String[] receiverDetails = JdbcConnector.getCustomerDetails(receiverId);
                String receiverName = receiverDetails[0] + " " + receiverDetails[1];

                int confirm = JOptionPane.showConfirmDialog(null, "Do you want to transfer " + transfer +
                        " to " + receiverName + "?"); // Confirmation dialog

                if (confirm == JOptionPane.YES_OPTION) {
                    String balance = JdbcConnector.getBalance(this.id);

                    if (!balance.equals("offline")) {
                        int currentBalance = Integer.parseInt(balance);

                        if (currentBalance >= transfer) {
                            boolean senderSuccess = JdbcConnector.updateBalance(this.id, -transfer);
                            boolean receiverSuccess = JdbcConnector.updateBalance(receiverId, transfer);

                            if (senderSuccess && receiverSuccess) {
                                JdbcConnector.insertTransactionRecord(Integer.parseInt(this.id),
                                        "Transfer", transfer, Integer.parseInt(receiverId));
                                JOptionPane.showMessageDialog(null, "Transfer successful!");
                                obJFrame.setVisible(false);
                                this.obJFrame.dispose();
                                new transcationPage(id, username);
                            } else {
                                JOptionPane.showMessageDialog(null, "Failed to transfer. Please try again later.");
                                if (!senderSuccess) {
                                    JdbcConnector.updateBalance(this.id, transfer); // Rollback the sender's balance
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Insufficient balance.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Database connection error. Please try again later.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Transfer canceled.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid account number or amount. Please check your input.");
            }
        } else if (e.getSource() == this.backJButton) {
            new transcationPage(id, username);
            obJFrame.setVisible(false);
            this.obJFrame.dispose();
        }
    }
}
