package exceptions;

import java.rmi.RemoteException;

public class InvalidSessionIDException extends Exception {
    public InvalidSessionIDException() {
        super("Invalid or expired session ID.");
    }

    public InvalidSessionIDException(String message) {
        super(message);
    }
}
