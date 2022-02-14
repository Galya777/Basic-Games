package server;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server extends Application {
    @Override
    public void start(Stage primaryStage) throws AlreadyBoundException, RemoteException {
        IServer serverInterface = new StartServer();
        try{
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("", serverInterface);
            System.setProperty("java.rmi.server.hostname","1.2.3.4");
        } catch (IOException e){
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        launch(args);
    }

}
