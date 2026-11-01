package foodstorage.exceptions;

/**
 * Thrown when the user types an invalid value (name, weight or date).
 */
public class InvalidInputException extends Exception {

    public InvalidInputException(String message) {
        super(message);
    }
}