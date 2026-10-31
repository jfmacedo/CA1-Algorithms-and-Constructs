package foodstorage.model;

/**
 * The five types of food the restaurant can store.
 */
public enum FoodType {
    BURGER("Burger"),
    PIZZA("Pizza"),
    FRIES("Fries"),
    SANDWICH("Sandwich"),
    HOTDOG("Hotdog");

    private final String displayName;

    FoodType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * Converts user text (e.g. "pizza", " Hot dog ") into a FoodType.
     *
     * @return the matching FoodType, or null if the text is not a valid food
     */
    public static FoodType fromString(String text) {
        if (text == null) {
            return null;
        }
        String clean = text.trim().replace(" ", "").replace("-", "");
        for (FoodType type : values()) {
            if (type.displayName.equalsIgnoreCase(clean)) {
                return type;
            }
        }
        return null;
    }
}
