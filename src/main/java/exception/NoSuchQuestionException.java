package exception;

public class NoSuchQuestionException extends Exception
{
    public NoSuchQuestionException()
    {
        super("No such question");
    }

    public NoSuchQuestionException(String message)
    {
        super(message);
    }
}
