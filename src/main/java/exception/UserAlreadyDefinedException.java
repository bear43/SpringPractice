package exception;

public class UserAlreadyDefinedException extends Exception
{
    public UserAlreadyDefinedException()
    {
        super("User is already defined");
    }

    public UserAlreadyDefinedException(String message)
    {
        super(message);
    }
}
