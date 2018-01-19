package main;

import TCP.TCPClient;
import controller.ClientController;
import controller.Controller;
import interfaces.RemoteButtonController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Model;
import view.ClientView;

import java.rmi.Naming;

/**
 * Created by rebeccamarsh on 1/9/18.
 */
public class ClientMain extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //ist ok??? (war vorher Thread tcp = new TCPClient();
        TCPClient tcp = new TCPClient();
        tcp.start();

        //TCPClient braucht Zeit, den serverName über Streams zu bekommen
        while(tcp.getServerName() == null){
            Thread.sleep(1000);
            System.out.println("Sleep");
        }

        RemoteButtonController remoteController =
                (RemoteButtonController) Naming.lookup(tcp.getServerName());

        ClientView view = new ClientView(remoteController);

        //Model model = new Model();
        //ClientController controller = new ClientController();

        //Show JavaFX GUI
        primaryStage.setTitle("Music Player");
        Scene scene = new Scene(view);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
