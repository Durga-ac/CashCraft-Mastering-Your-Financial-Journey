import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import java.io.Serializable;
import java.awt.Font;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Homepage extends JFrame implements ActionListener, Serializable {
    private static final long serialVersionUID = 1L; // Unique identifier for the class
    private JLabel bg;
    private JLabel cashcraft;
    private JLabel bankLogoJLabel;
    private JButton userButton;
    private JLabel signUpLabel;

    public Homepage() {
        setTitle("Cashcraft: Mastering your financial journey");
        setBounds(80, 100, 1200, 600);
        setLayout(null); // Using null layout for absolute positioning
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set default close operation

        bg = new JLabel(new ImageIcon(getClass().getResource("background.jpg")));
        bg.setBounds(0, 0, 1200, 600);
        add(bg);
        
        //heading title image
        cashcraft = new JLabel(new ImageIcon(getClass().getResource("cashcraft.png")));
        cashcraft.setBounds(0, 0, 1200, 200);
        bg.add(cashcraft);
        
        //finance image
        bankLogoJLabel = new JLabel(new ImageIcon(getClass().getResource("finance.png")));
        bankLogoJLabel.setBounds(0, 300, 340, 170);
        bg.add(bankLogoJLabel);

        //login button
        userButton = new JButton(new ImageIcon(getClass().getResource("finalLogin.png")));
        userButton.setContentAreaFilled(false); // Make the content area transparent
        userButton.setBorderPainted(false); // Remove the border // Remove the border
        userButton.setBounds(480, 300, 250, 130);
        userButton.addActionListener(this);
        bg.add(userButton);
        
        signUpLabel = new JLabel("Don't have an account? Sign up");
        signUpLabel.setFont(new Font("Roboto Mono", Font.BOLD, 16));
        signUpLabel.setBounds(480, 450, 300, 20);
        signUpLabel.setForeground(Color.GREEN);
        signUpLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signUpLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // Handle click event, open account creation page
                new CreateAccountPage();
                setVisible(false);
                dispose();
            }
        });
        bg.add(signUpLabel);

        setVisible(true);
    }

    public static void main(String[] args) {
        // Use SwingUtilities.invokeLater() to ensure thread safety
        SwingUtilities.invokeLater(() -> {
            new Homepage();
        });
    }

    public void actionPerformed(ActionEvent e) {
        new user_log_in();
        setVisible(false);
        dispose();
    }
}
