package main;

import TCP.TCPClient;
import controller.ClientController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Model;
import view.ClientView;

/**
 * Created by rebeccamarsh on 1/9/18.
 */
public class ClientMain extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Model model = new Model();
        ClientView view = new ClientView();
        ClientController controller = new ClientController();
        controller.link(model, view);

        Thread tcp = new TCPClient();
        tcp.start();
        tcp.join();


        //Show GUI
        primaryStage.setTitle("Music Player");
        Scene scene = new Scene(view);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
