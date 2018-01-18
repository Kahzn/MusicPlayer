package controller;

import javafx.application.Platform;
import javafx.scene.media.MediaPlayer;
import view.View;

public class Timer implements Runnable {

    private MediaPlayer player;
    private View view;

    private static String timeShown = "00:00";
    private String minutes = "";
    private String seconds = "";

    private int min = 0;
    private int sec = 0;
    private double time = 0.0;

    private boolean request = false;


    Timer (MediaPlayer player, View view) {
        this.player = player;
        this.view = view;
    }

    @Override
    public void run() {

        while(!request) {
            //ermittle aktuelle Abspielzeit
            if (player != null) setTime(time);

            //erzeuge den zu übergebenen String
            setTimeShown(timeShown);

            //Upadte UI in JavaFX Application Thread using Platform.runLater()
            //Platform.runLater(() -> view.setTime(t));

            //lasse 1 Sekunde vergehen
            try { Thread.sleep(1000); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    //setzt den Double time auf die aktuelle Abspielzeit (in Sekunden)
    public void setTime(double t) {
        t = player.getCurrentTime().toSeconds();
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