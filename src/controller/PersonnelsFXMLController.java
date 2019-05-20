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

import dao.PersonnelDAO;
import java.net.URL;
import java.util.Random;
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
import model.Personnel;

/**
 * FXML Controller class
 *
 * @author Houssem Ben Mabrouk
 */
public class PersonnelsFXMLController implements Initializable {

    // Personnels TextFields
    @FXML
    private TextField numBadgeTF;
    @FXML
    private TextField nomTF;
    @FXML
    private TextField prenomTF;
    @FXML
    private TextField adrPersoTF;
    @FXML
    private TextField adrTravailTF;
    @FXML
    private TextField posteTF;
    @FXML
    private ChoiceBox superieurCB;
    // Table Personnels
    @FXML
    private TableView<Personnel> tablePersonnels;
    @FXML
    private TableColumn<Personnel, Integer> NumBadgeCol;
    @FXML
    private TableColumn<Personnel, String> NomPersoCol;
    @FXML
    private TableColumn<Personnel, String> PrenomPersoCol;
    @FXML
    private TableColumn<Personnel, String> AdrPersoCol;
    @FXML
    private TableColumn<Personnel, String> AdrTravailCol;
    @FXML
    private TableColumn<Personnel, String> PostePersoCol;
    @FXML
    private TableColumn<Personnel, Personnel> SupPersoCol;

    private FXMLDocumentController controller;

    public PersonnelsFXMLController(FXMLDocumentController controller) {
        this.controller = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillStaffChoiceBox();
        initializeStaffTableColumns();
        fillStaffTable();
    }

    private void fillStaffChoiceBox() {
        ObservableList obslist = FXCollections.observableList(new PersonnelDAO().getAll());
        superieurCB.setItems(obslist);
    }

    private void initializeStaffTableColumns() {
        NumBadgeCol.setCellValueFactory(new PropertyValueFactory<Personnel, Integer>("numBadge"));
        NomPersoCol.setCellValueFactory(new PropertyValueFactory<Personnel, String>("nom"));
        PrenomPersoCol.setCellValueFactory(new PropertyValueFactory<Personnel, String>("prenom"));
        AdrPersoCol.setCellValueFactory(new PropertyValueFactory<Personnel, String>("adressePerso"));
        AdrTravailCol.setCellValueFactory(new PropertyValueFactory<Personnel, String>("adresseTravail"));
        PostePersoCol.setCellValueFactory(new PropertyValueFactory<Personnel, String>("poste"));
        SupPersoCol.setCellValueFactory(new PropertyValueFactory<Personnel, Personnel>("superieur"));
    }

    private void fillStaffTable() {
        ObservableList<Personnel> masterData = FXCollections.observableArrayList();
        masterData.setAll(new PersonnelDAO().getAll());
        FilteredList<Personnel> filteredData = new FilteredList<>(masterData, p -> true);

        controller.filterField.textProperty().addListener((obs, oldValue, newValue) -> {
            filteredData.setPredicate(personnel -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (personnel.getNom().toLowerCase().contains(lowerCaseFilter)
                        || personnel.getPrenom().toLowerCase().contains(lowerCaseFilter)
                        || String.valueOf(personnel.getNumBadge()).contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        tablePersonnels.setItems(filteredData);
    }
    
    @FXML
    private void ajouterPersonnel(ActionEvent event) {
        if (!numBadgeTF.getText().isEmpty()
                && !nomTF.getText().isEmpty()
                && !prenomTF.getText().isEmpty()
                && !adrPersoTF.getText().isEmpty()
                && !adrTravailTF.getText().isEmpty()
                && !posteTF.getText().isEmpty()) {
            int numBadge = Integer.parseInt(numBadgeTF.getText());
            Personnel sup = null;
            if (superieurCB.getSelectionModel().getSelectedIndex() != -1) {
                sup = (Personnel) superieurCB.getSelectionModel().getSelectedItem();
            }
            Personnel p = new Personnel(
                    nomTF.getText(),
                    prenomTF.getText(),
                    adrPersoTF.getText(),
                    adrTravailTF.getText(),
                    posteTF.getText(),
                    sup,
                    numBadge
            );
            new PersonnelDAO().add(p);

            resetPersoForm(null);
            fillStaffChoiceBox();
            fillStaffTable();
        } else {
            JOptionPane.showMessageDialog(null, "Vérifier vos données!", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @FXML
    private void generateNumBadge(ActionEvent event) {
        Random r = new Random();
        numBadgeTF.setText(String.valueOf(r.nextInt() & Integer.MAX_VALUE));
    }
    
    @FXML
    private void resetPersoForm(ActionEvent event) {
        numBadgeTF.clear();
        nomTF.clear();
        prenomTF.clear();
        adrPersoTF.clear();
        adrTravailTF.clear();
        posteTF.clear();
    }

}
