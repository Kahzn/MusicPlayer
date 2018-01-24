package TCP;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;


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
            os.writeObject(clientName);
            String password = "password";
            os.writeObject(password);

            //Give client the name of the server's remote object as assigned in the server's registry
            try {
                serverName = (String) is.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println("Server's name is " + serverName);


        } catch (UnknownHostException e) {
            System.out.println("Could not connect to port 5020");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO Problem");
        }

    }

    public String getServerName(){
        return serverName;
    }
    public String getClientName() { return clientName;}
}
