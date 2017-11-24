package serializable;

import interfaces.Playlist;
import interfaces.SerializableStrategy;
import interfaces.Song;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;


public class XMLStrategy implements SerializableStrategy {

    FileOutputStream fos = null;
    XMLEncoder encoder = null; // = oos

    FileInputStream fis = null;
    XMLDecoder decoder = null; // = ois

    @Override
    public void openWritableLibrary() throws IOException {
        fos = new FileOutputStream("xmlLibraray.xml");
    }

    @Override
    public void openReadableLibrary() throws IOException {
        encoder = new XMLEncoder(fos);
    }

    @Override
    public void openWritablePlaylist() throws IOException {
        fis = new FileInputStream("xmlPlaylist.xml");
    }

    @Override
    public void openReadablePlaylist() throws IOException {
        decoder = new XMLDecoder(fis);
    }

    @Override
    public void writeSong(Song s) throws IOException {
        encoder.writeObject(s);
    }

    @Override
    public Song readSong() throws IOException, ClassNotFoundException {
        return (Song) decoder.readObject();
    }

    @Override
    public void writeLibrary(Playlist p) throws IOException {
        encoder.writeObject(p);
    }

    @Override
    public Playlist readLibrary() throws IOException, ClassNotFoundException {
        return (Playlist) decoder.readObject();
    }

    @Override
    public void writePlaylist(Playlist p) throws IOException {
        encoder.writeObject(p);
    }

    @Override
    public Playlist readPlaylist() throws IOException, ClassNotFoundException {
        return (Playlist) decoder.readObject();
    }

    @Override
    public void closeWritableLibrary() {
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeReadableLibrary() {
        try {
            encoder.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeWritablePlaylist() {
        try {
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeReadablePlaylist() {
        try {
            decoder.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
