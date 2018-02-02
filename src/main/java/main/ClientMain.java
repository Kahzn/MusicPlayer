package main;

import TCP.TCPClient;
import UDP.UDPClient;
import interfaces.RemoteButtonController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.ClientView;

import java.rmi.Naming;
import java.rmi.RemoteException;


public class ClientMain extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        TCPClient tcp = new TCPClient();
        tcp.start();

        //TCPClient verursacht Verzögerung -> warte auf tcp
        while(tcp.getServerName() == null){
            Thread.sleep(1000);
            System.out.println("Sleep");
        }

        RemoteButtonController remoteController =
                (RemoteButtonController) Naming.lookup(tcp.getServerName());

        ClientView view = new ClientView(remoteController);

        //Start UDPClient
        UDPClient udpClient = new UDPClient(view);
        new Thread(udpClient).start();

        //Show JavaFX GUI
        primaryStage.setTitle("Music Player: " + tcp.getClientName());
        Scene scene = new Scene(view);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e-> {
            try {
                remoteController.logout(tcp.getClientName()); //remove client from server's client list
                udpClient.closeRequest(); //close udp connection
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        });

    }
}
