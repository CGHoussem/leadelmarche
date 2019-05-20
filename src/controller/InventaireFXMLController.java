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
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import model.Produit;
import model.TypeProduit;

/**
 * FXML Controller class
 *
 * @author Houssem Ben Mabrouk
 */
public class InventaireFXMLController implements Initializable {

    // Produits TextFields
    @FXML
    private TextField nomProduit;
    @FXML
    private TextField description;
    @FXML
    private ChoiceBox typeProduit;
    @FXML
    private TextField prix;
    @FXML
    private TextField pays;
    @FXML
    private TextField tva;
    @FXML
    private TextField qte;
    @FXML
    private TextField nomFournisseur;

    // Table Produits
    @FXML
    private TableView<Produit> tableProduits;
    @FXML
    private TableColumn<Produit, String> NomProduit;
    @FXML
    private TableColumn<Produit, String> DescriptionProduit;
    @FXML
    private TableColumn<Produit, String> TypeProduitTable;
    @FXML
    private TableColumn<Produit, Float> PrixProduit;
    @FXML
    private TableColumn<Produit, String> PaysProduit;
    @FXML
    private TableColumn<Produit, Integer> QteStockProduit;

    private FXMLDocumentController controller;

    public InventaireFXMLController(FXMLDocumentController controller) {
        this.controller = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeProductTableColumns();

        fillProductTable();
        fillProductChoiceBox();
    }

    private void initializeProductTableColumns() {
        NomProduit.setCellValueFactory(new PropertyValueFactory<Produit, String>("nom"));
        DescriptionProduit.setCellValueFactory(new PropertyValueFactory<Produit, String>("description"));
        TypeProduitTable.setCellValueFactory(new PropertyValueFactory<Produit, String>("type"));
        PrixProduit.setCellValueFactory(new PropertyValueFactory<Produit, Float>("prix"));
        PaysProduit.setCellValueFactory(new PropertyValueFactory<Produit, String>("pays"));
        QteStockProduit.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("qteStock"));
    }

    private void fillProductTable() {
        ObservableList<Produit> masterData = FXCollections.observableArrayList();
        masterData.setAll(new ProduitDAO().getAll());
        FilteredList<Produit> filteredData = new FilteredList<>(masterData, p -> true);

        controller.filterField.textProperty().addListener((obs, oldValue, newValue) -> {
            filteredData.setPredicate(produit -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (produit.getNom().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        tableProduits.setItems(filteredData);
    }

    private void fillProductChoiceBox() {
        List<String> list = new ArrayList<>();
        list.add("Aliment");
        list.add("Médecine");
        list.add("Appareil Électronique");
        list.add("Autre");
        ObservableList obslist = FXCollections.observableList(list);
        typeProduit.setItems(obslist);
    }

    @FXML
    private void ajouterProduit(ActionEvent event) {
        if (!nomProduit.getText().isEmpty()
                && !description.getText().isEmpty()
                && typeProduit.getSelectionModel().getSelectedIndex() != -1
                && !prix.getText().isEmpty()
                && !pays.getText().isEmpty()
                && !tva.getText().isEmpty()
                && !qte.getText().isEmpty()
                && !nomFournisseur.getText().isEmpty()) {
            try {
                float fprix = Float.parseFloat(prix.getText());
                float ftva = Float.parseFloat(tva.getText());
                int fqte = Integer.parseInt(qte.getText());
                TypeProduit type = TypeProduit.ALIMENT;
                switch (typeProduit.getSelectionModel().getSelectedIndex()) {
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
                        nomProduit.getText(),
                        description.getText(),
                        type,
                        fprix,
                        pays.getText(),
                        ftva,
                        fqte,
                        nomFournisseur.getText()
                );
                new ProduitDAO().add(p);

                resetForm(null);
                fillProductTable();
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "Vérifier le prix ou la quantité!", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vérifier vos données!", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void resetForm(ActionEvent event) {
        nomProduit.clear();
        description.clear();
        prix.clear();
        pays.clear();
        tva.clear();
        qte.clear();
        nomFournisseur.clear();
    }
}
