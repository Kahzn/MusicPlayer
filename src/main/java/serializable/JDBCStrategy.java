package serializable;

import interfaces.Playlist;
import interfaces.SerializableStrategy;
import interfaces.Song;

import java.io.IOException;
import java.sql.*;

public class JDBCStrategy implements SerializableStrategy {
    //static: A single copy to be shared by all instances of the class.
    private static Connection con = null;
    private ResultSet rs = null;
    private static PreparedStatement pstmt = null;
    private TableName name = null;

    //Basically enums are like string constants..
    public enum TableName {
        LIBRARY, PLAYLIST,
    }

    public JDBCStrategy(){
        //Load Driver
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void delete(TableName table) {
        try {
            //Register Driver with Manager
            con = DriverManager.getConnection("jdbc:sqlite:MusicPlayer.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            switch (table) {
                case LIBRARY:
                    pstmt = con.prepareStatement("DELETE FROM library");
                    break;
                case PLAYLIST:
                    pstmt = con.prepareStatement("DELETE FROM playlist");
                    break;
            }
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Opens the database connection for our library and deletes previous table
    //Save libary
    @Override
    public void openWritableLibrary() throws IOException {
        delete(TableName.LIBRARY);
    }

    //Load library
    @Override
    public void openReadableLibrary() throws IOException {

    }

    //Save playlist
    @Override
    public void openWritablePlaylist() throws IOException {
        delete(TableName.PLAYLIST);
    }

    //Load playlist
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
