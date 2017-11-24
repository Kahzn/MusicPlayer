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

    FileOutputStream fos = null;
    ObjectOutputStream oos = null;

    FileInputStream fis = null;
    ObjectInputStream ois = null;

    @Override
    public void openWritableLibrary() throws IOException {

           fos = new FileOutputStream("binLibrary.ser");
    }

    @Override
    public void openReadableLibrary() throws IOException {

            fis = new FileInputStream("binLibrary.ser");
    }

    @Override
    public void openWritablePlaylist() throws IOException {
             oos = new ObjectOutputStream(fos);
    }

    @Override
    public void openReadablePlaylist() throws IOException {
            ois = new ObjectInputStream(fis);
    }

    @Override
    public void writeSong(Song s) throws IOException {

            oos.writeObject(s);

    }

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
    public void closeWritableLibrary() throws IOException {
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
