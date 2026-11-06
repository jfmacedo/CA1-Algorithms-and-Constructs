# CA1 — Algorithms and Constructs: Fast Food Storage

CCT College Dublin — BSc (Hons) in Computing, Semester 5.

**Group:** João, Gabriel, Letícia

## The problem

A fast-food restaurant stores Burger, Pizza, Fries, Sandwich and Hotdog trays in a rectangular storage unit that holds **up to 8 trays**. The chef **adds food at the front** and **removes food from the opposite side**. Each item records its name, weight (g), best-before date (max 14 days) and the time it was placed in the storage.

## Chosen data structure: Queue (FIFO) with a circular array

First in, first out: the oldest food is always used first. All main operations are O(1). See [docs/COMPLEXITY.md](docs/COMPLEXITY.md).

## Project structure

```
src/main/java/foodstorage/
├── Main.java                 entry point
├── app/StorageApp.java       console menu
├── model/                    FoodItem (abstract) + Burger, Pizza, Fries, Sandwich, Hotdog, FoodType, FoodFactory
├── storage/                  FoodStorage (interface) + FoodQueue (circular array)
├── validation/               InputValidator
└── exceptions/               StorageFullException, StorageEmptyException, InvalidInputException
src/test/java/foodstorage/    JUnit 5 tests
docs/                         complexity analysis and test cases
```

## OOP principles used

- **Encapsulation:** private fields with getters in `FoodItem` and `FoodQueue`.
- **Abstraction / Interface:** `FoodStorage` defines what a storage does; `FoodQueue` implements it.
- **Inheritance:** `Burger`, `Pizza`, ... extend the abstract `FoodItem`; custom exceptions extend `Exception`.
- **Polymorphism:** `getType()` and `getCategory()` are overridden in each food subclass.

## How to run

1. Open NetBeans → **File → Open Project** → select this folder.
2. Press **F6** (Run). Tests: **Alt+F6**.

Requires JDK 17 or newer.

## Menu

1. Add food item · 2. Remove food item · 3. Show food at the top · 4. Display all · 5. Search · 6. Exit
