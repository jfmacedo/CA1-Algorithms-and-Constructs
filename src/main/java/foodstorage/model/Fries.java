package foodstorage.model;

import java.time.LocalDate;

/**
 * A Fries stored in the food storage unit.
 */
public class Fries extends FoodItem {

    public Fries(double weightGrams, LocalDate bestBefore) {
        super(FoodType.FRIES.getDisplayName(), weightGrams, bestBefore);
    }

    @Override
    public FoodType getType() {
        return FoodType.FRIES;
    }

    @Override
    public String getCategory() {
        return "Side";
    }
}
