package me.senel.demo.service.exception;


public class UserNotFoundExeption  extends Exception {

    private static final long serialVersionUID = -80133229047570831L;

    public UserNotFoundExeption(){}

    public UserNotFoundExeption(String message) {
        super(message);
    }
}
