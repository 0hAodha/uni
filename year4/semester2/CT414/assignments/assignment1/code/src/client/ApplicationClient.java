package client;

import interfaces.ApplicationForm;
import interfaces.ApplicationHandler;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ApplicationClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // connect to registry
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            System.out.println("Connected to RMI Registry");

            // look up application handler in registry
            ApplicationHandler handler = (ApplicationHandler) registry.lookup("ApplicationHandler");
            System.out.println("ApplicationHandler found in registry");

            // login
            System.out.printf("Enter your username\n> ");
            String username = scanner.nextLine();

            System.out.printf("Enter your password\n> ");
            String password = scanner.nextLine();

            long sessionID = handler.login(username, password);
            System.out.println("Successfully logged in with session ID: " + sessionID);

            // download application form
            ApplicationForm form = handler.downloadApplicationForm(sessionID);
            System.out.println("Successfully downloaded application form");

            // print form info
            System.out.println("------ FORM INFORMATION ------");
            System.out.println(form.getFormInfo());
            System.out.println("Number of questions to be answered: " + form.getTotalQuestions());
            System.out.println("------------------------------");

            // answer questiosn
            for (int i = 0; i < form.getTotalQuestions(); i++) {
                System.out.println("Q" + i + ": " + form.getQuestion(i));
                System.out.printf("A> ");
                form.answerQuestion(i, scanner.nextLine());
            }
            System.out.println("Form filled successfully.");

            // submit form
            handler.submitApplicationForm(sessionID, form);
            System.out.println("Successfully submitted application form.");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
