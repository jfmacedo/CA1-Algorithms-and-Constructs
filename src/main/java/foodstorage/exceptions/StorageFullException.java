package foodstorage.exceptions;

/**
 * Thrown when we try to add a food item but all 8 trays are already in use.
 */
public class StorageFullException extends Exception {

    public StorageFullException(String message) {
        super(message);
    }
}
