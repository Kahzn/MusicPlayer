package TCP;

import org.apache.openjpa.lib.meta.SourceTracker;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Scanner;

public class TCPClient extends Thread {
    private static String serverName;
    private static String clientName;

//    public TCPClient(){
//        run();
//    }

    public void run() {
        System.out.println("TCPClient started");
        try (Socket serverCon = new Socket("localhost", 5020);
             InputStream in = serverCon.getInputStream();
             OutputStream out = serverCon.getOutputStream();
             ObjectOutputStream os = new ObjectOutputStream(out);
             ObjectInputStream is = new ObjectInputStream(in)) {


            clientName = "client"+ System.currentTimeMillis();
            os.writeObject(clientName);
            System.out.println("Client's name: "+ clientName);
            String password = "password";
            os.writeObject(password);
            System.out.println("Password "+password);
            serverName = (String) is.readObject();
            System.out.println("Server's name is " + serverName);


        } catch (UnknownHostException e) {
            System.out.println("Could not connect to port 5020");
        } catch (IOException e) {
            System.out.println("IO Problem");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
