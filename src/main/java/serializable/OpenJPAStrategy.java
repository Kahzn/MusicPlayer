package serializable;

import interfaces.Playlist;
import interfaces.SerializableStrategy;
import interfaces.Song;
import org.apache.openjpa.lib.rop.ResultList;
import org.apache.openjpa.persistence.OpenJPAPersistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenJPAStrategy implements SerializableStrategy {

    private EntityManagerFactory factory = null;
    private EntityManager manager = null;
    private EntityTransaction trans = null;

    //private model.Playlist newLib = new model.Playlist();
    private model.Song s;
    private ResultList resultList;

    private boolean konficIsUsed = false; //wird eine Konfig.-Datei genutzt?

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

    @Override
    //öffne die Datenbankverbindung (OpenJPA)
    public void openWritableLibrary() throws IOException {
        if (konficIsUsed == false) {
            if (factory == null) factory = getWithoutConfig(); //ohne Konfig.-Datei
            if (manager == null) manager = factory.createEntityManager(); //erstelle EntityManager
            if (trans == null) trans = manager.getTransaction(); //erhalte EntityTransaction
        }  else {
            if (factory == null) factory = Persistence.createEntityManagerFactory("persistence.xml");
            if (manager == null) manager = factory.createEntityManager(); //erstelle EntityManager
            if (trans == null) trans = manager.getTransaction(); //erhalte EntityTransaction
        }
    }

    @Override
    //schreibe ein Objekt (Song) in die Datenbak
    public void writeSong(Song s) throws IOException {
        manager.persist(s);
        System.out.println("writing Song: " + s.getTitle());
    }

    @Override
    //schreibe die Playlist library in die Datenbank
    public void writeLibrary(Playlist p) throws IOException {
        trans.begin();
        for (Song s : p) {
            writeSong(s);
        }
        trans.commit();
    }

    @Override
    public void openReadableLibrary() throws IOException {
        openWritableLibrary();
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
        //liest library aus
        //ohne den Aufruf von readSong()
        for (Object o : manager.createQuery("SELECT x FROM Library x").getResultList()) {
            s = (model.Song) o;
            resultList.add(s);
            //System.out.println(z.getId()+") "+z.getName() + ", Small: " +z.getSmall()+", Big: "+z.getBig());
        }
        return (Playlist) resultList;
    }

    @Override
    //schließe Datenbankverbindung
    public void closeWritableLibrary() {
        if (manager != null) manager.close();
        if (factory != null) factory.close();
    }

    @Override
    public void closeReadableLibrary() {
        if (manager != null) manager.close();
        if (factory != null) factory.close();
    }

    /** ***************************************************************************************************** **/

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