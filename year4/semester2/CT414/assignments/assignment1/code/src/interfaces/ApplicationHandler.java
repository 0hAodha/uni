package interfaces;

import exceptions.InvalidCredentialsException;
import exceptions.InvalidSessionIDException;

import java.rmi.*;

public interface ApplicationHandler extends Remote {
    long login(String username, String password) throws RemoteException, InvalidCredentialsException;

    ApplicationForm downloadApplicationForm(long sessionID) throws RemoteException, InvalidSessionIDException;

    void submitApplicationForm(long sessionID, ApplicationForm applicationForm) throws RemoteException, InvalidSessionIDException;
}
