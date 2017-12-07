package serializable;

import interfaces.Playlist;
import interfaces.SerializableStrategy;
import interfaces.Song;

import java.io.IOException;
import java.sql.*;

public class JDBCStrategy implements SerializableStrategy {
    //static: A single copy to be shared by all instances of the class.
    private static Connection con = null;
    private static ResultSet rs = null;
    private static PreparedStatement pstmt = null;
    String insert ="";
    private TableName name = null;

    //Basically enums are like string constants..
    public enum TableName {
        LIBRARY, PLAYLIST,
    }

    //TESTESTESTESTESTESTESTEST


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
            switch (table) {
                case LIBRARY:
                    pstmt = con.prepareStatement("DELETE FROM Library");
                    break;
                case PLAYLIST:
                    pstmt = con.prepareStatement("DELETE FROM Playlist");
                    break;
            }
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Check whether deleted
        System.out.println("Check whether DB deleted.");
        try {
            pstmt = con.prepareStatement("SELECT * from Library");
            rs =pstmt.executeQuery();
            while(rs!=null && rs.next()){
                System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getString(4)+"  "+rs.getString(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    private void registerDriver() {
        try {
            con = DriverManager.getConnection("jdbc:sqlite:MusicPlayerDB1.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void testTable(){
        Statement stmt= null;
        try {
            stmt = con.createStatement();
        } catch (SQLException e) {
            System.out.println("Bad connection.");
        }
        ResultSet rs= null;
        try {
            rs = stmt.executeQuery("select * from library");
        } catch (SQLException e) {
            System.out.println("Problem with execute");
        }
        try {
            if(rs == null) System.out.println("No result set.");
            else{
                while(rs.next())
                    System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getString(4)+"  "+rs.getString(5));
            }
        } catch (SQLException e) {
            System.out.println("Problem reading data");
        }
//        try {
//            con.close();
//        } catch (SQLException e) {
//            System.out.println("Problem closing connection");
//        }
    }

    //Opens the database connection for our library and deletes previous table
    //Save libary
    @Override
    public void openWritableLibrary() throws IOException {
        registerDriver();
        testTable();
        delete(TableName.LIBRARY);
        insert= "INSERT INTO Library (ID, Title, Artist, Album, Path) VALUES (?,?,?,?,?);";
    }


    //Load library
    @Override
    public void openReadableLibrary() throws IOException {
        registerDriver();
        try {
            //Create PreparedStatement
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
        delete(TableName.PLAYLIST);
        insert= "INSERT INTO Playlist (ID, Title, Artist, Album, Path) VALUES (?,?,?,?, ?);";
    }

    //Load playlist
    @Override
    public void openReadablePlaylist() throws IOException {
        registerDriver();
        try {
            //Create PreparedStatement
            pstmt = con.prepareStatement("SELECT * FROM Playlist");
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Insert songs into DB (Save songs from playlist)
    @Override
    public void writeSong(Song s) throws IOException {
        //System.out.println("Song Title: "+ s.getTitle());
        try {
            pstmt = con.prepareStatement(insert);
            pstmt.setLong(1,  s.getId());
            pstmt.setString(2, s.getTitle());
            pstmt.setString(3, s.getInterpret());
            pstmt.setString(4, s.getAlbum());
            pstmt.setString(5, s.getPath());
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Check what is in table.");
        testTable();
    }

   //Read a song from Database
    @Override
    public Song readSong() throws IOException, ClassNotFoundException {
        Song song = new model.Song();
        try {
            while(rs.next()){
                song.setId(rs.getLong("ID"));
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

//        if(pstmt != null) {
//            try {
//                pstmt.close();
//            } catch (SQLException e) {
//                System.out.println("Prepared Statement konnte nicht geschlossen werden (nach Library Speicherung).");
//            }
//        }

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

//        if(pstmt != null) {
//            try {
//                pstmt.close();
//            } catch (SQLException e) {
//                System.out.println("Prepared Statement konnte nicht geschlossen werden (nach PLaylist Speicherung)");
//            }
//        }

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
