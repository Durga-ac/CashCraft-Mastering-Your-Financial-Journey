import java.awt.Color;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class account_history implements ActionListener {
  JLabel bg;
  JLabel bankLogoJLabel;
  
  JLabel bgr;
  
  JButton withdrawJButton;
  
  JButton depositJButton;
  
  JButton transferJButton;
  
  JButton recentJButton;
  
  JButton backJButton;
  
  String id;
  
  String username;
  
  JFrame obj;
  
  private final Font font = new Font("Arial Rounded MT Bold", 0, 25);
  private Color color = new Color(96, 192, 34);
  
  public account_history(String id, String username) {
    this.id = id;
    this.username = username;
    this.obj = new JFrame();
    String[] customerDetails = JdbcConnector.getCustomerDetails(this.id);
    this.obj.setTitle(String.valueOf(customerDetails[0] + " " + customerDetails[1]) + " ACCOUNT DETAILS");
    this.obj.setBounds(485, 100, 410, 600);
    this.obj.setLayout((LayoutManager)null);
    this.obj.setDefaultCloseOperation(3);
    
    //background image
    ImageIcon image1 = new ImageIcon(getClass().getResource("background.jpg"));
    this.bg = new JLabel(image1);
    this.bg.setBounds(0, 0, 410, 600);
    this.obj.add(this.bg);
    
    //recent transaction button
    this.recentJButton = new JButton("Recent Transcations");
    this.recentJButton.setBounds(20, 80, 355, 60);
    this.recentJButton.setFont(this.font);
    this.recentJButton.setBackground(this.color);
    recentJButton.setForeground(Color.WHITE);
    this.recentJButton.addActionListener(this);
    this.bg.add(this.recentJButton);
    
    //withdraw history button
    this.withdrawJButton = new JButton("Withdraw History");
    this.withdrawJButton.setBounds(20, 180, 355, 60);
    this.withdrawJButton.setFont(this.font);
    this.withdrawJButton.setBackground(this.color);
    withdrawJButton.setForeground(Color.WHITE);
    this.withdrawJButton.addActionListener(this);
    this.bg.add(this.withdrawJButton);
    
    //deposit history button
    this.depositJButton = new JButton("Deposits History");
    this.depositJButton.setBounds(20, 280, 355, 60);
    this.depositJButton.setFont(this.font);
    this.depositJButton.setBackground(this.color);
    depositJButton.setForeground(Color.WHITE);
    this.depositJButton.addActionListener(this);
    this.bg.add(this.depositJButton);
    
    //transfer history button
    this.transferJButton = new JButton("Transfer History");
    this.transferJButton.setBounds(20, 380, 355, 60);
    this.transferJButton.setFont(this.font);
    this.transferJButton.addActionListener(this);
    this.transferJButton.setBackground(this.color);
    transferJButton.setForeground(Color.WHITE);
    this.bg.add(this.transferJButton);
    
    //back button
    this.backJButton = new JButton(" < ");
    this.backJButton.setBounds(5, 5, 70, 30);
    this.backJButton.setFont(this.font);
    this.backJButton.addActionListener(this);
    this.backJButton.setBackground(this.color);
    backJButton.setForeground(Color.WHITE);
    this.bg.add(this.backJButton);
    this.obj.setVisible(true);
  }
  
  public static void main(String[] args) {}
  
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == this.withdrawJButton)
      tablesView.withdraw_sheet(this.id); 
    if (e.getSource() == this.depositJButton)
      tablesView.deposit_sheet(this.id); 
    if (e.getSource() == this.transferJButton)
      tablesView.transfer_sheet(this.id); 
    if (e.getSource() == this.recentJButton)
      tablesView.recent_sheet(this.id); 
    if (e.getSource() == this.backJButton) {
    	obj.setVisible(false);
    	obj.dispose();
    	new transcationPage(id,username);}
  }
}