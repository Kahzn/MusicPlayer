package controller;

import interfaces.ButtonController;
import model.Model;
import view.View;


public class Controller implements ButtonController {
    Model model;
    View view;
    //making a change...


    public void link(Model model, View view){
        this.model = model;
        this.view = view;

        //Bind data to view. D.h.: die ListView elements werden Elemente hinzufügt aus dem Model mit Methode setItems
        this.view.bindData(this.model);


        this.view.getPlayButton().setOnAction(e -> play());
    }


    @Override
    public void addAll() {

    }

    @Override
    public void addToPlaylist() {

    }

    @Override
    public void removeFromPlaylist() {

    }

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

    }

    @Override
    public void save() {

    }


}
