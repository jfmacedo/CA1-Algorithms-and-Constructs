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

    /** The weight must be a number between MIN_WEIGHT and MAX_WEIGHT grams. */
    public static double validateWeight(String input) throws InvalidInputException {
        if (input == null || input.trim().isEmpty()) {
            throw new InvalidInputException("Weight cannot be empty.");
        }
        double weight;
        try {
            weight = Double.parseDouble(input.trim());
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Weight must be a number (e.g. 250).");
        }
        if (Double.isNaN(weight) || Double.isInfinite(weight)) {
            throw new InvalidInputException("Weight must be a real number.");
        }
        if (weight < MIN_WEIGHT || weight > MAX_WEIGHT) {
            throw new InvalidInputException("Weight must be between " + (int) MIN_WEIGHT
                    + " and " + (int) MAX_WEIGHT + " grams.");
        }
        return weight;
    }

    /** Best-before date in dd/MM/yyyy, from today up to 14 days ahead. */
    public static LocalDate validateBestBefore(String input) throws InvalidInputException {
        return validateBestBefore(input, LocalDate.now());
    }

    /** Same as above, but 'today' can be fixed - useful for unit tests. */
    public static LocalDate validateBestBefore(String input, LocalDate today) throws InvalidInputException {
        if (input == null || input.trim().isEmpty()) {
            throw new InvalidInputException("Best-before date cannot be empty.");
        }
        LocalDate date;
        try {
            date = LocalDate.parse(input.trim(), DATE_FORMAT);
        } catch (DateTimeParseException e) {
            throw new InvalidInputException("Invalid date. Use the format dd/MM/yyyy (e.g. 05/11/2026).");
        }
        if (date.isBefore(today)) {
            throw new InvalidInputException("This food is already expired. Date cannot be in the past.");
        }
        LocalDate maxDate = today.plusDays(FoodItem.MAX_SHELF_LIFE_DAYS);
        if (date.isAfter(maxDate)) {
            throw new InvalidInputException("Best-before date can be at most "
                    + FoodItem.MAX_SHELF_LIFE_DAYS + " days from today (latest: "
                    + maxDate.format(DATE_FORMAT) + ").");
        }
        return date;
    }

    /** Menu choice must be a whole number between min and max. */
    public static int validateMenuOption(String input, int min, int max) throws InvalidInputException {
        if (input == null || input.trim().isEmpty()) {
            throw new InvalidInputException("Please type an option number.");
        }
        int option;
        try {
            option = Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Option must be a whole number between " + min + " and " + max + ".");
        }
        if (option < min || option > max) {
            throw new InvalidInputException("Option must be between " + min + " and " + max + ".");
        }
        return option;
    }
}
