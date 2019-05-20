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
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import model.Client;

/**
 * FXML Controller class
 *
 * @author Houssem Ben Mabrouk
 */
public class ClientsFXMLController implements Initializable {

    // Clients TextFields
    @FXML
    private TextField nomClientTF;
    @FXML
    private TextField prenomClientTF;
    @FXML
    private TextField numCarteFideliteTF;
    @FXML
    private TextField mailClientTF;
    @FXML
    private TextField codePostaleClientTF;
    // Table Clients
    @FXML
    private TableView<Client> tableClients;
    @FXML
    private TableColumn<Client, String> NomClientCol;
    @FXML
    private TableColumn<Client, String> PrenomClientCol;
    @FXML
    private TableColumn<Client, Integer> NumCarteClientCol;
    @FXML
    private TableColumn<Client, String> MailClientCol;
    @FXML
    private TableColumn<Client, Integer> CodePostaleClientCol;

    private FXMLDocumentController controller;

    public ClientsFXMLController(FXMLDocumentController controller) {
        this.controller = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeClientTableColumns();
        fillClientTable();
        generateNumCarteFidelite();
    }

    private void initializeClientTableColumns() {
        NomClientCol.setCellValueFactory(new PropertyValueFactory<Client, String>("nom"));
        PrenomClientCol.setCellValueFactory(new PropertyValueFactory<Client, String>("prenom"));
        NumCarteClientCol.setCellValueFactory(new PropertyValueFactory<Client, Integer>("numCarteFidelite"));
        MailClientCol.setCellValueFactory(new PropertyValueFactory<Client, String>("mail"));
        CodePostaleClientCol.setCellValueFactory(new PropertyValueFactory<Client, Integer>("codePostal"));
    }

    private void fillClientTable() {
        ObservableList<Client> masterData = FXCollections.observableArrayList();
        masterData.setAll(new ClientDAO().getAll());
        FilteredList<Client> filteredData = new FilteredList<>(masterData, c -> true);

        controller.filterField.textProperty().addListener((obs, oldValue, newValue) -> {
            filteredData.setPredicate(client -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (client.getNom().toLowerCase().contains(lowerCaseFilter)
                        || client.getPrenom().toLowerCase().contains(lowerCaseFilter)
                        || String.valueOf(client.getNumCarteFidelite()).contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        tableClients.setItems(filteredData);
    }

    @FXML
    private void ajouterClient(ActionEvent event) {
        if (!nomClientTF.getText().isEmpty()
                && !prenomClientTF.getText().isEmpty()
                && !numCarteFideliteTF.getText().isEmpty()
                && !mailClientTF.getText().isEmpty()
                && !codePostaleClientTF.getText().isEmpty()) {
            int numCarteFidelite = Integer.parseInt(numCarteFideliteTF.getText());
            int codePostal = Integer.parseInt(codePostaleClientTF.getText());

            Client c = new Client(
                    nomClientTF.getText(),
                    prenomClientTF.getText(),
                    numCarteFidelite,
                    mailClientTF.getText(),
                    codePostal
            );
            new ClientDAO().add(c);

            resetClientForm(null);
            fillClientTable();
        } else {
            JOptionPane.showMessageDialog(null, "Vérifier vos données!", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generateNumCarteFidelite() {
        int num = new Random().nextInt() & Integer.MAX_VALUE;
        numCarteFideliteTF.setText(String.valueOf(num));
    }

    @FXML
    private void resetClientForm(ActionEvent event) {
        nomClientTF.clear();
        prenomClientTF.clear();
        generateNumCarteFidelite();
        mailClientTF.clear();
        codePostaleClientTF.clear();
    }

}
