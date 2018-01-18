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

    public void run() {
        System.out.println("TCPClient started");
        try (Socket serverCon = new Socket("localhost", 5020);
             InputStream in = serverCon.getInputStream();
             OutputStream out = serverCon.getOutputStream();
             ObjectOutputStream os = new ObjectOutputStream(out);
             ObjectInputStream is = new ObjectInputStream(in)) {


            System.out.print("Client's name is: ");
            clientName = "client"+ System.currentTimeMillis();
            System.out.println(clientName);
            os.writeUTF(clientName);
            String password = "password";
            os.writeUTF(password);

            //Give client the name of the server's remote object as assigned in the server's registry
            serverName = is.readUTF();
            System.out.println("Server's name is " + serverName);


        } catch (UnknownHostException e) {
            System.out.println("Could not connect to port 5020");
        } catch (IOException e) {
            System.out.println("IO Problem");
        }

    }

    public String getServerName(){
        return serverName;
    }
}
