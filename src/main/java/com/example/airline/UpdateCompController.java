package com.example.airline;

import com.example.airline.entities.Companies;
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
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateCompController {

    @FXML
    private Button saveButton;

    @FXML
    private TextField t_code;

    @FXML
    private TextField t_nameComp;
    @FXML
    private TextField codeTextField;
    @FXML
    private TextField libelleTextField;

    private Companies company;

    public void setCompany(Companies company) {
        this.company = company;

        // Initialise les champs du formulaire avec les valeurs de l'objet Companies
        t_code.setText(company.getCode());
        t_nameComp.setText(company.getLibelle());
    }


    @FXML
    void updateComp(ActionEvent event) {
        System.out.println(t_code.getText());
        System.out.println(company.getIdComp());

        String code = t_code.getText();
        String nameCom = t_nameComp.getText();
        Connection con = ConnexionDB.getConnexion();

                // Le code n'existe pas, procéder à l'insertion
                String insertSql = "UPDATE companies SET libelle=? WHERE code=?";
                try (PreparedStatement insertStmt = con.prepareStatement(insertSql)) {
                    insertStmt.setString(2, code);
                    insertStmt.setString(1, nameCom);
                    insertStmt.executeUpdate();

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation d'insertion !");
                    alert.setHeaderText(null);
                    alert.setContentText("Insertion effectuée dans la base");
                    alert.showAndWait();
                    redirectToInterfaceComp(event);


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