import java.util.ArrayList;
import java.util.List;

/**
 * The Database class represents a simple in-memory database to store products.
 * It encapsulates a list of Product objects and provides access to this list.
 */
class Database {
    // List to hold Product objects. It's initialized to an empty ArrayList.
    private final List<Product> productList = new ArrayList<>();

    /**
     * Constructor for the Database.
     * This constructor currently does not perform any specific operation as the productList is
     * initialized at the point of declaration. However, the constructor is provided to allow
     * for future extensions, such as connecting to an actual database if needed.
     */
    public Database() {
        // The constructor could be expanded in the future to include setup operations for the database.

    }

    /**
     * Retrieves the list of products.
     *
     * @return A List containing the Product objects stored in the database.
     *         This method returns a direct reference to the productList, which
     *         means that callers can modify the list. In a real-world scenario, it
     *         might be safer to return a read-only view or a deep copy of the list
     *         to prevent external modifications.
     */
    public List<Product> getProductList() {
        return this.productList;
    }
}

