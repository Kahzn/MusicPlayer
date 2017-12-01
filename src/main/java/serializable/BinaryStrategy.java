package serializable;


import interfaces.Playlist;
import interfaces.SerializableStrategy;
import interfaces.Song;

import java.io.*;

/**
 * Created by rebeccamarsh on 11/23/17.
 * implemented by Katharina Ziolkowski on 11/24/17
 */
public class BinaryStrategy implements SerializableStrategy {

    //For Serialization
    FileOutputStream fos = null;
    ObjectOutputStream oos = null;

    //For Deserialization
    FileInputStream fis = null;
    ObjectInputStream ois = null;

    //Serialize library
    @Override
    public void openWritableLibrary() throws IOException {

           fos = new FileOutputStream("binLibrary.ser");
    }

    //Deserialize Library
    @Override
    public void openReadableLibrary() throws IOException {

            fis = new FileInputStream("binLibrary.ser");
    }

    //Serialize Playlist
    @Override
    public void openWritablePlaylist() throws IOException {
             oos = new ObjectOutputStream(fos);
    }

    //Deserialize Playlist
    @Override
    public void openReadablePlaylist() throws IOException {
            ois = new ObjectInputStream(fis);
    }

    //Serialize song
    @Override
    public void writeSong(Song s) throws IOException {

            oos.writeObject(s);
    }

    //Deserialize song
    @Override
    public Song readSong() throws IOException, ClassNotFoundException {
            return (Song) ois.readObject();
    }

    @Override
    public void writeLibrary(Playlist p) throws IOException {
            oos.writeObject(p);
    }

    @Override
    public Playlist readLibrary() throws IOException, ClassNotFoundException {
        return (Playlist) ois.readObject();
    }

    @Override
    public void writePlaylist(Playlist p) throws IOException {
            oos.writeObject(p);
    }

    @Override
    public Playlist readPlaylist() throws IOException, ClassNotFoundException {
        return (Playlist) ois.readObject();
    }

    @Override
    public void closeWritableLibrary() {
            try{
                fos.close();
            }catch(IOException e){
                e.printStackTrace();
            }
    }

    @Override
    public void closeReadableLibrary() {
        try {
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeWritablePlaylist() {
        try{
            oos.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void closeReadablePlaylist() {
        try{
            ois.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
