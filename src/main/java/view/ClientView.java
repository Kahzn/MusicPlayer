package view;

import interfaces.ButtonController;
import interfaces.Song;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Model;

/**
 * Created by rebeccamarsh on 1/9/18.
 */
public class ClientView extends BorderPane {

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

    //Muss über RMI (nicht lokal) ausgeführt werden
    private ButtonController controller;   //Für EventHandling



    public ClientView(){

        createTopPanel();
        createLibraryPanel();
        createPlaylistPanel();
        createRightPanel();
        createBottomPanel();
        play.setOnAction(e -> controller.play(playlist.getSelectionModel().getSelectedIndex()));
        addToPlaylist.setOnAction(e -> controller.addToPlaylist(library.getSelectionModel().getSelectedItem()));
        delete.setOnAction(e -> controller.removeFromPlaylist(playlist.getSelectionModel().getSelectedItem()));
        enter.setOnAction(e -> controller.edit());
        addAll.setOnAction(e -> controller.addAll());
        skip.setOnAction(e -> controller.skip());
        pause.setOnAction(e -> controller.pause());
        save.setOnAction(e -> controller.save());
        load.setOnAction(e -> controller.load());


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

    public void setController(ButtonController controller) {
        this.controller = controller;
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

    public String getSerializationType(){
        return dropDownMenu.getSelectionModel().getSelectedItem().toString();
    }
}
