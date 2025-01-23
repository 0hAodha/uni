package interfaces;

import java.rmi.*;

public interface ApplicationForm extends Remote {

    // method to retrieve general information about the form
    String getFormInfo() throws RemoteException;

    // method to retrieve the total number of questions in the application form
    int getTotalQuestions() throws RemoteException;

    // method to retrieve a specific question based on its number
    String getQuestion(int questionNumber) throws RemoteException, IllegalArgumentException;

     // method to provide a String answer to a question based off its questionNumber
    void answerQuestion(int questionNumber, String answer) throws RemoteException, IllegalArgumentException;
}
