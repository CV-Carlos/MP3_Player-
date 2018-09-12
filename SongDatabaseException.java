import java.lang.Exception;

public class SongDatabaseException extends Exception
{
    private static final long serialVersionUID = 1L;

	public SongDatabaseException(String message)
    {
        super(message);
    }    
}