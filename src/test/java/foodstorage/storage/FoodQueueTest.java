package foodstorage.storage;

import static org.junit.jupiter.api.Assertions.*;

import foodstorage.exceptions.StorageEmptyException;
import foodstorage.exceptions.StorageFullException;
import foodstorage.model.FoodFactory;
import foodstorage.model.FoodItem;
import foodstorage.model.FoodType;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FoodQueueTest {

    private FoodQueue queue;

    @BeforeEach
    void setUp() {
        queue = new FoodQueue();
    }

    private FoodItem item(FoodType type) {
        return FoodFactory.create(type, 200, LocalDate.now().plusDays(3));
    }

    @Test
    void newQueueIsEmptyWithCapacity8() {
        assertTrue(queue.isEmpty());
        assertFalse(queue.isFull());
        assertEquals(0, queue.size());
        assertEquals(8, queue.capacity());
    }

    @Test
    void removeFromEmptyThrows() {
        assertThrows(StorageEmptyException.class, () -> queue.remove());
    }

    @Test
    void peekOnEmptyThrows() {
        assertThrows(StorageEmptyException.class, () -> queue.peek());
    }

    @Test
    void displayOnEmptyReturnsEmptyList() {
        assertTrue(queue.display().isEmpty());
    }

    @Test
    void addNullIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> queue.add(null));
    }

    @Test
    void canAddExactly8ThenFull() throws Exception {
        for (int i = 0; i < 8; i++) {
            queue.add(item(FoodType.PIZZA));
        }
        assertTrue(queue.isFull());
        assertThrows(StorageFullException.class, () -> queue.add(item(FoodType.BURGER)));
        assertEquals(8, queue.size());
    }

    @Test
    void firstInFirstOut() throws Exception {
        queue.add(item(FoodType.BURGER));
        queue.add(item(FoodType.PIZZA));
        queue.add(item(FoodType.FRIES));

        assertEquals("Burger", queue.peek().getName());
        assertEquals("Burger", queue.remove().getName());
        assertEquals("Pizza", queue.remove().getName());
        assertEquals("Fries", queue.remove().getName());
        assertTrue(queue.isEmpty());
    }

    @Test
    void peekDoesNotRemove() throws Exception {
        queue.add(item(FoodType.HOTDOG));
        queue.peek();
        assertEquals(1, queue.size());
    }

    @Test
    void circularArrayWrapsAroundCorrectly() throws Exception {
        for (int i = 0; i < 8; i++) {
            queue.add(item(FoodType.PIZZA));
        }
        queue.remove();
        queue.remove();
        queue.add(item(FoodType.SANDWICH));   // goes into a reused slot
        queue.add(item(FoodType.HOTDOG));

        List<FoodItem> all = queue.display();
        assertEquals(8, all.size());
        assertEquals("Sandwich", all.get(6).getName());
        assertEquals("Hotdog", all.get(7).getName());
        assertTrue(queue.isFull());
    }

    @Test
    void searchFindsPositionIgnoringCase() throws Exception {
        queue.add(item(FoodType.BURGER));
        queue.add(item(FoodType.FRIES));
        assertEquals(1, queue.search("burger"));
        assertEquals(2, queue.search("FRIES"));
    }

    @Test
    void searchNotFoundReturnsMinusOne() throws Exception {
        queue.add(item(FoodType.BURGER));
        assertEquals(-1, queue.search("Pizza"));
        assertEquals(-1, queue.search(""));
        assertEquals(-1, queue.search(null));
    }

    @Test
    void invalidCapacityRejected() {
        assertThrows(IllegalArgumentException.class, () -> new FoodQueue(0));
    }
}
