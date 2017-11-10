package interfaces;

public interface ButtonController {
    void addAll();
    void addToPlaylist(Song s);
    void removeFromPlaylist();

    void play(int index);
    void pause();
    void skip();
    void edit();

    void load();
    void save();
}
