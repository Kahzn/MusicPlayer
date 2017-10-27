package interfaces;

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
