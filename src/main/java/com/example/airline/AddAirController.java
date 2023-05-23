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

public class AddAirController {
    @FXML
    private Button addButton;
    @FXML
    private TextField t_nameAir;

    @FXML
    private TextField t_city;
    @FXML
private TextField t_codeAir;

    private ListAirController listAirController;
    private Stage mainStage;

    public void setListAirController(ListAirController listAirController) {
        this.listAirController = listAirController;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @FXML
    void AddAirport(ActionEvent event) {
        if (t_codeAir.getText().equals("")|| t_nameAir.getText().equals("") || t_city.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Form Error!");
            alert.setHeaderText(null);
            alert.setContentText("Priére de renseigner les champs manquants");
            alert.showAndWait();
        } else {
            String codeAir=t_codeAir.getText();
            String nameAir = t_nameAir.getText();
            String city = t_city.getText();
            Connection con = ConnexionDB.getConnexion();

            // Vérification si le code existe déjà
            String checkSql = "SELECT * FROM airport WHERE codeAir = ?";
            try (PreparedStatement checkStmt = con.prepareStatement(checkSql)) {
                checkStmt.setString(1, codeAir);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    // Le code existe déjà, afficher un message d'erreur
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur d'insertion");
                    alert.setHeaderText(null);
                    alert.setContentText("L'aéroport existe déjà");
                    alert.showAndWait();
                } else {
                    // Le code n'existe pas, procéder à l'insertion
                    String insertSql = "INSERT INTO airport (codeAir,nameAir, city) VALUES (?, ?, ?)";
                    try (PreparedStatement insertStmt = con.prepareStatement(insertSql)) {
                        insertStmt.setString(1, codeAir);

                        insertStmt.setString(2, nameAir);
                        insertStmt.setString(3, city);
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
        listAirController.rafraichirListe();
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
