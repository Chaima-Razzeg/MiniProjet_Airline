package com.example.airline;

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

public class AddFliController {
    @FXML
    private ChoiceBox<String> Comp;

    @FXML
    private ChoiceBox<String> arrAir;

    @FXML
    private ChoiceBox<String> depAir;

    @FXML
    private Button btnAdd;

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

    private ListFliController listFliController;
    private Stage mainStage;


    public void setListFliController(ListFliController listFliController) {
        this.listFliController = listFliController;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @FXML
    void initialize() {
        Connection con = ConnexionDB.getConnexion();

        // Requête SQL pour récupérer les noms de compagnies
        String queryComp = "SELECT libelle FROM companies";
        try (Statement stmtComp = con.createStatement()) {
            ResultSet rsComp = stmtComp.executeQuery(queryComp);

            // Ajouter les noms de compagnies à la ChoiceBox "Comp"
            while (rsComp.next()) {
                String libelle = rsComp.getString("libelle");
                Comp.getItems().add(libelle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'exécution de la requête SQL : " + e.getMessage());
        }

        // Requête SQL pour récupérer les noms d'aéroports
        String queryAir = "SELECT nameAir FROM airport";
        try (Statement stmtAir = con.createStatement()) {
            ResultSet rsAir = stmtAir.executeQuery(queryAir);

            // Ajouter les noms d'aéroports aux ChoiceBox "arrAir" et "depAir"
            while (rsAir.next()) {
                String nameAir = rsAir.getString("nameAir");
                arrAir.getItems().add(nameAir);
                depAir.getItems().add(nameAir);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'exécution de la requête SQL : " + e.getMessage());
        }
    }

    @FXML
    void AddFlight(ActionEvent event) {
        System.out.println();
        if (t_codeFli.getText().isEmpty() || t_capacite.getText().isEmpty() ||
                t_heureArr.getText().isEmpty() || t_heureDep.getText().isEmpty() ||
                t_jourArr.getValue() == null || t_jourDep.getValue() == null ||
                Comp.getValue() == null ||
                arrAir.getValue() == null || depAir.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Form Error!");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez renseigner tous les champs.");
            alert.showAndWait();
        } else {
            String codeFli = t_codeFli.getText();
            Integer capacite = Integer.parseInt(t_capacite.getText());
            LocalDate jourArr = t_jourArr.getValue();
            LocalDate jourDep = t_jourDep.getValue();
            Time heureDep = Time.valueOf(t_heureDep.getText());
            Time heureArr = Time.valueOf(t_heureArr.getText());

            Connection con = ConnexionDB.getConnexion();

            // Vérification si le code existe déjà
            String checkSql = "SELECT * FROM flight WHERE codeFli = ?";
            try (PreparedStatement checkStmt = con.prepareStatement(checkSql)) {
                checkStmt.setString(1, codeFli);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    // Le code existe déjà, afficher un message d'erreur
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur d'insertion");
                    alert.setHeaderText(null);
                    alert.setContentText("Le vol existe déjà");
                    alert.showAndWait();
                } else {
                    // Le code n'existe pas, procéder à l'insertion
                    String insertSql = "INSERT INTO `flight` (`codeFli`, `heureDep`, `heureArr`, `jourDep`, `jourArr`, `capacite`, `etat`, `idAirDep`, `idAirArr`,idCom) VALUES (?, ?, ?, ?, ?, ?, 'Open', ?, ?, ?)";
                    try (PreparedStatement insertStmt = con.prepareStatement(insertSql)) {
                        insertStmt.setString(1, codeFli);
                        insertStmt.setTime(2, heureDep);
                        insertStmt.setTime(3, heureArr);
                        insertStmt.setDate(4, Date.valueOf(jourDep));
                        insertStmt.setDate(5, Date.valueOf(jourArr));
                        insertStmt.setString(6, String.valueOf(capacite));
                        insertStmt.setInt(7, getAeroportId(depAir.getValue()));
                        insertStmt.setInt(8, getAeroportId(arrAir.getValue()));
                        insertStmt.setInt(9, getCompanyId(Comp.getValue()));

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
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'exécution de la requête SQL : " + e.getMessage());
            }
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
    private int getAeroportId(String nomAeroport) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int aeroportId = -1;

        try {
            conn = ConnexionDB.getConnexion();
            String query = "SELECT idAir FROM airport WHERE nameAir = ?";
            statement = conn.prepareStatement(query);
            statement.setString(1, nomAeroport);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                aeroportId = resultSet.getInt("idAir");
            }
        } finally {
            // Fermer le ResultSet, PreparedStatement et Connection
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return aeroportId;
    }
    private int getCompanyId(String nomCompany) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int companyId = -1;

        try {
            conn = ConnexionDB.getConnexion();
            String query = "SELECT idComp FROM companies WHERE libelle = ?";
            statement = conn.prepareStatement(query);
            statement.setString(1, nomCompany);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                companyId = resultSet.getInt("idComp");
            }
        } finally {
            // Fermer le ResultSet, PreparedStatement et Connection
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return companyId;
    }

}
