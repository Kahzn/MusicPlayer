package view;

import interfaces.ButtonController;
import interfaces.Song;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Model;


public class View extends BorderPane {
    //Model model;

    private HBox hboxTop = new HBox();
    private ChoiceBox dropDownMenu = new ChoiceBox(
            FXCollections.observableArrayList("Binary", "XML", "JDBC", "OpenJPA"));
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

    private ButtonController listener;



    public View(){
        //this.model = model;
        createTopPanel();
        createLibraryPanel();
        createPlaylistPanel();
        createRightPanel();
        createBottomPanel();
        play.setOnAction(e -> listener.play(playlist.getSelectionModel().getSelectedIndex()));
        addToPlaylist.setOnAction(e -> listener.addToPlaylist(library.getSelectionModel().getSelectedItem()));
        delete.setOnAction(e -> listener.removeFromPlaylist(playlist.getSelectionModel().getSelectedItem()));
        enter.setOnAction(e -> listener.edit());
        addAll.setOnAction(e -> listener.addAll());
        skip.setOnAction(e -> listener.skip());
        pause.setOnAction(e -> listener.pause());


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

    public void createPlaylistPanel() {
        setCellFactory(playlist);
        setCenter(playlist);

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
        hboxTop.setSpacing(10);
        hboxTop.getChildren().addAll(dropDownMenu, time, load, save);
        setTop(hboxTop);
    }

    public void bindData(Model model){
        //Sets items in listview objects so that they are filled with objects in the model,
        //listview will observe the models playlists, because the playlists are observable
        library.setItems(model.getLibrary());
        playlist.setItems(model.getPlaylist());
    }

    public void setListener(ButtonController listener) {
        this.listener = listener;
    }
    public ListView<Song> getPlaylist() {
        return playlist;
    }

    public String getTitel() {
        return titel.getText();
    }

    public String getInterpret() {
        return interpret.getText();
    }

    public String getAlbum() {
        return album.getText();
    }
}
