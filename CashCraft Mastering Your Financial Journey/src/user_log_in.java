import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.io.Serializable;
import java.awt.LayoutManager;

public class user_log_in extends JFrame implements ActionListener, Serializable {
    private static final long serialVersionUID = 1L; // Unique identifier for the class
    private JLabel background; 
    private ImageIcon image1; 
    private JLabel user;  
    private JLabel passwordlabel; 
    private JTextField fn;  
    private JButton login; 
    private JButton backJButton; 
    private JPasswordField pw; 
    private JLabel loginpageLabel, forgotPasswordLabel;
  
  Color color = new Color(96, 192, 34);
  final Font font = new Font("Arial Rounded MT Bold", 0, 20);
  final Font font1 = new Font("Arial Rounded MT Bold", 0, 30);
  
  user_log_in() {
    setTitle("Login to your Cashcraft Account");
    setBounds(450, 150, 765, 450);
    setLayout((LayoutManager)null);
    setDefaultCloseOperation(3);
    setResizable(false);
    setLocationRelativeTo(this.rootPane);
    
    //background image
    this.image1 = new ImageIcon(getClass().getResource("background.jpg"));
    this.background = new JLabel(this.image1);
    this.background.setBounds(0, 0, 765, 450);
    add(this.background);
    
    //header title
    this.loginpageLabel = new JLabel(" LOG IN PAGE");
    this.loginpageLabel.setForeground(Color.WHITE);
    this.loginpageLabel.setBounds(300, 30, 220, 50);
    this.loginpageLabel.setFont(this.font1);
    this.background.add(this.loginpageLabel);
    
    //user pin label
    this.user = new JLabel("    USER PIN");
    this.user.setOpaque(true);
    this.user.setBackground(color);
    this.user.setForeground(Color.WHITE);
    this.user.setBounds(80, 120, 300, 60);
    this.user.setFont(this.font1);
    this.background.add(this.user);
    
    //user pin textfield
    this.fn = new JTextField("");
    this.fn.setBounds(420, 120, 240, 60);
    this.fn.setFont(this.font1);
    this.background.add(this.fn);
    
    //password label
    this.passwordlabel = new JLabel("    PASSWORD");
    this.passwordlabel.setOpaque(true);
    this.passwordlabel.setBackground(color);
    this.passwordlabel.setForeground(Color.WHITE);
    this.passwordlabel.setBounds(80, 200, 300, 60);
    this.passwordlabel.setFont(this.font1);
    this.background.add(this.passwordlabel);
    
    //password textfield
    this.pw = new JPasswordField("");
    this.pw.setBounds(420, 200, 240, 60);
    this.pw.setFont(this.font);
    add(this.pw);
    
    //login button
    this.login = new JButton("LOG IN");
    this.login.setBackground(color);
    this.login.setForeground(Color.WHITE);
    this.login.setBorderPainted(false);
    this.login.setBounds(325, 300, 150, 50);
    this.login.setFont(this.font1);
    this.login.addActionListener(this);
    this.background.add(this.login);
    
 // Forgot Password label
    this.forgotPasswordLabel = new JLabel("Forgot Password?");
    this.forgotPasswordLabel.setForeground(Color.WHITE);
    this.forgotPasswordLabel.setBounds(320, 360, 250, 30);
    this.forgotPasswordLabel.setFont(this.font);
    this.forgotPasswordLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    this.forgotPasswordLabel.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
        	dispose();
            new forget_password();
        }
    });
    this.background.add(this.forgotPasswordLabel);
    
    //back button
    this.backJButton = new JButton("<");
    this.backJButton.setBackground(color);
    this.backJButton.setForeground(Color.WHITE);
    this.backJButton.setBorderPainted(false);
    this.backJButton.setBounds(0, 0, 60, 40);
    this.backJButton.setFont(this.font1);
    this.background.add(this.backJButton);
    this.backJButton.addActionListener(this);
    setVisible(true);
  }
  
  public static void main(String[] args) {
  new user_log_in();}
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == this.backJButton) {
        new Homepage();
        setVisible(false);
    	dispose(); }
      
    
    String pinidString = "", passwordString = "";
    if (e.getSource() == this.login) {
      pinidString = this.fn.getText();
      passwordString = String.valueOf(this.pw.getPassword());
      String username = JdbcConnector.databasecheck_userid_password(pinidString, passwordString);
      if (!username.equals("yes")) {
        new transcationPage(pinidString,username);
        setVisible(false);
        this.dispose();
    } 
  }}
}
