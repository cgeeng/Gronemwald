package finproject;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

import finproject.Exceptions.*;

public class MapGUI implements ActionListener {
		int state = 0; // holds state of application
		JFrame mapFrame;
		JPanel welcomePanel, titlePanel, mapPanel, optionsPanel;
		JButton addVillage, delVillage, placeGnome, moveGnome, addRoad, welcomeButton, addCountry;
		Graph graph;
	
	public MapGUI() { // builds the main window/frame
		mapFrame = new JFrame("Gnomenwald");
		mapFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mapFrame.setSize(600, 400);			
		mapFrame.setMinimumSize(new Dimension(800, 500));
			
		mapFrame.getContentPane().repaint();
		controller();
	} // end of constructor
	
	public void controller () { // does the heavy lifting, handles state changes
		if (state == GUIConstants.STATE_WELCOME) {
			welcomePanel = new JPanel();
			welcomePanel.setPreferredSize(new Dimension (600,400));
			welcomePanel.setBackground(Color.GRAY);
			welcomePanel.setLayout(new BorderLayout());
		
			addWelcome();
		
			mapFrame.getContentPane().add(welcomePanel, BorderLayout.CENTER);
			mapFrame.pack();
			mapFrame.setVisible(true); }
		
		else if (state == GUIConstants.STATE_ACTIVE) {
			titlePanel = new JPanel();
			titlePanel.setPreferredSize(new Dimension(800, 50));
			titlePanel.setBackground(Color.DARK_GRAY);
			
			mapPanel = new JPanel();
			mapPanel.setPreferredSize(new Dimension(650, 450));
			mapPanel.setBackground(Color.RED);
			mapPanel.setLayout(new GridLayout(3,3));
			
			optionsPanel = new JPanel();
			optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
			optionsPanel.setPreferredSize(new Dimension(150, 450));
			optionsPanel.setBackground(Color.BLUE);
			
			addTitle();
			addOptions();
			if (graph == null) {createGraph();}
			drawGraph();
			
			mapFrame.getContentPane().add(titlePanel, BorderLayout.NORTH);
			mapFrame.getContentPane().add(mapPanel, BorderLayout.CENTER);
			mapFrame.getContentPane().add(optionsPanel, BorderLayout.EAST);
			mapFrame.pack();
			mapFrame.setVisible(true);
		}
	} // end of controller()
	
	public void addWelcome() {
		JLabel welcomeLabel = new JLabel("Welcome to Gnomenwald!", SwingConstants.CENTER);
		welcomeButton = new JButton("Click to start");
		welcomeButton.addActionListener(this);
		
		welcomePanel.add(welcomeLabel, BorderLayout.CENTER);
		welcomePanel.add(welcomeButton, BorderLayout.SOUTH);
	} // end of addWelcome()
	
	public void addTitle() {
		JLabel title = new JLabel("Gnomenwald", SwingConstants.CENTER);
		title.setForeground(Color.WHITE);
		titlePanel.add(title);
	} // end of addTitle()
	
	public void createGraph() { // used for testing, will change to user input
		try {
		if (graph == null) { // creates new graph with 5 villages of population 5
			graph = new Graph();
			for (int i=0; i<5; i++) {graph.insert(new Village(5));}
		}
		
		// trying known values for now for testing
			graph.find(1).connect(2, graph.find(2));
			graph.find(1).connect(4, graph.find(3));
			graph.find(2).connect(5, graph.find(4));
			graph.find(2).connect(1, graph.find(3));
			graph.find(4).connect(1, graph.find(5)); // two-way road
			graph.find(5).connect(1, graph.find(4)); // two-way road
			graph.find(5).connect(3, graph.find(3));
		} catch (RoadAlreadyExistsException e) {System.out.println(e.getMessage());
		} catch (GraphEmptyException e) {System.out.println(e.getMessage());
		} catch (NotFoundException e) {System.out.println(e.getMessage());
		} catch (SameVillageException e) {System.out.println(e.getMessage());
		} catch (VillageFullException e) {System.out.println(e.getMessage());} 
		
		graph.printGraph();
	} // end of addGraph()
	
	public void drawGraph() {
		if (! graph.isEmpty()) {
			Village currentVill = graph.getFirst();
			while (currentVill != null) {
				new DrawVillage(currentVill);
				/*
				if (! currentVill.outgoing.isEmpty()) {
					RoadIterator currentRoad = currentVill.outgoing.getFirst();
					while (currentRoad != null) {
						DrawRoad newRoad = new DrawRoad(currentRoad);
						mapPanel.add(newRoad);
						currentRoad = currentRoad.getNext();
					}
				}*/
				
				currentVill = currentVill.getNext();
			}
		}
	} // end of drawGraph()
	
	public void addOptions() {
		addVillage = new JButton("Add village");
		delVillage = new JButton("Delete village");
		placeGnome = new JButton("Place new gnome");
		moveGnome = new JButton("Move gnome");
		addRoad = new JButton("Add road");
		addCountry = new JButton("Add country");
		
		optionsPanel.add(Box.createRigidArea(new Dimension(0,5)));
		addOptionsButton(addVillage); optionsPanel.add(Box.createRigidArea(new Dimension(0,5)));
		addOptionsButton(delVillage); optionsPanel.add(Box.createRigidArea(new Dimension(0,5)));
		addOptionsButton(placeGnome); optionsPanel.add(Box.createRigidArea(new Dimension(0,5)));
		addOptionsButton(moveGnome);  optionsPanel.add(Box.createRigidArea(new Dimension(0,5)));
		addOptionsButton(addRoad);    optionsPanel.add(Box.createRigidArea(new Dimension(0,5)));
		addOptionsButton(addCountry); optionsPanel.add(Box.createRigidArea(new Dimension(0,5)));
	} // end of addOptions()
	
	public void addOptionsButton(JButton button) {
		button.addActionListener(this);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		optionsPanel.add(button);
	} // end of addOptionsButton()
	
	public void actionPerformed(ActionEvent e) { 
		if (e.getSource() == welcomeButton) {
			welcomeButton();
		} else if (e.getSource() == addVillage) {
        	addVillage();
        } else if (e.getSource() == delVillage) {
        	delVillage();
        } else if (e.getSource() == placeGnome) {
        	placeGnome();
        } else if (e.getSource() == moveGnome) {
        	moveGnome();
        } else if (e.getSource() == addRoad) {
        	addRoad();
        } else if (e.getSource() == addCountry) {
        	addCountry();
        }
    } // end of actionPerformed() 
	
	public void addVillage() {
		Village temp = new Village();
		graph.insert(temp); // default to zero gnomes
		
		DrawVillage newVill = new DrawVillage(temp);
		mapPanel.add(newVill);
		
		JOptionPane.showMessageDialog(mapFrame,
        		"Village " + temp.getName() + " has been created with a population of zero gnomes.",
        		"Adding a village", JOptionPane.PLAIN_MESSAGE);
		
		mapFrame.repaint();
	}
	
	public void delVillage() {
		try {
			Object [] options = villageList();
			String strVillage = (String) JOptionPane.showInputDialog(mapFrame,
			        "Which village would you like to delete?",
			        "Deleting a village", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
			if (strVillage == null) {return;}
			
			Village village = graph.find(Integer.parseInt(strVillage));
			
			// deletes all associated roads
			RoadIterator current = village.outgoing.firstRoad;
			for (int i=0; i<village.outgoing.length; i++) {
				village.deleteRoad(current.getData()); current = current.getNext();
			}
			
			graph.delete(village.getName());
			graph.printGraph();
			
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(mapFrame, "You did not enter an integer. Try again.", "NumberFormatException", JOptionPane.ERROR_MESSAGE);
		} catch (NotFoundException e) {
			JOptionPane.showMessageDialog(mapFrame, e.getMessage(), "NotFoundException", JOptionPane.ERROR_MESSAGE);
		} catch (GraphEmptyException e) {
			JOptionPane.showMessageDialog(mapFrame, e.getMessage(), "GraphEmptyException", JOptionPane.ERROR_MESSAGE);
		}
	} 
	
	public void placeGnome() {
		try {
			if (graph.isEmpty()) {throw new GraphEmptyException();}

			Object [] options = villageList();
			
			String strVillage = (String) JOptionPane.showInputDialog(mapFrame,
			            "Which village would you like to place the new gnome in?",
			            "Placing a new gnome", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
			if (strVillage == null) {return;}
			
			Village village = graph.find(Integer.parseInt(strVillage));
			village.printGnomes();
			village.insertGnome(new Gnome());
			village.printGnomes();
			
			JOptionPane.showMessageDialog(mapFrame,
	            		"A new gnome has been placed in village " + village.getName(),
	            		"Placing a gnome", JOptionPane.PLAIN_MESSAGE);
			
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(mapFrame, "You did not enter an integer. Try again.", "NumberFormatException", JOptionPane.ERROR_MESSAGE);
			} catch (GraphEmptyException e) {
				JOptionPane.showMessageDialog(mapFrame, e.getMessage(), "GraphEmptyException", JOptionPane.ERROR_MESSAGE);
			} catch (NotFoundException e) { // theoretically not possible
				JOptionPane.showMessageDialog(mapFrame, e.getMessage(), "NotFoundException", JOptionPane.ERROR_MESSAGE);
			} catch (VillageFullException e) {
				JOptionPane.showMessageDialog(mapFrame, e.getMessage(), "VillageFullException", JOptionPane.ERROR_MESSAGE);
			}
	} // end of placeGnome()
	
	public void moveGnome() {
		try {
			if (graph.isEmpty()) {throw new GraphEmptyException();}

			Object [] villOptions = villageList();
			
			String start = (String) JOptionPane.showInputDialog(mapFrame,
			            "From which village would you like to move a gnome?",
			            "Moving a gnome", JOptionPane.PLAIN_MESSAGE, null, villOptions, villOptions[0]);
			if (start == null) {return;}
			
			Village startVillage = graph.find(Integer.parseInt(start));
			startVillage.printGnomes();
			
			Object [] gnomeOptions = gnomeList(startVillage);
			
			String gnomeID = (String) JOptionPane.showInputDialog(mapFrame,
		            "Choose a gnome from village " + start + " to remove.",
		            "Moving a gnome", JOptionPane.PLAIN_MESSAGE, null, gnomeOptions, gnomeOptions[0]);
			if (gnomeID == null) {return;}
			
			Gnome gnome = startVillage.find(Integer.parseInt(gnomeID));
			
			// takes away choice of starting village
			Object [] lessOptions = new Object [villOptions.length-1];
			int nextIndex2 = 0;
			for (int i=0; i<villOptions.length; i++) {
				if (! villOptions[i].equals(start)) {lessOptions[nextIndex2] = villOptions[i]; nextIndex2++;}}
			
			String end = (String) JOptionPane.showInputDialog(mapFrame,
		            "Gnome " + gnome.getID() + " has been chosen." + 
		            	"\nAnd to which village would you like to move the gnome?",
		            "Moving a gnome", JOptionPane.PLAIN_MESSAGE, null, lessOptions, lessOptions[0]);
			if (end == null) {return;}

			Village endVillage = graph.find(Integer.parseInt(end));
			endVillage.printGnomes();

			startVillage.removeGnome(gnome);
			endVillage.insertGnome(gnome);
			
			startVillage.printGnomes();
			endVillage.printGnomes();
			
			JOptionPane.showMessageDialog(mapFrame,
	            		"Gnome " + gnome.getID() + " has been moved from village " + 
	            			startVillage.getName() + " to village " + endVillage.getName(),
	            		"Placing a gnome", JOptionPane.PLAIN_MESSAGE);
			
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(mapFrame, "You did not enter an integer. Try again.", "NumberFormatException", JOptionPane.ERROR_MESSAGE);
			} catch (GraphEmptyException e) {
				JOptionPane.showMessageDialog(mapFrame, e.getMessage(), "GraphEmptyException", JOptionPane.ERROR_MESSAGE);
			} catch (NotFoundException e) { // theoretically not possible
				JOptionPane.showMessageDialog(mapFrame, e.getMessage(), "NotFoundException", JOptionPane.ERROR_MESSAGE);
			} catch (VillageFullException e) {
				JOptionPane.showMessageDialog(mapFrame, e.getMessage(), "VillageFullException", JOptionPane.ERROR_MESSAGE);
			} catch (VillageEmptyException e) { // should go back to first screen to choose another village
				JOptionPane.showMessageDialog(mapFrame, e.getMessage(), "VillageEmptyException", JOptionPane.ERROR_MESSAGE);
			}
	} // end of moveGnome()
	
	public void addRoad() { 
		try {
		if (graph.isEmpty()) {throw new GraphEmptyException();}
		
		Object [] options = villageList();
		
		String start = (String) JOptionPane.showInputDialog(mapFrame,
		            "Please choose the village you would like \nthe road to start at:",
		            "Building a road", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		if (start == null) {return;}
		
		// takes away choice of starting village
		Object [] lessOptions = new Object [options.length-1];
		int nextIndex2 = 0;
		for (int i=0; i<options.length; i++) {
			if (! options[i].equals(start)) {lessOptions[nextIndex2] = options[i]; nextIndex2++;}}
		
		String end = (String) JOptionPane.showInputDialog(mapFrame,
                	"Village " + start + " was chosen as the starting village."
                		+ "\nNow please choose the end village.",
                	"Building a road", JOptionPane.PLAIN_MESSAGE, null, lessOptions, lessOptions[0]);
		if (end == null) {return;}
		
		String cost = (String) JOptionPane.showInputDialog(mapFrame,
            		"This road will lead from village " + start + " to village " + end
            		+ "\nPlease enter an integer for the toll of the new road.",
            		"Building a road", JOptionPane.PLAIN_MESSAGE);
		
		int intStart = Integer.parseInt(start);
		int intEnd = Integer.parseInt(end);
		int intCost = Integer.parseInt(cost);
			
		graph.find(intStart).connect(intCost, graph.find(intEnd));
		graph.printGraph();
		
		} catch (RoadAlreadyExistsException e) {
			JOptionPane.showMessageDialog(mapFrame, e.getMessage(), "RoadAlreadyExistsException", JOptionPane.ERROR_MESSAGE);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(mapFrame, "You did not enter an integer. Try again.", "NumberFormatException", JOptionPane.ERROR_MESSAGE);
		} catch (GraphEmptyException e) {
			JOptionPane.showMessageDialog(mapFrame, e.getMessage(), "GraphEmptyException", JOptionPane.ERROR_MESSAGE);
		} catch (NotFoundException e) { // theoretically not possible
			JOptionPane.showMessageDialog(mapFrame, e.getMessage(), "NotFoundException", JOptionPane.ERROR_MESSAGE);
		} catch (SameVillageException e) { // theoretically not possible
			JOptionPane.showMessageDialog(mapFrame, e.getMessage(), "SameVillageException", JOptionPane.ERROR_MESSAGE);
		}
	} // end of addRoad()
	
	public void addCountry() {
		System.out.println("Adding a country");
		Graph graph2 = new Graph();
	} // end of addCountry()
	
	public void welcomeButton() {
		welcomePanel.setVisible(false);
		state = GUIConstants.STATE_ACTIVE;
		controller();
	} // end of welcomeButton()
	
	public Object [] villageList() { // returns the villages in the graph as an array of their names
									 // used for main options buttons
		Object [] options = new Object [graph.getLength()];
		Village current = graph.getFirst(); int nextIndex = 0;
		for (int i=0; i<graph.getLength(); i++) {
			while (current != null) {
				options[nextIndex] = Integer.toString(current.getName());
				current = current.getNext();
				nextIndex++;
			}
		}
		return options;
	} // end of villageList()
	
	public Object [] gnomeList(Village v) {
		Object [] options = new Object[v.populationSize];
		
		for (int i=0; i<v.populationSize; i++) {
			options[i] = Integer.toString(v.population[i].getID());
		}
		return options;
	}
	
	public static void createAndShowGUI() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		MapGUI map = new MapGUI();
	} // end of createAndShowGUI()
	
	public static void main (String [] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() { createAndShowGUI(); }
		});
	} // end of main()
	
	public class DrawVillage extends JPanel {
		Village village;
		BufferedImage img;
		
		public DrawVillage(Village v) {
			this.village = v;
			
			ImageIcon icon = new ImageIcon(URL);
			JLabel label = new JLabel(Integer.toString(village.getName()), icon, JLabel.CENTER);
			mapPanel.add(label);
		}
		
		public Dimension getPreferredSize() {
			return new Dimension(40,40);
		}
		
		/* 
		public DrawVillage(Village v) {	
			setPreferredSize(new Dimension(40,40));
			setBackground(Color.WHITE);
			setOpaque(true);
			
			this.village = v;
		}
	
		
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2D = (Graphics2D) g.create();
			
			Shape circle = new Ellipse2D.Double(0,0,40,40);
			g2D.setColor(getBackground());
			g2D.fill(circle);
			
			ShapeIcon circleIcon = new ShapeIcon(circle);
			JLabel circleLabel = new JLabel(circleIcon);
			
			g2D.dispose();
		}
		*/
	} // end of drawVillage
	
	public class DrawRoad extends JPanel {
		RoadIterator ri;
		
		public DrawRoad(RoadIterator ri) {
			setBackground(Color.WHITE);
			this.ri = ri;
		}
		
		protected void paintComponent(Graphics g) {
			Graphics2D g2D = (Graphics2D) g;
			g2D.setColor(getBackground());
			
			
			
			// g2D.drawLine(x1, y1, x2, y2);
		}
	} // end of DrawRoad
	
} // end of MapGUI()
