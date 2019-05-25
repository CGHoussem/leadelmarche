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

import dao.ProduitDAO;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.Produit;
import model.TypeProduit;

/**
 * FXML Controller class
 *
 * @author Houssem Ben Mabrouk
 */
public class UpdateProduitFXMLController implements Initializable {

    @FXML
    private TextField nomTF;
    @FXML
    private TextField prixTF;
    @FXML
    private TextField tvaTF;
    @FXML
    private TextField qteStockTF;
    @FXML
    private TextField fournisseurTF;
    @FXML
    private TextField paysTF;
    @FXML
    private TextArea descriptionTA;
    @FXML
    private ComboBox typeCB;

    private Produit produit;
    private InventaireFXMLController parent;

    public UpdateProduitFXMLController(InventaireFXMLController parent, Produit produit) {
        this.parent = parent;
        this.produit = produit;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillTFs();
        fillCB();
    }

    private void fillTFs() {
        nomTF.setText(String.valueOf(produit.getNom()));
        prixTF.setText(String.valueOf(produit.getPrixHorsTax()));
        tvaTF.setText(String.valueOf(produit.getTva()));
        qteStockTF.setText(String.valueOf(produit.getQteStock()));
        fournisseurTF.setText(produit.getNomFournisseur());
        descriptionTA.setText(produit.getDescription());
        paysTF.setText(produit.getPays());
    }

    private void fillCB() {
        List<String> list = new ArrayList<>();
        list.add("Aliment");
        list.add("Médecine");
        list.add("Appareil Électronique");
        list.add("Autre");
        ObservableList obslist = FXCollections.observableList(list);
        typeCB.setItems(obslist);

        switch (produit.getType()) {
            case ALIMENT:
                typeCB.getSelectionModel().select("Aliment");
                break;
            case MEDECINE:
                typeCB.getSelectionModel().select("Médecine");
                break;
            case APPAREIL_ELECTRONIQUE:
                typeCB.getSelectionModel().select("Appareil Électronique");
                break;
            case AUTRE:
                typeCB.getSelectionModel().select("Autre");
                break;
        }
    }

    @FXML
    private void resetForm(ActionEvent event) {
        fillTFs();
    }

    @FXML
    private void update(ActionEvent event) {
        if (!nomTF.getText().isEmpty()
                && !prixTF.getText().isEmpty()
                && !tvaTF.getText().isEmpty()
                && !qteStockTF.getText().isEmpty()
                && !fournisseurTF.getText().isEmpty()
                && !descriptionTA.getText().isEmpty()
                && !paysTF.getText().isEmpty()
                && typeCB.getSelectionModel().getSelectedIndex() != -1) {

            float fprix = Float.parseFloat(prixTF.getText());
            float ftva = Float.parseFloat(tvaTF.getText());
            int fqte = Integer.parseInt(qteStockTF.getText());
            TypeProduit type = TypeProduit.ALIMENT;
            switch (typeCB.getSelectionModel().getSelectedIndex()) {
                case 1:
                    type = TypeProduit.MEDECINE;
                    break;
                case 2:
                    type = TypeProduit.APPAREIL_ELECTRONIQUE;
                    break;
                case 3:
                    type = TypeProduit.AUTRE;
                    break;
            }
            Produit p = new Produit(
                    produit.getId(),
                    nomTF.getText(),
                    descriptionTA.getText(),
                    type,
                    fprix,
                    paysTF.getText(),
                    ftva,
                    fqte,
                    fournisseurTF.getText()
            );
            new ProduitDAO().update(p);

            parent.fillProductTable();

            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
        } else {
            JOptionPane.showMessageDialog(null, "Vérifier vos données!", "Erreur", JOptionPane.WARNING_MESSAGE);
        }
    }
}
