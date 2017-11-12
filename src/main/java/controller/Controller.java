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
    //Song song;
    private MediaPlayer player;



    public void link(Model model, View view){
        this.model = model;
        this.view = view;

        //Bind data to view. D.h.: die ListView elements werden Elemente aus dem Model mit Methode setItems hinzugefügt
        this.view.bindData(this.model);

        //Wichtig: eine Instanz der View Klasse braucht einen ButtonController Feld um die EventHandling auszuführen
        view.setListener(this);

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
        try {
            if (s != null) model.getPlaylist().deleteSong(s);
        } catch (RemoteException e) {
            System.out.println(("Entfernter Rechner nicht zu erreichen:"));
            e.printStackTrace();
        }
    }

    @Override
    /*spielt einen Song ab
    * gebraucht für andere Implementationen:
    *      pause()
    *      skip()
    */
    public void play(int index) {
        Song so = view.getPlaylist().getSelectionModel().getSelectedItem();
        if (player == null) { //kein Lied wird gespielt
            //wirft IllegalArgumentException...
            player = new MediaPlayer(new Media(new File((so.getPath())).toURI().toString())); //player wird auf die ID des ausgewählten Liedes initialisiert
            player.play(); //spiele Lied ab
        }
        if (player != null && player.getStatus().equals(MediaPlayer.Status.PLAYING)) { //ein Lied wird gespielt
            player.play(); //spiele Lied ab
        }
        if (player != null && player.getStatus().equals(MediaPlayer.Status.PAUSED)) { //ein Lied ist pausiert
            player.play(); //spiele Lied ab
        }
    }

    @Override
    /*pausiert einen (gespielten) Song
    * bei erneutem pausieren: Song wird abgespielt
    * gebraucht für andere Implementationen:
    *      skip()
    */
    public void pause() {
        //Song lied = view.getPlaylist().getSelectionModel().getSelectedItem();
        if (player != null && player.getStatus().equals(MediaPlayer.Status.PLAYING)) { //ein Lied wird gespiet
            player.pause(); //pausiert ein lied
        }
        if (player != null && player.getStatus().equals(MediaPlayer.Status.PAUSED)) { //ein Lied wird gespiet
            player.play(); //spielt den pausierten Song (weiter) ab
        }
    }

    @Override
    /*überspringt einen Song in der Playlist
    * Ende der Playlist: beginne mit erstem Song in dr Playlist
    * muss überarbeitet werden
    */
    public void skip() {
        Song s2 = view.getPlaylist().getSelectionModel().getSelectedItem();
        if (player == null) { //kein Lied wird gespielt
            //wirft IllegalArgumentException...
            player = new MediaPlayer(new Media(new File((s2.getPath())).toURI().toString())); //player wird auf die ID des ausgewählten Liedes initialisiert
        }





    }


    /*lets the user edit a song's attributes
    *
    *
    * Funktioniert noch nicht. Logikfehler?
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
