package interfaces;

import controller.Timer;
import model.*;
import model.Playlist;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface RemoteButtonController extends Remote{

    void addAll()throws RemoteException;
    void addToPlaylist(Song s) throws RemoteException;
    void removeFromPlaylist(Song s) throws RemoteException;

    void play(int index) throws RemoteException;
    void pause() throws RemoteException;
    void skip() throws RemoteException;
    void edit() throws RemoteException;

    void load() throws RemoteException;
    void save() throws RemoteException;

    Playlist getLibary() throws RemoteException;
    Playlist getPlaylist() throws RemoteException;

    void logout(String cName) throws RemoteException;
    Timer getTimer() throws RemoteException;
}
