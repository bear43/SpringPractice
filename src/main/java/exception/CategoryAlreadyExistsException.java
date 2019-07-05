package exception;

public class CategoryAlreadyExistsException extends Exception
{
    public CategoryAlreadyExistsException()
    {
        super("Category already exists");
    }

    public CategoryAlreadyExistsException(String message)
    {
        super(message);
    }
}
