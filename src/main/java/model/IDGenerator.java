package model;

/**
 *
 */
public class IDGenerator {

    private long id = 0;

    public static long getNextID() throws IDOverFlowException {
        id++;
        return id;

    }

}
