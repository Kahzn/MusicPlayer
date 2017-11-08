package model;

import interfaces.*;
import interfaces.Song;
import javafx.collections.ModifiableObservableListBase;

import java.rmi.RemoteException;
import java.util.ArrayList;


public class Playlist extends ModifiableObservableListBase<interfaces.Song> implements interfaces.Playlist  {
    private ArrayList<Song> songs = new ArrayList<Song>();

    @Override
    public boolean addSong(Song s) throws RemoteException {
        return add(s);
    }

    @Override
    public boolean deleteSong(Song s) throws RemoteException {
        return remove(s);
    }

    @Override
    public boolean deleteSongByID(long id) throws RemoteException {
        return remove(id);
    }

    @Override
    public void setList(ArrayList<Song> s) throws RemoteException {
        this.songs = s;

    }

    @Override
    public ArrayList<Song> getList() throws RemoteException {
        return  songs;
    }

    @Override
    public void clearPlaylist() throws RemoteException {
        songs.clear();
    }

    @Override
    public int sizeOfPlaylist() throws RemoteException {
        return songs.size();
    }

    @Override
    public Song findSongByPath(String name) throws RemoteException {
//        for(Song s : songs){
//            if(s.getPath().equals(name))
//                return s;
//        }
//        return null;

        //equivalent use with a stream
        return songs.stream().filter(s -> s.getPath().equals(name)).findFirst().get();
    }

    @Override
    public Song findSongByID(long id) throws RemoteException {
//        for( interfaces.Song s : songs){
//            long ident = s.getId();
//            if(ident == id) return s;
//        }return null;
//
        //equivalent use with a stream
        return songs.stream().filter(s-> s.getId() == id).findFirst().get();

    }

    @Override
    public Song get(int index) { return songs.get(index); }

    @Override
    public int size() {
        return songs.size();
    }

    @Override
    protected void doAdd(int index, Song element) {
        songs.add(index, element);
    }

    @Override
    protected Song doSet(int index, Song element) {

        return songs.set(index, element);
    }

    @Override
    protected Song doRemove(int index) {

        return songs.remove(index);
    }
}
