package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;


public class Song implements interfaces.Song {


    private SimpleStringProperty path = new SimpleStringProperty();
    private SimpleStringProperty title = new SimpleStringProperty();
    private SimpleStringProperty album = new SimpleStringProperty();
    private SimpleStringProperty interpret = new SimpleStringProperty();
    private long id ;

    /**
    public Song(){

    }**/

    public Song(String path, String title, String album, String interpret, long id){
        this.path.set(path);
        this.title.set(title);
        this.album.set(album);
        this.interpret.set(interpret);
        this.id = id;
        System.out.println("Added Song: " + getTitle());
    }



    @Override
    public String getAlbum() {
        return album.getValue();
    }

    @Override
    public void setAlbum(String album) {
        this.album.setValue(album);
    }

    @Override
    public String getInterpret() {
        return interpret.getValue();
    }

    @Override
    public void setInterpret(String interpret) {
        this.interpret.setValue(interpret);
    }

    @Override
    public String getPath() {
        return path.getValue();
    }

    @Override
    public void setPath(String path) {
        this.path.setValue(path);
    }

    @Override
    public String getTitle() {
        return title.get();
    }

    @Override
    public void setTitle(String title) {
        this.title.set(title);
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public ObservableValue<String> pathProperty() {
        return path;
    }

    @Override
    public ObservableValue<String> albumProperty() {
        return album;
    }

    @Override
    public ObservableValue<String> interpretProperty() {
        return interpret;
    }

    @Override
    public ObservableValue<String> titleProperty() {
        return title;
    }

}
