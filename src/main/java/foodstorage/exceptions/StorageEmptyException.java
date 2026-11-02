package foodstorage.exceptions;

/**
 * Thrown when we try to remove or peek but there is no food in the storage.
 */
public class StorageEmptyException extends Exception {

    public StorageEmptyException(String message) {
        super(message);
    }
}
