package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;


public class Song implements interfaces.Song {


    private SimpleStringProperty path = new SimpleStringProperty();
    private SimpleStringProperty title = new SimpleStringProperty();
    private SimpleStringProperty album = new SimpleStringProperty();
    private SimpleStringProperty interpreter = new SimpleStringProperty();
    private long id ;

    public Song(){
    }

    public Song(String path, String title, String album, String interpreter, long id){
        this.path.set(path);
        this.title.set(title);
        this.album.set(album);
        this.interpreter.set(interpreter);
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
        return interpreter.getValue();
    }

    @Override
    public void setInterpret(String interpret) {
        this.interpreter.setValue(interpret);
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
        return interpreter;
    }

    @Override
    public ObservableValue<String> titleProperty() {
        return title;
    }

}
