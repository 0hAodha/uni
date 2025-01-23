package implementations;

import interfaces.*;
import java.util.*;
import java.rmi.*;

public class ApplicationFormV1 implements ApplicationForm {
    private String formInfo = "University of Galway Application Form - Version 1";
    private String[] questions = new String[5];
    private String[] answers = new String[5];

    public ApplicationFormV1() {
        questions[0] = "What is your full name?";
        questions[1] = "What is your address?";
        questions[2] = "What is your e-mail address?";
        questions[3] = "What is your contact number?";
        questions[4] = "Provide a personal statement (e.g., additional details about yourself, a summary of your existing qualifications or results).";
    }

    @Override
    public String getFormInfo() throws RemoteException {
        return formInfo;
    }

    @Override
    public int getTotalQuestions() throws RemoteException {
        return questions.length;
    }

    @Override
    public String getQuestion(int questionNumber) throws RemoteException, IllegalArgumentException {
        if (questionNumber < 0 || questionNumber > questions.length) {
                throw new IllegalArgumentException("Invalid question number: " + questionNumber);
        }
        return questions[questionNumber];
    }

    @Override
    public void answerQuestion(int questionNumber, String answer) throws RemoteException, IllegalArgumentException {
        if (questionNumber < 0 || questionNumber > questions.length) {
            throw new IllegalArgumentException("Invalid question number: " + questionNumber);
        }
        answers[questionNumber] = answer;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < questions.length; i++) {
            sb.append("Q").append(i).append(": ").append(questions[i]).append("\n");
            sb.append("A").append(i).append(": ").append(answers[i]).append("\n\n");
        }

        return sb.toString();
    }
}
