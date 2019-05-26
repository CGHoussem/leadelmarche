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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.Client;

/**
 * FXML Controller class
 *
 * @author Houssem Ben Mabrouk
 */
public class UpdateClientFXMLController implements Initializable {

    @FXML
    private TextField nomTF;
    @FXML
    private TextField prenomTF;
    @FXML
    private TextField numCarteFideliteTF;
    @FXML
    private TextField mailTF;
    @FXML
    private TextField codePostalTF;

    private Client client;
    private ClientsFXMLController parent;

    public UpdateClientFXMLController(ClientsFXMLController parent, Client client) {
        this.parent = parent;
        this.client = client;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillTFs();
    }

    private void fillTFs() {
        nomTF.setText(client.getNom());
        prenomTF.setText(client.getPrenom());
        numCarteFideliteTF.setText(String.valueOf(client.getNumCarteFidelite()));
        mailTF.setText(client.getMail());
        codePostalTF.setText(String.valueOf(client.getCodePostal()));
    }

    @FXML
    private void resetForm(ActionEvent event) {
        fillTFs();
    }

    @FXML
    private void update(ActionEvent event) {
        if (!nomTF.getText().isEmpty()
                && !prenomTF.getText().isEmpty()
                && !numCarteFideliteTF.getText().isEmpty()
                && !mailTF.getText().isEmpty()
                && !codePostalTF.getText().isEmpty()) {

            int numCarteFid = Integer.parseInt(numCarteFideliteTF.getText());
            int codePostal = Integer.parseInt(codePostalTF.getText());
            Client c = new Client(
                    client.getId(),
                    nomTF.getText(),
                    prenomTF.getText(),
                    numCarteFid,
                    mailTF.getText(),
                    codePostal
            );
            new ClientDAO().update(c);

            parent.fillClientTable();

            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
        } else {
            JOptionPane.showMessageDialog(null, "Vérifier vos données!", "Erreur", JOptionPane.WARNING_MESSAGE);
        }
    }
}
