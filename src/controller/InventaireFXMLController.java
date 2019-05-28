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

import Utility.ResourceLoader;
import dao.ProduitDAO;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import model.Personnel;
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
    private TableColumn<Produit, String> PrixProduit;
    @FXML
    private TableColumn<Produit, Float> TvaProduit;
    @FXML
    private TableColumn<Produit, String> PaysProduit;
    @FXML
    private TableColumn<Produit, Integer> QteStockProduit;
    @FXML
    private TableColumn ActionCol;

    private FXMLDocumentController parent;
    private InventaireFXMLController controller;

    public InventaireFXMLController(FXMLDocumentController parent) {
        controller = this;
        this.parent = parent;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeProductTableColumns();

        fillProductTable();
        fillProductChoiceBox();
    }

    private void initializeProductTableColumns() {
        NomProduit.setCellValueFactory(new PropertyValueFactory<>("nom"));
        DescriptionProduit.setCellValueFactory(new PropertyValueFactory<>("description"));
        TypeProduitTable.setCellValueFactory(new PropertyValueFactory<>("type"));
        PrixProduit.setCellValueFactory(new PropertyValueFactory<>("stringPrixHorsTax"));
        TvaProduit.setCellValueFactory(new PropertyValueFactory<>("tva"));
        PaysProduit.setCellValueFactory(new PropertyValueFactory<>("pays"));
        QteStockProduit.setCellValueFactory(new PropertyValueFactory<>("qteStock"));

        Callback<TableColumn<Personnel, String>, TableCell<Personnel, String>> cellFactory;
        cellFactory = (TableColumn<Personnel, String> param) -> {
            final TableCell<Personnel, String> cell = new TableCell<Personnel, String>() {
                final Button updateBtn = new Button("", new ImageView(ResourceLoader.getImage("edit.png")));
                final Button deleteBtn = new Button("", new ImageView(ResourceLoader.getImage("delete.png")));

                HBox hbox = new HBox(updateBtn, deleteBtn);

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        updateBtn.setOnAction(event -> {
                            try {
                                Produit produit = tableProduits.getItems().get(getIndex());
                                Stage stage = new Stage();
                                URL x = getClass().getResource("/view/UpdateProduitFXML.fxml");
                                FXMLLoader loader = new FXMLLoader(x);
                                loader.setController(new UpdateProduitFXMLController(controller, produit));
                                Parent pane = loader.load();
                                stage.setScene(new Scene(pane));
                                stage.setTitle("Mis à jour d'un produit");
                                stage.initModality(Modality.WINDOW_MODAL);
                                stage.initOwner(((Node) event.getSource()).getScene().getWindow());
                                stage.show();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                                System.exit(1);
                            }
                        });
                        deleteBtn.setOnAction(event -> {
                            Produit produit = tableProduits.getItems().get(getIndex());
                            new ProduitDAO().delete(produit);
                            fillProductTable();
                        });
                        setGraphic(hbox);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        ActionCol.setCellFactory(cellFactory);
    }

    public void fillProductTable() {
        ObservableList<Produit> masterData = FXCollections.observableArrayList();
        masterData.setAll(new ProduitDAO().getAll());
        FilteredList<Produit> filteredData = new FilteredList<>(masterData, p -> true);

        parent.filterField.textProperty().addListener((obs, oldValue, newValue) -> {
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
                
                JOptionPane.showMessageDialog(null, "Le produit a été ajouter avec succée", "Information", JOptionPane.INFORMATION_MESSAGE);
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
