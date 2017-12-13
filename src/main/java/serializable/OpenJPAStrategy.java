package serializable;

import interfaces.Playlist;
import interfaces.SerializableStrategy;
import interfaces.Song;
import org.apache.openjpa.persistence.OpenJPAPersistence;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenJPAStrategy implements SerializableStrategy {

    private EntityManagerFactory factory = null;
    private EntityManager manager = null;
    private EntityTransaction trans = null;

    private model.Song s;
    private List<Song> resultList;

    private boolean useConfigFile = false; //true falls eine Konfig.-Datei genutzt wird

    //arbeite ohne eine Konfig.-Datei
    public static EntityManagerFactory getWithoutConfig() {
        Map<String, String> map = new HashMap<>();

        map.put("openjpa.ConnectionURL","jdbc:sqlite:MusicPlayerDB1.db");
        map.put("openjpa.ConnectionDriverName", "org.sqlite.JDBC");
        map.put("openjpa.RuntimeUnenhancedClasses", "supported");
        map.put("openjpa.jdbc.SynchronizeMappings", "false");

        // find all classes to registrate them
        List<Class<?>> types = new ArrayList<Class<?>>();
        types.add(model.Song.class);

        if (!types.isEmpty()) {
            StringBuffer buf = new StringBuffer();
            for (Class<?> c : types) {
                if (buf.length() > 0)
                    buf.append(";");
                buf.append(c.getName());
            }
            // <class>Pizza</class>
            map.put("openjpa.MetaDataFactory", "jpa(Types=" + buf.toString()+ ")");
        }

        return OpenJPAPersistence.getEntityManagerFactory(map);

    }

    private void setUpConfiguration() {
        if(useConfigFile){ //Fall: Wir nutzen Konfigurationsdatei
            factory = Persistence.createEntityManagerFactory("openjpa");
            //factory = Persistence.createEntityManagerFactory("openjpa", System.getProperties());
            manager = factory.createEntityManager(); //erstelle EntityManager
            trans = manager.getTransaction(); //erhalte EntityTransaction
        }else{ //Fall: Wir nutzen keine Konfigurationsdatei
            factory = getWithoutConfig(); //ohne Konfig.-Datei
            manager = factory.createEntityManager(); //erstelle EntityManager
            trans = manager.getTransaction(); //erhalte EntityTransaction
        }
    }

    @Override
    //öffne die Datenbankverbindung (OpenJPA)
    public void openWritableLibrary() throws IOException {
        setUpConfiguration();
        //todo delete Library from DB before saving
        trans.begin();
        Query q = manager.createQuery("DELETE FROM Song");
        q.executeUpdate();
        trans.commit();

    }



    @Override
    //schreibe ein Objekt (Song) in die Datenbak
    public void writeSong(Song s) throws IOException {
        trans.begin();
        manager.persist(s);
        trans.commit();
        System.out.println("JPA save song" + s.getTitle());
    }

    @Override
    //schreibe die Playlist library in die Datenbank
    public void writeLibrary(Playlist p) throws IOException {

            for (Song s : p) {
                writeSong(s);
            }


    }

    @Override
    public void openReadableLibrary() throws IOException {
       setUpConfiguration();
       resultList =  manager.createQuery("SELECT x FROM Song x").getResultList();

    }

    @Override
    //lese aus der Datenbank
    //lese ein einzelnes Objekt (model.Song) aus der Datenbank aus
    public Song readSong() throws IOException, ClassNotFoundException {
        return null;
    }

    @Override
    //lese aus der Datenbank
    //nutzte hierzu die Methode readSong()
    //erstelle so eine (neue) libraray (model.Playlist) -> siehe oben (23)
    public Playlist readLibrary() throws IOException, ClassNotFoundException {
        Playlist list = new model.Playlist();
        //liest library aus
        //ohne den Aufruf von readSong()
        for (Object o : resultList) {
            s = (model.Song) o;
            list.addSong(s);
        }
        return list;
    }

    @Override
    //schließe Datenbankverbindung
    public void closeWritableLibrary() {

//        if (manager != null) manager.close();
//        if (factory != null) factory.close();
//        manager.close();
//        factory.close();
    }

    @Override
    public void closeReadableLibrary() {
//        if (manager != null) manager.close();
//        if (factory != null) factory.close();
//        manager.close();
//        factory.close();
    }

    //Folgende Funktionen werden nicht benutzt
    /******************************************************************************************************** **/

    @Override
    public void writePlaylist(Playlist p) throws IOException {
        //nicht für diese Übung relevant
    }

    @Override
    public Playlist readPlaylist() throws IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public void openWritablePlaylist() throws IOException {
        //nicht für diese Übung relevant
    }

    @Override
    public void openReadablePlaylist() throws IOException {
        //nicht für diese Übung relevant
    }

    @Override
    public void closeWritablePlaylist() {
        //nicht für diese Übung relevant
    }

    @Override
    public void closeReadablePlaylist() {
        //nicht für diese Übung relevant
    }
}