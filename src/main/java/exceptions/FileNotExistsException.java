package exceptions;


public class FileNotExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	public FileNotExistsException (String msg) {
		super(msg);
	}
}
