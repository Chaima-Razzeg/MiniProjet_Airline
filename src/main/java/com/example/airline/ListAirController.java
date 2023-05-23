package com.example.airline;

import com.example.airline.entities.Airport;
import com.example.airline.entities.Companies;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

public class ListAirController {
    @FXML
    private Button btnAir;
    @FXML
    private TableView<Airport> tableView;
    @FXML
    private TableColumn<Airport, String> nameAirColumn;
    @FXML
    private TableColumn<Airport, String> cityColumn;
    @FXML
    private TableColumn<Airport, String> codeAirColumn;



    private ObservableList<Airport> airports = FXCollections.observableArrayList();

    public void initialize() {
        // Configure les colonnes de la TableView
        codeAirColumn.setCellValueFactory(new PropertyValueFactory<>("codeAir"));
        nameAirColumn.setCellValueFactory(new PropertyValueFactory<>("nameAir"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));


        // Charge les données de la base de données dans la TableView
        loadData();
    }

    private void loadData() {
        Connection con = ConnexionDB.getConnexion();
        String sql = "SELECT * FROM airport";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            airports.clear();

            while (rs.next()) {
                String codeAir = rs.getString("codeAir");
                String nameAir = rs.getString("nameAir");
                String city = rs.getString("city");
                Airport airport = new Airport(codeAir,nameAir, city);
                airports.add(airport);
            }

            tableView.setItems(airports);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/AddAero.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            // Obtient le contrôleur de la fenêtre d'ajout (AddCompController)
            AddAirController addAirController = loader.getController();

            // Passe la référence du ListCompController au AddCompController
            addAirController.setListAirController(this);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void deleteAirportFromDatabase(Airport airport) {
        Connection con = ConnexionDB.getConnexion();
        String sql = "DELETE FROM airport WHERE codeAir = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, airport.getCodeAir());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Airport supprimée avec succès de la base de données.");
            } else {
                System.out.println("La suppression de la aeroport a échoué.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'exécution de la requête SQL : " + e.getMessage());
        }
    }
    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        // Vérifie si une compagnie est sélectionnée
        Airport selectedAirport = tableView.getSelectionModel().getSelectedItem();
        if (selectedAirport == null) {
            // Affiche un message d'erreur si aucune compagnie n'est sélectionnée
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une aeroport.");
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
            airports.remove(selectedAirport);
            deleteAirportFromDatabase(selectedAirport);
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
        Airport selectedAirport = tableView.getSelectionModel().getSelectedItem();
        if (selectedAirport == null) {
            // Affiche un message d'erreur si aucune compagnie n'est sélectionnée
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une compagnie.");
            alert.showAndWait();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/UpdateAero.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            // Obtient le contrôleur de la fenêtre de mise à jour (UpdateCompController)
            UpdateAirController updateAirController = loader.getController();

            // Passe la compagnie sélectionnée au contrôleur de la fenêtre de mise à jour
            updateAirController.setAirport(selectedAirport);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateAirportInDatabase(Airport airport) {
        Connection con = ConnexionDB.getConnexion();
        String sql = "UPDATE airport SET codeAir = ?, nameAir = ?, city = ? WHERE idAir = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, airport.getCodeAir());

            stmt.setString(2, airport.getNameAir());
            stmt.setString(3, airport.getCity());
            stmt.setInt(4, airport.getIdAir());

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
