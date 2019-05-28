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
import dao.ClientDAO;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
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
import model.Client;
import model.Personnel;

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
    @FXML
    private TableColumn ActionsCol;

    private FXMLDocumentController parent;
    private final ClientsFXMLController controller;

    public ClientsFXMLController(FXMLDocumentController parent) {
        this.parent = parent;
        controller = this;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeClientTableColumns();
        fillClientTable();
        generateNumCarteFidelite();
    }

    private void initializeClientTableColumns() {
        NomClientCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        PrenomClientCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        NumCarteClientCol.setCellValueFactory(new PropertyValueFactory<>("numCarteFidelite"));
        MailClientCol.setCellValueFactory(new PropertyValueFactory<>("mail"));
        CodePostaleClientCol.setCellValueFactory(new PropertyValueFactory<>("codePostal"));

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
                                Client client = tableClients.getItems().get(getIndex());
                                Stage stage = new Stage();
                                URL x = getClass().getResource("/view/UpdateClientFXML.fxml");
                                FXMLLoader loader = new FXMLLoader(x);
                                loader.setController(new UpdateClientFXMLController(controller, client));
                                Parent pane = loader.load();
                                stage.setScene(new Scene(pane));
                                stage.setTitle("Mis à jour d'un client");
                                stage.initModality(Modality.WINDOW_MODAL);
                                stage.initOwner(((Node) event.getSource()).getScene().getWindow());
                                stage.show();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                                System.exit(1);
                            }
                        });
                        deleteBtn.setOnAction(event -> {
                            Client client = tableClients.getItems().get(getIndex());
                            new ClientDAO().delete(client);
                            fillClientTable();
                        });
                        setGraphic(hbox);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        ActionsCol.setCellFactory(cellFactory);
    }

    private String simplifyName(String name) {
        String temp = name.toUpperCase().replaceAll("[ÉÈËÊ]", "E");
        return temp.replaceAll("[ÀÄÂ]", "A");
    }
    
    public void fillClientTable() {
        ObservableList<Client> masterData = FXCollections.observableArrayList();
        masterData.setAll(new ClientDAO().getAll());
        FilteredList<Client> filteredData = new FilteredList<>(masterData, c -> true);

        parent.filterField.textProperty().addListener((obs, oldValue, newValue) -> {
            filteredData.setPredicate(client -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = simplifyName(newValue).toLowerCase();
                if (simplifyName(client.getNom()).toLowerCase().contains(lowerCaseFilter)
                        || simplifyName(client.getPrenom()).toLowerCase().contains(lowerCaseFilter)
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
                && mailClientTF.getText().contains("@")
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
