package controller;

import interfaces.ButtonController;
import interfaces.Song;
import javafx.scene.media.MediaPlayer;
import model.Model;
import view.View;

import java.rmi.RemoteException;

public class Controller implements ButtonController {
    Model model;
    View view;
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
    public void addAll() {
        for(Song song : model.getLibrary()) {
            try {
                model.getPlaylist().addSong(song);
            } catch (RemoteException e) {
                System.out.println("Entfernter Rechner nicht zu erreichen");
            }
        }

    }

    @Override
    public void addToPlaylist() {
        Song song = view.getLibrary().getSelectionModel().getSelectedItem();
        if (song != null) {
            try {
                model.getPlaylist().addSong(song);
            } catch (RemoteException e) {
                System.out.println("Remote connection failed.");
            }
        }else
            System.out.println("No Song selected.");
    }

    @Override
    public void removeFromPlaylist() {
        Song song = view.getPlaylist().getSelectionModel().getSelectedItem();
        long indexOfSongInPlaylist = view.getPlaylist().getSelectionModel().getSelectedIndex();

        if(song != null){
            try {
                model.getPlaylist().deleteSongByID(indexOfSongInPlaylist);

            } catch (RemoteException e) {
                System.err.println("Remote connection error");
            }
        }else System.out.println("No song selected.");
    }

    //MusicPlayer methods (play, pause, skip)
    @Override
    public void play() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void skip() {

    }

    @Override
    public void edit() {

    }

    @Override
    public void load() {
        //todo with deserialization

    }

    @Override
    public void save() {
        //todo serialization

    }


}
