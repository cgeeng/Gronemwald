package finproject;
import java.awt.*;

import javax.swing.*;          

public class MapGUI extends JFrame{
	String country = "Gronemwald";
	int WIDTH = 500;
	int HEIGHT = 700;
	JPanel villageGraph;
	
	public MapGUI() {
		setDefaultCloseOperation(MapGUI.EXIT_ON_CLOSE);
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setVisible(true);
		setSize(WIDTH,HEIGHT);
		
		add(createHeader("Country of " + country));
		
		
	}//end constructor
	
	//methods	
	
	public JPanel createGraph() {
		JPanel graph = new JPanel;
		graph.setLayout(new GridLayout());
	}
	
	public JLabel createHeader(String text) {
		//create header JLabel
		JLabel header = new JLabel(text);
		header.setFont(new Font("Serif", Font.PLAIN, 20));
		//header.setHorizontalAlignment(JLabel.CENTER);
		return header;
	}//end createHeader method
	
}//end MapGUI class
