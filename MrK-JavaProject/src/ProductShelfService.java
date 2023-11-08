import java.math.BigDecimal;

/**
 * The ProductShelfService class provides services related to managing the product shelf,
 * such as calculating the quantity needed to refill a product on the shelf.
 */
class ProductShelfService {
    
    /**
     * Calculates the quantity to refill for a given product shelf.
     *
     * @param shelf The product shelf to calculate refill quantity for.
     * @return The quantity to refill as a BigDecimal.
     */
    public static BigDecimal quantityToRefill(ProductShelf shelf) {
        BigDecimal difference = shelf.getMax().subtract(shelf.getCurrent());
        return difference.max(BigDecimal.ZERO); // Ensure that we don't return a negative quantity.
    }
}

