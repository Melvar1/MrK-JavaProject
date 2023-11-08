import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents a product with a unique ID, name, shelf details, expiry date, and markdown period.
 */
class Product {
    // Unique identifier for the product
    private final int id;
    // Name of the product
    private final String productName;
    // The shelf where the product is stored, including price and quantity details
    private final ProductShelf productShelf;
    // The date when the product is set to expire
    private final LocalDate expiryDate;
    // The date indicating when the markdown period starts for the product
    private final LocalDate timeDurationForMarkDown;

    /**
     * Constructs a Product with the specified ID, name, expiry date, and markdown date.
     *
     * @param productId   The unique identifier for the product.
     * @param productName The name of the product.
     * @param expiryDate  The date when the product will expire.
     * @param markDownDate The date indicating when the markdown period starts.
     */
    public Product(int productId, String productName, LocalDate expiryDate, LocalDate markDownDate) {
        this.id = productId;
        this.productName = productName;
        this.expiryDate = expiryDate;
        this.timeDurationForMarkDown = markDownDate;
        // Initialize the product shelf with some default values for price and quantity.
        this.productShelf = new ProductShelf(new BigDecimal(5), new BigDecimal(5));
    }

    /**
     * Gets the product ID.
     *
     * @return The unique identifier for the product.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Gets the product name.
     *
     * @return The name of the product.
     */
    public String getProductName() {
        return this.productName;
    }

    /**
     * Gets the shelf details of the product.
     *
     * @return The ProductShelf object containing shelf details of the product.
     */
    public ProductShelf getProductShelf() {
        return this.productShelf;
    }

    /**
     * Gets the expiry date of the product.
     *
     * @return The expiry date of the product.
     */
    public LocalDate getExpiryDate() {
        return this.expiryDate;
    }

    /**
     * Gets the markdown start date of the product.
     *
     * @return The start date of the markdown period for the product.
     */

    public LocalDate getTimeDurationForMarkDown() {
        return this.timeDurationForMarkDown;
    }

}

  