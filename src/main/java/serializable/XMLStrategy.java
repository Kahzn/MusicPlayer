package serializable;

import interfaces.Playlist;
import interfaces.SerializableStrategy;
import interfaces.Song;
import model.Model;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;


public class XMLStrategy implements SerializableStrategy {

    FileOutputStream fos = null;
    XMLEncoder encoder = null; // = oos

    FileInputStream fis = null;
    XMLDecoder decoder = null; // = ois

    //Model model;
    //Playlist playlist = model.getPlaylist();
    //Playlist library = model.getLibrary();

    @Override
    /** serialisiere library **/
    public void openWritableLibrary() throws IOException {
        fos = new FileOutputStream("xmlLibraray.xml");
        encoder = new XMLEncoder(fos);
        System.out.println("Streams geöffnet - library - ser");
    }

    @Override
    /** deserialisiere library **/
    public void openReadableLibrary() throws IOException {
        fis = new FileInputStream("xmlLibraray.xml");
        decoder = new XMLDecoder(fis);
        System.out.println("Streams geöffnet - library - deser");
    }

    @Override
    /** serialisiere playlist **/
    public void openWritablePlaylist() throws IOException {
        fos = new FileOutputStream("xmlPlaylist.xml");
        encoder = new XMLEncoder(fos);
        System.out.println("Streams geöffnet - playlist - ser");
    }

    @Override
    /** deserialisiere playlist **/
    public void openReadablePlaylist() throws IOException {
        fis = new FileInputStream("xmlPlaylist.xml");
        decoder = new XMLDecoder(fis);
        System.out.println("Streams geöffnet - playlist - deser");
    }

    /** ******************************************************************************************************* **/

    @Override
    /** schreibe Song(Objekt) **/
    // serialisiere ein Object
    public void writeSong(Song s) throws IOException {
        //model.getPlaylist().add(s);
        encoder.writeObject(s);
        System.out.println("Songs geschrieben");
    }

    @Override
    /** lese Song(Objekt) **/
    // deserialisiere ein Object
    public Song readSong() throws IOException, ClassNotFoundException {
        System.out.println("Objekt geschrieben");
        return (Song) decoder.readObject();

    }

    @Override
    /** schreibe Song(Objekt) (writeSong() ) in library **/
    public void writeLibrary(Playlist p) throws IOException {
        for (Song s : p) {
            //encoder.writeObject(s);
            writeSong(s);
        }
        System.out.println("library geschrieben");
    }

    @Override
    /** lese Song(Objekt) (readSong() ) aus library **/
    // gibt ganze playlist zurück
    /*
        - erstelle neue Playlist
        - lese Song     (exception möglich)
        - füge gelesenen Song der Playlist hinzu
        - wenn keine Songs mehr gelesen werden können gebe Playlist zurück
     */
    public Playlist readLibrary() throws IOException, ClassNotFoundException {
        //return (Playlist) decoder.readObject();
        model.Playlist p = new model.Playlist();
        while (fis.available()!= -1) {
            try {
                Song s = (model.Song) decoder.readObject();
                p.add(s);
            } catch (Exception e) { e.printStackTrace(); }

        }
        System.out.println("library ausgelesen");
        return p;
    }

    @Override
    /** schreibe Song(Objekt) (writeSong() ) in playlist **/
    public void writePlaylist(Playlist p) throws IOException {
        for (Song s : p) {
            writeSong(s);
        }
        System.out.println("playlist geschrieben");
    }

    @Override
    /** lese Song(Objekt) (readSong() ) aus playlist **/
    public Playlist readPlaylist() throws IOException, ClassNotFoundException {
        model.Playlist p = new model.Playlist();
        while (fis.available()!= -1) {
            try {
                Song s = (model.Song) decoder.readObject();
                p.add(s);
            } catch (Exception e) { e.printStackTrace(); }
        }
        System.out.println("playlist ausgelesen");
        return p;
    }

    /** ******************************************************************************************************** **/

    @Override
    public void closeWritableLibrary() {
        try {
            if (encoder != null) encoder.close();
            System.out.println("encoder geschlossen");
            if (fos != null) fos.close();
            System.out.println("fos geschlossen");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeReadableLibrary() {
        try {
            if (decoder != null) decoder.close();
            System.out.println("decoder geschlossen");
            if (fis != null) fis.close();
            System.out.println("fis geschlossen");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeWritablePlaylist() {
        try {
            if (encoder != null) encoder.close();
            System.out.println("encoder geschlossen");
            if (fos != null) fos.close();
            System.out.println("fos geschlossen");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeReadablePlaylist() {
        try {
            if (decoder != null) decoder.close();
            System.out.println("decoder geschlossen");
            if (fis != null) fis.close();
            System.out.println("fis geschlossen");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
