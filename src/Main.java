
import dao.Connexion;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Houssem Ben Mabrouk
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/FXMLDocument.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        Connexion.closeConnection();
        super.stop();
    }

    
    
    public static void main(String[] args) {
        launch(args);
    }

}
