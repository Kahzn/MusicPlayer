package main;

import TCP.TCPClient;
import controller.ClientController;
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
        RemoteButtonController remoteController =
                (RemoteButtonController) Naming.lookup(tcp.getServerName());


        Model model = new Model();
        ClientView view = new ClientView(remoteController);
        //ClientController controller = new ClientController();
        //controller.link(model, view);//??



        //Show JavaFX GUI
        primaryStage.setTitle("Music Player");
        Scene scene = new Scene(view);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
