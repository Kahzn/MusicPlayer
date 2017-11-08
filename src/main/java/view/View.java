package view;

import controller.Controller;
import interfaces.ButtonController;
import interfaces.Song;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Model;
import model.Playlist;

/**
 * Created by rebeccamarsh on 9/28/17.
 */
public class View extends BorderPane {

    //BorderPane Top Content
    private HBox hbox = new HBox();
    private ChoiceBox dropDownMenu = new ChoiceBox(
            FXCollections.observableArrayList("Binary", "XML", "JDBC", "OpenJPA"));
    private Label time = new Label("00:00");
    private Button load = new Button("load");
    private Button save = new Button("save");

    //BorderPane Left and Center Content
    private ListView<Song> library = new ListView<>();
    private ListView<Song> playlist = new ListView<>();

    //Border Pane Bottom Content
    private Button addToPlaylist = new Button("Add Song");
    private Button addAll   = new Button ("Add All Songs");
    private Button delete = new Button("Delete Song");

    //Border Pane Right Content
    private TextField title = new TextField();
    private TextField interpret = new TextField();
    private TextField album = new TextField();
    private Button play = new Button(">");
    private Button pause = new Button("||");
    private Button skip = new Button(">>|");
    private Button edit = new Button("enter");

    //Event listener hinzufügen
    ButtonController listener;



    public View(){
        createTopPanel();
        createLibraryPanel();
        createPlaylistPanel();
        createRightPanel();
        createBottomPanel();

    }

    public void setListener(Controller listener){
        this.listener = listener;
    }

    private void createRightPanel() {
        setRight(new VBox(new Label("Titel:"), title, new Label("Interpret:"), interpret,
                new Label("Album:"), album, new HBox(play, pause, skip, edit)));
        play.setOnAction(e -> listener.play());
        pause.setOnAction(e -> listener.pause());
        skip.setOnAction(e -> listener.skip());
        edit.setOnAction(e -> listener.edit());
    }

    private void createBottomPanel() {
        setBottom(new HBox(addAll, addToPlaylist, delete));
        addAll.setOnAction(e -> listener.addAll());
        addToPlaylist.setOnAction(e -> listener.addToPlaylist());
        delete.setOnAction(e -> listener.removeFromPlaylist());
    }

    private void createPlaylistPanel() {
        setCellFactory(playlist);
        setCenter(new VBox(new Label("Playlist"),playlist));

    }

    private void createLibraryPanel() {
        setCellFactory(library);
        setLeft(new VBox(new Label("Library"),library));


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
        load.setOnAction(e -> listener.load());
        save.setOnAction(e -> listener.save());
    }

    public void bindData(Model model){
        //Sets items in listview objects so that they are filled with objects in the model,
        //listview will observe the models playlists, because the playlists are observable
        library.setItems(model.getLibrary());
        playlist.setItems(model.getPlaylist());
    }

    public ListView<Song> getLibrary(){
        return this.library;
    }

    public ListView<Song> getPlaylist(){
        return this.playlist;
    }




}
