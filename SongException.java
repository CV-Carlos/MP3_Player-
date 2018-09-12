import java.lang.Exception;

public class SongException extends Exception
{
    private static final long serialVersionUID = 1L;

	public SongException(String message)
    {
        super(message);
    }    
}