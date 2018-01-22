package UDP;

import controller.Controller;
import controller.Timer;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;


public class UDPServer implements Runnable {
    private Controller controller;

    public UDPServer(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
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
                    new UDPServerThread(packet, socket, controller).start();


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

