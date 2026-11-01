package foodstorage.model;

import java.time.LocalDate;

/**
 * A Hotdog stored in the food storage unit.
 */
public class Hotdog extends FoodItem {

    public Hotdog(double weightGrams, LocalDate bestBefore) {
        super(FoodType.HOTDOG.getDisplayName(), weightGrams, bestBefore);
    }

    @Override
    public FoodType getType() {
        return FoodType.HOTDOG;
    }

    @Override
    public String getCategory() {
        return "Main course";
    }
}
