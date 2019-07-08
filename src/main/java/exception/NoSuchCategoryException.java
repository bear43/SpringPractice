package exception;

public class NoSuchCategoryException extends Exception
{
    public NoSuchCategoryException()
    {
        super("No such category");
    }

    public NoSuchCategoryException(String message)
    {
        super(message);
    }
}
