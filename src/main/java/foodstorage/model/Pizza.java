package foodstorage.model;

import java.time.LocalDate;

/**
 * A Pizza stored in the food storage unit.
 */
public class Pizza extends FoodItem {

    public Pizza(double weightGrams, LocalDate bestBefore) {
        super(FoodType.PIZZA.getDisplayName(), weightGrams, bestBefore);
    }

    @Override
    public FoodType getType() {
        return FoodType.PIZZA;
    }

    @Override
    public String getCategory() {
        return "Main course";
    }
}
