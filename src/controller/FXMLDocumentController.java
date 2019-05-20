package controller;

import dao.Connexion;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 *
 * @author Houssem Ben Mabrouk
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Pane mainContainer;
    @FXML
    TextField filterField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void afficherInventaire(ActionEvent event) {
        try {
            mainContainer.getChildren().clear();
            URL x = FXMLDocumentController.class.getResource("/view/InventaireFXML.fxml");
            FXMLLoader loader = new FXMLLoader(x);
            loader.setController(new InventaireFXMLController(this));
            Node pane = loader.load();
            mainContainer.getChildren().add(pane);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.exit(1);
        }
    }

    @FXML
    private void afficherPtsVente(ActionEvent event) {
        try {
            mainContainer.getChildren().clear();
            URL x = FXMLDocumentController.class.getResource("/view/PointsVenteFXML.fxml");
            FXMLLoader loader = new FXMLLoader(x);
            Node pane = loader.load();
            mainContainer.getChildren().add(pane);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.exit(1);
        }
    }

    @FXML
    private void afficherPersonnels(ActionEvent event) {
        try {
            mainContainer.getChildren().clear();
            URL x = FXMLDocumentController.class.getResource("/view/PersonnelsFXML.fxml");
            FXMLLoader loader = new FXMLLoader(x);
            loader.setController(new PersonnelsFXMLController(this));
            Node pane = loader.load();
            mainContainer.getChildren().add(pane);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.exit(1);
        }
    }

    @FXML
    private void afficherClients(ActionEvent event) {
        try {
            mainContainer.getChildren().clear();
            URL x = FXMLDocumentController.class.getResource("/view/ClientsFXML.fxml");
            FXMLLoader loader = new FXMLLoader(x);
            loader.setController(new ClientsFXMLController(this));
            Node pane = loader.load();
            mainContainer.getChildren().add(pane);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.exit(1);
        }
    }

    @FXML
    private void quitApplication(ActionEvent event) {
        Connexion.closeConnection();
        System.exit(0);
    }

}
