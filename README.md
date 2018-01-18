# MusicPlayer
FPT-AB 3 2017/18
Todo:
A1 set-up
- Main-Methode für Server und Client erstellen: Check!
- Main muss noch in Servermain umbenannt  und angepasst werden
- RMI: Änderung der Button-Funktionalität beim Client

A2 UDP
- Klassen UDPServer und UDPClient erstellen: Check!
- View: Darstellung der momentanen Abspielzeit (mm:ss) einfügen: Check!
- UDPServer wartet auf Port 5000 auf eingehende JSON-Pakete mit dem Inhalt {"cmd":"time"}
- UDPSever-Methode zur Rückgabe der Abspielzeit bei Eintreffen von Paketen implememtieren
- UDPClient-Methode implementieren, die jeden Client jede Sekunde ein Paket an den Server senden lässt und bei Rückantwort des Servers die View neu rendert. (-->Threads nutzen!)
- Timeout implementieren, damit das Programm sich bei Pakteverlust nicht aufhängt (und "ewig wartet").

A3 TCP
- Klassen TCPServer und TCPClient erstellen: check!
- Properties: Name, Passwort (beide) einfügen
- ArrayList Registry (Server), manuell synchronisiert einfügen
- TCPClient wartet auf Port 5020 auf eintreffende TCPClients
- Servermethode: Falls ein Client eintrifft, soll dieser in einem neuen Thread abgearbeitet werden, um während der
Abarbeitung den Server nicht zu blockieren.

A4 RMI
- Remote Interface schreiben
- Remote Object Klasse schreiben
- Servermethode implementieren: Anmeldung in der Registry
- Clientmethode implementieren: getPlaylists(), die sowohl die Library als auch die Playlist vom Server bekommt und in der View darstellt
- Alle implementierten Button-Funktionen müssen auf dem Server ablaufen
- Auf dem Client werden die Server-Button-Funktionen per RMI aufgerufen
- Clientmethode implementieren, die die View anpasst, wenn die Daten auf dem Server geändert werden.
- Zugriffe des Remote-Objekts synchronisieren.

