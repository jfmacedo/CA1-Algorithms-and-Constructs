package foodstorage.model;

import java.time.LocalDate;

/**
 * A Sandwich stored in the food storage unit.
 */
public class Sandwich extends FoodItem {

    public Sandwich(double weightGrams, LocalDate bestBefore) {
        super(FoodType.SANDWICH.getDisplayName(), weightGrams, bestBefore);
    }

    @Override
    public FoodType getType() {
        return FoodType.SANDWICH;
    }

    @Override
    public String getCategory() {
        return "Main course";
    }
}
