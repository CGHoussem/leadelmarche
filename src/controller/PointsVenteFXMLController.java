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
import dao.VenteDAO;
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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import model.Client;
import model.Personnel;
import model.Produit;
import model.Vente;

/**
 * FXML Controller class
 *
 * @author Houssem Ben Mabrouk
 */
public class PointsVenteFXMLController implements Initializable {

    // Table Ventes
    @FXML
    private TableView<Vente> tableVentes;
    @FXML
    private TableColumn<Vente, String> nomPTVenteTC;
    @FXML
    private TableColumn<Vente, Personnel> caissierTC;
    @FXML
    private TableColumn<Vente, Client> clientTC;
    @FXML
    private TableColumn<Vente, String> totalTC;
    @FXML
    private TableColumn actionTC;

    @FXML
    private TextField nomPtVenteTF;
    @FXML
    private TextField caissierTF;
    @FXML
    private ComboBox clientCB;
    @FXML
    private Label nbrProduitsLabel;
    @FXML
    private Label sousTotalLabel;
    @FXML
    private Label totalLabel;
    @FXML
    private CheckBox anonymousClient;

    private float sousTotal;
    private float total;

    FXMLDocumentController controller;
    List<Produit> produitsAAchetes;

    public PointsVenteFXMLController(FXMLDocumentController controller) {
        this.controller = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeTT();
        fillTable();
        produitsAAchetes = new ArrayList<>();
        caissierTF.setText(controller.caissier.toString());
        nbrProduitsLabel.setText("0 produits");
        sousTotalLabel.setText("SOUS TOTAL: 0 €");
        totalLabel.setText("TOTAL: 0 €");
    }

    private void initializeTT() {
        nomPTVenteTC.setCellValueFactory(new PropertyValueFactory<>("nom"));
        caissierTC.setCellValueFactory(new PropertyValueFactory<>("caissier"));
        clientTC.setCellValueFactory(new PropertyValueFactory<>("client"));
        totalTC.setCellValueFactory(new PropertyValueFactory<>("stringTotal"));
        actionTC.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        Callback<TableColumn<Vente, String>, TableCell<Vente, String>> cellFactory;
        cellFactory = (TableColumn<Vente, String> param) -> {
            final TableCell<Vente, String> cell = new TableCell<Vente, String>() {
                final Button afficherProduitsBtn = new Button("Liste des produits");

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        afficherProduitsBtn.setOnAction(event -> {
                            try {
                                Vente vente = tableVentes.getItems().get(getIndex());
                                Stage stage = new Stage();
                                URL x = PointsVenteFXMLController.class.getResource("/view/VenteProduitsFXML.fxml");
                                FXMLLoader loader = new FXMLLoader(x);
                                loader.setController(new VenteProduitsFXMLController(vente));
                                Parent pane = loader.load();
                                stage.setScene(new Scene(pane));
                                stage.setTitle("Liste des produits achetés");
                                stage.initModality(Modality.WINDOW_MODAL);
                                stage.initOwner(((Node) event.getSource()).getScene().getWindow());
                                stage.show();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                                System.out.println(ex.getMessage());
                                System.exit(1);
                            }
                        });
                        setGraphic(afficherProduitsBtn);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        actionTC.setCellFactory(cellFactory);
    }

    private void fillTable() {
        tableVentes.getItems().setAll(new VenteDAO().getAll());
    }

    private void fillClientCB() {
        clientCB.getItems().setAll(new ClientDAO().getAll());
    }

    void updateFields() {
        nbrProduitsLabel.setText(produitsAAchetes.size() + " produits");
        sousTotal = 0;
        total = 0;

        for (Produit p : produitsAAchetes) {
            sousTotal += p.getPrixHorsTax();
            total += p.getPrix();
        }

        sousTotalLabel.setText("SOUS TOTAL: " + sousTotal + " €");
        totalLabel.setText("TOTAL: " + total + " €");
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
        if (produitsAAchetes.size() <= 0) {
            JOptionPane.showMessageDialog(null, "Ajouter les produits a achetés", "Erreur", JOptionPane.WARNING_MESSAGE);
        } else if (!nomPtVenteTF.getText().isEmpty()
                && (clientCB.getSelectionModel().getSelectedIndex() != -1 || anonymousClient.isSelected())) {
            Client client = (Client) clientCB.getSelectionModel().getSelectedItem();
            Vente v = new Vente(nomPtVenteTF.getText(), client, controller.caissier, produitsAAchetes, sousTotal, total);

            new VenteDAO().add(v);

            JOptionPane.showMessageDialog(null, "La vente a été ajouter avec succée", "Information", JOptionPane.INFORMATION_MESSAGE);
            resetVenteForm(null);
        } else {
            JOptionPane.showMessageDialog(null, "Vérifier vos données!", "Erreur", JOptionPane.WARNING_MESSAGE);
        }
    }

    @FXML
    private void resetVenteForm(ActionEvent e) {
        produitsAAchetes.clear();
        updateFields();
    }
}
