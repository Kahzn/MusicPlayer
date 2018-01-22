package main;

import TCP.TCPServer;
import UDP.UDPServer;
import controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Model;
import view.View;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;


public class Main extends Application {

    public static void main(String[] args) throws Exception{
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //start TCPServer (Thread) to manage requests from Clients to access remote object
        TCPServer t = new TCPServer();
        t.start();

        LocateRegistry.createRegistry(1099);

        Model model = new Model();
        View view = new View();
        Controller controller = new Controller(t);
        Remote remote = controller;

        controller.link(model, view);
        Naming.rebind("//127.0.0.1:1099/server", remote);
        //Naming.rebind("//134.91.44.132:1099/server", remote);

        //Starte UDPServer wo Zeit von clients abgefragt werden kann
        UDPServer udpServer = new UDPServer(controller);
        Thread udpthread = new Thread(udpServer);
        udpthread.start();



        //Show GUI
        primaryStage.setTitle("Music Player");
        Scene scene = new Scene(view);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
