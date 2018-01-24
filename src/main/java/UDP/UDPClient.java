package UDP;


import javafx.application.Platform;
import view.ClientView;

import java.io.IOException;
import java.net.*;
import java.rmi.RemoteException;

public class UDPClient implements Runnable {
    private ClientView view;
    private boolean request = true;

    public UDPClient(ClientView view){
        this.view = view;
    }

    @Override
    public void run() {
        InetAddress ia = null;

        try {
            ia = InetAddress.getByName("localhost");
        } catch (UnknownHostException e2) {
            e2.printStackTrace();
        }
        // Socket für den Klienten anlegen
        //"Wilcard" port wird benutzt für DatagramSocket -> belieber Port
        try (DatagramSocket dSocket = new DatagramSocket();) {
            try {
                String command = "cmd:time";
                while(request) {
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
                    String currentPacketTime = new String(packet.getData(), 0, packet.getLength());
                    Platform.runLater(() -> {
                        view.setTimeLabel(currentPacketTime);
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        catch(SocketException e){
            e.printStackTrace();
        }
    }

    public void closeRequest(){
        this.request = false;
    }


}


