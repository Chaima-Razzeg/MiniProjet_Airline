package com.example.airline;

import com.example.airline.entities.Airport;
import com.example.airline.entities.Companies;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateAirController {


    private Airport airport;
    @FXML
    private TextField t_city;

    @FXML
    private TextField t_nameAir;
    @FXML
    private TextField t_codeAir;



    public void setAirport(Airport airport) {
        this.airport = airport;

        // Initialise les champs du formulaire avec les valeurs de l'objet Companies
        t_codeAir.setText(airport.getCodeAir());

        t_nameAir.setText(airport.getNameAir());
        t_city.setText(airport.getCity());
    }

    @FXML

    void updateAirport(ActionEvent event) {

        System.out.println(t_codeAir.getText());

        System.out.println(t_nameAir.getText());
        System.out.println(t_city.getText());

        System.out.println(airport.getIdAir());

        String codeAir = t_codeAir.getText();

        String nameAir = t_nameAir.getText();
        String city = t_city.getText();
        Connection con = ConnexionDB.getConnexion();

        // Le code n'existe pas, procéder à l'insertion
        String insertSql = "UPDATE airport SET nameAir=?, city=? WHERE codeAir=?";
        try (PreparedStatement insertStmt = con.prepareStatement(insertSql)) {
            insertStmt.setString(1, nameAir);

            insertStmt.setString(2, city);
            insertStmt.setString(3, codeAir);
            insertStmt.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation d'insertion !");
            alert.setHeaderText(null);
            alert.setContentText("Insertion effectuée dans la base");
            alert.showAndWait();
            redirectToInterfaceAir(event);

            // retournerALaListe();
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
