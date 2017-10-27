package interfaces;

/**
 * Created by rebeccamarsh on 10/27/17.
 */
public interface ButtonController {
    void addAll();
    void addToPlaylist();
    void removeFromPlaylist();

    void play();
    void pause();
    void skip();
    void edit();

    void load();
    void save();

}
