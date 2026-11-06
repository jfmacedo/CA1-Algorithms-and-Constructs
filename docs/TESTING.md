# Testing and Validation

## 1. Automated tests (JUnit 5)

Run in NetBeans: right-click the project → **Test** (or Alt+F6).

- `FoodQueueTest` — empty storage errors, capacity of 8, FIFO order, peek, circular wrap-around, search.
- `InputValidatorTest` — valid/invalid names, weights and dates, menu options.

## 2. Manual test cases (show these in the video)

| # | Action | Input | Expected result | Pass? |
|---|---|---|---|---|
| 1 | Remove with empty storage | option 2 | `[ERROR] Storage is empty...` | |
| 2 | Peek with empty storage | option 3 | `[ERROR] Storage is empty...` | |
| 3 | Display with empty storage | option 4 | `[INFO] Storage is empty...` | |
| 4 | Search with empty storage | option 5 | `[INFO] Storage is empty...` | |
| 5 | Invalid menu option | `9`, `abc` | Error, menu shown again | |
| 6 | Invalid food name | `Cake` | Error, asks again | |
| 7 | Invalid weight | `abc`, `-5`, `0`, `99999` | Error, asks again | |
| 8 | Date in the past | yesterday's date | Error, asks again | |
| 9 | Date more than 14 days ahead | today + 20 days | Error, asks again | |
| 10 | Wrong date format | `2026-11-05`, `31/02/2026` | Error, asks again | |
| 11 | Add valid item | `Burger`, `250`, today + 3 | `[OK] Added: Burger ...` | |
| 12 | Fill storage | add 8 items | 9th add → `[ERROR] Storage is full` | |
| 13 | FIFO order | add Burger, Pizza, Fries → remove | Burger removed first | |
| 14 | Peek shows top | option 3 | Shows the first item added, size unchanged | |
| 15 | Search found / not found | `Pizza` / `Hotdog` | Position shown / not found message | |
| 16 | Exit | option 6 | `Application terminated. Goodbye!` | |
