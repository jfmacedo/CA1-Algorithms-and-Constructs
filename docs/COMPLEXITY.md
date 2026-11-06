# Time Complexity Analysis — FoodQueue (circular array)

n = number of food items currently in the storage (maximum 8).

| Operation | Method | Best case | Worst case | Why |
|---|---|---|---|---|
| Add (enqueue) | `add()` | O(1) | O(1) | Only moves `rear` forward one slot `(rear + 1) % capacity` and writes the item. No shifting. |
| Remove (dequeue) | `remove()` | O(1) | O(1) | Reads `items[front]`, clears it and moves `front` forward. No shifting thanks to the circular array. |
| Peek (top) | `peek()` | O(1) | O(1) | Direct access to `items[front]`. |
| Display | `display()` | O(n) | O(n) | Must visit every stored item once. |
| Search | `search()` | O(1) | O(n) | Linear search from front to rear. Best case: item is at the front. Worst case: last or not present. |
| isEmpty / isFull / size | — | O(1) | O(1) | Uses the `count` variable. |

**Space complexity:** O(capacity) = O(8), a fixed array created once.

## Why a Queue (FIFO)?

- The scenario allows the chef to **add at the front and remove from the opposite side** — this is exactly how a queue works: the first tray in is the first tray out.
- Every item has a **best-before date (max 14 days)**. FIFO guarantees the **oldest food is always used first**, reducing waste and food-safety risk. A stack (LIFO) would leave the oldest food stuck at the bottom.
- The storage holds a **fixed maximum of 8 trays**, so a fixed-size array is a natural fit and uses no extra memory.

## Why a circular array instead of a normal array or linked list?

- **Normal array queue:** removing from index 0 requires shifting all other items left → O(n). The circular array avoids this → O(1).
- **Linked list:** also O(1) for add/remove, but each node needs extra memory for a pointer and capacity must be checked manually. With a known small limit (8), an array is simpler and more cache-friendly.

## Limitations

- Capacity is fixed (8). To store more, a new array would be needed.
- Search is linear O(n) — acceptable for n ≤ 8, but would be slow for very large storages (a HashMap index could make it O(1)).
- Only the item at the front can be removed; a specific item in the middle cannot be taken out directly (this is by design of a queue).
