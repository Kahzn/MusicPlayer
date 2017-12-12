package serializable;

import interfaces.Playlist;
import interfaces.SerializableStrategy;
import interfaces.Song;
import org.apache.openjpa.persistence.OpenJPAPersistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenJPAStrategy implements SerializableStrategy {

    private EntityManagerFactory fac =null;
    private EntityManager em = null;
    private EntityTransaction tr = null;

    public static EntityManagerFactory getWithoutConfig() {

        Map<String, String> map = new HashMap<String, String>();

        map.put("openjpa.ConnectionURL","jdbc:sqlite:MusicPlayerDB1.db");
        map.put("openjpa.ConnectionDriverName", "org.sqlite.JDBC");
        map.put("openjpa.RuntimeUnenhancedClasses", "supported");
        map.put("openjpa.jdbc.SynchronizeMappings", "false");

        // find all classes to register them
        List<Class<?>> types = new ArrayList<Class<?>>();
        types.add(model.Song.class);

        if (!types.isEmpty()) {
            StringBuffer buf = new StringBuffer();
            for (Class<?> c : types) {
                if (buf.length() > 0)
                    buf.append(";");
                buf.append(c.getName());
            }
            // <class>model.Song</class>
            map.put("openjpa.MetaDataFactory", "jpa(Types=" + buf.toString()+ ")");
        }

        return OpenJPAPersistence.getEntityManagerFactory(map);

    }
    public static EntityManagerFactory getWithConfig() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "openjpa" );
        return emfactory;
    }


    @Override
    public void openWritableLibrary() throws IOException {
        fac = getWithoutConfig();
        //fac = getWithConfig();

        em = fac.createEntityManager();


        //A database tr consists of a set of SQL
        // operations that are committed or rolled back as a single unit.
        tr = em.getTransaction();

        //lösche existierende Library Tabelle aus der DB
        em.createNativeQuery("DELETE FROM Library").executeUpdate();




    }

    @Override
    public void writeSong(Song s) throws IOException {
        em.persist(s);


    }

    @Override
    public void writeLibrary(Playlist p) throws IOException {

        for( Song s : p){
            tr.begin();
            writeSong(s);
            tr.commit();
        }


    }



    @Override
    public void openReadableLibrary() throws IOException {

    }


    @Override
    public Song readSong() throws IOException, ClassNotFoundException {
        return null;
    }


    @Override
    public Playlist readLibrary() throws IOException, ClassNotFoundException {
        return null;
    }
    //Nicht zu implementieren
    //////////////////////////////

    @Override
    public void openWritablePlaylist() throws IOException {

    }

    @Override
    public void openReadablePlaylist() throws IOException {

    }
    @Override
    public void writePlaylist(Playlist p) throws IOException {

    }

    @Override
    public Playlist readPlaylist() throws IOException, ClassNotFoundException {
        return null;
    }

    ///////////////////////
    @Override
    public void closeWritableLibrary() {
        em.close();
        fac.close();

    }

    @Override
    public void closeReadableLibrary() {

    }

    @Override
    public void closeWritablePlaylist() {

    }

    @Override
    public void closeReadablePlaylist() {

    }
}
