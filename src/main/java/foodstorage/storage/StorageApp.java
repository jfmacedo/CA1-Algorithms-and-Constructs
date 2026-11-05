package foodstorage.app;

import foodstorage.exceptions.InvalidInputException;
import foodstorage.exceptions.StorageEmptyException;
import foodstorage.exceptions.StorageFullException;
import foodstorage.model.FoodFactory;
import foodstorage.model.FoodItem;
import foodstorage.model.FoodType;
import foodstorage.storage.FoodQueue;
import foodstorage.storage.FoodStorage;
import foodstorage.validation.InputValidator;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Console menu that lets the chef use the food storage.
 */
public class StorageApp {

    private static final int OPTION_ADD = 1;
    private static final int OPTION_REMOVE = 2;
    private static final int OPTION_PEEK = 3;
    private static final int OPTION_DISPLAY = 4;
    private static final int OPTION_SEARCH = 5;
    private static final int OPTION_EXIT = 6;

    private final FoodStorage storage;
    private final Scanner scanner;

    public StorageApp() {
        this(new FoodQueue(), new Scanner(System.in));
    }

    public StorageApp(FoodStorage storage, Scanner scanner) {
        this.storage = storage;
        this.scanner = scanner;
    }

    public void run() {
        System.out.println("==============================================");
        System.out.println("   FAST FOOD STORAGE  -  Queue (FIFO), 8 trays");
        System.out.println("   Add at the FRONT  ->  Remove at the BACK");
        System.out.println("==============================================");

        boolean running = true;
        while (running) {
            printMenu();
            String line = readLine("Choose an option: ");
            if (line == null) {          // input stream closed
                break;
            }
            int option;
            try {
                option = InputValidator.validateMenuOption(line, OPTION_ADD, OPTION_EXIT);
            } catch (InvalidInputException e) {
                System.out.println("[ERROR] " + e.getMessage());
                continue;
            }

            switch (option) {
                case OPTION_ADD:
                    addFood();
                    break;
                case OPTION_REMOVE:
                    removeFood();
                    break;
                case OPTION_PEEK:
                    peekFood();
                    break;
                case OPTION_DISPLAY:
                    displayFood();
                    break;
                case OPTION_SEARCH:
                    searchFood();
                    break;
                case OPTION_EXIT:
                    running = false;
                    break;
                default:
                    break;
            }
        }
        System.out.println("Application terminated. Goodbye!");
    }

    private void printMenu() {
        System.out.println();
        System.out.println("---------------- MENU (" + storage.size() + "/" + storage.capacity() + " trays used) ----------------");
        System.out.println(" 1. Add food item");
        System.out.println(" 2. Remove food item");
        System.out.println(" 3. Show food item at the top (next to be removed)");
        System.out.println(" 4. Display all food items");
        System.out.println(" 5. Search food item by name");
        System.out.println(" 6. Exit");
    }

    private void addFood() {
        if (storage.isFull()) {
            System.out.println("[ERROR] Storage is full (" + storage.capacity()
                    + " trays). Remove an item first.");
            return;
        }

        FoodType type = null;
        while (type == null) {
            String input = readLine("Food name (Burger, Pizza, Fries, Sandwich, Hotdog): ");
            if (input == null) {
                return;
            }
            try {
                type = InputValidator.validateFoodName(input);
            } catch (InvalidInputException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }

        double weight = -1;
        while (weight < 0) {
            String input = readLine("Weight in grams: ");
            if (input == null) {
                return;
            }
            try {
                weight = InputValidator.validateWeight(input);
            } catch (InvalidInputException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }

        LocalDate bestBefore = null;
        while (bestBefore == null) {
            String input = readLine("Best-before date (dd/MM/yyyy, max 14 days): ");
            if (input == null) {
                return;
            }
            try {
                bestBefore = InputValidator.validateBestBefore(input);
            } catch (InvalidInputException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }

        try {
            FoodItem item = FoodFactory.create(type, weight, bestBefore);
            storage.add(item);
            System.out.println("[OK] Added: " + item);
        } catch (StorageFullException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private void removeFood() {
        try {
            FoodItem removed = storage.remove();
            System.out.println("[OK] Removed: " + removed);
        } catch (StorageEmptyException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private void peekFood() {
        try {
            FoodItem top = storage.peek();
            System.out.println("Food at the top: " + top.getName());
            System.out.println("Details: " + top);
        } catch (StorageEmptyException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private void displayFood() {
        if (storage.isEmpty()) {
            System.out.println("[INFO] Storage is empty. No food items to display.");
            return;
        }
        List<FoodItem> items = storage.display();
        System.out.println("Food in storage (position 1 = next to be removed):");
        for (int i = 0; i < items.size(); i++) {
            String flag = items.get(i).isExpired() ? "  <-- EXPIRED" : "";
            System.out.println(" " + (i + 1) + ". " + items.get(i) + flag);
        }
    }

    private void searchFood() {
        if (storage.isEmpty()) {
            System.out.println("[INFO] Storage is empty. Nothing to search.");
            return;
        }
        String input = readLine("Food name to search: ");
        if (input == null) {
            return;
        }
        try {
            FoodType type = InputValidator.validateFoodName(input);
            int position = storage.search(type.getDisplayName());
            if (position == -1) {
                System.out.println("[INFO] No " + type.getDisplayName() + " in the storage.");
            } else {
                System.out.println("[OK] " + type.getDisplayName() + " found at position "
                        + position + " (1 = next to be removed).");
            }
        } catch (InvalidInputException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    /** Reads one line; returns null if there is no more input. */
    private String readLine(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) {
            return null;
        }
        return scanner.nextLine();
    }
}