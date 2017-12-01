package model;
import exception.IDOverFlowException;

/**
 *
 */
public class IDGenerator {

    private static long id=0;

    public static long getNextID()  {
        if((id+1)> 9999){
            throw new IDOverFlowException();
        }else{
            id++;
            return id;
        }


    }

}
