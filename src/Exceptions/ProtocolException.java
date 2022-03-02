package Exceptions;

public class ProtocolException extends Exception {

    /**
     * An exception that will be thrown when the messages do not follow the protocol.
     */

    private static final long serialVersionUID = 1L;

    public ProtocolException(String msg) {
        super(msg);
    }

}
