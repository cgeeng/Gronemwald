package finproject;

public class Exceptions {
	public static class NoOutgoingRoadsException extends Exception {
		public NoOutgoingRoadsException(String message) {
			super(message);
		}
		public NoOutgoingRoadsException(){
			super("Cannot leave this village. There are no outgoing roads.");
		}
	} // end NoOutgoingRoadsException
	
	public static class NoIncomingRoadsException extends Exception {
		public NoIncomingRoadsException(String message) {
			super(message);
		}
		public NoIncomingRoadsException(){
			super("Cannot enter this village. There are no incoming roads.");
		}
	} // end NoIncomingRoadsException
	public static class NotFoundException extends Exception {
		public NotFoundException(String message) {
			super(message);
		}
		public NotFoundException() {
			super("This village/road/gnome could not be found.");
		}
	} // end NotFoundException
	
	public static class GraphEmptyException extends Exception {
		public GraphEmptyException(String message) {
			super(message);
		}
		public GraphEmptyException() {
			super("This graph is empty.");
		}
	} // end GraphEmptyException
	
	public static class RoadAlreadyExistsException extends Exception {
		public RoadAlreadyExistsException(Village start, Village end) {
			super("A road already goes from village " + start.getName() + " to village " + end.getName() + ".");
		}
		public RoadAlreadyExistsException() {
			super("A road already goes between these two villages.");
		}
	} //end RoadAlreadyExistsException
	
	public static class SameVillageException extends Exception {
		public SameVillageException() {
			super("A road cannot lead to and from the same village.");
		}
	} // end SameVillageException
	
	public static class VillageEmptyException extends Exception {
		public VillageEmptyException() {
			super("This village is empty.");
		}
	} // end VillageEmptyException
	
	public static class VillageFullException extends Exception {
		public VillageFullException() {
			super("This village has already reached its population limit!");
		}
	} // end of VillageFullException
	
	public static class VillageNotFoundException extends Exception {
		public VillageNotFoundException (int name) {
			super("Village "+name + " does not exist!");
		}
	}//end VillageNotFoundException
	
	public static class FileNotFoundException extends Exception {
		public FileNotFoundException() {
			super("The image file could not be found (check the path).");
		}
	}//end FileNotFoundException

	public static class UnconnectedGraphException extends Exception {
		public UnconnectedGraphException() {
			super("Your graph is not connected!");
		}
	}

	public static class RoadProposalException extends Exception {
		public RoadProposalException() {
			super("A new road is being proposed.");
		}
	} // end of RoadProposalException

}
