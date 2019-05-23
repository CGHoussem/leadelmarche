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

import dao.VenteDAO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Produit;
import model.Vente;

/**
 * FXML Controller class
 *
 * @author Houssem Ben Mabrouk
 */
public class VenteProduitsFXMLController implements Initializable {

    // Table Produits
    @FXML
    private TableView<Produit> tableProduits;
    @FXML
    private TableColumn<Produit, String> NomTC;
    @FXML
    private TableColumn<Produit, String> DescriptionTC;
    @FXML
    private TableColumn<Produit, Float> PrixUnitaireTC;
    @FXML
    private TableColumn<Produit, Float> PrixVenteTC;

    private Vente vente;

    public VenteProduitsFXMLController(Vente vente) {
        this.vente = vente;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeTable();
        fillTable();
    }

    private void initializeTable() {
        NomTC.setCellValueFactory(new PropertyValueFactory<>("nom"));
        DescriptionTC.setCellValueFactory(new PropertyValueFactory<>("tva"));
        PrixUnitaireTC.setCellValueFactory(new PropertyValueFactory<>("prixHorsTax"));
        PrixVenteTC.setCellValueFactory(new PropertyValueFactory<>("prix"));
    }

    private void fillTable() {
        tableProduits.getItems().setAll(new VenteDAO().get(vente.getId()).getProduitsVendus());
    }

    @FXML
    private void fermer(ActionEvent e) {
        Node node = (Node) e.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

}
