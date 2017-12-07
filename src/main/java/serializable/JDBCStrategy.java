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
    String insert ="";
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

    public static void deleteLibrary(TableName table) {
        try {

                    pstmt = con.prepareStatement("DELETE FROM Library");


            pstmt.execute();
        } catch (SQLException e) {
            System.out.println("Konnte Library aus Datenbank nicht löschen.");
        }
    }

    public static void deletePlaylist(TableName table){
        try {
            pstmt = con.prepareStatement("DELETE FROM Playlist");
        } catch (SQLException e) {
            System.out.println("Konnte Playlist aus Datenbank nicht löschen.");
        }
    }

    private void registerDriver() {
        try {
            con = DriverManager.getConnection("jdbc:sqlite:MusicPlayerDB1.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Opens the database connection for our library and deletes previous table
    //Save libary
    @Override
    public void openWritableLibrary() throws IOException {
        registerDriver();
        deleteLibrary(TableName.LIBRARY);
        insert= "INSERT INTO Library (ID, Title, Artist, Album, Path) VALUES (?,?,?,?, ?);";
    }


    //Load library
    @Override
    public void openReadableLibrary() throws IOException {
        registerDriver();
        try {
            pstmt = con.prepareStatement("SELECT * FROM Library");
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Save playlist
    @Override
    public void openWritablePlaylist() throws IOException {
        registerDriver();
        deletePlaylist(TableName.PLAYLIST);
        insert= "INSERT INTO Playlist (ID, Title, Artist, Album, Path) VALUES (?,?,?,?, ?);";
    }

    //Load playlist
    @Override
    public void openReadablePlaylist() throws IOException {
        registerDriver();
        try {
            pstmt = con.prepareStatement("SELECT * FROM Playlist");
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Insert songs into DB (Save songs from playlist)
    @Override
    public void writeSong(Song s) throws IOException {
        try {
            pstmt = con.prepareStatement(insert);
            pstmt.setInt(1, (int) s.getId());
            pstmt.setString(2, s.getTitle());
            pstmt.setString(3, s.getInterpret());
            pstmt.setString(4, s.getAlbum());
            pstmt.setString(5, s.getPath());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   //Read a song from Database
    @Override
    public Song readSong() throws IOException, ClassNotFoundException {
        Song song = new model.Song();
        try {
            while(rs.next()){
                song.setId(rs.getInt("ID"));
                song.setTitle(rs.getString("Title"));
                song.setInterpret(rs.getString("Artist"));
                song.setAlbum(rs.getString("Album"));
                song.setPath(rs.getString("Path"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return song;
    }

    /*
   Write songs from the library by calling writeSong for each Song in library
   */
    @Override
    public void writeLibrary(Playlist p) throws IOException {
        for(Song s : p)
            writeSong(s);
    }

    /*
     Read songs into the library by calling readSong until null is returned
     */
    @Override
    public Playlist readLibrary() throws IOException, ClassNotFoundException {
        Playlist list = new model.Playlist();
        Song s = readSong();

        //s ==null wann keine Byte code mehr zu lesen ist
        while(s != null){
            list.addSong(s);
            //Read next song
            s = readSong();
        }
        return list;
    }

    @Override
    public void writePlaylist(Playlist p) throws IOException {
        for(Song s : p)
            writeSong(s);
    }

    @Override
    public Playlist readPlaylist() throws IOException, ClassNotFoundException {
        Playlist list = new model.Playlist();
        Song s = readSong();

        //s ==null wann keine Byte code mehr zu lesen ist
        while(s != null){
            list.addSong(s);
            //Read next song
            s = readSong();
        }
        return list;
    }

    @Override
    public void closeWritableLibrary() {

        try{
            if(con != null) {
                con.close();
            }

        }catch (SQLException E){
            System.out.println("Connection konnte nicht geschlossen werden.");
        }

        if(pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                System.out.println("Prepared Statement konnte nicht geschlossen werden (nach Library Speicherung).");
            }
        }

    }

    @Override
    public void closeReadableLibrary() {
        try{
            if(con != null) {
                con.close();
            }
            if(pstmt != null) {
                pstmt.close();
            }



        }catch (SQLException E){
            System.out.println("Datenbank konnte nicht geschlossen werden.");
        }
    }

    @Override
    public void closeWritablePlaylist() {
        try{
            if(con != null) {
                con.close();
            }


        }catch (SQLException E){
            System.out.println("Connection konnte nicht geschlossen werden.");
        }

        if(pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                System.out.println("Prepared Statement konnte nicht geschlossen werden (nach PLaylist Speicherung)");
            }
        }

    }

    @Override
    public void closeReadablePlaylist() {
        if(con != null){
            try{
                con.close();
                pstmt.close();
                rs.close();
            }catch (SQLException E){
                System.out.println("Datenbank konnte nicht geschlossen werden.");
            }
        }
    }

}
