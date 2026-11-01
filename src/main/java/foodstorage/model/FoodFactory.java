package foodstorage.model;

import java.time.LocalDate;

/**
 * Creates the correct FoodItem subclass for a given FoodType (Factory pattern).
 */
public final class FoodFactory {

    private FoodFactory() {
        // utility class - no objects needed
    }

    public static FoodItem create(FoodType type, double weightGrams, LocalDate bestBefore) {
        switch (type) {
            case BURGER:
                return new Burger(weightGrams, bestBefore);
            case PIZZA:
                return new Pizza(weightGrams, bestBefore);
            case FRIES:
                return new Fries(weightGrams, bestBefore);
            case SANDWICH:
                return new Sandwich(weightGrams, bestBefore);
            case HOTDOG:
                return new Hotdog(weightGrams, bestBefore);
            default:
                throw new IllegalArgumentException("Unknown food type: " + type);
        }
    }
}
