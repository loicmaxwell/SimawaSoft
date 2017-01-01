package exceptions;

public class WrongPasswordExecption extends Exception {
	private static final long serialVersionUID = 1L;

    public WrongPasswordExecption()
    {
    	super();
    }
    
    public WrongPasswordExecption(String message)
    {
    	super(message);
    }
}

