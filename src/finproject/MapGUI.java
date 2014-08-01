// round button class from http://stackoverflow.com/questions/778222/make-a-button-round

package finproject;

// >>>>>>> origin/master

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import finproject.Exceptions.*;

public class MapGUI implements ActionListener {
		int state = 0; // holds state of application
		JFrame mapFrame;
		JPanel welcomePanel, titlePanel, mapPanel, optionsPanel;
		JButton addVillage, delVillage, placeGnome, moveGnome, addRoad, welcomeButton;
		Queue graph;
	
	public MapGUI() { // builds the main window/frame
		mapFrame = new JFrame("Gnomenwald");
		mapFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mapFrame.setSize(600, 400);			
		mapFrame.setMinimumSize(new Dimension(800, 500));
			
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
	
	public void createGraph() { // TODO ask user for inputs
		if (graph == null) { // creates new graph with 5 villages of population 5
			graph = new Queue();
			for (int i=0; i<5; i++) {graph.insert(new Node(new Village(5)));}
		}
		
		// trying known values for now for testing
		try {
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
		} catch (SameVillageException e) {System.out.println(e.getMessage());}
		
		graph.printGraph();
	} // end of addGraph()
	
	public void drawGraph() {
		// TODO
	}
	
	public void addOptions() {
		addVillage = new JButton("Add village");
		delVillage = new JButton("Delete village");
		placeGnome = new JButton("Place new gnome");
		moveGnome = new JButton("Move gnome");
		addRoad = new JButton("Add road");
		
		optionsPanel.add(Box.createRigidArea(new Dimension(0,5)));
		addOptionsButton(addVillage); optionsPanel.add(Box.createRigidArea(new Dimension(0,5)));
		addOptionsButton(delVillage); optionsPanel.add(Box.createRigidArea(new Dimension(0,5)));
		addOptionsButton(placeGnome); optionsPanel.add(Box.createRigidArea(new Dimension(0,5)));
		addOptionsButton(moveGnome); optionsPanel.add(Box.createRigidArea(new Dimension(0,5)));
		addOptionsButton(addRoad); optionsPanel.add(Box.createRigidArea(new Dimension(0,5)));
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
        }
    } // end of actionPerformed() 
	
	public void addVillage() {
		System.out.println("Add village button"); // TODO options for number of gnomes, adding roads
		graph.insert(new Node(new Village())); // default to zero gnomes
	}
	
	public void delVillage() {
		System.out.println("Delete village button"); // TODO
	} 
	
	public void placeGnome() {
		try {
			if (graph.isEmpty()) {throw new GraphEmptyException();}

			Object [] options = villageList();
			
			String village = (String) JOptionPane.showInputDialog(mapFrame,
			            "Which village would you like to place the new gnome in?",
			            "Placing a new gnome", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
			if (village == null) {return;}
			
			int intVillage = Integer.parseInt(village);
			Gnome temp = new Gnome(graph.find(intVillage));
			
			JOptionPane.showMessageDialog(mapFrame,
	            		"New gnome " + temp.getID() + " has been placed in village " + intVillage,
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
	} 
	
	public void moveGnome() {
		System.out.println("Move gnome button"); // TODO
	} 
	
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
	
	public void welcomeButton() {
		welcomePanel.setVisible(false);
		state = GUIConstants.STATE_ACTIVE;
		controller();
	} // end of welcomeButton()
	
	public Object [] villageList() { // returns the villages in the graph as an array of their names
									 // used for main options buttons
		Object [] options = new Object [graph.length()];
		Node current = graph.getFirst(); int nextIndex = 0;
		for (int i=0; i<graph.length(); i++) {
			while (current != null) {
				options[nextIndex] = Integer.toString(current.getVillage().getName());
				current = current.getNext();
				nextIndex++;
			}
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
	
	public class RoundButton extends JButton {
		public RoundButton(String label) {
			super(label);
			
			Dimension size = getPreferredSize();
			size.width = size.height = Math.max(size.width, size.height);
			setContentAreaFilled(false);
		}
		
		protected void paintComponent(Graphics g) {
			g.setColor(getBackground());
			g.fillOval(0,0,getSize().width,getSize().height);
			super.paintComponent(g);
		}
		
		protected void paintBorder(Graphics g) {
			g.setColor(getForeground());
			g.drawOval(0,0,getSize().width,getSize().height);
		}
		
	} // end of class RoundButton
	
} // end of MapGUI()
// >>>>>>> origin/master
