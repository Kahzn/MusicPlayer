package serializable;

import interfaces.Playlist;
import interfaces.SerializableStrategy;
import interfaces.Song;

import java.io.IOException;

public class JDBCStrategy implements SerializableStrategy {

    //stelle Datenbank-Verbindung her

    @Override
    public void openWritableLibrary() throws IOException {
        //erstelle Tabelle für library
        //fülle Tabelle
        //PREPARED_STATEMENTS
    }

    @Override
    public void openReadableLibrary() throws IOException {
        //lese aus Tabelle für library
        //PREPARED_STATEMENTS
    }

    @Override
    public void openWritablePlaylist() throws IOException {
        //erstelle Tabelle für playlist
        //fülle Tabelle
        //PREPARED_STATEMENTS
    }

    @Override
    public void openReadablePlaylist() throws IOException {
        //lese aus Tabelle für playlist
        //PREPARED_STATEMENTS

    }

    @Override
    public void writeSong(Song s) throws IOException {
        //schreibe einen Song in die Tabelle
        //PREPARED_STATEMENTS
    }

    @Override
    public Song readSong() throws IOException, ClassNotFoundException {
        //lese einen Song aus der Tabelle
        //gib ihn zurück
        //PREPARED_STATEMENTS
        return null;
    }

    @Override
    public void writeLibrary(Playlist p) throws IOException {
        //fülle die Tabelle für library
        //benutze writeSong()
        //PREPARED_STATEMENTS
    }

    @Override
    public Playlist readLibrary() throws IOException, ClassNotFoundException {
        //lese aus die Tabelle für library
        //benutze readSong()
        //PREPARED_STATEMENTS
        return null;
    }

    @Override
    public void writePlaylist(Playlist p) throws IOException {
        //fülle die Tabelle für playlist
        //benutze writeSong()
        //PREPARED_STATEMENTS
    }

    @Override
    public Playlist readPlaylist() throws IOException, ClassNotFoundException {
        //lese aus der Tabelle für playlist
        //benutze readSong()
        //PREPARED_STATEMENTS
        return null;
    }

    @Override
    public void closeWritableLibrary() {
        //trenne Datenbank-Verbindung - library
    }

    @Override
    public void closeReadableLibrary() {
        //trenne Datenbank-Verbindung - library
    }

    @Override
    public void closeWritablePlaylist() {
        //trenne Datenbank-Verbindung - playlist
    }

    @Override
    public void closeReadablePlaylist() {
        //trenne Datenbank-Verbindung - playlist
    }
}
