package com.example.airline;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HomeController {
    @FXML
    private ImageView admin;

    @FXML
    private ImageView customer;

    @FXML
    public void handleAdminClick(MouseEvent event) {
        redirectToLogin();
    }

    @FXML
    public void handleCustomerClick(MouseEvent event) {
        redirectToSignIn();
    }

    private void redirectToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) admin.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any potential exceptions here
        }
    }

    private void redirectToSignIn() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Acc.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) customer.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any potential exceptions here
        }
    }
}
