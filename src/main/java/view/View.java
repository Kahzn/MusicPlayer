package view;

import interfaces.Song;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Model;
import model.Playlist;


public class View extends BorderPane {
    //Model model;

    private HBox hbox = new HBox();
    private ChoiceBox dropDownMenu = new ChoiceBox(
            FXCollections.observableArrayList("Binary", "XML", "JDBC", "OpenJPA"));
    private Label time = new Label("00:00");
    private Button load = new Button("load");
    private Button save = new Button("save");

    private ListView<Song> library = new ListView<>();
    private ListView<Song> playlist = new ListView<>();

    private VBox vbox = new VBox();
    private Button play = new Button(">");
    private Button pause = new Button("||");
    private Button skip = new Button(">>|");
    private Button enter = new Button("enter");

    private Button addToPlaylist = new Button("Add Song");
    private Button addAll   = new Button ("Add All Songs");
    private Button delete = new Button("Delete Song");



    public View(){
        //this.model = model;
        createTopPanel();
        createLibraryPanel();
        createPlaylistPanel();
        createRightPanel();
        createBottomPanel();


    }

    private void createRightPanel() {
        //todo
    }

    private void createBottomPanel() {
        //todo
    }

    private void createPlaylistPanel() {
        //todo

    }

    private void createLibraryPanel() {
        setCellFactory(library);
        setLeft(library);

    }

    public void setCellFactory(ListView<Song> list){
        //the setCellFactory method takes a single parameter of type Callback<P, R>.
        //this means that the java compiler can deduce that the lambda express will implement
        //the Callback interface with its one method call
        list.setCellFactory(e -> {
            ListCell<Song> cell = new ListCell<Song>() {
                @Override
                protected void updateItem(Song myObject, boolean b) {
                    super.updateItem(myObject, myObject == null || b);

                    if(myObject != null) {
                        setText(myObject.getTitle());
                        // System.out.println("Whoa "+ myObject);
                    } else {
                        setText("");
                    }
                }
            };

            return cell;
        });
    }

    private void createTopPanel() {
        hbox.getChildren().addAll(dropDownMenu, time, load, save);
        setTop(hbox);
    }

    public void bindData(Model model){
        //Sets items in listview objects so that they are filled with objects in the model,
        //listview will observe the models playlists, because the playlists are observable
        library.setItems(model.getLibrary());
        playlist.setItems(model.getPlaylist());
    }

    public Button getPlayButton(){
        return play;
    }

    //todo: Button getters ....



}
