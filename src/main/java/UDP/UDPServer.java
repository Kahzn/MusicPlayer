package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;




public class UDPServer {

    public static int testseconds = 0;


    public static void main(String[] args) {

        DatagramSocket socket = null;

        // Socket erstellen, unter dem der Server erreichbar ist

        try {
            socket = new DatagramSocket(5000);

            while (true) {

                    // Neues Paket anlegen
                    DatagramPacket packet = new DatagramPacket(new byte[8], 8);
                    // Auf eingehende Pakete warten
                    try {
                        socket.receive(packet);

                        // Empfangendes Paket in einem neuen Thread abarbeiten
                        new UDPServerThread(packet, socket, testseconds).start();
                        testseconds++;

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


            }
        } catch (SocketException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }

    }
}
