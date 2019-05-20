/*
 * Copyright (C) 2019 Houssem Ben Mabrouk
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package controller;

import dao.ClientDAO;
import dao.PersonnelDAO;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Produit;

/**
 * FXML Controller class
 *
 * @author Houssem Ben Mabrouk
 */
public class PointsVenteFXMLController implements Initializable {

    @FXML
    private TextField nomPtVenteTF;
    @FXML
    private ComboBox caissierCB;
    @FXML
    private ComboBox clientCB;
    @FXML
    private Label nbrProduitsLabel;
    @FXML
    private Label sousTotalLabel;
    @FXML
    private Label TVALabel;
    @FXML
    private Label totalLabel;
    @FXML
    private CheckBox anonymousClient;

    List<Produit> produitsAAchetes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        produitsAAchetes = new ArrayList<>();
        fillCaissierCB();
    }

    private void fillCaissierCB() {
        caissierCB.getItems().setAll(new PersonnelDAO().getAllCaissier());
    }

    private void fillClientCB() {
        clientCB.getItems().setAll(new ClientDAO().getAll());
    }

    @FXML
    private void anonymousAction(ActionEvent e) {
        if (anonymousClient.isSelected()) {
            clientCB.getItems().clear();
            clientCB.setDisable(true);
        } else {
            fillClientCB();
            clientCB.setDisable(false);
        }
    }

    @FXML
    private void gererProduits(ActionEvent e) {
        try {
            Stage stage = new Stage();
            URL x = PointsVenteFXMLController.class.getResource("/view/GererProduitsFXML.fxml");
            FXMLLoader loader = new FXMLLoader(x);
            loader.setController(new GererProduitsFXMLController(this));
            Parent pane = loader.load();
            stage.setScene(new Scene(pane));
            stage.setTitle("Ajouter/Supprimer produits a achetés");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) e.getSource()).getScene().getWindow());
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.exit(1);
        }
    }

    @FXML
    private void validerVente(ActionEvent e) {

    }

    @FXML
    private void resetVenteForm(ActionEvent e) {

    }

    void updateFields() {
        nbrProduitsLabel.setText(produitsAAchetes.size() + " produits");
        float somme = 0, tva = 0;
        for (Produit p : produitsAAchetes) {
            somme += p.getPrix();
            tva += p.getTva();
        }
        sousTotalLabel.setText("SOUS TOTAL: " + somme + " €");
        TVALabel.setText("TVA: " + tva + "%");
        totalLabel.setText("TOTAL: " + (somme + somme * tva / 100) + " €");
    }

}
