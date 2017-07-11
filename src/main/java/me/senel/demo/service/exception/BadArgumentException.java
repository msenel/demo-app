package me.senel.demo.service.exception;

public class BadArgumentException extends Exception {

	private static final long serialVersionUID = -818540422006309916L;

	public BadArgumentException(String message) {
		super(message);
	}
}
