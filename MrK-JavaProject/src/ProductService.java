import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Provides services related to products, including creation and display functions.
 */
class ProductService {
    private final Database database; // The database where products are stored.

    /**
     * Constructs a ProductService with a reference to a database.
     *
     * @param database The database containing product data.
     */
    public ProductService(Database database) {
        this.database = database;
    }

    /**
     * Creates a new product with the specified details and adds it to the database.
     *
     * @param productId   The unique ID for the new product.
     * @param productName The name of the new product.
     * @param expiryDate  The expiry date of the new product.
     * @param markDownDate The start date for the markdown period of the new product.
     * @return true if the product is successfully created and added; false otherwise.
     */
    public boolean createProduct(int productId, String productName, LocalDate expiryDate, LocalDate markDownDate) {
        // Check if the product ID is unique
        if (!isUniqueId(productId)) {
            System.out.println("Product ID must be unique. " + productName + " already exists with the same unique ID.");
            return false;
        }

        // Create and add the product to the database
        Product product = new Product(productId, productName, expiryDate, markDownDate);
        database.getProductList().add(product);
        System.out.println(productName + " with Product ID " + productId + " created successfully.");
        return true;
    }

    /**
     * Checks if the provided product ID is unique within the database.
     *
     * @param productId The product ID to check.
     * @return true if no other product in the database has the same ID; false otherwise.
     */
    private boolean isUniqueId(int productId) {
        return database.getProductList().stream().noneMatch(p -> p.getId() == productId);
    }

    /**
     * Displays the details of a given product.
     *
     * @param product The product to display.
     */
    public static void showProduct(Product product) {
        System.out.printf("ProductId: %d. Product Name: %s. Expiry date: %s. Time Duration For Mark Down: %s%n",
                product.getId(),
                product.getProductName(),
                formatDate(product.getExpiryDate()),
                formatDate(product.getTimeDurationForMarkDown()));
    }

    /**
     * Formats a LocalDate to a string in the format MM/dd/yyyy.
     *
     * @param date The date to format.
     * @return A string representation of the date.
     */
    private static String formatDate(LocalDate date) {
        if (date != null) {
            return date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        }
        return "N/A";
    }

    /**
     * Displays refill information for a single product or all products based on the provided ID.
     * If the ID is null, all products that need refilling are displayed.
     *
     * @param productId The ID of the product to check for refill, or null to check all products.
     */

    public void displayProductToRefill(Integer productId) {
        boolean refillNeeded = false;
        for (Product product : database.getProductList()) {
            BigDecimal quantityToRefill = ProductShelfService.quantityToRefill(product.getProductShelf());

            if (productId == null || product.getId() == productId) {
                if (quantityToRefill.compareTo(BigDecimal.ZERO) > 0) {
                    refillNeeded = true;
                    System.out.println("Product ID: " + product.getId() + " needs to be refilled by " + quantityToRefill);
                } else if (productId != null) {
                    System.out.println("Product ID: " + productId + " does not need to be refilled.");
                }
            }
        }

        if (!refillNeeded && productId == null) {
            System.out.println("No products need to be replenished.");
        }
    }

    /**
     * Displays the count of a single product or all products on the shelf based on the provided ID.
     * If the ID is null, counts for all products are displayed.
     *
     * @param productId The ID of the product to display the count for, or null to display all counts.
     */
    public void displayProductCount(Integer productId) {
        boolean productDisplayed = false;
        for (Product product : database.getProductList()) {
            if (productId == null || product.getId() == productId) {
                productDisplayed = true;
                System.out.println("Product ID: " + product.getId() + ", Count on Shelf: " + product.getProductShelf().getCurrent());
            }
        }

        if (!productDisplayed) {
            System.out.println("No products on shelf or ProductID not found");
        }


        boolean productsForMarkDownFound = false;
        if (!productsForMarkDownFound) {
                System.out.println("No products need to be marked down a week from now.");
            }
        }

    /**
     * Displays all products in the database.
     */
    public void displayAllProducts() {
        List<Product> products = database.getProductList();
        if (products.isEmpty()) {
            System.out.println("No products available.");
        } else {
            for (Product product : products) {
                showProduct(product);
            }
        }
    }

    /**
     * Displays the details of a specific product identified by its ID.
     * If the product is not found, a message is shown.
     *
     * @param productId The ID of the product to be displayed.
     */
    public void displayProduct(Integer productId) {
        Product product = findProductById(productId);
        if (product != null) {
            showProduct(product);
        } else {
            System.out.println("Product with ID " + productId + " not found.");
        }
    }

    /**
     * Finds and returns a product by its ID. If no product is found, returns null.
     *
     * @param productId The ID of the product to find.
     * @return The Product object with the given ID, or null if not found.
     */
    private Product findProductById(int productId) {
        for (Product product : database.getProductList()) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null; // If no product is found with the given ID
    }

    /**
     * Displays products that are scheduled for a markdown in the upcoming week.
     */

    public void displayProductsForMarkDown() {
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(7);
        boolean markdownScheduled = false;

        for (Product product : database.getProductList()) {
            if (product.getTimeDurationForMarkDown() != null &&
                    product.getTimeDurationForMarkDown().isAfter(today) &&
                    product.getTimeDurationForMarkDown().isBefore(nextWeek)) {
                markdownScheduled = true;
                System.out.println("Product ID: " + product.getId() + " (" + product.getProductName() + 
                                   ") is scheduled for markdown on " + formatDate(product.getTimeDurationForMarkDown()));
            }
        }

        if (!markdownScheduled) {
            System.out.println("No products are scheduled for markdown in the upcoming week.");
        }
    }

    /**
     * Displays the expiry dates for all products.
     */
    public void displayProductsExpiryDate() {
        for (Product product : database.getProductList()) {
            System.out.println("Product ID: " + product.getId() + " (" + product.getProductName() + 
                               ") expires on " + formatDate(product.getExpiryDate()));
        }
    }

    /**
     * Displays products that are currently in the markdown period.
     */
    public void displayProductsInMarkDown() {
        LocalDate today = LocalDate.now();
        boolean productInMarkdown = false;

        for (Product product : database.getProductList()) {
            if (product.getTimeDurationForMarkDown() != null &&
                    product.getTimeDurationForMarkDown().isEqual(today) ||
                    product.getTimeDurationForMarkDown().isBefore(today)) {
                productInMarkdown = true;
                System.out.println("Product ID: " + product.getId() + " (" + product.getProductName() + 
                                   ") is currently in the markdown period.");
            }
        }

        if (!productInMarkdown) {
            System.out.println("No products are currently in markdown.");
        }
    }

    /**
     * Displays products that have expired.
     */
    public void displayExpiredProducts() {
        LocalDate today = LocalDate.now();
        boolean expiredProductFound = false;

        for (Product product : database.getProductList()) {
            if (product.getExpiryDate().isBefore(today)) {
                expiredProductFound = true;
                System.out.println("Product ID: " + product.getId() + " (" + product.getProductName() + 
                                   ") has expired on " + formatDate(product.getExpiryDate()));
            }
        }

        if (!expiredProductFound) {
            System.out.println("No expired products.");
        }
    }
}