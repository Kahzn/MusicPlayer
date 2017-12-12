package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import org.apache.openjpa.persistence.Persistent;
import org.apache.openjpa.persistence.jdbc.Strategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.*;

@Entity()
@Table(name = "Library")
public class Song implements interfaces.Song, Externalizable {

    private static final long serialVersionUID = 700L;

    @Persistent
    @Strategy("StringPropertyValueHandler")
    private SimpleStringProperty path = new SimpleStringProperty();

    @Persistent
    @Strategy("StringPropertyValueHandler")
    private SimpleStringProperty title = new SimpleStringProperty();

    @Persistent
    @Strategy("StringPropertyValueHandler")
    private SimpleStringProperty album = new SimpleStringProperty();

    @Persistent
    @Strategy("StringPropertyValueHandler")
    private SimpleStringProperty interpret = new SimpleStringProperty();

    @Id
    private long id ;

    public Song() {}

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
        return album.get();
    }

    @Override
    public void setAlbum(String album) {
        this.album.setValue(album);
    }

    @Override
    public String getInterpret() {
        return interpret.get();
    }

    @Override
    public void setInterpret(String interpret) {
        this.interpret.setValue(interpret);
    }

    @Override
    public String getPath() {
        return path.get();
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

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(getPath());
        out.writeUTF(getTitle());
        out.writeUTF(getAlbum());
        out.writeUTF(getInterpret());
        out.writeLong(getId());

    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.path.set(in.readUTF());
        this.title.set(in.readUTF());
        this.album.set(in.readUTF());
        this.interpret.set(in.readUTF());
        this.id = in.readLong();
    }
}
