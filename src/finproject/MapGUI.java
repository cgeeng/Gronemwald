package finproject;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.util.Random;

import finproject.Exceptions.VillageFullException;
import finproject.Exceptions.*;

public class MapGUI implements ActionListener {
		int state = 0; // holds state of application
		JFrame mapFrame;
		JPanel welcomePanel, titlePanel, mapPanel, optionsPanel;
		JButton addVillage, delVillage, placeGnome, moveGnomeSim, addRoad, welcomeButton, addCountry,
				moveGnomeExt, delRoad, startThreads;
		DrawVillage [] villCircles = new DrawVillage[20]; int arrLength=0;// used for building roads
		Graph graph; Thread g;
		Graph graph2; Thread g2;
		int numGraphs=0;
	
	public MapGUI() { // builds the main window/frame
		mapFrame = new JFrame("Gnomenwald");
		mapFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mapFrame.setPreferredSize(GUIConstants.frameSize);			
		mapFrame.setMinimumSize(GUIConstants.frameSize);
			
		mapFrame.getContentPane().repaint();
		controller();
	} // end of constructor
	
	public void controller () { // handles state changes
		if (state == GUIConstants.STATE_WELCOME) {
			welcomePanel = new JPanel();
			welcomePanel.setBackground(Color.GRAY);
			welcomePanel.setPreferredSize(GUIConstants.frameSize);
			welcomePanel.setLayout(new BorderLayout());
		
			addWelcome();
		
			mapFrame.getContentPane().add(welcomePanel, BorderLayout.CENTER);
			mapFrame.pack();
			mapFrame.setVisible(true); }
		
		else if (state == GUIConstants.STATE_ACTIVE) {
			titlePanel = new JPanel();
			titlePanel.setPreferredSize(new Dimension(800, 50));
			titlePanel.setBackground(Color.BLUE);
			
			mapPanel = new JPanel(null);
			mapPanel.setPreferredSize(new Dimension(650, 450));
			mapPanel.setLayout(new BorderLayout());
			mapPanel.setBackground(Color.GRAY);
			
			optionsPanel = new JPanel();
			optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
			optionsPanel.setPreferredSize(new Dimension(200, 450));
			optionsPanel.setBackground(Color.RED);
			
			addTitle();
			drawGraph();
			addOptions();
			
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
	
	public void createPreGraph() { // used for testing and option for user
		try {
		if (graph == null) { // creates new graph with 5 villages of population 5
			graph = new Graph();
			for (int i=0; i<5; i++) {graph.insert(new Village(5));} // 5 villages with 5 gnomes each
		}
		
		graph.find(1).connect(2, graph.find(2));
		graph.find(1).connect(4, graph.find(3));
		graph.find(2).connect(5, graph.find(4));
		graph.find(2).connect(1, graph.find(3));
		graph.find(4).connect(1, graph.find(5));
		graph.find(5).connect(1, graph.find(4));
		graph.find(5).connect(3, graph.find(3));
		
		graph.printGraph();
		graph.travelTopSort();
		} catch (RoadAlreadyExistsException | GraphEmptyException |
				NotFoundException | SameVillageException | VillageFullException e) 
		{System.out.println(e.getMessage());
		} 
	} // end of addGraph()
	
	public void createPreGraph2() { // used for testing and option for user
		try {
		if (graph2 == null) { // creates new graph with 5 villages of population 5
			graph2 = new Graph();
			for (int i=0; i<5; i++) {graph2.insert(new Village(5));} // 5 villages with 5 gnomes each
		}
		
		graph2.find(1).connect(3, graph2.find(2));
		graph2.find(2).connect(2, graph2.find(3));
		graph2.find(2).connect(1, graph2.find(5));
		graph2.find(3).connect(4, graph2.find(4));
		graph2.find(5).connect(1, graph2.find(4));
		
		graph2.printGraph();
		graph2.travelTopSort();
		} catch (RoadAlreadyExistsException | GraphEmptyException | NotFoundException |
				SameVillageException | VillageFullException e)
			{System.out.println(e.getMessage());} 
	} // end of addGraph()
	
	public void drawGraph() {
		JLabel graphLabel = new JLabel(graph.printGraph());
		graphLabel.setBorder(new EmptyBorder(20,20,20,20));
		mapPanel.add(graphLabel, BorderLayout.CENTER);
//		drawVillages(graph);
//		drawRoads(graph);
	} // end of drawGraph()
	
	public void addOptions() {
		addVillage = new JButton("Add village");
		delVillage = new JButton("Delete village");
		placeGnome = new JButton("Place new gnome");
		moveGnomeSim = new JButton("Move gnome (simple)");
		moveGnomeExt = new JButton("Move gnome (extended)");
		addRoad = new JButton("Add road");
		delRoad = new JButton("Delete road");
		addCountry = new JButton("Add country");
		startThreads = new JButton("Start threads");
		
		optionsPanel.add(Box.createRigidArea(new Dimension(0,10)));
		JLabel optionsLabel = new JLabel("OPTIONS");
		optionsLabel.setForeground(Color.WHITE);
		optionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		optionsPanel.add(optionsLabel);
		optionsPanel.add(Box.createRigidArea(new Dimension(0,30)));
		// village group
		addOptionsButton(addVillage);   optionsPanel.add(Box.createRigidArea(new Dimension(0,5)));
		addOptionsButton(delVillage);   optionsPanel.add(Box.createRigidArea(new Dimension(0,20)));
		// gnome group
		addOptionsButton(placeGnome);   optionsPanel.add(Box.createRigidArea(new Dimension(0,5)));
		addOptionsButton(moveGnomeSim); optionsPanel.add(Box.createRigidArea(new Dimension(0,5)));
		addOptionsButton(moveGnomeExt); optionsPanel.add(Box.createRigidArea(new Dimension(0,20)));
		// road group
		addOptionsButton(addRoad);      optionsPanel.add(Box.createRigidArea(new Dimension(0,5)));
		addOptionsButton(delRoad);      optionsPanel.add(Box.createRigidArea(new Dimension(0,20)));
		// country group
		addOptionsButton(addCountry);   optionsPanel.add(Box.createRigidArea(new Dimension(0,20)));
		// thread group
		addOptionsButton(startThreads);
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
        } else if (e.getSource() == moveGnomeSim) {
        	moveGnomeSim();
        } else if (e.getSource() == moveGnomeExt) {
        	moveGnomeExt();
        } else if (e.getSource() == addRoad) {
        	addRoad();
        } else if (e.getSource() == delRoad) {
        	delRoad();
        } else if (e.getSource() == addCountry) {
        	addCountry();
        } else if (e.getSource() == startThreads) {
        	startThreads();
        }
    } // end of actionPerformed() 
	
	public void startThreads() { // starts threads for simulation (villages and gnomes)
		g = new Thread(graph);
		g.start();
		drawGraph();
	}
	
	public synchronized void addVillage() {
		try {
			Object [] options = villageList();
			
			Village newVill = new Village();
			graph.insert(newVill); // default to zero gnomes
			
			String strVill = (String) JOptionPane.showInputDialog(mapFrame,
			        "Village " + newVill.getName() + " has been created with a population of zero gnomes." + 
			        		"\nThere should be a road leading to this village." +
			        		"\nWhich village would you like the new road to start from?",
			        "Adding a village", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
			if (strVill == null) {return;}
			
			Village startVill = graph.find(Integer.parseInt(strVill));
			
			String cost = (String) JOptionPane.showInputDialog(mapFrame,
					"This road will lead from village " + startVill.getName() + " to village " + newVill.getName()
					+ "\nPlease enter an integer for the toll of the new road.",
					"Adding a village", JOptionPane.PLAIN_MESSAGE);
			
			Road newRoad = startVill.connect(Integer.parseInt(cost), newVill);
			
			JOptionPane.showMessageDialog(mapFrame,
					"A new road has been created from village " + newRoad.start.getName() + 
						" to village " + newRoad.end.getName() + " at cost " + newRoad.cost,
					"Adding a village", JOptionPane.PLAIN_MESSAGE);
			
			mapPanel.removeAll(); 
			drawGraph(); 
			mapPanel.repaint(); 
			mapFrame.pack();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(mapFrame, "You did not enter an integer. Please try again.", "NumberFormatException", JOptionPane.PLAIN_MESSAGE);
		} catch (NotFoundException e) {
			JOptionPane.showMessageDialog(mapFrame, e.getMessage(), "NotFoundException", JOptionPane.PLAIN_MESSAGE);
		} catch (GraphEmptyException e) {
			JOptionPane.showMessageDialog(mapFrame, e.getMessage(), "GraphEmptyException", JOptionPane.PLAIN_MESSAGE);
		} catch (SameVillageException e) { // theoretically not possible
			JOptionPane.showMessageDialog(mapFrame, e.getMessage(), "SameVillageException", JOptionPane.PLAIN_MESSAGE);
		} catch (RoadAlreadyExistsException e) { // theoretically not possible
			JOptionPane.showMessageDialog(mapFrame, e.getMessage(), "RoadAlreadyExistsException", JOptionPane.PLAIN_MESSAGE);
		} catch (VillageFullException e) { // theoretically not possible
			JOptionPane.showMessageDialog(mapFrame, e.getMessage(), "VillageFullException", JOptionPane.PLAIN_MESSAGE);
		}
	} // end of addVillage()
	
	public void delVillage() {
		try {
			Object [] options = villageList();
			String strVillage = (String) JOptionPane.showInputDialog(mapFrame,
			        "Which village would you like to delete?",
			        "Deleting a village", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
			if (strVillage == null) {return;}
			
			Village village = graph.find(Integer.parseInt(strVillage));
			
			String [] delOptions = {"Delete all associated roads", "Reroute existing roads manually"};
			// last option could lead to WARNING message - "could lead to existing roads being deleted"
			
			String ans = (String) JOptionPane.showInputDialog(mapFrame,
			        "Please choose an option for how to deal with the existing road structure.",
			        "Deleting a village", JOptionPane.PLAIN_MESSAGE, null, delOptions, delOptions[0]);
			if (ans == null) {return;}
			
			if (ans.equals(delOptions[0])) {
				// deletes all associated roads (could be moved into village)
				RoadIterator current = village.outgoing.firstRoad;
				while (current != null) {
					village.deleteOutRoad(current.getData()); current = current.getNext();
				}
				RoadIterator current2 = village.incoming.firstRoad;
				while (current2 != null) {
					village.deleteInRoad(current2.getData()); current2 = current2.getNext();
				}
				JOptionPane.showMessageDialog(mapFrame, "All associated roads have been deleted.", 
						"Deleting a village", JOptionPane.PLAIN_MESSAGE);
			} else {
				// reroute existing roads
				if (!village.incoming.isEmpty() && !village.outgoing.isEmpty()) {
					RoadIterator outFirst = village.outgoing.getFirst();
					RoadIterator inCurrent = village.incoming.getFirst();
					RoadIterator outCurrent = village.outgoing.getFirst();
					for (int in=0; in<village.indegree; in++) {
						for (int out=0; out<village.outdegree; out++) {
							Village start = inCurrent.getData().start, end = outCurrent.getData().end; 
							if (!start.equals(end) && !graph.roadExists(start, end)) {							
							int ans2 = JOptionPane.showConfirmDialog(mapFrame, "There is no direct road yet between village " + 
										start.getName() + " and village " + end.getName() + ".\nWould you like to " + 
										"add one?", "Deleting a village", JOptionPane.YES_NO_CANCEL_OPTION);
							if (ans2 == JOptionPane.YES_OPTION) {
								String toll = JOptionPane.showInputDialog(mapFrame, "Please enter a toll for the new road.", 
										"Deleting a village", JOptionPane.PLAIN_MESSAGE);
								start.connect(Integer.parseInt(toll), end);
							} else if (ans2 == JOptionPane.NO_OPTION) {
								// do nothing, move on to next road
							} else {return;}
							} // end if !roadExists
							outCurrent = outCurrent.getNext();
						} // end while outCurrent!=null
						outCurrent = outFirst; inCurrent = inCurrent.getNext();
					} // end while inCurrent!=null
			}}
			
			graph.delete(village.getName());
			graph.printGraph();
			
			mapPanel.removeAll(); 
			drawGraph(); 
			// mapPanel.repaint(); 
			mapFrame.pack();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(mapFrame, "You did not enter an integer. Try again.", "NumberFormatException", JOptionPane.ERROR_MESSAGE);
		} catch (NotFoundException e) {
			JOptionPane.showMessageDialog(mapFrame, e.getMessage(), "NotFoundException", JOptionPane.ERROR_MESSAGE);
		} catch (GraphEmptyException e) {
			JOptionPane.showMessageDialog(mapFrame, e.getMessage(), "GraphEmptyException", JOptionPane.ERROR_MESSAGE);
		} catch (RoadAlreadyExistsException e) { // theoretically not possible
			JOptionPane.showMessageDialog(mapFrame, e.getMessage(), "RoadAlreadyExistsException", JOptionPane.ERROR_MESSAGE);
		} catch (SameVillageException e) { // theoretically not possible
			JOptionPane.showMessageDialog(mapFrame, e.getMessage(), "SameVillageException", JOptionPane.ERROR_MESSAGE);
		}
	} // end of delVillage()
	
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
			
			mapPanel.removeAll(); 
			drawGraph(); 
			// mapPanel.repaint(); 
			mapFrame.pack();
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
	
	public void moveGnomeSim() {
		try {
			if (graph.isEmpty()) {throw new GraphEmptyException();}

			Object [] villOptions = villageList();
			
			String start = (String) JOptionPane.showInputDialog(mapFrame,
			            "From which village would you like to move a gnome?",
			            "Moving a gnome", JOptionPane.PLAIN_MESSAGE, null, villOptions, villOptions[0]);
			if (start == null) {return;}
			
			Village startVillage = graph.find(Integer.parseInt(start));
			if (startVillage.outdegree == 0) {
				JOptionPane.showMessageDialog(mapFrame, "Village " + startVillage.getName() + " has no outgoing roads." + 
						"\nYou should build one!", "Moving a gnome", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			Object [] gnomeOptions = gnomeList(startVillage);
			
			String gnomeID = (String) JOptionPane.showInputDialog(mapFrame,
		            "Choose a gnome from village " + start + " to remove.",
		            "Moving a gnome", JOptionPane.PLAIN_MESSAGE, null, gnomeOptions, gnomeOptions[0]);
			if (gnomeID == null) {return;}
			
			Gnome gnome = startVillage.find(Integer.parseInt(gnomeID));
			
			String [] moveOptions = {"Choose adjacent village", "Move randomly to adjacent village"};
			
			String ans = (String) JOptionPane.showInputDialog(mapFrame,
		            "Gnome " + gnome.getID() + " has been chosen." + 
		            	"\nHow would you like to move the gnome?",
		            "Moving a gnome", JOptionPane.PLAIN_MESSAGE, null, moveOptions, moveOptions[0]);
			if (ans == null) {return;}
			
			Object [] adjVill = new Object [startVillage.outdegree];
			if (! startVillage.outgoing.isEmpty()) {
				RoadIterator current = startVillage.outgoing.getFirst();
				for (int i=0; i<adjVill.length; i++) {
					adjVill[i] = Integer.toString(current.getData().end.getName());
					current = current.getNext();
				}
			}
			
			String strEnd;
			if (ans.equals(moveOptions[0])) {
				strEnd = (String) JOptionPane.showInputDialog(mapFrame,
			            "Please choose an adjacent village to which the gnome will travel",
			            "Moving a gnome", JOptionPane.PLAIN_MESSAGE, null, adjVill, adjVill[0]);
				if (strEnd == null) {return;}
			} else {
				Random rand = new Random();
				strEnd = (String) adjVill[rand.nextInt(adjVill.length)];
			}

			Village endVillage = graph.find(Integer.parseInt(strEnd));
			
			startVillage.removeGnome(gnome);
			endVillage.insertGnome(gnome);
			
			JOptionPane.showMessageDialog(mapFrame,
	            		"Gnome " + gnome.getID() + " has been moved from village " + 
	            			startVillage.getName() + " to village " + endVillage.getName(),
	            		"Moving a gnome", JOptionPane.PLAIN_MESSAGE);
			
			mapPanel.removeAll(); 
			drawGraph(); 
			mapPanel.repaint(); 
			mapFrame.pack();
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
	} // end of moveGnomeSim()
	
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
            		+ "\nPlease enter a toll for the new road.",
            		"Building a road", JOptionPane.PLAIN_MESSAGE);
		
		int n = JOptionPane.showConfirmDialog(mapFrame,
        		"This road will cost $" + Integer.parseInt(cost)*100 + " to build." + 
        				"\nPress OK to confirm.",
        		"Building a road", JOptionPane.OK_CANCEL_OPTION);
		if (n == JOptionPane.CANCEL_OPTION) {return;}
		
		int intStart = Integer.parseInt(start);
		int intEnd = Integer.parseInt(end);
		int intCost = Integer.parseInt(cost);
			
		graph.find(intStart).connect(intCost, graph.find(intEnd));
		graph.printGraph();
		
		mapPanel.removeAll();
		drawGraph();
		mapPanel.repaint();
		mapFrame.pack();
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
	
	public void moveGnomeExt() {
		// moves gnome using shortest path and top sort
		try {
			Object [] villOptions = villageList();
			
			String strStart = (String) JOptionPane.showInputDialog(mapFrame,
			            "From which village would you like to move a gnome?",
			            "Moving a gnome", JOptionPane.PLAIN_MESSAGE, null, villOptions, villOptions[0]);
			if (strStart == null) {return;}
			
			Village startVillage = graph.find(Integer.parseInt(strStart));
			if (startVillage.outdegree == 0) {
				JOptionPane.showMessageDialog(mapFrame, "Village " + startVillage.getName() + " has no outgoing roads." + 
						"\nYou should build one!", "Moving a gnome", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			Object [] gnomeOptions = gnomeList(startVillage);
			
			String gnomeID = (String) JOptionPane.showInputDialog(mapFrame,
			        "Choose a gnome from village " + startVillage.getName() + " to remove.",
			        "Moving a gnome", JOptionPane.PLAIN_MESSAGE, null, gnomeOptions, gnomeOptions[0]);
			if (gnomeID == null) {return;}
			
			Gnome gnome = startVillage.find(Integer.parseInt(gnomeID));
			
			Object [] lessOptions = new Object [villOptions.length-1];
			int nextIndex2 = 0;
			for (int i=0; i<villOptions.length; i++) {
				if (! villOptions[i].equals(strStart)) {lessOptions[nextIndex2] = villOptions[i]; nextIndex2++;}}
			
			String strEnd = (String) JOptionPane.showInputDialog(mapFrame,
			            "Please choose the village to which the gnome will travel",
			            "Moving a gnome", JOptionPane.PLAIN_MESSAGE, null, lessOptions, lessOptions[0]);
			if (strEnd == null) {return;}
			
			Village endVillage = graph.find(Integer.parseInt(strEnd));
			System.out.println("Based on topological sort, this gnome must have visited village " +
					" " + " before visiting village " );
			
			graph.travelMinExpPath(startVillage, endVillage);
			
			startVillage.removeGnome(gnome);
			endVillage.insertGnome(gnome);
			
			mapPanel.removeAll(); 
			drawGraph(); 
			mapPanel.repaint(); 
			mapFrame.pack();	
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(mapFrame, "You did not enter an integer.  Please try again.", "NumberFormatException", JOptionPane.ERROR_MESSAGE);
		} catch (NotFoundException e) {
			JOptionPane.showMessageDialog(mapFrame, e.getMessage(), "NotFoundException", JOptionPane.ERROR_MESSAGE);
		} catch (GraphEmptyException e) {
			JOptionPane.showMessageDialog(mapFrame, e.getMessage(), "GraphEmptyException", JOptionPane.ERROR_MESSAGE);
		} catch (VillageEmptyException e) {
			JOptionPane.showMessageDialog(mapFrame, e.getMessage(), "VillageEmptyException", JOptionPane.ERROR_MESSAGE);
		} catch (SameVillageException e) {
			JOptionPane.showMessageDialog(mapFrame, e.getMessage(), "SameVillageException", JOptionPane.ERROR_MESSAGE);
		} catch (NoIncomingRoadsException e) {
			JOptionPane.showMessageDialog(mapFrame, e.getMessage(), "NoIncomingRoadsException", JOptionPane.ERROR_MESSAGE);
		} catch (NoOutgoingRoadsException e) {
			JOptionPane.showMessageDialog(mapFrame, e.getMessage(), "NoOutgoingRoadsException", JOptionPane.ERROR_MESSAGE);
		} catch (VillageFullException e) {
			JOptionPane.showMessageDialog(mapFrame, e.getMessage(), "VillageFullException", JOptionPane.ERROR_MESSAGE);
		}
	} // end of moveGnomeExt()
	
	public void delRoad() { // deletes a road
		try {
			Object [] options = villageList();
			
			String start = (String) JOptionPane.showInputDialog(mapFrame,
			        "Choose the starting village of the road you would like to delete.",
			        "Deleting a road", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
			if (start == null) {return;}

			// takes away choice of starting village
			Object [] lessOptions = new Object [options.length-1];
			int nextIndex2 = 0;
			for (int i=0; i<options.length; i++) {
				if (! options[i].equals(start)) {lessOptions[nextIndex2] = options[i]; nextIndex2++;}}

			String end = (String) JOptionPane.showInputDialog(mapFrame,
			    	"Village " + start + " was chosen as the starting village."
			    		+ "\nNow please choose the end village of the road.",
			    	"Deleting a road", JOptionPane.PLAIN_MESSAGE, null, lessOptions, lessOptions[0]);
			if (end == null) {return;}
			
			Village startVill = graph.find(Integer.parseInt(start));
			Village endVill = graph.find(Integer.parseInt(end));
			
			Road toDelete = startVill.outgoing.findRoad(endVill);
			startVill.deleteOutRoad(toDelete);
			endVill.deleteInRoad(toDelete);
			
			JOptionPane.showMessageDialog(mapFrame, "Road from village " + start + " to village " + end + " has been deleted.");
			mapPanel.removeAll(); 
			drawGraph(); 
			mapPanel.repaint(); 
			mapFrame.pack();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(mapFrame, "You did not enter an integer. Please try again.", "NumberFormatException", JOptionPane.ERROR_MESSAGE);
		} catch (NotFoundException e) {
			JOptionPane.showMessageDialog(mapFrame, e.getMessage(), "NotFoundException", JOptionPane.ERROR_MESSAGE);
		} catch (GraphEmptyException e) {
			JOptionPane.showMessageDialog(mapFrame, e.getMessage(), "GraphEmptyException", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void addCountry() {
		try {
			Object [] options = {"Use premade map", "Create my own map"};
			
			String ans = (String) JOptionPane.showInputDialog(mapFrame, "Would you like to use a premade map " + 
					" or create your own map?", "Building map", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
			if (ans == null) {return;}
			
			if (ans.equals(options[0])) {createPreGraph2();}
			else {
				String strVill = (String) JOptionPane.showInputDialog(mapFrame, "How many villages would you like to start with?" +
						" (as an integer).", "Building map", JOptionPane.PLAIN_MESSAGE);
				if (strVill == null) {return;}
				
				int numVill = Integer.parseInt(strVill);
				graph2 = new Graph();
				for (int i=0; i<numVill; i++) {graph2.insert(new Village(2));};
				
				JOptionPane.showMessageDialog(mapFrame, "A new map has been created with " + numVill + " villages." + 
							"\nNext, you should add roads to connect the two countries (see options on right).", "Building map", JOptionPane.PLAIN_MESSAGE);
			}
			
			mapPanel.removeAll();
			drawGraph(); 
			mapPanel.repaint();
			mapFrame.pack();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(mapFrame, "You did not enter an integer. Please try again.", "NumberFormatException", JOptionPane.ERROR_MESSAGE);
		} catch (VillageFullException e) {
			JOptionPane.showMessageDialog(mapFrame, e.getMessage(), "VillageFullException", JOptionPane.ERROR_MESSAGE);
		}
	} // end of addCountry()
	
	public void welcomeButton() {	
		try {
			Object [] options = {"Use premade map", "Create my own map"};
			
			String ans = (String) JOptionPane.showInputDialog(mapFrame, "Would you like to use a premade map " + 
					" or create your own map?", "Building map", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
			if (ans == null) {return;}
			
			if (ans.equals(options[0])) {createPreGraph();}
			else {
				String strVill = (String) JOptionPane.showInputDialog(mapFrame, "How many villages would you like to start with?" +
						" (as an integer).", "Building map", JOptionPane.PLAIN_MESSAGE);
				if (strVill == null) {return;}
				
				int numVill = Integer.parseInt(strVill);
				graph = new Graph();
				for (int i=0; i<numVill; i++) {graph.insert(new Village());};
				
				JOptionPane.showMessageDialog(mapFrame, "A new map has been created with " + numVill + " villages." + 
							"\nNext, you should add some roads to your map (see options on right).", "Building map", JOptionPane.PLAIN_MESSAGE);
			}
			
			welcomePanel.setVisible(false);
			state = GUIConstants.STATE_ACTIVE;
			controller();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(mapFrame, "You did not enter an integer. Please try again.", "NumberFormatException", JOptionPane.ERROR_MESSAGE);
		} catch (VillageFullException e) { // theoretically not possible
			JOptionPane.showMessageDialog(mapFrame, e.getMessage(), "VillageFullException", JOptionPane.ERROR_MESSAGE);
		}
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
	
	public void drawVillages(Graph graph) { // draws all villages currently in graph
		
		/*
		Village current = graph.getFirst();
		DrawVillage dv = new DrawVillage(null,0,0);
		// cut off around 30,30
		
		addToArray(dv);
		mapPanel.add(dv);
		// dv.setBounds(dv.x, dv.y, dv.r*2, dv.r*2);
		*/
		
		
		if (! graph.isEmpty()) {
			int r = GUIConstants.radius, x, y;
			double angle = 0, step = (2*Math.PI)/graph.getLength();
			int mapWidth = 650, mapHeight = 450; // mapPanel.getWidth(), mapHeight = mapPanel.getHeight();
			Village current = graph.getFirst();
			while (current != null) {
				x = (int) Math.round(mapWidth/2 + 3*r*Math.cos(angle)-100);
				y = (int) Math.round(mapHeight/2 + 3*r*Math.sin(angle)-50);
				System.out.println("x is: " + x + " y is: " + y);
				
				DrawVillage dv = new DrawVillage(current,x,y);
				dv.setLayout(new GridBagLayout());
				JLabel name = new JLabel(Integer.toString(dv.village.getName()));
				name.setForeground(Color.RED);
				dv.add(name);
				
				addToArray(dv);
				mapPanel.add(dv);
				dv.setBounds(dv.x, dv.y, dv.r*2, dv.r*2);
				
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
	
	public void drawRoads(Graph graph) { // draws all roads currently in graph
		if (! graph.isEmpty()) {
			int x1, y1, x2, y2;
			for (int i=0; i<arrLength; i++) {
				DrawVillage dv = villCircles[i];
				RoadIterator current = dv.village.outgoing.getFirst();
				while (current != null) {
					x1 = dv.x-100;
					y1 = dv.y-50;
					x2 = findCircle(current.getData().end).x-100;
					y2 = findCircle(current.getData().end).y-50;
					DrawRoad dr = new DrawRoad(current,x1,y1,x2,y2);
					mapPanel.add(dr);
					dr.setBounds(x1,y1,x1+x2,y1+y2);
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
		private Village village;
		private Dimension preferredSize = new Dimension(GUIConstants.radius*2, GUIConstants.radius*2);
		private int x, y, r=GUIConstants.radius;
		
		public DrawVillage(Village v) {	
			this(v, 0, 0);
		}
		
		public DrawVillage(Village v, int x, int y) {
			setPreferredSize(new Dimension(GUIConstants.radius*2, GUIConstants.radius*2));
			setBackground(Color.BLACK);
			setOpaque(true);
			
			this.village = v;
			this.x = x;
			this.y = y;
		}
		
//		public void setLocation(int x, int y) {
//			this.x= x;
//			this.y= y;
//		}
		
		@Override
		public Dimension getPreferredSize() {return preferredSize;}
		
		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2= (Graphics2D) g;
			Ellipse2D.Double circle= new Ellipse2D.Double(this.x, this.y, this.r, this.r);
			g2.setColor(Color.BLACK);
			g2.fill(circle);
		}
		
	} // end of drawVillage
	
	public class DrawRoad extends JPanel {
		RoadIterator ri;
		int x1, y1, x2, y2;
		
		public DrawRoad(RoadIterator ri) {
			this(ri,0,0,0,0);
		}
		
		public DrawRoad(RoadIterator ri, int x1, int y1, int x2, int y2) {
			setForeground(Color.DARK_GRAY);
			this.ri = ri;
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(5));
			g2.draw(new Line2D.Float(x1, y1, x2, y2));
		}
	} // end of DrawRoad
	
} // end of MapGUI()