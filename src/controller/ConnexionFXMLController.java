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

import Utility.MD5;
import dao.PersonnelDAO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import model.Personnel;

/**
 * FXML Controller class
 *
 * @author Houssem Ben Mabrouk
 */
public class ConnexionFXMLController implements Initializable {

    @FXML
    private TextField idTF;
    @FXML
    private TextField mdpTF;

    private FXMLDocumentController controller;

    public ConnexionFXMLController(FXMLDocumentController controller) {
        this.controller = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void connect(ActionEvent event) {
        if (idTF.getText().isEmpty()
                || mdpTF.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vérifier vos données", "Erreur d'identifaction", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                int numBadge = Integer.valueOf(idTF.getText());
                String mdp = mdpTF.getText();
                Personnel p = new PersonnelDAO().getByNumBadge(numBadge);
                if (p != null
                        && p.getPoste().toLowerCase().equals("caissier")
                        && MD5.getMd5(mdp).equals(p.getMdp())) {
                    controller.isConnected = true;
                    controller.caissier = p;
                    controller.filterField.setVisible(true);
                    controller.afficherInventaire(null);
                } else {
                    JOptionPane.showMessageDialog(null, "Mot de passe incorrecte", "Erreur d'identification", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "Vérifier vos données", "Erreur d'identification", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
