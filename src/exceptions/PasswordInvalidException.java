package exceptions;

public class PasswordInvalidException extends Exception {

    private static final long serialVersionUID = 1L;

    public PasswordInvalidException()
    {
    	super();
    }
    
    public PasswordInvalidException(String message)
    {
    	super(message);
    }
}
