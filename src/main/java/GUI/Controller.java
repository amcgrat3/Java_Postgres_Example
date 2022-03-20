package GUI;

// Java Imports
import java.util.stream.Collectors;

// JavaFx Imports
import static javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

// Local Imports
import Database.PostgresqlDatabase;
import DataObjects.Grocery;

/**
 * Class for controlling the GUI
 */
public class Controller {
    // Data Members
    public static PostgresqlDatabase pgDB = new PostgresqlDatabase("store_database", "localhost", 5432, "postgres", "letmein");
    @FXML public AnchorPane groceryAnchor;
    @FXML public TableView<Grocery> cart = new TableView<>();
    @FXML public Button checkOut;
    public TableView<Grocery> groceryTableView = new TableView<>();

    // Public Methods
    /**
     * Initializes GUI functionality
     */
    public void init() {
        // Create table and columns
        groceryTableView.setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        TableColumn<Grocery,String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        TableColumn<Grocery,Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory("price"));
        TableColumn<Grocery,Integer> stockCol = new TableColumn<>("Stock");
        stockCol.setCellValueFactory(new PropertyValueFactory("stock"));

        // Add to GUI
        groceryTableView.getColumns().addAll(nameCol, priceCol, stockCol);
        groceryTableView.setItems(FXCollections.observableList(pgDB.getAllGroceries()));
        groceryAnchor.getChildren().add(groceryTableView);
        AnchorPane.setRightAnchor(groceryTableView, 0.0);
        AnchorPane.setLeftAnchor(groceryTableView, 0.0);
        AnchorPane.setTopAnchor(groceryTableView, 0.0);

        // Create and add button
        Button addToCart = new Button("Add To Cart");
        addToCart.setOnAction(add -> {
            Grocery selected = groceryTableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                Grocery grocery = new Grocery(selected.getId(), selected.getName(), selected.getPrice(), 1);
                if (!cart.getItems().stream().map(Grocery::getId).collect(Collectors.toList()).contains(grocery.getId())) {
                    cart.getItems().add(grocery);
                }
                else {
                    Grocery original = null;
                    for (Grocery cartItems : cart.getItems()) {
                        if (cartItems.getId() == grocery.getId()) {
                            original = cartItems;
                        }
                    }
                    assert original != null;
                    original.setStock(original.getStock() + 1);
                }
                cart.refresh();
            }
        });
        groceryAnchor.getChildren().add(addToCart);
        AnchorPane.setBottomAnchor(addToCart, 0.0);
    }
    /**
     * Checkout the items added to the cart
     */
    public void checkout() {
        for (Grocery grocery : cart.getItems()) {
            pgDB.checkout(grocery.getId(), grocery.getStock());
        }
        cart.getItems().clear();
        cart.refresh();
        groceryTableView.setItems(FXCollections.observableList(pgDB.getAllGroceries()));
        groceryTableView.refresh();
    }
}
