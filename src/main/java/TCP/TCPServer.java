package TCP;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TCPServer extends Thread {
    private static ArrayList<String> clientNames;

    public TCPServer(){
        clientNames = new ArrayList<>();
        //int connections = 0;
        try {
            ServerSocket server = new ServerSocket(5020);
            while(true){
                Socket clientSocket = server.accept();

                new TCPServerThreadForClients(clientNames, clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


//private class
class TCPServerThreadForClients implements Runnable {
    ArrayList<String> clientNames;
    Socket socket;

    public TCPServerThreadForClients(ArrayList<String> clientNames, Socket clientSocket) {
        this.clientNames = clientNames;
        this.socket = clientSocket;
    }

    @Override
    public void run() {

    }
}