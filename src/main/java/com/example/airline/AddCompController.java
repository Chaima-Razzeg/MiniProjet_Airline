package com.example.airline;

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

public class AddCompController {
    @FXML
    private Button addButton;
    @FXML
    private TextField t_code;

    @FXML
    private TextField t_nameComp;

    private ListCompController listCompController;
    private Stage mainStage;

    public void setListCompController(ListCompController listCompController) {
        this.listCompController = listCompController;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @FXML
    void AddCompagny(ActionEvent event) {
        if (t_code.getText().equals("") || t_nameComp.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Form Error!");
            alert.setHeaderText(null);
            alert.setContentText("Priére de renseigner les champs manquants");
            alert.showAndWait();
        } else {
            String code = t_code.getText();
            String nameCom = t_nameComp.getText();
            Connection con = ConnexionDB.getConnexion();

            // Vérification si le code existe déjà
            String checkSql = "SELECT * FROM companies WHERE code = ?";
            try (PreparedStatement checkStmt = con.prepareStatement(checkSql)) {
                checkStmt.setString(1, code);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    // Le code existe déjà, afficher un message d'erreur
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur d'insertion");
                    alert.setHeaderText(null);
                    alert.setContentText("La compagnie existe déjà");
                    alert.showAndWait();
                } else {
                    // Le code n'existe pas, procéder à l'insertion
                    String insertSql = "INSERT INTO companies (code, libelle) VALUES (?, ?)";
                    try (PreparedStatement insertStmt = con.prepareStatement(insertSql)) {
                        insertStmt.setString(1, code);
                        insertStmt.setString(2, nameCom);
                        insertStmt.executeUpdate();

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation d'insertion !");
                        alert.setHeaderText(null);
                        alert.setContentText("Insertion effectuée dans la base");
                        alert.showAndWait();

                        retournerALaListe();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.err.println("Erreur lors de l'exécution de la requête SQL : " + e.getMessage());
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'exécution de la requête SQL : " + e.getMessage());
            }
        }
    }

    public void retournerALaListe() {
        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();
        listCompController.rafraichirListe();
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
