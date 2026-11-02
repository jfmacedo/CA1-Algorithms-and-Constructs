package foodstorage.storage;

import foodstorage.exceptions.StorageEmptyException;
import foodstorage.exceptions.StorageFullException;
import foodstorage.model.FoodItem;
import java.util.List;

/**
 * Operations that any food storage unit must support (abstraction via interface).
 */
public interface FoodStorage {

    /** Puts a food item into the storage. */
    void add(FoodItem item) throws StorageFullException;

    /** Takes the next food item out of the storage. */
    FoodItem remove() throws StorageEmptyException;

    /** Shows the next food item to be removed, without removing it. */
    FoodItem peek() throws StorageEmptyException;

    /** Returns all items in order, from the next to be removed to the last. */
    List<FoodItem> display();

    /**
     * Finds the first food item with the given name.
     *
     * @return its position (1 = next to be removed), or -1 if not found
     */
    int search(String name);

    boolean isEmpty();

    boolean isFull();

    int size();

    int capacity();
}
