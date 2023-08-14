package labseq.com.backend_api.exceptions;

/**
 * Exception thrown when a user tries to calculate the labseq sequence value with an invalid N
 */
public class IncorrectParameterValueException extends RuntimeException {
    public IncorrectParameterValueException(String message) {
        super(message);
    }
}
