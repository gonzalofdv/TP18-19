package tp.p2.exceptions;

public class CommandExecuteException extends Exception {
	
	private static final long serialVersionUID = -7266247483099174505L;

	public CommandExecuteException() {
	}

	public CommandExecuteException(String message) {
		super(message);
	}
}
