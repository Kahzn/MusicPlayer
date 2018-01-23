package controller;

import javafx.application.Platform;
import javafx.scene.media.MediaPlayer;
import view.View;



public class Timer extends Thread{

    private MediaPlayer player;
    private View view;
    private static String timeShown = "00:00";
    private int time;

    private boolean request = true;


    public Timer (MediaPlayer player, View view) {
        this.player = player;
        this.view = view;
    }

    public void setPlayer(MediaPlayer player){
        this.player = player;

    }

    @Override
    public void run() {

        while(request) {

            //ermittle aktuelle Abspielzeit
            if (player != null) {
                //System.out.println("Bin drin");
                timeShown = calculateTime();
                System.out.println("Timer's time: " + timeShown);
                Platform.runLater(()
                        -> view.setTimeLabel(timeShown));
                System.out.println(timeShown);
            }

            //lasse 1 Sekunde vergehen
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


 public String calculateTime() {
        time = (int) player.getCurrentTime().toSeconds();
        int min = time/60;
        int sec = time%60;
        //Für führende Nullen usw. (zB. 4 min soll 04 min sein)
        String mins = min/10 + "" + min%10;
        String secs = sec/10 + "" + sec%10;
        return mins + ":" +secs;

    }

    //zum Beenden von Operationen
    public void closeRequest() {
        this.request = false;
    }

    //
    public String getTime() {
        return timeShown;
    }

}