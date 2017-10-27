package model;


import java.io.File;
import java.rmi.RemoteException;

public class Model {

    private Playlist library = new Playlist();
    private Playlist playlist= new Playlist();

    public Model() {
        //essentially a load data method
        File f= new File(System.getProperty("user.dir") + "/Songs");
        File[] paths= f.listFiles();


        if (paths != null) {
            try {
                for (File path : paths) {
                    //Version before creating ID Generator
                    if (path.toString().endsWith(".mp3")) {

                        library.addSong(new Song( path.toString(), path.getName(), "", "", 0));

                    }
                }


            } catch (RemoteException e) {
                System.out.println("Entfernter Rechner nicht zu erreichen");
            }
        }
    }


    public Playlist getLibrary(){ return library; }

    public Playlist getPlaylist() {
        return playlist;
    }


}


