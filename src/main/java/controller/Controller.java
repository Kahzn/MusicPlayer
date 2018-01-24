package controller;


import TCP.TCPServer;
import interfaces.RemoteButtonController;
import interfaces.SerializableStrategy;
import interfaces.Song;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.Model;
import model.Playlist;
import view.View;
import serializable.*;



import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Controller extends UnicastRemoteObject implements RemoteButtonController, Serializable {
    private static final long serialVersionUID = 1L;

    Model model;
    View view;
    private int currentIndex = 0; //index des ausgewählten Liedes
    private MediaPlayer player;
    private String path;
    TCPServer tcpServer;
    private Timer timer;

    public Controller(TCPServer t) throws RemoteException{
        super();
        this.tcpServer = t;

    }

    public void link(Model model, View view) {
        this.model = model;
        this.view = view;
        new Timer(player,view);

        //Bind data to view. D.h.: den ListView elements werden Elemente aus dem Model mit Methode setItems hinzugefügt
        this.view.bindData(this.model);

        //Wichtig: eine Instanz der View Klasse braucht einen ButtonController Feld um das EventHandling auszuführen
        view.setController(this);

        //start Timer
        timer = new Timer(player, view);
        timer.start();
    }




    //Methoden haben bei der Server's Controller keine Funktionalität... löschen?
    @Override
    public synchronized void addAll() throws RemoteException{
        for(Song song : model.getLibrary()) {
            try {
                model.getPlaylist().addSong(song);
            } catch (RemoteException e) {
                System.out.println("Entfernter Rechner nicht zu erreichen:");
                e.printStackTrace();
            }
        }

    }

    @Override
    public synchronized void addToPlaylist(Song s) throws RemoteException {
        try {
            if (s !=  null) {
                model.getPlaylist().addSong(s);
            }
        } catch (RemoteException e) {
            System.out.println("Entfernter Rechner nicht zu erreichen:");
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void removeFromPlaylist(Song s) throws RemoteException{
        s = view.getPlaylist().getSelectionModel().getSelectedItem();
        long id = view.getPlaylist().getSelectionModel().getSelectedIndex();
        try {
            if (s != null) model.getPlaylist().deleteSongByID(id);
        } catch (RemoteException e) {
            System.out.println(("Entfernter Rechner nicht zu erreichen:"));
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void play(int index) throws RemoteException{
        try {

            currentIndex = index;
            System.out.println("This index is: "+ currentIndex);

            /** 
             *  jetzt: tatsächlicher Index des Liedes dient als Parameter der Play-Methode
             */
            Song so; //Objekt des Liedes wird erstellt (Typ Song) = null

            /** übersteigt der Index des ausgewählten Liedes die Größe der Playlist:
             *  beginne mit dem ersten Element in der Playlist
             *  ansonsten: arbeite mit ausgewähltem Index
             */
            if (index >= view.getPlaylist().getItems().size()) {
                so = view.getPlaylist().getItems().get(0);
            } else {
                so = view.getPlaylist().getItems().get(index);
            }

            if(so != null) {
                if(player!=null){
                    player.pause();
                }
                //Initialisierung oder ein anderes Lied "gewählt" wird
                if(player == null || !so.getPath().equals(path)){
                    path = so.getPath();
                    /**der MediaPlayer arbeitet mit Media Objekten
                    Klasse Media nimmt sich ein File Objekt
                    toURI() konvertiert den Pfad ins richtige Format
                    toString() konvertiert das Ergebnis von toURI() in einen String**/
                    player = new MediaPlayer(new Media(new File((so.getPath())).toURI().toString())); //player wird auf die ID des ausgewählten Liedes initialisiert
                    timer.setPlayer(player);

                    if (player.getStatus().equals(MediaPlayer.Status.PLAYING)) {
                        player.play();
                    }
                    if (player.getStatus().equals(MediaPlayer.Status.PAUSED)) {
                        player.play();
                    }
                }
                player.play();

                /**
                 * spiele den nächsten Song ab
                 *
                 * setOnEndOfMedia ist eine Methode des Medialayers
                 * Runnable() ist ein Interface
                 * run() ist die einzige Methode des Interfaces Runnable
                 *    muss überschrieben werden
                **/

                player.setOnEndOfMedia(() -> {
                    try {
                        play(index + 1);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
            }
            else{
                //avoid NullPointerException in case user presses play before player has been initialized
                if(player != null && player.getStatus().equals(MediaPlayer.Status.PAUSED)) player.play();
            }


        }
        catch(NullPointerException | ArrayIndexOutOfBoundsException e){
            System.out.println("kein Lied ausgewählt!");
        }
    }

    @Override
    public synchronized void pause() throws RemoteException{

        if (player != null && player.getStatus().equals(MediaPlayer.Status.PLAYING)) { //ein Lied wird gespielt
            player.pause(); //pausiert ein lied
        }
    }

    @Override
    public synchronized void skip()throws RemoteException {
        try {
            //die selectNext()-Methode wählt das Lied mit dem nächsthöheren Index in der ListView aus.
            //Wenn aktuell kein Lied gewählt ist, wählt diese Methode das erste Lied aus.

            play(currentIndex+1);
        }
        catch(NullPointerException | IndexOutOfBoundsException e){
            System.out.println("kein Lied ausgewählt!");
    }
    }

    @Override
    public synchronized void edit() throws RemoteException{
        Song s = view.getPlaylist().getSelectionModel().getSelectedItem();
        String titel = view.getTitel();
        String interpret = view.getInterpret();
        String album = view.getAlbum();
        int songIndex = view.getPlaylist().getSelectionModel().getSelectedIndex();
        try {
            if (s != null) {
                if (!titel.isEmpty()) {
                    model.getPlaylist().get(songIndex).setTitle(titel);
                    s.setTitle(titel);
                }
                if (!interpret.isEmpty()) {
                    model.getPlaylist().get(songIndex).setInterpret(interpret);
                    s.setInterpret(interpret);
                }
                if (!album.isEmpty()) {
                    model.getPlaylist().get(songIndex).setAlbum(album);
                    s.setAlbum(album);
                }
            }
        }catch (Exception e) {
            System.out.println(("Entfernter Rechner nicht zu erreichen:"));
            e.printStackTrace();
        }
        view.createPlaylistPanel();

    }

    @Override
    public synchronized void load() throws RemoteException{
        //delete current library and playlist
        try {
            model.getLibrary().clearPlaylist();
            model.getPlaylist().clearPlaylist();

        } catch (RemoteException e) {
            System.out.println("Problem beim Löschen der Library oder Playlist.");
        }

        //load (deserialize) library and playlist using strategy strat
        SerializableStrategy strat = serializationType();
        if(strat != null){
            //Deserialize library
            try {
                strat.openReadableLibrary();
                for(Song s : strat.readLibrary()) {
                    model.getLibrary().addSong(s);
                    System.out.println("Load song: " + s.getTitle());
                }
            } catch (RemoteException e) {
                System.out.println("Fehler in readlibrary");
            } catch (IOException e) {
                System.out.println("Beim Laden der Library ist ein Fehler aufgetreten.");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally{
                strat.closeReadableLibrary();
            }

            //Deserialize Playlist
            if(!(strat instanceof OpenJPAStrategy)) {
                try {
                    strat.openReadablePlaylist();
                    for (Song s : strat.readPlaylist()) {

                        if (model.getLibrary().findSongByID(s.getId()) != null) {
                            s = model.getLibrary().findSongByID(s.getId());

                        }
                        model.getPlaylist().addSong(s);
                    }
                } catch (IOException e) {
                    System.out.println("Beim Laden der Playlist ist ein Fehler aufgetreten.");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace(); //Fehler bei readsong/ObjectInputStream
                } finally {
                    strat.closeReadablePlaylist();
                }
            }
        }else{
            System.out.println("Bitte angeben, welche Datei deserialisiert werden soll.");
        }



    }

    @Override
    public synchronized void save() throws RemoteException{

        /*save (serialize) library and playlist using strategy strat */
        SerializableStrategy strat = serializationType();

        //Serialize library
        try {
            strat.openWritableLibrary();
            strat.writeLibrary(model.getLibrary());
        } catch (IOException e) {
            System.out.println("Beim Speichern der Library ist ein Fehler aufgetreten.");
        }finally {
            strat.closeWritableLibrary();
        }

        //Serialize Playlist
        try {
            strat.openWritablePlaylist();
            strat.writePlaylist(model.getPlaylist());
        } catch (IOException e) {
            System.out.println("Beim Speichern der Playlist ist ein Fehler aufgetreten.");
        }finally{
            strat.closeWritablePlaylist();
        }

    }


    private SerializableStrategy serializationType() {
        SerializableStrategy strat = null;

        if(view.getSerializationType().equals("Binary")) strat = new BinaryStrategy();
        if(view.getSerializationType().equals("XML")) strat = new XMLStrategy();
        if(view.getSerializationType().equals("JDBC")) strat = new JDBCStrategy();
        if(view.getSerializationType().equals("OpenJPA")) strat = new OpenJPAStrategy();

        return strat;
    }


    @Override
    public Playlist getLibary() throws RemoteException {
        return model.getLibrary();
    }

    @Override
    public Playlist getPlaylist() throws RemoteException {
        return model.getPlaylist();
    }

    @Override
    public void logout(String cName) throws RemoteException {
        tcpServer.removeClient(cName);
    }


    public Timer getTimer() {
        return timer;
    }


}
