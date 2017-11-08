package main;

import controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Model;
import view.View;


public class Main extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Model model = new Model();
        View view = new View();
        Controller controller = new Controller();
        controller.link(model, view);

        //Show GUI
        primaryStage.setTitle("Music Player");
        Scene scene = new Scene(view);
        primaryStage.setScene(scene);
        //primaryStage.setResizable(false);
        primaryStage.show();

    }
}
