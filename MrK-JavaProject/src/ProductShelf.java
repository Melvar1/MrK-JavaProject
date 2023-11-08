import java.math.BigDecimal;

/**
 * This class represents a shelf where a product is stored in a market or store.
 * It holds information about the minimum and maximum quantity thresholds,
 * as well as the current quantity of the product on the shelf.
 */
class ProductShelf {
    // Minimum threshold for product quantity on the shelf.
    private final BigDecimal min;
    // Maximum threshold for product quantity on the shelf.
    private final BigDecimal max;
    // Current quantity of the product on the shelf.
    private final BigDecimal current;

    /**
     * Constructs a ProductShelf with specified minimum and maximum thresholds.
     * The current quantity is set to a default value.
     *
     * @param min The minimum quantity threshold for the product on this shelf.
     * @param max The maximum quantity threshold for the product on this shelf.
     */
    public ProductShelf(BigDecimal min, BigDecimal max) {
        this.min = min;
        this.max = max;
        // The default current quantity is set to 5. This can be adjusted as needed.
        this.current = new BigDecimal(5);
    }

    /**
     * Retrieves the maximum quantity threshold of the product on the shelf.
     *
     * @return The maximum threshold as a BigDecimal.
     */
    public BigDecimal getMax() {
        return this.max;
    }

    /**
     * Retrieves the current quantity of the product on the shelf.
     *
     * @return The current quantity as a BigDecimal.
     */
    public BigDecimal getCurrent() {
        return this.current;
    }

    /**
     * Retrieves the minimum quantity threshold of the product on the shelf.
     *
     * @return The minimum threshold as a BigDecimal.
     */
    public BigDecimal getMin() {
        return min;
    }
}
