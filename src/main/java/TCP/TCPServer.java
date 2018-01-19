package TCP;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TCPServer extends Thread {
    private static ArrayList<String> clientNames;


    public void run(){
        clientNames = new ArrayList<>();
        //int connections = 0;
        try {
            ServerSocket server = new ServerSocket(5020);
            while(true){
                Socket clientSocket = server.accept();
                System.out.println("Client");

                //übergebe Referenz auf clientNames
                TCPServerThreadForClients t = new
                        TCPServerThreadForClients(clientNames, clientSocket);
                Thread t1 = new Thread(t);
                t1.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void removeClient(String cName){
        clientNames.remove(cName);
        System.out.println("Print registered client names. ");
        for(String c : clientNames) System.out.println(c);
    }
}


//private class
class TCPServerThreadForClients implements Runnable {
    public static final String PASSWORD = "password";
    ArrayList<String> clientNames;
    private Socket socket;

    public TCPServerThreadForClients(ArrayList<String> clientNames, Socket clientSocket) {
        this.clientNames = clientNames;
        this.socket = clientSocket;
    }

    @Override
    public void run() {
        //Access to clientNames from Threads is mutually exclusive to avoid inconsistent data
        try (InputStream in = socket.getInputStream();
             OutputStream out = socket.getOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(out);
            ObjectInputStream is = new ObjectInputStream(in)){
            String name = (String) is.readObject();
            String inputPassword = (String) is.readObject();
            if(inputPassword.equals(PASSWORD)){
                synchronized (clientNames) {
                    clientNames.add(name);
                    os.writeObject("server");
                }
            }else{
                os.writeObject("Wrong password");
            }
            out.flush();
            os.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}