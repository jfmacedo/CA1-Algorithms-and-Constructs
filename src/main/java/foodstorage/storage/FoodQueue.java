package foodstorage.storage;

import foodstorage.exceptions.StorageEmptyException;
import foodstorage.exceptions.StorageFullException;
import foodstorage.model.FoodItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Food storage implemented as a QUEUE (FIFO - First In, First Out)
 * using a fixed-size circular array.
 *
 * Scenario: the chef ADDS food at the FRONT side of the storage unit and
 * REMOVES food from the OPPOSITE side. So the first tray placed in is the
 * first tray taken out - the oldest food is always used first, which is
 * important because every item has a best-before date.
 *
 * The array is "circular": when 'rear' reaches the end of the array it wraps
 * back to index 0, so free slots left by removed items are reused and no
 * element ever needs to be shifted.
 */
public class FoodQueue implements FoodStorage {

    public static final int DEFAULT_CAPACITY = 8;

    private final FoodItem[] items;
    private int front;  // index of the next item to be removed
    private int rear;   // index of the last item added
    private int count;  // how many items are stored now

    public FoodQueue() {
        this(DEFAULT_CAPACITY);
    }

    public FoodQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero.");
        }
        items = new FoodItem[capacity];
        front = 0;
        rear = -1;
        count = 0;
    }

    /** Enqueue - Time complexity: O(1). */
    @Override
    public void add(FoodItem item) throws StorageFullException {
        if (item == null) {
            throw new IllegalArgumentException("Food item cannot be null.");
        }
        if (isFull()) {
            throw new StorageFullException("Storage is full (" + items.length
                    + " trays). Remove an item before adding a new one.");
        }
        rear = (rear + 1) % items.length;  // move rear forward, wrapping around
        items[rear] = item;
        count++;
    }

    /** Dequeue - Time complexity: O(1). */
    @Override
    public FoodItem remove() throws StorageEmptyException {
        if (isEmpty()) {
            throw new StorageEmptyException("Storage is empty. There is no food to remove.");
        }
        FoodItem removed = items[front];
        items[front] = null;                 // free the slot
        front = (front + 1) % items.length;  // move front forward, wrapping around
        count--;
        return removed;
    }

    /** Peek - Time complexity: O(1). */
    @Override
    public FoodItem peek() throws StorageEmptyException {
        if (isEmpty()) {
            throw new StorageEmptyException("Storage is empty. There is no food at the top.");
        }
        return items[front];
    }

    /** Display - Time complexity: O(n), visits every stored item once. */
    @Override
    public List<FoodItem> display() {
        List<FoodItem> result = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            result.add(items[(front + i) % items.length]);
        }
        return result;
    }

    /** Linear search - Time complexity: O(n) worst case, O(1) best case. */
    @Override
    public int search(String name) {
        if (name == null || name.trim().isEmpty()) {
            return -1;
        }
        String target = name.trim();
        for (int i = 0; i < count; i++) {
            FoodItem current = items[(front + i) % items.length];
            if (current.getName().equalsIgnoreCase(target)) {
                return i + 1;  // 1 = next item to be removed
            }
        }
        return -1;
    }

    /** O(1) */
    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    /** O(1) */
    @Override
    public boolean isFull() {
        return count == items.length;
    }

    /** O(1) */
    @Override
    public int size() {
        return count;
    }

    /** O(1) */
    @Override
    public int capacity() {
        return items.length;
    }
}

