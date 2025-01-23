package exceptions;

import java.rmi.RemoteException;

public class InvalidCredentialsException extends RemoteException {
    public InvalidCredentialsException() {
        super("Invalid username or password.");
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
