package foodstorage.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Abstract parent class for every food item kept in the storage.
 * Each concrete food (Burger, Pizza, ...) extends this class (inheritance).
 */
public abstract class FoodItem {

    /** A food item can have a best-before date of at most two weeks. */
    public static final int MAX_SHELF_LIFE_DAYS = 14;

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private final String name;
    private final double weightGrams;
    private final LocalDate bestBefore;
    private final LocalDateTime timeAdded;

    protected FoodItem(String name, double weightGrams, LocalDate bestBefore) {
        this.name = name;
        this.weightGrams = weightGrams;
        this.bestBefore = bestBefore;
        this.timeAdded = LocalDateTime.now(); // time it was placed into the storage
    }

    /** Each subclass says which type of food it is. */
    public abstract FoodType getType();

    /** Each subclass says its menu category (polymorphism). */
    public abstract String getCategory();

    public String getName() {
        return name;
    }

    public double getWeightGrams() {
        return weightGrams;
    }

    public LocalDate getBestBefore() {
        return bestBefore;
    }

    public LocalDateTime getTimeAdded() {
        return timeAdded;
    }

    public boolean isExpired() {
        return LocalDate.now().isAfter(bestBefore);
    }

    @Override
    public String toString() {
        return String.format("%-9s | %-11s | %7.1f g | best before %s | added %s",
                name, getCategory(), weightGrams,
                bestBefore.format(DATE_FORMAT), timeAdded.format(TIME_FORMAT));
    }
}
