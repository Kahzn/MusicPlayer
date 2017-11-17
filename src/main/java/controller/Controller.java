package controller;

import interfaces.ButtonController;
import interfaces.Song;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.Model;
import view.View;

import java.io.File;
import java.rmi.RemoteException;

public class Controller implements ButtonController {
    Model model;
    View view;
    private int currentIndex = 0; //index des ausgewählten Liedes
    private MediaPlayer player;
    private String path;



    public void link(Model model, View view){
        this.model = model;
        this.view = view;

        //Bind data to view. D.h.: den ListView elements werden Elemente aus dem Model mit Methode setItems hinzugefügt
        this.view.bindData(this.model);


        //Wichtig: eine Instanz der View Klasse braucht einen ButtonController Feld um das EventHandling auszuführen
        view.setController(this);

    }


    @Override
    //fügt alle Songs aus der library der playlist hinzu
    public void addAll() {
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
    //fügt einen Song aus der library der playlist hinzu
    public void addToPlaylist(Song s) {
        try {
            if (s !=  null) {
                model.getPlaylist().addSong(s);
                System.out.println(s.getPath()); //wenn alles klappt: auskommentieren
            }
        } catch (RemoteException e) {
            System.out.println("Entfernter Rechner nicht zu erreichen:");
            e.printStackTrace();
        }
    }

    @Override
    //entfernt einen Song aus der playlist
    public void removeFromPlaylist(Song s) {
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
    /*spielt einen Song ab
    * gebraucht für andere Implementierungen:
    *      pause()
    *      skip()
    */
    public void play(int index) {
        try {

            currentIndex = index;

            /** 
             *  jetzt: tatsächlicher Index des Liedes dient als Parameter der Play-Methode
             */
            Song so; //Objekt des Liedes wird erstellt (Typ Song) = null

            /** übersteigt der Index des ausgewählten Liedes die Größe der Playlist:
             *       beginne mit dem ersten Element in der Playlist
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

                if(player == null || !so.getPath().equals(path)){
                    path = so.getPath();
                    /**der MediaPlayer arbeitet mit Media Objekten
                    Klasse Media nimmt sich ein File Objekt
                    toURI() konvertiert den Pfad ins richtige Format
                    toString() konvertiert das Ergebnis von toURI() in einen String**/
                    player = new MediaPlayer(new Media(new File((so.getPath())).toURI().toString())); //player wird auf die ID des ausgewählten Liedes initialisiert
                    if (player.getStatus().equals(MediaPlayer.Status.PLAYING)) {
                        player = new MediaPlayer(new Media(new File((so.getPath())).toURI().toString())); //Erklärung: 115
                        player.play();
                    }
                    if (player.getStatus().equals(MediaPlayer.Status.PAUSED)) {
                        player = new MediaPlayer(new Media(new File((so.getPath())).toURI().toString())); //Erklärung: 115
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
                /* player.setOnEndOfMedia(new Runnable() {
                    @Override
                    public void run() {
                        this.play(index + 1);
                    }
                });
                */
                player.setOnEndOfMedia( () -> play(index + 1 ) );



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
    /*pausiert einen (gespielten) Song
    * bei erneutem pausieren: Song wird abgespielt
    */
    public void pause() {

        //Song lied = view.getPlaylist().getSelectionModel().getSelectedItem();
        if (player != null && player.getStatus().equals(MediaPlayer.Status.PLAYING)) { //ein Lied wird gespiet
            player.pause(); //pausiert ein lied
        }
        if (player != null && player.getStatus().equals(MediaPlayer.Status.PAUSED)) { //ein Lied wird gespiet
            play(currentIndex); //spielt den pausierten Song ab
        }
    }

    @Override
    /*überspringt einen Song in der Playlist
    * muss überarbeitet werden
    */
    public void skip() {

        try {
            //die selectNext()-Methode wählt das Lied mit dem nächsthöheren Index in der ListView aus.
            //Wenn aktuell kein Lied gewählt ist, wählt diese Methode das erste Lied aus.
            //view.getPlaylist().getSelectionModel().selectNext();
            //Song naechstesLied = view.getPlaylist().getSelectionModel().getSelectedItem();

            play(currentIndex+1);
        }
        catch(NullPointerException | IndexOutOfBoundsException e){
            System.out.println("kein Lied ausgewählt!");
    }
    }


    /*lets the user edit a song's attributes
    *
    *
    * Funktioniert jetzt - die View muss entsprechend geupdated werden.
    */
    @Override
    public void edit() {
        Song s = view.getPlaylist().getSelectionModel().getSelectedItem();
        String titel = view.getTitel();
        String interpret = view.getInterpret();
        String album = view.getAlbum();
        try {
            if (s != null) {
                if (!titel.isEmpty()) {
                    s.setTitle(titel);
                }
                if (!interpret.isEmpty()) {
                    s.setInterpret(interpret);
                }
                if (!album.isEmpty()) {
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
    public void load() {
        //Noch nicht nötig für diese Übung
    }

    @Override
    public void save() {
        //Noch nicht nötig für diese Übung
    }


}
