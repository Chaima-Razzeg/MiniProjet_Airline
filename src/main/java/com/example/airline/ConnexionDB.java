package com.example.airline;

import java.nio.channels.ConnectionPendingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionDB {
    static String user="root";
    static String password="";
    static  String url="jdbc:mysql://"+"localhost:3306/login";
    static String driver="com.mysql.jdbc.Driver";
    public static Connection getConnexion(){
        Connection connection = null;
        try{
            Class.forName(driver);
        try{
            connection = DriverManager.getConnection(url,user,password);
                   }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }catch(ClassNotFoundException e){
        throw new RuntimeException(e);
    }
    return connection;
}
}
