package server;

import implementations.ApplicationHandlerImpl;
import interfaces.ApplicationHandler;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ApplicationServer {
    public static void main(String[] args) {
        try {
            // start RMI registry
            Registry registry = LocateRegistry.getRegistry();

            // create applicationhandlerstub and bind it
            ApplicationHandler applicationHandler = new ApplicationHandlerImpl();
            System.out.println("Instance of ApplicationHandlerImpl created");

            ApplicationHandler stub = (ApplicationHandler) UnicastRemoteObject.exportObject(applicationHandler, 0);
            Naming.rebind("ApplicationHandler", stub);
            System.out.println("Name rebind completed");

            System.out.println("Application Server up and running");
        }
        catch (Exception e) {
            System.out.println("Failed to start server");
            e.printStackTrace();
        }
    }
}
