package com.example.airline;

import com.example.airline.entities.Airport;
import com.example.airline.entities.Companies;
import com.example.airline.entities.Flight;
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

public class ListFliController {
    @FXML
    private TableView<Flight> tableView;
    @FXML
    private TableColumn<Flight, String> codeColumn;
    @FXML
    private TableColumn<Flight, Time> heuDepCol;
    @FXML
    private TableColumn<Flight, Time> heuArrCol;
    @FXML
    private TableColumn<Flight, Date> jDepCol;
    @FXML
    private TableColumn<Flight, Date> jArrCol;
    @FXML
    private TableColumn<Flight, Integer> capaCol;
    @FXML
    private TableColumn<Flight, String> statusCol;
    @FXML
    private Button btnAdd;

    @FXML
    private Button btnClimb;

    @FXML
    private Button btnClose;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnOpen;

    @FXML
    private Button btnUpdate;

    @FXML
    void OpenFlight(ActionEvent event) {

    }

    @FXML
    void addClimb(ActionEvent event) {

    }

    @FXML
    void addFlight(ActionEvent event) {

    }

    @FXML
    void closeFlight(ActionEvent event) {

    }



    @FXML
    void updateFlight(ActionEvent event) {

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
    private ObservableList<Flight> flights = FXCollections.observableArrayList();

    public void initialize() {
        // Configure les colonnes de la TableView
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("codeFli"));
        heuDepCol.setCellValueFactory(new PropertyValueFactory<>("heureDep"));
        heuArrCol.setCellValueFactory(new PropertyValueFactory<>("heureArr"));
        jDepCol.setCellValueFactory(new PropertyValueFactory<>("jourDep"));

        jArrCol.setCellValueFactory(new PropertyValueFactory<>("jourArr"));
        capaCol.setCellValueFactory(new PropertyValueFactory<>("capacite"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("etat"));


        // Charge les données de la base de données dans la TableView
        loadData();
    }

    private void loadData() {
        Connection con = ConnexionDB.getConnexion();
        String sql = "SELECT * FROM flight";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            flights.clear();

            while (rs.next()) {
                String codeFli = rs.getString("codeFli");
                Time heureDep = rs.getTime("heureDep");
                Time heureArr = rs.getTime("heureArr");
                Date jourDep = rs.getDate("jourDep");

                Date jourArr = rs.getDate("jourArr");
                Integer capacite = rs.getInt("capacite");
                String etat = rs.getString("etat");
                Flight flight = new Flight(codeFli,capacite, etat,jourDep,jourArr,heureDep,heureArr);
                flights.add(flight);
            }

            tableView.setItems(flights);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'exécution de la requête SQL : " + e.getMessage());
        }
    }
    private void updateFlightStatusInDatabase(Flight flight) {
        Connection con = ConnexionDB.getConnexion();
        String sql = "UPDATE flight SET etat = ? WHERE codeFli = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, flight.getEtat());
            stmt.setString(2, flight.getCodeFli());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Flight status updated successfully in the database.");
            } else {
                System.out.println("Failed to update flight status in the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
    }
    @FXML
    private void handleCloseFlightButtonAction(ActionEvent event) {
        // Vérifie si un vol est sélectionné
        Flight selectedFlight = tableView.getSelectionModel().getSelectedItem();
        if (selectedFlight == null) {
            // Affiche un message d'erreur si aucun vol n'est sélectionné
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un vol à fermer.");
            alert.showAndWait();
            return;
        }

        // Vérifie si le vol est déjà fermé
        if (selectedFlight.getEtat().equals("Close")) {
            // Affiche un message d'erreur si le vol est déjà fermé
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Le vol est déjà fermé.");
            alert.showAndWait();
            return;
        }

        // Met à jour l'état dans la liste
        selectedFlight.setEtat("Close");

        // Met à jour l'état dans la base de données
        updateFlightStatusInDatabase(selectedFlight);

        // Rafraîchit le TableView pour refléter la mise à jour
        tableView.refresh();
    }
    @FXML
    private void handleOpenFlightButtonAction(ActionEvent event) {
        // Vérifie si un vol est sélectionné
        Flight selectedFlight = tableView.getSelectionModel().getSelectedItem();
        if (selectedFlight == null) {
            // Affiche un message d'erreur si aucun vol n'est sélectionné
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un vol à ouvrir.");
            alert.showAndWait();
            return;
        }

        // Vérifie si le vol est déjà fermé
        if (selectedFlight.getEtat().equals("Open")) {
            // Affiche un message d'erreur si le vol est déjà fermé
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Le vol est déjà ouvert.");
            alert.showAndWait();
            return;
        }

        // Met à jour l'état dans la liste
        selectedFlight.setEtat("Open");

        // Met à jour l'état dans la base de données
        updateFlightStatusInDatabase(selectedFlight);

        // Rafraîchit le TableView pour refléter la mise à jour
        tableView.refresh();
    }




    @FXML
    private void handleAddButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/AddFli.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            // Obtient le contrôleur de la fenêtre d'ajout (AddCompController)
            AddFliController addFliController = loader.getController();


            // Passe la référence du ListCompController au AddCompController
            addFliController.setListFliController(this);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void deleteFlightFromDatabase(Flight flight) {
        Connection con = ConnexionDB.getConnexion();
        String sql = "DELETE FROM flight WHERE codeFli = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, flight.getCodeFli());
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
        Flight selectedFlight = tableView.getSelectionModel().getSelectedItem();
        if (selectedFlight == null) {
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
            flights.remove(selectedFlight);
            deleteFlightFromDatabase(selectedFlight);
        }
    }




    @FXML
    private void handleUpdateButtonAction(ActionEvent event) {
        // Vérifie si une compagnie est sélectionnée
        Flight selectedFlight = tableView.getSelectionModel().getSelectedItem();
        if (selectedFlight == null) {
            // Affiche un message d'erreur si aucune compagnie n'est sélectionnée
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une compagnie.");
            alert.showAndWait();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/UpdateFli.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            // Obtient le contrôleur de la fenêtre de mise à jour (UpdateCompController)
            UpdateFliController updateFliController = loader.getController();

            // Passe la compagnie sélectionnée au contrôleur de la fenêtre de mise à jour
            updateFliController.setFlight(selectedFlight);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateFlightInDatabase(Flight flight) {
        Connection con = ConnexionDB.getConnexion();
        String sql = "UPDATE flight SET codeFli = ?, heureDep = ?, heureArr = ?, jourDep = ?, jourArr =?, capacite = ? WHERE idFli = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, flight.getCodeFli());
            stmt.setTime(2, flight.getHeureDep());
            stmt.setTime(3, flight.getHeureArr());
            stmt.setDate(4, (Date) flight.getJourDep());

            stmt.setDate(5, (Date) flight.getJourArr());
            stmt.setInt(6, flight.getCapacite());
            stmt.setInt(7, flight.getIdFli());

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
