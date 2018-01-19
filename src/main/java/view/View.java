package view;

import interfaces.RemoteButtonController;
import interfaces.Song;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Model;

import java.rmi.RemoteException;


public class View extends BorderPane {

    private HBox hboxTop = new HBox();
    private ChoiceBox dropDownMenu = new ChoiceBox();
    private Label time = new Label("00:00");
    private Button load = new Button("load");
    private Button save = new Button("save");

    private ListView<Song> library = new ListView<>();
    private ListView<Song> playlist = new ListView<>();

    private VBox vbox = new VBox();
    private HBox hboxRight = new HBox();
    private TextField titel = new TextField("");
    private TextField interpret = new TextField("");
    private TextField album = new TextField("");
    private Text titelText = new Text("Titel");
    private Text albumText = new Text("Album");
    private Text interpretText = new Text("Interpret");
    private Button play = new Button(">");
    private Button pause = new Button("||");
    private Button skip = new Button(">>|");
    private Button enter = new Button("enter");

    private HBox hboxBottom = new HBox();
    private Button addToPlaylist = new Button("Add Song");
    private Button addAll   = new Button ("Add All Songs");
    private Button delete = new Button("Delete Song ");

    private RemoteButtonController controller;   //Für EventHandling



    public View(){

        createTopPanel();
        createLibraryPanel();
        createPlaylistPanel();
        //createRightPanel(); //do not add because the server's view has no functionality
        //createBottomPanel();//do not add becasue the server's view has no functionality
        play.setOnAction(e -> {
            try {
                controller.play(playlist.getSelectionModel().getSelectedIndex());
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        });
        addToPlaylist.setOnAction(e -> {
            try {
                controller.addToPlaylist(library.getSelectionModel().getSelectedItem());
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        });
        delete.setOnAction(e -> {
            try {
                controller.removeFromPlaylist(playlist.getSelectionModel().getSelectedItem());
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        });
        enter.setOnAction(e -> {
            try {
                controller.edit();
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        });
        addAll.setOnAction(e -> {
            try {
                controller.addAll();
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        });
        skip.setOnAction(e -> {
            try {
                controller.skip();
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        });
        pause.setOnAction(e -> {
            try {
                controller.pause();
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        });
        save.setOnAction(e -> {
            try {
                controller.save();
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        });
        load.setOnAction(e -> {
            try {
                controller.load();
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        });


    }

    private void createTopPanel() {
        hboxTop.setSpacing(10);
        hboxTop.getChildren().addAll(dropDownMenu, time, load, save);
        dropDownMenu.getItems().addAll("Binary", "XML", "JDBC", "OpenJPA");
        dropDownMenu.getSelectionModel().selectFirst();
        setTop(hboxTop);
    }

    private void createLibraryPanel() {
        setCellFactory(library);
        setLeft(library);

    }

    public void createPlaylistPanel() {
        setCellFactory(playlist);
        setCenter(playlist);

    }

    private void createRightPanel() {
        vbox.setSpacing(2);
        hboxRight.setSpacing(5);
        hboxRight.getChildren().addAll(play, pause, skip, enter);
        vbox.getChildren().addAll(titelText, titel, interpretText, interpret, albumText, album, hboxRight);
        setRight(vbox);
    }

    private void createBottomPanel() {
        hboxBottom.setSpacing(10);
        hboxBottom.getChildren().addAll(addToPlaylist, addAll, delete);
        setBottom(hboxBottom);
    }

    public void setCellFactory(ListView<Song> list){
        //setCelfactory ersetzt die Speicherreferenz des Objekts mit dem Titel, dem Albumnamen und Künstlernamen.
        list.setCellFactory(e -> {
            ListCell<Song> cell = new ListCell<Song>() {
                @Override
                protected void updateItem(Song myObject, boolean b) {
                    super.updateItem(myObject, myObject == null || b);
                    if(myObject != null) {
                        setText(myObject.getTitle() + " - " + myObject.getInterpret() + " - " + myObject.getAlbum());
                    } else {
                        setText(" ");
                    }
                }
            };
            return cell;
        });
    }

    public void bindData(Model model){
        //Sets items in listview objects so that they are filled with objects in the model,
        //listview will observe the models playlists, because the playlists are observable
        library.setItems(model.getLibrary());
        playlist.setItems(model.getPlaylist());
    }

    public void setController(RemoteButtonController controller) {
        this.controller = controller;
    }

    public ListView<Song> getPlaylist() {
        return playlist;
    }

//    public ListView<Song> getLibrary() { return library;}

    public String getTitel() {
        return titel.getText();
    }

    public String getInterpret() {
        return interpret.getText();
    }

    public String getAlbum() {
        return album.getText();
    }

    public String getSerializationType(){
       return dropDownMenu.getSelectionModel().getSelectedItem().toString();
    }


}
