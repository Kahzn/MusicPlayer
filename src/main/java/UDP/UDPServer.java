package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;
import java.util.Scanner;

public class UDPServer {

    public static void main(String[] args) {

        // Socket erstellen, unter dem der Server erreichbar ist
        try(DatagramSocket socket = new DatagramSocket(5000);) {
            while (true) {

                    // Neues Paket anlegen
                    DatagramPacket packet = new DatagramPacket(new byte[8], 8);
                    // Auf eingehende Pakete warten
                    try {
                        socket.receive(packet);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Daten auslesen
                    InetAddress address = packet.getAddress();
                    int port = packet.getPort();
                    int len = packet.getLength();
                    byte[] data = packet.getData();

                    System.out.println("Anfrage von "+address+" vom Port "+port+" mit der Länge "+len+"\n"+new String(data));

                    // Nutzdaten in ein Stringobjekt übergeben
                    String da = new String(data);
                    // Kommandos sollen durch : getrennt werden
                    try (Scanner sc = new Scanner(da).useDelimiter(":")) {
                        // Erstes Kommando filtern
                        String cmd = sc.next();

                        if(cmd.equals("cmd")){
                            String time = sc.next();
                            if(time.equals("time")){

                                System.out.println("Neue Abspielzeit an Client gesendet");

                                /***
                                 *
                                 * aktuelle Abspielzeit an Client zurücksenden. Erfordert TIMER.
                                 */

                                Date dt = new Date();
                                byte[] myDate =  dt.toString().getBytes();

                                // Paket mit neuen Daten (Datum) als Antwort vorbereiten
                                packet = new DatagramPacket(myDate, myDate.length,
                                        address, port);
                                // Paket versenden
                                socket.send(packet);
                            }
                        }
                        else {
                            byte[] myDate = null;
                            myDate = new String("Command unknown").getBytes();

                            // Paket mit Information, dass das Schlüsselwort ungültig ist als Antwort vorbereiten
                            packet = new DatagramPacket(myDate, myDate.length,
                                    address, port);
                            // Paket versenden
                            socket.send(packet);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
