package finproject;

public class Exceptions {
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
		public RoadAlreadyExistsException(int cost, int start, int end) {
			super("A road cost " + cost + " already connects villages " + start + " and " + end + ".");
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
			super("This village has already reached its limit of 10 gnomes!");
		}
	} // end of VillageFullException
	
	public static class VillageNotFoundException extends Exception {
		public VillageNotFoundException (int name) {
			super("Village "+name + " does not exist!");
		}
	}//end VillageNotFoundException
	
	public static class FileNotFoundException extends Exception {
		public FileNotFoundException () {
			super("The image file could not be found (check the path).");
		}
	}//end FileNotFoundException
	
}
