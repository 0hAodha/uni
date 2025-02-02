package implementations;
import exceptions.InvalidCredentialsException;
import exceptions.InvalidSessionIDException;
import interfaces.*;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.io.*;

public class ApplicationHandlerImpl implements ApplicationHandler {
    private ArrayList<Long> sessions = new ArrayList<Long>();

    // private method to validate session ids
    private boolean isSessionIDValid(long sessionID) {
        // check that session is valid and has not expired
        // letting session ids expire after 60 seconds for easier testing, a more practical number would be something like 10 minutes
        return sessions.contains(sessionID) && ((System.currentTimeMillis()) - sessionID < 6000);
    }

    @Override
    public long login(String username, String password) throws RemoteException, InvalidCredentialsException {
        // hardcoded username and password (great practice for security)
        if (username.equals("admin") && password.equals("admin")) {
            long sessionId = System.currentTimeMillis(); // use current time as session id
            sessions.add(sessionId);

            System.out.println("User " + username + " logged in with session ID: " + sessionId);

            return sessionId;
        } else {
            System.out.println("Login failed with user " + username + " and password " + password);
            throw new InvalidCredentialsException("Invalid username or password.");
        }
    }

    @Override
    public ApplicationForm downloadApplicationForm(long sessionID) throws RemoteException, InvalidSessionIDException {
        // check that session is valid and has not expired
        if (isSessionIDValid(sessionID)) {
            System.out.println("Returning application form for session ID: " + sessionID);
            return new ApplicationFormV1();
        } else {
            throw new InvalidSessionIDException("Invalid session ID.");
        }
    }

    @Override
    public void submitApplicationForm(long sessionID, ApplicationForm applicationForm) throws RemoteException, InvalidSessionIDException {
        if (isSessionIDValid(sessionID)) {
            String filename = applicationForm.getName().replaceAll("\\s", "_") + ".txt";

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
                System.out.println("Saving application form to file: " + filename);
                writer.write(applicationForm.toString());
                System.out.println("Successfully saved application form to file: " + filename);
            } catch (IOException e) {
                System.out.println("Failed to save application form to file:" + filename);
                e.printStackTrace();
            }
        } else {
            throw new InvalidSessionIDException("Invalid session ID.");
        }
    }
}
