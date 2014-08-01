package finproject;

public class Exceptions {
	public static class NotFoundException extends Exception {
		public NotFoundException(String message) {
			super(message);
		}
		public NotFoundException() {
			super("This village could not be found.");
		}
	}// end NotFoundException
	
	public static class GraphEmptyException extends Exception {
		public GraphEmptyException(String message) {
			super(message);
		}
		public GraphEmptyException() {
			super("This graph is empty.");
		}
	}// end GraphEmptyException
	
	public static class RoadAlreadyExistsException extends Exception {
		public RoadAlreadyExistsException(int cost, int start, int end) {
			super("A road " + cost + " already connects villages " + start + " and " + end + ".");
		}
	}//end RoadAlreadyExistsException
	
	public static class SameVillageException extends Exception {
		public SameVillageException() {
			super("A road cannot lead to and from the same village.");
		}
	}
}
