package serializable;


import controller.Controller;
import interfaces.Playlist;
import interfaces.SerializableStrategy;
import interfaces.Song;
import model.Model;

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
           oos = new ObjectOutputStream(fos);

    }

    //Deserialize Library
    @Override
    public void openReadableLibrary() throws IOException {
            fis = new FileInputStream("binLibrary.ser");
            ois = new ObjectInputStream(fis);
    }

    //Serialize Playlist
    @Override
    public void openWritablePlaylist() throws IOException {
            fos = new FileOutputStream("binPlaylist.ser");
            oos = new ObjectOutputStream(fos);
    }

    //Deserialize Playlist
    @Override
    public void openReadablePlaylist() throws IOException {
            fis = new FileInputStream("binPlaylist.ser");
            ois = new ObjectInputStream(fis);
    }

    //Serialize song (Write to binary code)
    @Override
    public void writeSong(Song s) throws IOException {
            oos.writeObject(s);
            System.out.println("writing song: "+ s.getTitle());
    }



    //new!
    /*
    Write songs from the library by calling writeSong for each Song in library
    */
    @Override
    public void writeLibrary(Playlist p) throws IOException {
        //Problem ... Playlist p oben ist von interfaces!??
        for(Song s : p)
            writeSong(s);
    }

    //Deserialize song (Read from binary Byte Code)
    @Override
    public Song readSong() throws IOException, ClassNotFoundException {
        try{
            Song song = (Song) ois.readObject();
            return song;
        }catch(EOFException e){
            return null;
        }

    }
    /*
     Read songs into the library by calling readSong until null is returned
     */
    @Override
    public Playlist readLibrary() throws IOException, ClassNotFoundException {

        Playlist list = new model.Playlist();
        Song s = readSong();

        //s ==null wann keine Byte code mehr zu lesen ist
        while(s != null){
            list.addSong(s);
            //Read next song
            s = readSong();
        }
        return list;
    }

    /*
    Write songs from the Playlist by calling writeSong for each Song in Playlist
    */
    @Override
    public void writePlaylist(Playlist p) throws IOException {
        //Problem ... Playlist p oben ist von interfaces!??
        for (Song s : p)
            writeSong(s);
    }


    /*
     Read songs into the Playlist by calling readSong until null is returned
     */
    @Override
    public Playlist readPlaylist() throws IOException, ClassNotFoundException {
        Playlist list = new model.Playlist();
        Song s = readSong();

        while(s != null){
            list.addSong(s);
            //Read next song
            s = readSong();
        }
        return list;
    }
    //end new


    @Override
    public void closeWritableLibrary() {
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (oos != null) {
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void closeReadableLibrary() {
        if (ois != null) {
            try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void closeWritablePlaylist() {
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (oos != null) {
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void closeReadablePlaylist() {
        if (ois != null) {
            try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




}
