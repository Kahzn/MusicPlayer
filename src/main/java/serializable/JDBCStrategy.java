package serializable;

import interfaces.Playlist;
import interfaces.SerializableStrategy;
import interfaces.Song;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

public class JDBCStrategy implements SerializableStrategy {
    private Connection con = null;
    private ResultSet rs = null;


    public enum TableName {
        LIBRARY, PLAYLIST,
    }

    private TableName name = null;

    //Opens the database connection for our library and deletes previous table
    @Override
    public void openWritableLibrary() throws IOException {
        name = TableName.LIBRARY;
//        openConnection();
//        DatabaseUtils.recreateDB(name);
    }

    @Override
    public void openReadableLibrary() throws IOException {

    }

    @Override
    public void openWritablePlaylist() throws IOException {

    }

    @Override
    public void openReadablePlaylist() throws IOException {

    }

    @Override
    public void writeSong(Song s) throws IOException {

    }

    @Override
    public Song readSong() throws IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public void writeLibrary(Playlist p) throws IOException {

    }

    @Override
    public Playlist readLibrary() throws IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public void writePlaylist(Playlist p) throws IOException {

    }

    @Override
    public Playlist readPlaylist() throws IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public void closeWritableLibrary() {

    }

    @Override
    public void closeReadableLibrary() {

    }

    @Override
    public void closeWritablePlaylist() {

    }

    @Override
    public void closeReadablePlaylist() {

    }
}
