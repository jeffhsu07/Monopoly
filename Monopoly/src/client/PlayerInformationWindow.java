package client;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import utilities.AppearanceConstants;
import utilities.AppearanceSettings;
public class PlayerInformationWindow extends JFrame {
	private JLabel usernameLabel;
	private JLabel moneyLabel;
	private JLabel jailLabel;
	private JLabel propertiesOwnedLabel;
	private JTextArea allPropertiesOwnedArea;
	private JButton closeWindowButton;

	public PlayerInformationWindow(){
		super("Player Information");
		initializeComponents();
		createGUI();
		addListeners();
	}
	
	private void initializeComponents(){
		usernameLabel = new JLabel("username");
		moneyLabel = new JLabel("<html><b>Money:</b><br>$100 </html>");
		jailLabel = new JLabel("<html><b>number of get out of jail free cards:</b><br> 1 </html>");
		propertiesOwnedLabel = new JLabel("<html><b>Properties Owned: </b></html>");
		allPropertiesOwnedArea = new JTextArea();
		closeWindowButton = new JButton("Close");
	}
	
	private void createGUI(){
		setSize(500,500);
		JPanel northPanel = new JPanel(new GridLayout(2,1));
		JPanel moneyAndJailPanel = new JPanel(new GridLayout(1,2));
		JPanel centerPanel = new JPanel();
		allPropertiesOwnedArea.setEditable(false);
		JScrollPane propertiesScrollPane = new JScrollPane(allPropertiesOwnedArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		AppearanceSettings.setTextAlignment(usernameLabel);
		AppearanceSettings.setSize(400, 400, propertiesScrollPane);
		usernameLabel.setFont(AppearanceConstants.fontLarge);
		moneyAndJailPanel.add(moneyLabel);
		moneyAndJailPanel.add(jailLabel);
		northPanel.add(usernameLabel);
		northPanel.add(moneyAndJailPanel);
		add(northPanel, BorderLayout.NORTH);
		
		centerPanel.add(propertiesOwnedLabel, BorderLayout.NORTH);
		centerPanel.add(propertiesScrollPane, BorderLayout.CENTER);
		//centerPanel.add(closeWindowButton, BorderLayout.SOUTH);
		add(centerPanel,BorderLayout.CENTER);
		add(closeWindowButton, BorderLayout.SOUTH);
		
	}
	
	private void addListeners(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		closeWindowButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				dispose();
				System.exit(0);
			}
		});
	}
}
