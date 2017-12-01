package exception;

public class IDOverFlowException extends RuntimeException {

    private final long maxID = 9999;

    public IDOverFlowException(long id) {
        super();
        if (id > maxID) {
            throw IDOverFlowException;
        }
    }

}
