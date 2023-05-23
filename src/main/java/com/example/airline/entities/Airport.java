package com.example.airline.entities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Airport {
    private SimpleIntegerProperty idAir;
    private SimpleStringProperty codeAir;


    private SimpleStringProperty nameAir;
    private SimpleStringProperty city;
    public Airport(String codeAir,String nameAir,  String city) {
       this.idAir = new SimpleIntegerProperty();
        this.codeAir = new SimpleStringProperty(codeAir);

        this.nameAir = new SimpleStringProperty(nameAir);
        this.city= new SimpleStringProperty(city);

    }



    public int getIdAir() {
        return idAir.get();
    }

    public SimpleIntegerProperty idAirProperty() {
        return idAir;
    }

    public void setIdAir(int idAir) {
        this.idAir.set(idAir);
    }

    public String getNameAir() {
        return nameAir.get();
    }

    public SimpleStringProperty nameAirProperty() {
        return nameAir;
    }

    public void setNameAir(String nameAir) {
        this.nameAir.set(nameAir);
    }

    public String getCity() {
        return city.get();
    }

    public SimpleStringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public String getCodeAir() {
        return codeAir.get();
    }

    public SimpleStringProperty codeAirProperty() {
        return codeAir;
    }

    public void setCodeAir(String codeAir) {
        this.codeAir.set(codeAir);
    }

    @Override
    public String toString() {
        return "Airport{" +
                "idAir=" + idAir +
                ", codeAir=" + codeAir +
                ", nameAir=" + nameAir +
                ", city=" + city +
                '}';
    }
}
