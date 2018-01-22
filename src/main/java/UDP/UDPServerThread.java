package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.util.Scanner;
import controller.Timer;
import controller.Controller;

public class UDPServerThread  extends Thread {

    private DatagramPacket packet;
    private DatagramSocket socket;
    private Controller controller;


    public UDPServerThread(DatagramPacket packet, DatagramSocket socket, Controller controller)
            throws SocketException {
        this.packet = packet;
        this.socket = socket;
        this.controller = controller;

    }

    public void run() {
        // Daten auslesen
        InetAddress address = packet.getAddress();
        int port = packet.getPort();
        int len = packet.getLength();
        byte[] data = packet.getData();

        // Nutzdaten in ein Stringobjekt übergeben
        String da = new String(data);
        // Kommandos sollen durch : getrennt werden
        try (Scanner sc = new Scanner(da).useDelimiter(":")) {
            // Erstes Kommando filtern
            String cmd = sc.next();
            if(cmd.equals("cmd")){
                String time = sc.next();
                if(time.equals("time")){

                    //String dt = new Timer().getTime();
                    String dt = controller.getTimer().getTime();
                    System.out.println("time sent is: " +dt);
                    byte[] myTime =dt.getBytes();


                    // Paket mit neuen Daten (Datum) als Antwort vorbereiten
                    packet = new DatagramPacket(myTime, myTime.length,
                            address, port);
                    // Paket versenden
                    socket.send(packet);
                }
                sc.close();
            }

            else {
                byte[] myTime = new byte[1024];
                myTime = new String("Command unknown").getBytes();

                // Paket mit Information, dass das Schlüsselwort ungültig ist als Antwort vorbereiten
                packet = new DatagramPacket(myTime, myTime.length,
                        address, port);
                // Paket versenden
                socket.send(packet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}


