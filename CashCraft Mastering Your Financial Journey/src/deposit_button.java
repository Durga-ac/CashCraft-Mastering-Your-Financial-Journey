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

public class deposit_button implements ActionListener {

    JFrame obJFrame = null;

    String id;

    String username;

    Color color = new Color(96, 192, 34);

    JTextField moneydepField;

    JButton depButton;

    JButton backJButton;

    JLabel bgJLabel;
    final Font font1 = new Font("Arial Rounded MT Bold", 0, 30);
    final Font font = new Font("Arial Rounded MT Bold", 0, 20);

    public deposit_button(String id, String username) {
        this.obJFrame = new JFrame("Deposit");
        this.id = id;
        this.username = username;
        this.obJFrame.setBounds(485, 100, 410, 600);
        this.obJFrame.setLayout((LayoutManager)null);
        this.obJFrame.setDefaultCloseOperation(3);
        this.obJFrame.setResizable(false);
        
        //background image
        this.bgJLabel = new JLabel(new ImageIcon(getClass().getResource("background.jpg")));
        this.bgJLabel.setBounds(0, 0, 410, 600);
        this.obJFrame.add(this.bgJLabel);
        
        //page header
        JLabel titleLabel = new JLabel("Deposit Amount");
        titleLabel.setBounds(80, 70, 250, 50);
        titleLabel.setFont(this.font1);
        titleLabel.setForeground(Color.WHITE); // Set text color to white
        this.bgJLabel.add(titleLabel);
        
        //deposit amount textfield
        this.moneydepField = new JTextField();
        this.moneydepField.setBounds(60, 160, 250, 50);
        this.moneydepField.setFont(this.font);
        this.bgJLabel.add(this.moneydepField);
        
        //deposit button
        this.depButton = new JButton(" Deposit ");
        this.depButton.setBackground(this.color);
        this.depButton.setForeground(Color.WHITE);
        this.depButton.setBounds(80, 260, 200, 70);
        this.depButton.setFont(this.font);
        this.depButton.setVisible(true);
        this.depButton.addActionListener(this);
        this.bgJLabel.add(this.depButton);
        
        //back button
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
        if (e.getSource() == this.depButton) {
            String depositAmount = this.moneydepField.getText();
            if (depositAmount == null || depositAmount.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter the deposit amount.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Exit the method if the amount is empty
            }

            // Validate if the input is a valid integer
            try {
                int deposit = Integer.parseInt(depositAmount);

                if (deposit > 0) {
                    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to deposit " + deposit + "?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        if (JdbcConnector.updateBalance(this.id, deposit)) {
                            JdbcConnector.insertTransactionRecord(Integer.parseInt(this.id), "Deposit", deposit, Integer.parseInt(this.id));
                            JOptionPane.showMessageDialog(null, "Deposit successful!");
                            obJFrame.setVisible(false);
                            this.obJFrame.dispose();
                            new transcationPage(id, username);
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to deposit. Please try again later.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid deposit amount. Please enter a positive amount.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid input for the deposit amount.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == this.backJButton) {
            obJFrame.setVisible(false);
            this.obJFrame.dispose();
            new transcationPage(id, username);
        }
    }


}
