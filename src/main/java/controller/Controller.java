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
    //adds all Songs from library to playlist
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
    public void addToPlaylist(Song s) {
        try {
            if (s !=  null) model.getPlaylist().addSong(s);
        } catch (RemoteException e) {
            System.out.println("Entfernter Rechner nicht zu erreichen:");
            e.printStackTrace();
        }
    }

    @Override
    public void removeFromPlaylist() {

    }

    @Override
    public void play(int index) {
        if (index >= 0) {

        }
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

    }

    @Override
    public void save() {

    }


}
