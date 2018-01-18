package interfaces;

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
}
