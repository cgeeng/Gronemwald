package finproject;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

import javax.swing.*;

import finproject.Exceptions.NotFoundException;
import finproject.Exceptions.*;

public class MapGUI implements ActionListener {
		int state = 0; // holds state of application
		JFrame mapFrame;
		JPanel welcomePanel, titlePanel, mapPanel, optionsPanel;
		JButton addVillage, delVillage, placeGnome, moveGnome, addRoad, welcomeButton, addCountry;
		DrawVillage [] villCircles = new DrawVillage[20]; int arrLength=0;// used for building roads
		Graph graph;
	
	public MapGUI() { // builds the main window/frame
		mapFrame = new JFrame("Gnomenwald");
		mapFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mapFrame.setPreferredSize(GUIConstants.frameSize);			
		mapFrame.setMinimumSize(GUIConstants.frameSize);
			
		mapFrame.getContentPane().repaint();
		controller();
	} // end of constructor
	
	public void controller () { // does the heavy lifting, handles state changes
		if (state == GUIConstants.STATE_WELCOME) {
			welcomePanel = new JPanel();
			welcomePanel.setPreferredSize(GUIConstants.frameSize);
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
			mapPanel.setLayout(null);
			
			optionsPanel = new JPanel();
			optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
			optionsPanel.setPreferredSize(new Dimension(150, 450));
			optionsPanel.setBackground(Color.BLUE);
			
			addTitle();
			if (graph == null) {createGraph();}
			drawGraph();
			addOptions();
			
			mapFrame.getContentPane().add(titlePanel, BorderLayout.NORTH);
			mapFrame.getContentPane().add(mapPanel, BorderLayout.CENTER);
			mapFrame.getContentPane().add(optionsPanel, BorderLayout.EAST);
			mapFrame.pack();
			mapFrame.setVisible(true);
		}
	} // end of controller()
	
	public void update() {
		mapFrame.invalidate();
		mapPanel.repaint();
		mapFrame.validate();
		mapFrame.pack();
	}
	
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
			graph.find(4).connect(1, graph.find(5));
			graph.find(5).connect(1, graph.find(4));
			graph.find(5).connect(3, graph.find(3));
		} catch (RoadAlreadyExistsException e) {System.out.println(e.getMessage());
		} catch (GraphEmptyException e) {System.out.println(e.getMessage());
		} catch (NotFoundException e) {System.out.println(e.getMessage());
		} catch (SameVillageException e) {System.out.println(e.getMessage());
		} catch (VillageFullException e) {System.out.println(e.getMessage());} 
		
		// graph.printGraph();
	} // end of addGraph()
	
	public void drawGraph() {
		drawVillages();
		drawRoads();
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
		Village newVill = new Village();
		graph.insert(newVill); // default to zero gnomes
		
		JOptionPane.showMessageDialog(mapFrame,
        		"Village " + newVill.getName() + " has been created with a population of zero gnomes.",
        		"Adding a village", JOptionPane.PLAIN_MESSAGE);
		
		drawVillages();
		update();
	}
	
	public void delVillage() {
		try {
			Object [] options = villageList();
			String strVillage = (String) JOptionPane.showInputDialog(mapFrame,
			        "Which village would you like to delete?",
			        "Deleting a village", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
			if (strVillage == null) {return;}
			
			Village village = graph.find(Integer.parseInt(strVillage));
			
			// deletes all associated roads (could be moved into village)
			RoadIterator current = village.outgoing.firstRoad;
			while (current != null) {
				village.deleteOutRoad(current.getData()); current = current.getNext();
			}
			RoadIterator current2 = village.incoming.firstRoad;
			while (current2 != null) {
				village.deleteInRoad(current2.getData()); current2 = current2.getNext();
			}
			
			graph.delete(village.getName());
			graph.printGraph();
			
			update();
			
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
			
			JOptionPane.showMessageDialog(mapFrame,
            		"The shortest path from village " + startVillage.getName() + " to village " + 
            			endVillage.getName() + " is by paths ",
            		"Moving a gnome", JOptionPane.PLAIN_MESSAGE);
			
			startVillage.removeGnome(gnome);
			endVillage.insertGnome(gnome);
			
			JOptionPane.showMessageDialog(mapFrame,
	            		"Gnome " + gnome.getID() + " has been moved from village " + 
	            			startVillage.getName() + " to village " + endVillage.getName(),
	            		"Moving a gnome", JOptionPane.PLAIN_MESSAGE);
			
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
		
		update();
		
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
		
		update();
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
	
	public void drawVillages() { // draws all villages currently in graph
		if (! graph.isEmpty()) {
			int r = GUIConstants.radius, x, y;
			double angle = 0, step = (2*Math.PI)/graph.getLength();
			int mapWidth = 650, mapHeight = 450; // mapPanel.getWidth(), mapHeight = mapPanel.getHeight();
			Village current = graph.getFirst();
			while (current != null) {
				x = (int) Math.round(mapWidth/2 + 7*r*Math.cos(angle)); // TODO make more exact
				y = (int) Math.round(mapHeight/2 + 7*r*Math.sin(angle));
				
				DrawVillage dv = new DrawVillage(current, x, y);
				addToArray(dv);
				mapPanel.add(dv);
				dv.setBounds(dv.x-dv.r, dv.y-dv.r, dv.r*2, dv.r*2);
				
				angle += step;
				current = current.getNext();
			}
		}
	} // end of drawVillages()
	
	public void addToArray(DrawVillage dv) {
		if (arrLength<20) {
			villCircles[arrLength] = dv;
			arrLength++;
		}
	} // end of addToArray()
	
	public void drawRoads() { // draws all roads currently in graph
		if (! graph.isEmpty()) {
			int x1, y1, x2, y2;
			for (int i=0; i<arrLength; i++) {
				DrawVillage dv = villCircles[i];
				RoadIterator current = dv.village.outgoing.getFirst();
				while (current != null) {
					x1 = dv.x;
					y1 = dv.y;
					x2 = findCircle(current.getData().end).x;
					y2 = findCircle(current.getData().end).y;
					DrawRoad dr = new DrawRoad(current,x1,y1,x2,y2);
					mapPanel.add(dr);
					// dr.setBounds(x1,y1,x1+x2,y1+y2);
					current = current.getNext();
				}
			}
		}
	} // end of drawRoads()
	
	public DrawVillage findCircle(Village v) { // helper for drawRoads, finds DrawVillage instance
										       // of village (end village of road)
		try {
			for (int i=0; i<arrLength; i++) {
				if (v.equals(villCircles[i].village)) {return villCircles[i];}
			} throw new NotFoundException();
		} catch (NotFoundException e) {
			JOptionPane.showMessageDialog(mapFrame, e.getMessage(), "NotFoundException", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	} // end of findCircle()
	
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
		Dimension preferredSize = new Dimension(GUIConstants.radius*2, GUIConstants.radius*2);
		int x, y, r=GUIConstants.radius;
		
		public DrawVillage(Village v) {	
			this(v, 0, 0);
		}
		
		public DrawVillage(Village v, int x, int y) {
			setPreferredSize(new Dimension(GUIConstants.radius*2, GUIConstants.radius*2));
			setBackground(Color.WHITE);
			setOpaque(true);
			
			this.village = v;
			this.x = x;
			this.y = y;
		}
		
		@Override
		public Dimension getPreferredSize() {return preferredSize;}
		
		@Override
		protected void paintComponent(Graphics g) {
			g.setColor(getBackground());
			super.paintComponent(g);
			g.drawOval(x-r,y-r,r*2,r*2);
		}
		
	} // end of drawVillage
	
	public class DrawRoad extends JPanel {
		RoadIterator ri;
		int x1, y1, x2, y2;
		
		public DrawRoad(RoadIterator ri) {
			this(ri,0,0,0,0);
		}
		
		public DrawRoad(RoadIterator ri, int x1, int y1, int x2, int y2) {
			setForeground(Color.GRAY);
			this.ri = ri;
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(getForeground());
			g.drawLine(x1, y1, x2, y2);
		}
	} // end of DrawRoad
	
} // end of MapGUI()
