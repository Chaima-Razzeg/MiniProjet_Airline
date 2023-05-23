package com.example.airline;

import com.example.airline.entities.Companies;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class ListCompController{
    @FXML
    private Button btnComp;
    @FXML
    private TableView<Companies> tableView;
    @FXML
    private TableColumn<Companies, String> codeColumn;
    @FXML
    private TableColumn<Companies, String> libelleColumn;


    private ObservableList<Companies> compagnies = FXCollections.observableArrayList();

    public void initialize() {
        // Configure les colonnes de la TableView
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        libelleColumn.setCellValueFactory(new PropertyValueFactory<>("libelle"));


        // Charge les données de la base de données dans la TableView
        loadData();
    }

    private void loadData() {
        Connection con = ConnexionDB.getConnexion();
        String sql = "SELECT * FROM companies";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            compagnies.clear();

            while (rs.next()) {
                String code = rs.getString("code");
                String libelle = rs.getString("libelle");
                Companies company = new Companies(code, libelle);
                compagnies.add(company);
            }

            tableView.setItems(compagnies);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'exécution de la requête SQL : " + e.getMessage());
        }
    }

    public void rafraichirListe() {
        loadData();
    }

    @FXML
    private void handleAddButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/AddComp.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            // Obtient le contrôleur de la fenêtre d'ajout (AddCompController)
            AddCompController addCompController = loader.getController();

            // Passe la référence du ListCompController au AddCompController
            addCompController.setListCompController(this);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void deleteCompanyFromDatabase(Companies company) {
        Connection con = ConnexionDB.getConnexion();
        String sql = "DELETE FROM companies WHERE code = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, company.getCode());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Compagnie supprimée avec succès de la base de données.");
            } else {
                System.out.println("La suppression de la compagnie a échoué.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'exécution de la requête SQL : " + e.getMessage());
        }
    }
    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        // Vérifie si une compagnie est sélectionnée
        Companies selectedCompany = tableView.getSelectionModel().getSelectedItem();
        if (selectedCompany == null) {
            // Affiche un message d'erreur si aucune compagnie n'est sélectionnée
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une compagnie.");
            alert.showAndWait();
            return;
        }

        // Demande confirmation à l'utilisateur avant de supprimer la compagnie
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmation");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Êtes-vous sûr de vouloir supprimer la compagnie sélectionnée?");
        Optional<ButtonType> result = confirmAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Supprime la compagnie de la TableView et de la base de données
            compagnies.remove(selectedCompany);
            deleteCompanyFromDatabase(selectedCompany);
        }
    }

    public void redirectToInterfaceComp(ActionEvent event) {
        try {
            // Charger le fichier FXML de l'interface souhaitée
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/ListComp.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la racine chargée depuis le fichier FXML
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'événement
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Changer la scène du stage actuel
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer les éventuelles exceptions lors du chargement du fichier FXML
        }
    }
    public void redirectToInterfaceAir(ActionEvent event) {
        try {
            // Charger le fichier FXML de l'interface souhaitée
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/ListAero.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la racine chargée depuis le fichier FXML
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'événement
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Changer la scène du stage actuel
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer les éventuelles exceptions lors du chargement du fichier FXML
        }
    }
    public void redirectToInterfaceFli(ActionEvent event) {
        try {
            // Charger le fichier FXML de l'interface souhaitée
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/ListFli.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la racine chargée depuis le fichier FXML
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'événement
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Changer la scène du stage actuel
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer les éventuelles exceptions lors du chargement du fichier FXML
        }
    }


    @FXML
    private void handleUpdateButtonAction(ActionEvent event) {
        // Vérifie si une compagnie est sélectionnée
        Companies selectedCompany = tableView.getSelectionModel().getSelectedItem();
        if (selectedCompany == null) {
            // Affiche un message d'erreur si aucune compagnie n'est sélectionnée
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une compagnie.");
            alert.showAndWait();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/UpdateComp.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            // Obtient le contrôleur de la fenêtre de mise à jour (UpdateCompController)
            UpdateCompController updateCompController = loader.getController();

            // Passe la compagnie sélectionnée au contrôleur de la fenêtre de mise à jour
            updateCompController.setCompany(selectedCompany);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateCompanyInDatabase(Companies company) {
        Connection con = ConnexionDB.getConnexion();
        String sql = "UPDATE companies SET code = ?, libelle = ? WHERE idComp = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, company.getCode());
            stmt.setString(2, company.getLibelle());
            stmt.setInt(3, company.getIdComp());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Compagnie mise à jour avec succès dans la base de données.");
            } else {
                System.out.println("La mise à jour de la compagnie a échoué.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'exécution de la requête SQL : " + e.getMessage());
        }
    }



}
