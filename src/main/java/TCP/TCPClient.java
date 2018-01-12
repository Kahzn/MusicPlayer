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

            System.out.print("Enter your client's name: ");
            Scanner input = new Scanner(System.in);
            clientName = input.next();
            //clientName = "BLA"+ System.currentTimeMillis();
            os.writeUTF(clientName);

            //System.out.println("Enter password (Hint: it's password)");
            //String password = input.next();

            String password = "password";
            os.writeUTF(password);

            serverName = is.readUTF();
            System.out.println("Server's name is " + serverName);


        } catch (UnknownHostException e) {
            System.out.println("Could not connect to port 5020");
        } catch (IOException e) {
            System.out.println("IO Problem");
        }

    }
}
