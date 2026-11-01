package foodstorage.model;

import java.time.LocalDate;

/**
 * A Burger stored in the food storage unit.
 */
public class Burger extends FoodItem {

    public Burger(double weightGrams, LocalDate bestBefore) {
        super(FoodType.BURGER.getDisplayName(), weightGrams, bestBefore);
    }

    @Override
    public FoodType getType() {
        return FoodType.BURGER;
    }

    @Override
    public String getCategory() {
        return "Main course";
    }
}
