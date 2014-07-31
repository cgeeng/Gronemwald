package finproject;
// <<<<<<< HEAD

/*
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MapGUI implements ActionListener {
		JFrame mapFrame;
		JPanel titlePanel, mapPanel, optionsPanel;

	public MapGUI() { // builds the window
		mapFrame = new JFrame("Gnomenwald");
		mapFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mapFrame.setSize(600,400);
		mapFrame.setMinimumSize(new Dimension(800,500));

		titlePanel = new JPanel();
		titlePanel.setPreferredSize(new Dimension(800,50));
		titlePanel.setBackground(Color.DARK_GRAY);

		mapPanel = new JPanel();
		mapPanel.setPreferredSize(new Dimension(650,450));
		mapPanel.setBackground(Color.RED);

		optionsPanel = new JPanel();
		optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
		optionsPanel.setPreferredSize(new Dimension(150,450));
		optionsPanel.setBackground(Color.BLUE);

		addTitle();
		addGraph();
		addOptions();

		mapFrame.getContentPane().add(titlePanel, BorderLayout.NORTH);
		mapFrame.getContentPane().add(mapPanel, BorderLayout.CENTER);
		mapFrame.getContentPane().add(optionsPanel, BorderLayout.EAST);
        mapFrame.pack();
        mapFrame.setVisible(true);
	} // end of constructor

	public void addTitle() {
		JLabel title = new JLabel("Gnomenwald", SwingConstants.CENTER);
		title.setForeground(Color.WHITE);
		titlePanel.add(title);
	} // end of addTitle()

	public void addGraph() {

	} // end of addGraph()

	public void addOptions() {
		JButton addVillage = new JButton("Add village");
		JButton delVillage = new JButton("Delete village");
		JButton placeGnome = new JButton("Place gnome");
		JButton moveGnome = new JButton("Move gnome");

		optionsPanel.add(Box.createRigidArea(new Dimension(0,5)));
		addOptionsButton(addVillage); optionsPanel.add(Box.createRigidArea(new Dimension(0,5)));
		addOptionsButton(delVillage); optionsPanel.add(Box.createRigidArea(new Dimension(0,5)));
		addOptionsButton(placeGnome); optionsPanel.add(Box.createRigidArea(new Dimension(0,5)));
		addOptionsButton(moveGnome); optionsPanel.add(Box.createRigidArea(new Dimension(0,5)));
	} // end of addOptions()

	public void addOptionsButton(JButton button) {
		button.addActionListener(this);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		optionsPanel.add(button);
	} // end of addOptionsButton()

	public void actionPerformed(ActionEvent event) {  
        // TODO
    } // end of actionPerformed() 

	public static void createAndShowGUI() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		MapGUI map = new MapGUI();
	} // end of createAndShowGUI()

	public static void main (String [] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() { createAndShowGUI(); }
		});
	} // end of main()

} // end of class MapGUI
=======
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MapGUI implements ActionListener {
		JFrame mapFrame;
		JPanel titlePanel, mapPanel, optionsPanel;
	
	public MapGUI() { // builds the window
		mapFrame = new JFrame("Gnomenwald");
		mapFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mapFrame.setSize(600,400);
		mapFrame.setMinimumSize(new Dimension(800,500));
		
		titlePanel = new JPanel();
		titlePanel.setPreferredSize(new Dimension(800,50));
		titlePanel.setBackground(Color.DARK_GRAY);
		
		mapPanel = new JPanel();
		mapPanel.setPreferredSize(new Dimension(650,450));
		mapPanel.setBackground(Color.RED);
		
		optionsPanel = new JPanel();
		optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
		optionsPanel.setPreferredSize(new Dimension(150,450));
		optionsPanel.setBackground(Color.BLUE);
		
		addTitle();
		addGraph();
		addOptions();
		
		mapFrame.getContentPane().add(titlePanel, BorderLayout.NORTH);
		mapFrame.getContentPane().add(mapPanel, BorderLayout.CENTER);
		mapFrame.getContentPane().add(optionsPanel, BorderLayout.EAST);
        mapFrame.pack();
        mapFrame.setVisible(true);
	} // end of constructor
	
	public void addTitle() {
		JLabel title = new JLabel("Gnomenwald", SwingConstants.CENTER);
		title.setForeground(Color.WHITE);
		titlePanel.add(title);
	} // end of addTitle()
	
	public void addGraph() {
		
	} // end of addGraph()
	
	public void addOptions() {
		JButton addVillage = new JButton("Add village");
		JButton delVillage = new JButton("Delete village");
		JButton placeGnome = new JButton("Place gnome");
		JButton moveGnome = new JButton("Move gnome");
		
		optionsPanel.add(Box.createRigidArea(new Dimension(0,5)));
		addOptionsButton(addVillage); optionsPanel.add(Box.createRigidArea(new Dimension(0,5)));
		addOptionsButton(delVillage); optionsPanel.add(Box.createRigidArea(new Dimension(0,5)));
		addOptionsButton(placeGnome); optionsPanel.add(Box.createRigidArea(new Dimension(0,5)));
		addOptionsButton(moveGnome); optionsPanel.add(Box.createRigidArea(new Dimension(0,5)));
	} // end of addOptions()
	
	public void addOptionsButton(JButton button) {
		button.addActionListener(this);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		optionsPanel.add(button);
	} // end of addOptionsButton()
	
	public void actionPerformed(ActionEvent event) {  
        // TODO
    } // end of actionPerformed() 
	
	public static void createAndShowGUI() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		MapGUI map = new MapGUI();
	} // end of createAndShowGUI()
	
	public static void main (String [] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() { createAndShowGUI(); }
		});
	} // end of main()
	
} // end of class MapGUI

// >>>>>>> origin/master
