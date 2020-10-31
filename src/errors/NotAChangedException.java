package errors;
/**
 * @author abuzreaq
 *
 */
@SuppressWarnings("serial")
public class NotAChangedException extends Exception {
	
	private String message;
	public NotAChangedException(String message) {
		super();
		this.message = message;
	}
	
	public String getMessage(){
		return this.message;
	}

}
