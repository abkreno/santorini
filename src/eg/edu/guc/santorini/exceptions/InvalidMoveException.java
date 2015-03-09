package eg.edu.guc.santorini.exceptions;

@SuppressWarnings("serial")
public class InvalidMoveException extends Exception {

	public InvalidMoveException() {
		super();
	}

	public InvalidMoveException(String message) {
		super(message);
	}
}
