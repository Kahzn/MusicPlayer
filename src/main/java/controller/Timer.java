package controller;

import javafx.application.Platform;
import javafx.scene.media.MediaPlayer;
import view.View;


public class Timer implements Runnable {

    private MediaPlayer player;
    private View view;

    private static String timeShown = "00:00";
    private String minutes = "00";
    private String seconds = "00";

    private int min;
    private int sec;
    private double time = 0.0;

    private boolean request = false;


    Timer (MediaPlayer player, View view) {
        this.player = player;
        this.view = view;
    }

    @Override
    public void run() {
        while(true) {

            //ermittle aktuelle Abspielzeit
            if (player != null) setTime();

            //erzeuge den zu übergebenen String
            setTimeShown(timeShown);
            view.setTimeLabel(timeShown);


            //Upadte UI in JavaFX Application Thread using Platform.runLater()
            //Platform.runLater(() -> view.setTime(t));

            //lasse 1 Sekunde vergehen
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //setzt den Double time auf die aktuelle Abspielzeit (in Sekunden)
    //target: Controler => Server
    public void setTime() {
        time = player.getCurrentTime().toSeconds();
    }

    //setzt den String timeShown zusammen
    public void setTimeShown (String ts) {

        //ermittle Werte für Minuten & Sekunden
        this.min = (int) time / 60;
        this.sec = (int) time % 60;

        //erzeuge Bestandteile des zu erstellenden Strings timeShown
        this.minutes = min / 10 + "" + min % 10;
        this.seconds = sec / 10 + "" + sec % 10;
        ts = this.minutes + ":" + this.seconds;
        //Endergebnis:
        //"minutes:seconds"
    }

    //zum Beenden von Operationen
    public void closeRequest() {
        this.request = true;
    }

    //
    public static String getTime() {
        return timeShown;
    }

}