package foodstorage.validation;

import foodstorage.exceptions.InvalidInputException;
import foodstorage.model.FoodItem;
import foodstorage.model.FoodType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Checks every value the user types before it reaches the storage.
 */
public final class InputValidator {

    public static final double MIN_WEIGHT = 1;     // grams
    public static final double MAX_WEIGHT = 5000;  // grams (5 kg per tray)

    // STRICT makes impossible dates such as 31/02/2026 fail
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    private InputValidator() {
        // utility class - no objects needed
    }

    /** The name must be one of: Burger, Pizza, Fries, Sandwich, Hotdog. */
    public static FoodType validateFoodName(String input) throws InvalidInputException {
        if (input == null || input.trim().isEmpty()) {
            throw new InvalidInputException("Food name cannot be empty.");
        }
        FoodType type = FoodType.fromString(input);
        if (type == null) {
            throw new InvalidInputException("'" + input.trim()
                    + "' is not on the menu. Choose: Burger, Pizza, Fries, Sandwich or Hotdog.");
        }
        return type;
    }
}
