
package me.senel.demo.service.exception;


public class DataAccessException extends Exception
{
	private static final long serialVersionUID = 3396269603522015473L;

	public DataAccessException(String message)
    {
        super(message);
    }
}
