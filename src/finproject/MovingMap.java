/*
 * This should be submitted via demo ... you should schedule this with me (GOB). 
 * Demos can be done next week on Weds and Thurs afternoon or on the following Saturday, Sunday or Monday. 
 * All demos need to be completed by Tuesday 5th August. Demos should be attended by all 
 * members of the group involved. In the country of GnomenWald there lie many isolated villages
 *  connected by roads (roads only ever go from village to village, 
 *  never going nowhere nor branching out partway). The gnomes are very organised, 
 *  so name their villages by integers, starting incrementally from "1". 
 *  In the days of the Grand Archgnome Zijphraagh, a complete road map was made of GnomenWald -- 
 *  some of the gnomes were surprised to see that some villages had many roads connecting to them, 
 *  whereas some had only two, or even only one. They also noticed that some of the roads were only one-way 
 *  (often due to the rigours of the terrain, but sometimes due to political sensitivities), 
 *  so the map-makers were very careful to label all their roads as one-way 
 *  (so that a two-way road between two villages would be drawn as a pair of one-way roads in each direction). 
 *  After several generations the population had increased so much that new villages started to spring up 
 *  (though always connected via a road or roads to one or more villages in GnomenWald), 
 *  and sometimes villages would disappear 
 *  (though that made it very hard for the mapmakers to cope with roads only joining villages and never branching). 
 *  Build a data structure to represent this road map by having nodes hold villages and 
 *  expanding on the idea of 'next' in order to connect villages via roads. Test your data structure 
 *  by using it in a program which tries to move some gnomes around the villages 'along' the roads 
 *  (at this stage you don't need to 'show' them going along a road -- 
 *  simply show the succession of villages a given gnome visits). 
 *  You should also test your ability to add new villages, 
 *  you should also try to handle the case of deleting villages 
 *  (though that's an intellectually amusing problem to think through). 
 *  Now write a GUI for this with the following properties. 
 *  It should display the graph of villages and roads, and allow the construction ab initio 
 *  of any reasonable number of villages plus an easy way to build one or two way roads between villages. 
 *  It should allow adding a new village to an existing setup, and permit deletion of individual villages 
 *  (on deletion of a village, two options should be provided: 
 *  (1) any roads that went through the village should also be deleted, or 
 *  (2) any roads that went through the village en route to other villages should be made direct 
 *  (for example, if there is a road from A to B and roads from B to C and from B to D, then in case 
 *  (2) there will now be a road from A to C and A to D). 
 *  There should also be a way to place a gnome in a specified village, and a button provided to 
 *  (1) make the gnome move randomly amongst adjacent villages, and 
 *  (2) a way to tell the gnome to move to a specified adjacent village. 
 *  (The user should be able to see which willage a gnome is residing.) 
 *  Be aware that you may well want to handle multiple gnomes moving in their own threads, 
 *  so do think carefully about your data structures and how to keep your code modular. 
 *  Now enhance your program by having at least two countries (GnomenWald being one of them), 
 *  permitting multiple gnomes to move around (in their own threads), 
 *  and having reasonable limits on the number of gnomes permitted to rest in any one village (limits should vary by village). 
 *  There should be intuitive ways to build countries (and hence add/delete villages and roads). 
 *  The roads (and perhaps even the villages) should have costs (tolls ... collected by trolls?) 
 *  associated with them and you should implement topological sort 
 *  (for cases where a gnome would not be permitted to enter a particular village without having 
 *  had its passport stamped by certain other villages) shortest path 
 *  (in order to specify a minimally expensive route for a given gnome to take from its current location to its desired destination) 
 *  minimal spanning tree (to allow country governments to minimise their investment in their total road network 
 *  ... before a road is built from one village to another, the two villages will submit a 
 *  building cost proposal to the country's government (you may assume that the cost of building a road will be 
 *  100 times the toll charged for using that road), and the government will then pick from the 
 *  list of proposals those which minimise the total cost of building enough roads to connect all the villages 
 *  (at least to connect all those which could be connected if all the proposed roads were built 
 *  -- here, connected is allowed to mean connected via other villages; we're not requiring 
 *  direct roads between all villages!!!). Notice that this raises the question of how to handle the case of a collection 
 *  of villages being created and submitting a road-building proposal which connect to any of the old villages after a set 
 *  of roads have already been built. Likewise, there are questions when villages or roads are deleted. Add such additional features as you find interesting.
 */


package finproject;

public class MovingMap {

	public static void main(String[] args) {
		MapGUI hello = new MapGUI();

	}
}
