package controller;

import dao.ClientDAO;
import dao.Connexion;
import dao.PersonnelDAO;
import dao.ProduitDAO;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javax.swing.JOptionPane;
import model.Client;
import model.Personnel;
import model.Produit;
import model.TypeProduit;

/**
 *
 * @author Houssem Ben Mabrouk
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TabPane inventaire;
    @FXML
    private TabPane gPersonnels;
    @FXML
    private TabPane gClients;
    @FXML
    private TextField filterField;
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
    private TableColumn<Produit, Float> PrixProduit;
    @FXML
    private TableColumn<Produit, String> PaysProduit;
    @FXML
    private TableColumn<Produit, Integer> QteStockProduit;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillProductChoiceBox();
        fillStaffChoiceBox();
        initializeProductTableColumns();
        initializeStaffTableColumns();
        initializeClientTableColumns();

        fillProductTable();
        fillStaffTable();
        fillClientTable();

        inventaire.setVisible(true);
        gPersonnels.setVisible(false);
        gClients.setVisible(false);
    }

    private void initializeProductTableColumns() {
        NomProduit.setCellValueFactory(new PropertyValueFactory<Produit, String>("nom"));
        DescriptionProduit.setCellValueFactory(new PropertyValueFactory<Produit, String>("description"));
        TypeProduitTable.setCellValueFactory(new PropertyValueFactory<Produit, String>("type"));
        PrixProduit.setCellValueFactory(new PropertyValueFactory<Produit, Float>("prix"));
        PaysProduit.setCellValueFactory(new PropertyValueFactory<Produit, String>("pays"));
        QteStockProduit.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("qteStock"));
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

    private void initializeClientTableColumns() {
        NomClientCol.setCellValueFactory(new PropertyValueFactory<Client, String>("nom"));
        PrenomClientCol.setCellValueFactory(new PropertyValueFactory<Client, String>("prenom"));
        NumCarteClientCol.setCellValueFactory(new PropertyValueFactory<Client, Integer>("numCarteFidelite"));
        MailClientCol.setCellValueFactory(new PropertyValueFactory<Client, String>("mail"));
        CodePostaleClientCol.setCellValueFactory(new PropertyValueFactory<Client, Integer>("codePostal"));
    }

    private void fillStaffTable() {
        ObservableList<Personnel> masterData = FXCollections.observableArrayList();
        masterData.setAll(new PersonnelDAO().getAll());
        FilteredList<Personnel> filteredData = new FilteredList<>(masterData, p -> true);

        filterField.textProperty().addListener((obs, oldValue, newValue) -> {
            filteredData.setPredicate(personnel -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (personnel.getNom().toLowerCase().contains(lowerCaseFilter) || 
                        personnel.getPrenom().toLowerCase().contains(lowerCaseFilter) ||
                        String.valueOf(personnel.getNumBadge()).contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        tablePersonnels.setItems(filteredData);
    }

    private void fillProductTable() {
        ObservableList<Produit> masterData = FXCollections.observableArrayList();
        masterData.setAll(new ProduitDAO().getAll());
        FilteredList<Produit> filteredData = new FilteredList<>(masterData, p -> true);

        filterField.textProperty().addListener((obs, oldValue, newValue) -> {
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

    private void fillClientTable() {
        ObservableList<Client> masterData = FXCollections.observableArrayList();
        masterData.setAll(new ClientDAO().getAll());
        FilteredList<Client> filteredData = new FilteredList<>(masterData, c -> true);

        filterField.textProperty().addListener((obs, oldValue, newValue) -> {
            filteredData.setPredicate(client -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (client.getNom().toLowerCase().contains(lowerCaseFilter) || 
                        client.getPrenom().toLowerCase().contains(lowerCaseFilter) ||
                        String.valueOf(client.getNumCarteFidelite()).contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        tableClients.setItems(filteredData);
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

    private void fillStaffChoiceBox() {
        ObservableList obslist = FXCollections.observableList(new PersonnelDAO().getAll());
        superieurCB.setItems(obslist);
    }

    @FXML
    private void afficherInventaire(ActionEvent event) {
        inventaire.setVisible(true);
        gPersonnels.setVisible(false);
        gClients.setVisible(false);
    }

    @FXML
    private void afficherPtsVente(ActionEvent event) {
        inventaire.setVisible(false);
        gPersonnels.setVisible(false);
        gClients.setVisible(false);
    }

    @FXML
    private void afficherPersonnels(ActionEvent event) {
        gPersonnels.setVisible(true);
        inventaire.setVisible(false);
        gClients.setVisible(false);
    }

    @FXML
    private void afficherClients(ActionEvent event) {
        gClients.setVisible(true);
        generateNumCarteFidelite();
        inventaire.setVisible(false);
        gPersonnels.setVisible(false);
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
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "Vérifier le prix ou la quantité!", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vérifier vos données!", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
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

    @FXML
    private void generateNumBadge(ActionEvent event) {
        Random r = new Random();
        numBadgeTF.setText(String.valueOf(r.nextInt() & Integer.MAX_VALUE));
    }

    private void generateNumCarteFidelite() {
        int num = new Random().nextInt() & Integer.MAX_VALUE;
        numCarteFideliteTF.setText(String.valueOf(num));
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

    @FXML
    private void resetPersoForm(ActionEvent event) {
        numBadgeTF.clear();
        nomTF.clear();
        prenomTF.clear();
        adrPersoTF.clear();
        adrTravailTF.clear();
        posteTF.clear();
    }

    @FXML
    private void resetClientForm(ActionEvent event) {
        nomClientTF.clear();
        prenomClientTF.clear();
        generateNumCarteFidelite();
        mailClientTF.clear();
        codePostaleClientTF.clear();
    }

    @FXML
    private void quitApplication(ActionEvent event) {
        Connexion.closeConnection();
        System.exit(0);
    }

}
