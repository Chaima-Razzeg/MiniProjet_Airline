package com.example.airline;

import com.example.airline.entities.Airport;
import com.example.airline.entities.Companies;
import com.example.airline.entities.Flight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class CustomerFliController {

    @FXML
    private TableColumn<?, ?> Comp;

    @FXML
    private TableColumn<?, ?> arrAir;

    @FXML
    private Button btnDetail;

    @FXML
    private Button btnRes;

    @FXML
    private TableColumn<?, ?> codeColumn;

    @FXML
    private TableColumn<?, ?> depAir;

    @FXML
    private TableView<Flight> tableView;
    private ObservableList<Flight> flights = FXCollections.observableArrayList();

    private String getAeroportId(int idAir) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String nomAir="";

        try {
            conn = ConnexionDB.getConnexion();
            String query = "SELECT nameAir FROM airport WHERE idAir = ?";
            statement = conn.prepareStatement(query);
            statement.setInt(1, idAir);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                nomAir = resultSet.getString("nameAir");
            }
        } finally {
            // Close the ResultSet, PreparedStatement, and Connection
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

        return nomAir;
    }

    private String getCompagnieId(int idAir) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String nomAir="";

        try {
            conn = ConnexionDB.getConnexion();
            String query = "SELECT libelle FROM companies WHERE idComp = ?";
            statement = conn.prepareStatement(query);
            statement.setInt(1, idAir);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                nomAir = resultSet.getString("libelle");
            }
        } finally {
            // Close the ResultSet, PreparedStatement, and Connection
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

        return nomAir;
    }




}
