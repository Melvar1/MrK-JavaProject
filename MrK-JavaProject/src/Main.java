import java.time.LocalDate;
import java.time.format.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * Main class for the store application.
 * It handles user interactions and processes user choices.
 */
public class Main {
    // List to hold the valid options for the user to select
    public static List<Integer> validOptions = new ArrayList<>();

    /**
     * main method and entry point of the application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        // Initialization of core components and create new objects
        Database database = new Database();
        ProductService service = new ProductService(database); //creates service object
        // Prepare the list of valid options
        initializeOptions();
        // Display a welcome message to the user
        displayWelcomeMessage();
        // Create a scanner object to read user input
        Scanner scanner = new Scanner(System.in);

        // Main loop to process user selections and keeps program running
        int option;
        do {
            displaySelectionMessage();
            option = readOption(scanner);
            if (option != 0) {
                processOption(option, service, scanner);
            }
        } while (option != 0);

        scanner.close();
        System.out.println("Program terminated.");
    }

    /**
     * method initializes the list of valid options.
     */
    private static void initializeOptions() {
        for (int i = 0; i <= 9; i++) {
            validOptions.add(i);
        }
    }

    /**
     * method that prints the welcome message to the console.
     */
    private static void displayWelcomeMessage() {
        System.out.println("Welcome to System Market Online");
        System.out.println();
    }

    /**
     * Displays the selection menu to the user.
     */
    private static void displaySelectionMessage() {
        // Display the menu options
        System.out.println("Which display would you like to go to:");
        System.out.println("1: Products");
        System.out.println("2: Create Product.");
        System.out.println("3: Products To Refill.");
        System.out.println("4: Product Count.");
        System.out.println("5: Product's Expiry Date.");
        System.out.println("6: Display Expired Products.");
        System.out.println("7: Products In Mark Down.");
        System.out.println("8: Products For Mark Down.");
        System.out.println("To close the program at any time, press: 0.");
        System.out.println();
    }

    /**
     * Reads the user's option selection from the console.
     *
     * @param scanner Scanner object to read the user input.
     * @return The user's selected option.
     */
    private static int readOption(Scanner scanner) {
        // Loop until an integer is provided
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid Input. Select a valid option (Integer).");
            scanner.next(); // Clear invalid input
        }
        int option = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over
        // Check if the entered option is valid
        if (!validOptions.contains(option)) {
            System.out.println("Invalid Input. Select a valid option.");
            return -1; // This is just an indication that the input is not valid
        }
        return option;
    }

    /**
     * Parses the product ID from the given input string and converts to integer
     *
     * @param input The input string containing the product ID.
     * @return The parsed product ID, or null if the input is invalid.
     */
    private static Integer parseProductId(String input) {
        try {
            // Attempt to parse the input as an integer
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            // Input was not a valid integer
            System.out.println("Invalid Input. Please enter a valid integer for Product ID.");
            return null;
        }
    }

    /**
     * Processes the user's menu option selection.
     *
     * @param option   The user's selected option.
     * @param service  The ProductService instance.
     * @param scanner  The Scanner object for user input.
     */
    private static void processOption(int option, ProductService service, Scanner scanner) {
        String input;
        Integer productId;
        switch (option) {
            case 1:
                displayProductMessage();
                input = scanner.nextLine().trim(); // Ensure input is assigned
                if (input.equalsIgnoreCase("back")) {
                    // User wants to go back to the main menu
                    break;
                } else if (input.isEmpty()) {
                    service.displayAllProducts(); // Display all products
                } else {
                    productId = parseProductId(input); // Method to parse and validate the product ID
                    if (productId != null) {
                        service.displayProduct(productId); // Display a specific product
                    } else {
                        System.out.println("Invalid Product ID. Please try again.");
                    }
                }
                break;
            case 2:
                createProduct(service, scanner);
                break;
            case 3:
                displayRefillMessage();
                System.out.println("Enter Product ID (or press Enter to display all products to refill):");
                input = scanner.nextLine();
                if (input.isEmpty()) {
                    service.displayProductToRefill(null); // Display all products that need refill
                }
                break;
            case 4:
                displayCountRefillMessage();
                System.out.println("Enter Product ID (or press Enter to display count for all products):");
                input = scanner.nextLine();
                if (input.isEmpty()) {
                    service.displayProductCount(null); // Display count for all products
                } else {
                    productId = parseProductId(input); // Read the product ID
                    if (productId != null) {
                        service.displayProductCount(productId); // Display count for a specific product
                    }
                }
                break;
            case 5: // Display products by expiry date
                displayExpiryDateMessage();
                System.out.println("Enter Product ID (or press Enter to display expiry dates for all products):");
                input = scanner.nextLine();
                if (input.isEmpty()) {
                    service.displayProductsExpiryDate(); // Display expiry dates for all products
                } else {
                    productId = parseProductId(input); // Read the product ID
                    if (productId != null) {
                        service.displayProductsExpiryDate(); // Display expiry date for a specific product
                    }
                }
                break;
            case 6: // Display expired products
                displayMessage("Display Expired Products:");
                service.displayExpiredProducts();
                break;         
            case 7: //Display all products that are past the MarkdownDate.
                displayInMarkDownMessage();
                service.displayProductsInMarkDown();
                break;
            case 9: //Display all products that need to be marked down a week from now.
                displayForMarkDownMessage();
                service.displayProductsForMarkDown();
                break;
            default:
                displayMessage("Invalid Input. Select a valid option.");
                break;
        }
    }



    private static void displayProductMessage() {
        System.out.println("Products display:");
        System.out.println("Enter a Product ID to display details for that product.");
        System.out.println("Press Enter without typing anything to display all products.");
        System.out.println("To return to the main menu, type 'back' and press Enter.");
        System.out.println();
    }

    private static void displayRefillMessage() {
        System.out.println("Products To Refill display:");
        System.out.println("To search for a product using its ID, just write and press enter.");
        System.out.println("To show all products that must be restocked, press enter. To exit, press 0.");
        System.out.println();
    }

    private static void displayCountRefillMessage() {
        System.out.println("Product's Count Refill display:");
        System.out.println("To search for a product using its ID, just write and press enter.");
        System.out.println("To show all products that must be restocked, press enter. To exit, press 0.");
        System.out.println();
    }

    private static void displayExpiryDateMessage() {
        System.out.println("Enter the ID of the product to check expiry date (or press Enter for all):");
    }

    private static void displayInMarkDownMessage() {
        System.out.println("Displaying all products that are past the markdown date:");
    }

    private static void displayForMarkDownMessage() {
        System.out.println("Displaying all products that need to be marked down within a week:");
    }



    private static void displayMessage(String message) {
        System.out.println(message);
    }



    /**
     * Checks if the provided string can be parsed as an integer.
     *
     * @param s The string to check.
     * @return true if the string can be parsed as an integer, false otherwise.
     */
    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    /**
     * Handles the creation of a new product based on user input.
     *
     * @param service The ProductService instance.
     * @param scanner The Scanner object for user input.
     */
    private static void createProduct(ProductService service, Scanner scanner) {
        System.out.println("Enter Product ID:");
        String productIdStr = scanner.nextLine();
        if (!isInt(productIdStr)) {
            System.out.println("Product ID must be an integer.");
            return;
        }
        int productId = Integer.parseInt(productIdStr);

        System.out.println("Enter Product Name:");
        String productName = scanner.nextLine();
        if (productName.isEmpty()) {
            System.out.println("Product Name cannot be empty.");
            return;
        }

        System.out.println("Enter Expiry Date (MM/dd/yyyy) or press Enter for default (3 months from today):");
        String expiryDateStr = scanner.nextLine();
        LocalDate expiryDate;
        if (expiryDateStr.isEmpty()) {
            expiryDate = LocalDate.now().plusMonths(3);
        } else {
            try {
                expiryDate = LocalDate.parse(expiryDateStr, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter the date in MM/dd/yyyy format.");
                return;
            }
        }

        System.out.println("Enter Markdown Duration in days (or press Enter for default of 6 days before expiry):");
        String markdownStr = scanner.nextLine();
        LocalDate markdownDate;
        if (markdownStr.isEmpty()) {
            markdownDate = expiryDate.minusDays(6);
        } else if (isInt(markdownStr)) {
            long markdownDuration = Long.parseLong(markdownStr);
            markdownDate = expiryDate.minusDays(markdownDuration);
        } else {
            System.out.println("Invalid input for markdown duration. It must be an integer.");
            return;
        }

        //boolean that tells you that product was successfully created or not
        boolean isCreated = service.createProduct(productId, productName, expiryDate, markdownDate);
        if (isCreated) {
            System.out.println(productName + " with Product ID " + productId + " created successfully.");
        } else {
            System.out.println("Failed to create product. Ensure the Product ID is unique and all inputs are valid.");
        }
    }


}

