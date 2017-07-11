package me.senel.demo.service.exception;

public class AlreadyExistsException extends Exception {

	private static final long serialVersionUID = -6071111006820273448L;

	public AlreadyExistsException(String message) {
		super(message);
	}

	public AlreadyExistsException() {}

}
