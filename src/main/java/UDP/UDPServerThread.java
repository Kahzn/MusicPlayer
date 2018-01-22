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


        public UDPServerThread(DatagramPacket packet, DatagramSocket socket, int seconds)
                throws SocketException {
            this.packet = packet;
            this.socket = socket;
            this.testseconds = seconds;
        }


    int testseconds = 0;

        public void run() {

            Controller controller = null;
            try {
                controller = new Controller();
            } catch (RemoteException e) {
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
                         * aktuelle Abspielzeit an Client zurücksenden. Erfordert TIMER-Objekt.
                         **/

                         //Calendar calendar = Calendar.getInstance();
                         //String dt = calendar.get(Calendar.HOUR_OF_DAY) + ":" +  calendar.get(Calendar.MINUTE)+ ":" + calendar.get(Calendar.SECOND);

                        Timer timer = controller.getTimer();
                        String dt = timer.getTime();

                        //String dt = "00:0" + testseconds;

                         byte[] myDate =  dt.getBytes();

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
    }


