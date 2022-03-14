package DataObjects;

/**
 * Class to hold grocery data
 */
public class Grocery {

    // Data Members
    int id;
    String name;
    double price;
    int stock;

    // Constructors
    /**
     * Parameterized Constructor
     * @param id        The primary key of the grocery
     * @param name      The name of the grocery
     * @param price     The price of the grocery
     * @param stock     The number of this item is stock
     */
    public Grocery(int id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
