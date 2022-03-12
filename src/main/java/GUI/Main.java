package GUI;

// Third Party Imports
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class to run the program
 */
public class Main extends Application {

    // Data Members
    public static Controller controller;

    // Public Methods
    /**
     * Starts the JavaFX GUI
     * @param primaryStage  The stage to display the GUI
     * @throws Exception caused by file load errors
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/primaryFrame.fxml"));
        Parent root = fxmlLoader.load();
        controller = fxmlLoader.getController();
        controller.init();
        primaryStage.setTitle("Welcome To The Store!");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }
    /**
     * The main function
     * @param args  Command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
