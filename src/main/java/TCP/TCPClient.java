package TCP;

import org.apache.openjpa.lib.meta.SourceTracker;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TCPClient extends Thread {
    private static String serverName;
    private static String clientName;

    public TCPClient() {
        System.out.println("TCPClient started");
        try (Socket serverCon = new Socket("localhost", 5020);
             InputStream in = serverCon.getInputStream();
             OutputStream out = serverCon.getOutputStream();
             ObjectOutputStream os = new ObjectOutputStream(out);
             ObjectInputStream is = new ObjectInputStream(in)) {

            System.out.print("Enter your client's name (type any name you want): ");
            Scanner input = new Scanner(System.in);
            clientName = input.next();
            os.writeUTF(clientName);

            System.out.println("Enter password (Hint: it's password)");
            String password = input.next();
            os.writeUTF(password);

            serverName = is.readUTF();
            System.out.println("Server's name is " + serverName);

            os.flush();
            //??
            out.flush();

        } catch (UnknownHostException e) {
            System.out.println("Could not connect to port 5020");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
