package com.example.airline;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignInController implements Initializable {

    @FXML
    private Button btn_signin;

    @FXML
    private PasswordField t_password;

    @FXML
    private TextField t_username;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_signin.setOnAction(actionEvent -> login());

    }
    public void login() {
        try {
            PreparedStatement st = null;
            ResultSet rs = null;
            Connection con = ConnexionDB.getConnexion();

            st = con.prepareStatement("SELECT * FROM customer WHERE email = ? AND PASSWORD = ?");
            st.setString(1, t_username.getText());
            st.setString(2, t_password.getText());
            rs = st.executeQuery();

            if (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Login Successful", ButtonType.OK);
                alert.show();

                // Chargement du fichier FXML "Dashboard.fxml"
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/CustomerFli.fxml"));
                Parent root = loader.load();

                // Obtention du contrôleur du fichier FXML chargé
                CustomerFliController listFliController = loader.getController();

                // Configuration de la scène avec le contenu du fichier FXML chargé
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();

                // Fermeture de la fenêtre de connexion
                Stage loginStage = (Stage) btn_signin.getScene().getWindow();
                loginStage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Login Error", ButtonType.OK);
                alert.show();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}