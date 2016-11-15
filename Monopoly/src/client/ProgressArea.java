package client;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

//Made by Jesse
public class ProgressArea extends JPanel {

	private static final long serialVersionUID = 7851410866223297293L;
	
	// Variables that are components of the progress area.
	private JLabel progressLabel;
	private JTextArea progressText;
	private JScrollPane progressArea;

	public ProgressArea() {
		initializeComponents();
		createGUI();
		addListeners();
	}

	private void initializeComponents() {
		// Set up the Progress Label.
		progressLabel = new JLabel("Game Progress");
		
		// Set up the text area.
		progressText = new JTextArea();
		progressText.setWrapStyleWord(true);
		progressText.setLineWrap(true);
		progressText.setEditable(false);
		
		// Set up the progress area and add the text to it.
		progressArea = new JScrollPane(progressText);
		progressArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
	}

	private void createGUI() {
		this.setLayout(new BorderLayout());
		
		this.add(progressLabel, BorderLayout.NORTH);
		this.add(progressArea, BorderLayout.CENTER);
	}

	private void addListeners() {
		// No action inside progress area.
	}
	
	// Adds a message to our progress dialog
	public void addProgress(String s) {
		String oldProgress = progressText.getText();
		progressText.setText(oldProgress + s);
		JScrollBar vertical = progressArea.getVerticalScrollBar();
		vertical.setValue( vertical.getMaximum() );
	}
}
