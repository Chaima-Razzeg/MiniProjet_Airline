package com.example.airline;

import com.example.airline.entities.Companies;
import com.example.airline.entities.Flight;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class UpdateFliController {
    @FXML
    private ChoiceBox<?> Comp;

    @FXML
    private ChoiceBox<?> arrAir;

    @FXML
    private Button btnUpdate;

    @FXML
    private ChoiceBox<?> depAir;

    @FXML
    private TextField t_capacite;

    @FXML
    private TextField t_codeFli;

    @FXML
    private TextField t_heureArr;

    @FXML
    private TextField t_heureDep;

    @FXML
    private DatePicker t_jourArr;

    @FXML
    private DatePicker t_jourDep;
    private Flight flight;

    public void setFlight(Flight flight) {
        this.flight = flight;

        // Initialise les champs du formulaire avec les valeurs de l'objet Companies
        t_codeFli.setText(flight.getCodeFli());
        t_capacite.setText(String.valueOf(flight.getCapacite()));
        t_heureArr.setText(String.valueOf(flight.getHeureArr()));
        t_heureDep.setText(String.valueOf(flight.getHeureDep()));
       // String jourDep = String.valueOf(flight.getJourDep());
      //  t_jourDep.setText(flight.getJourDep());

    }


    @FXML
    void updateFlight(ActionEvent event) {


        String codeFli = t_codeFli.getText();
        Integer capacite = Integer.valueOf(t_capacite.getText());
        Time heureDep = Time.valueOf(t_heureDep.getText());
        Time heureArr = Time.valueOf(t_heureArr.getText());
        LocalDate jourDep = t_jourDep.getValue();
        LocalDate jourArr = t_jourArr.getValue();


        Connection con = ConnexionDB.getConnexion();

        // Le code n'existe pas, procéder à l'insertion
        String insertSql = "UPDATE flight SET heureDep = ?, heureArr = ?, jourDep = ?, jourArr =?, capacite = ? WHERE codeFli = ?";
        try (PreparedStatement insertStmt = con.prepareStatement(insertSql)) {
            insertStmt.setTime(1, heureDep);
            insertStmt.setTime(2, heureArr);
            insertStmt.setDate(3, Date.valueOf(jourDep));
            insertStmt.setDate(4, Date.valueOf(jourArr));

            insertStmt.setInt(5, capacite);
            insertStmt.setString(6, codeFli);
            insertStmt.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation d'insertion !");
            alert.setHeaderText(null);
            alert.setContentText("Insertion effectuée dans la base");
            alert.showAndWait();
            redirectToInterfaceFli(event);


        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'exécution de la requête SQL : " + e.getMessage());
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


}
