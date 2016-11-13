package client;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginWindow extends JFrame {
	
	private JPanel mainPanel;
	private JLabel alertLabel;
	private JButton loginButton;
	private JButton createAccountButton;
	private JTextField usernameTF;
	private JPasswordField passwordField;
	
	public LoginWindow() {
		setSize(600, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		initializeComponents();
		createGUI();
		addListeners();
	}
	
	private void initializeComponents() {
		mainPanel = new JPanel();
		alertLabel =new JLabel("");
		loginButton = new JButton("Log In");
		createAccountButton = new JButton("Create Account");
		usernameTF = new JTextField("Username");
		passwordField = new JPasswordField("Password");
		passwordField.setEchoChar((char) 0);
	}
	
	private void createGUI() {
		
	}
	
	private void addListeners() {
		// TODO Auto-generated method stub
		
	}
	
}
