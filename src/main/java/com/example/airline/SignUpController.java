package com.example.airline;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignUpController {

    @FXML
    private Button btn_register;

    @FXML
    private TextField t_add;

    @FXML
    private TextField t_conPass;

    @FXML
    private TextField t_email;

    @FXML
    private TextField t_first;

    @FXML
    private TextField t_name;

    @FXML
    private TextField t_pass;

    @FXML
    private TextField t_passport;

    @FXML
    private TextField t_tel;





    @FXML
    void inserer(ActionEvent event) {
        if (t_add.getText().equals("") || t_conPass.getText().equals("") || t_email.getText().equals("")
                || t_name.getText().equals("") || t_pass.getText().equals("") || t_passport.getText().equals("")
                || t_tel.getText() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Form Error!");
            alert.setHeaderText(null);
            alert.setContentText("Priére de renseigner les champs manquants");
            alert.showAndWait();

        } else {
            String name = t_name.getText();
            String firstname = t_first.getText();
            String add = t_add.getText();
            String passport = t_passport.getText();
            String email = t_email.getText();
            String pass = t_pass.getText();
            String conPass = t_conPass.getText();
            int tel = Integer.parseInt(t_tel.getText());

            if (!pass.equals(conPass)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Form Error!");
                alert.setHeaderText(null);
                alert.setContentText("The two passwords are not identical");
                alert.showAndWait();
                return; // Stop further execution
            }

            PreparedStatement st = null;
            ResultSet rs = null;
            Connection con = ConnexionDB.getConnexion();
            String sql = "INSERT INTO customer (`name`, `firstname`, `passport`, `address`, `telephone`, `email`, `password`) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            try {
                st = con.prepareStatement(sql);
                st.setString(1, name);
                st.setString(2, firstname);
                st.setString(3, add);
                st.setString(4, passport);
                st.setInt(5, tel);
                st.setString(6, email);
                st.setString(7, pass);

                st.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation de l'insertion !");
                alert.setHeaderText(null);
                alert.setContentText("Insertion effectuée dans la base");
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'exécution de la requête SQL : " + e.getMessage());
            }
        }
    }




}
