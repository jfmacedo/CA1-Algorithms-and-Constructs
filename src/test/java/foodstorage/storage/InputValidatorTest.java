package foodstorage.validation;

import static org.junit.jupiter.api.Assertions.*;

import foodstorage.exceptions.InvalidInputException;
import foodstorage.model.FoodType;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class InputValidatorTest {

    private static final LocalDate TODAY = LocalDate.of(2026, 11, 1);

    // ---------- food name ----------
    @Test
    void validNamesAreAccepted() throws Exception {
        assertEquals(FoodType.BURGER, InputValidator.validateFoodName("Burger"));
        assertEquals(FoodType.PIZZA, InputValidator.validateFoodName("  pizza "));
        assertEquals(FoodType.HOTDOG, InputValidator.validateFoodName("Hot dog"));
    }

    @Test
    void invalidNamesAreRejected() {
        assertThrows(InvalidInputException.class, () -> InputValidator.validateFoodName("Cake"));
        assertThrows(InvalidInputException.class, () -> InputValidator.validateFoodName(""));
        assertThrows(InvalidInputException.class, () -> InputValidator.validateFoodName(null));
        assertThrows(InvalidInputException.class, () -> InputValidator.validateFoodName("123"));
    }

    // ---------- weight ----------
    @Test
    void validWeightIsAccepted() throws Exception {
        assertEquals(250.0, InputValidator.validateWeight("250"));
        assertEquals(99.5, InputValidator.validateWeight(" 99.5 "));
    }

    @Test
    void invalidWeightIsRejected() {
        assertThrows(InvalidInputException.class, () -> InputValidator.validateWeight("abc"));
        assertThrows(InvalidInputException.class, () -> InputValidator.validateWeight("-10"));
        assertThrows(InvalidInputException.class, () -> InputValidator.validateWeight("0"));
        assertThrows(InvalidInputException.class, () -> InputValidator.validateWeight("99999"));
        assertThrows(InvalidInputException.class, () -> InputValidator.validateWeight(""));
        assertThrows(InvalidInputException.class, () -> InputValidator.validateWeight("NaN"));
    }

    // ---------- best-before date ----------
    @Test
    void dateWithinTwoWeeksIsAccepted() throws Exception {
        assertEquals(TODAY, InputValidator.validateBestBefore("01/11/2026", TODAY));
        assertEquals(TODAY.plusDays(14), InputValidator.validateBestBefore("15/11/2026", TODAY));
    }

    @Test
    void dateInThePastIsRejected() {
        assertThrows(InvalidInputException.class,
                () -> InputValidator.validateBestBefore("31/10/2026", TODAY));
    }

    @Test
    void dateMoreThanTwoWeeksAheadIsRejected() {
        assertThrows(InvalidInputException.class,
                () -> InputValidator.validateBestBefore("16/11/2026", TODAY));
    }

    @Test
    void wrongFormatOrImpossibleDateIsRejected() {
        assertThrows(InvalidInputException.class,
                () -> InputValidator.validateBestBefore("2026-11-05", TODAY));
        assertThrows(InvalidInputException.class,
                () -> InputValidator.validateBestBefore("31/02/2026", TODAY));
        assertThrows(InvalidInputException.class,
                () -> InputValidator.validateBestBefore("hello", TODAY));
        assertThrows(InvalidInputException.class,
                () -> InputValidator.validateBestBefore("", TODAY));
    }

    // ---------- menu option ----------
    @Test
    void menuOptionValidation() throws Exception {
        assertEquals(3, InputValidator.validateMenuOption("3", 1, 6));
        assertThrows(InvalidInputException.class, () -> InputValidator.validateMenuOption("7", 1, 6));
        assertThrows(InvalidInputException.class, () -> InputValidator.validateMenuOption("x", 1, 6));
    }
}
