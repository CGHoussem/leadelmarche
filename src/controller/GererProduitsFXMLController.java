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
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Produit;

/**
 * FXML Controller class
 *
 * @author Houssem Ben Mabrouk
 */
public class GererProduitsFXMLController implements Initializable {

    @FXML
    private TextField codeABarreTF;
    @FXML
    private ComboBox produitsCB;
    @FXML
    private Label prixLabel;

    // Table Produits
    @FXML
    private TableView<Produit> tableProduits;
    @FXML
    private TableColumn<Produit, String> NomProduit;
    @FXML
    private TableColumn<Produit, Float> TVAProduit;
    @FXML
    private TableColumn<Produit, Float> PrixProduit;
    @FXML
    private TableColumn<Produit, Integer> QteStockProduit;
    @FXML
    private TableColumn Actions;

    private PointsVenteFXMLController ptsVenteController;

    public GererProduitsFXMLController(PointsVenteFXMLController ptsVenteController) {
        this.ptsVenteController = ptsVenteController;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeTable();
        refreshTableView();
        fillProduitsCB();
        prixLabel.setText("0 €");
    }

    private void initializeTable() {
        NomProduit.setCellValueFactory(new PropertyValueFactory<>("nom"));
        TVAProduit.setCellValueFactory(new PropertyValueFactory<>("tva"));
        PrixProduit.setCellValueFactory(new PropertyValueFactory<>("prix"));
        QteStockProduit.setCellValueFactory(new PropertyValueFactory<>("qteStock"));
        Actions.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        Callback<TableColumn<Produit, String>, TableCell<Produit, String>> cellFactory;
        cellFactory = (TableColumn<Produit, String> param) -> {
            final TableCell<Produit, String> cell = new TableCell<Produit, String>() {
                final Button deleteBtn = new Button("Enlever");

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        deleteBtn.setOnAction(event -> {
                            ptsVenteController.produitsAAchetes.remove(getIndex());
                            refreshTableView();
                        });
                        setGraphic(deleteBtn);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        Actions.setCellFactory(cellFactory);
    }

    private void refreshTableView() {
        tableProduits.getItems().clear();
        tableProduits.getItems().setAll(ptsVenteController.produitsAAchetes);
    }

    private void fillProduitsCB() {
        produitsCB.getItems().setAll(new ProduitDAO().getAll());
    }

    @FXML
    private void updatePrix(ActionEvent e) {
        Produit p = (Produit) (produitsCB.getSelectionModel().getSelectedItem());
        if (p != null) {
            prixLabel.setText(p.getPrix() + " €");
        }
    }

    @FXML
    private void ajouterProduit(ActionEvent e) {
        Produit p = (Produit) (produitsCB.getSelectionModel().getSelectedItem());
        if (p != null) {
            ptsVenteController.produitsAAchetes.add(p);
            prixLabel.setText(p.getPrix() + " €");
            refreshTableView();
        }
    }

    @FXML
    private void valider(ActionEvent e) {
        ptsVenteController.updateFields();

        Node node = (Node) e.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

}
