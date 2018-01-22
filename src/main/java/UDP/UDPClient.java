package UDP;


import java.io.IOException;
import java.net.*;
import java.rmi.RemoteException;

public class UDPClient {


    public static String currentPacketTime = "";

    public static String getCurrentPacketTime(){
        return currentPacketTime;
    }

    public static void main(String[] args) {
        // Eigene Adresse erstellen
        InetAddress ia = null;


        try {
            ia = InetAddress.getByName("localhost");
        } catch (UnknownHostException e2) {
            e2.printStackTrace();
        }
        // Socket für den Klienten anlegen
        try (DatagramSocket dSocket = new DatagramSocket(1234);) {

            try {
                int i = 0;
                while (i < 10) {
                    String command = "cmd:time";

                    byte buffer[] = command.getBytes();

                    // Paket mit der Anfrage vorbereiten
                    DatagramPacket packet = new DatagramPacket(buffer,
                            buffer.length, ia, 5000);
                    // Paket versenden
                    dSocket.send(packet);

                    byte answer[] = new byte[1024];
                    // Paket für die Antwort vorbereiten
                    packet = new DatagramPacket(answer, answer.length);
                    // Auf die Antwort warten
                    dSocket.receive(packet);
                    currentPacketTime = new String(packet.getData(), 0,packet.getLength());
                    System.out.println(currentPacketTime);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    i++;
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
        catch(SocketException e){
            e.printStackTrace();

        }
    }
}
