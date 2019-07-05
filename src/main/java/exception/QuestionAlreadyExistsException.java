package exception;

public class QuestionAlreadyExistsException extends Exception
{
    public QuestionAlreadyExistsException()
    {
        super("Question already exists");
    }

    public QuestionAlreadyExistsException(String message)
    {
        super(message);
    }
}
