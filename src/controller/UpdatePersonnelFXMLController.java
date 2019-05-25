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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.Personnel;

/**
 * FXML Controller class
 *
 * @author Houssem Ben Mabrouk
 */
public class UpdatePersonnelFXMLController implements Initializable {

    @FXML
    private TextField numBadgeTF;
    @FXML
    private TextField nomTF;
    @FXML
    private TextField prenomTF;
    @FXML
    private TextField adrPersoTF;
    @FXML
    private TextField lieuTravailTF;
    @FXML
    private TextField posteTF;
    @FXML
    private ComboBox supCB;

    private Personnel perso;
    private PersonnelsFXMLController parent;

    public UpdatePersonnelFXMLController(PersonnelsFXMLController parent, Personnel perso) {
        this.parent = parent;
        this.perso = perso;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillTFs();
        fillCB();
    }

    private void fillTFs() {
        numBadgeTF.setText(String.valueOf(perso.getNumBadge()));
        nomTF.setText(perso.getNom());
        prenomTF.setText(perso.getPrenom());
        adrPersoTF.setText(perso.getAdressePerso());
        lieuTravailTF.setText(perso.getAdresseTravail());
        posteTF.setText(perso.getPoste());
    }

    private void fillCB() {
        supCB.getItems().setAll(new PersonnelDAO().getAll());
        if (perso.getSuperieur() != null) {
            supCB.getSelectionModel().select(perso.getSuperieur());
        }
    }

    @FXML
    private void resetForm(ActionEvent event) {
        fillTFs();
    }

    @FXML
    private void update(ActionEvent event) {
        if (!numBadgeTF.getText().isEmpty()
                && !nomTF.getText().isEmpty()
                && !prenomTF.getText().isEmpty()
                && !adrPersoTF.getText().isEmpty()
                && !lieuTravailTF.getText().isEmpty()
                && !posteTF.getText().isEmpty()) {
            int numBadge = Integer.parseInt(numBadgeTF.getText());
            Personnel sup = null;
            if (supCB.getSelectionModel().getSelectedIndex() != -1) {
                sup = (Personnel) supCB.getSelectionModel().getSelectedItem();
            }
            Personnel p = new Personnel(
                    nomTF.getText(),
                    prenomTF.getText(),
                    adrPersoTF.getText(),
                    lieuTravailTF.getText(),
                    posteTF.getText(),
                    sup,
                    numBadge,
                    perso.getMdp()
            );
            p.setId(perso.getId());
            new PersonnelDAO().update(p);

            parent.fillStaffTable();

            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
        } else {
            JOptionPane.showMessageDialog(null, "Vérifier vos données!", "Erreur", JOptionPane.WARNING_MESSAGE);
        }
    }
}
