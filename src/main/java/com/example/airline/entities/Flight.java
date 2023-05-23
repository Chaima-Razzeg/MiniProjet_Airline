package com.example.airline.entities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Time;
import java.util.Date;

public class Flight {
    private SimpleIntegerProperty idFli;
    private SimpleStringProperty codeFli;
    private SimpleIntegerProperty capacite;
    private SimpleStringProperty etat;



    private Date jourDep;
    private Date jourArr;
    private Time heureDep;
    private Time heureArr;
    Airport airDep,airArr;
    Companies com;

    public Flight(String codeFli, Integer capacite, String etat, Date jourDep, Date jourArr,
                  Time heureDep, Time heureArr, Airport airDep,Airport airArr, Companies com) {
        this.idFli= new SimpleIntegerProperty();

        this.codeFli = new SimpleStringProperty(codeFli);
        this.capacite= new SimpleIntegerProperty(capacite);
        this.etat = new SimpleStringProperty(etat);
        this.jourDep=jourDep;
        this.jourArr=jourArr;
        this.heureDep=heureDep;
        this.heureArr=heureArr;
        this.airDep=airDep;
        this.airArr=airArr;
        this.com=com;


    }

    public Flight(String codeFli, Integer capacite, String etat, Date jourDep, Date jourArr,
                  Time heureDep, Time heureArr) {
        this.idFli= new SimpleIntegerProperty();

        this.codeFli = new SimpleStringProperty(codeFli);
        this.capacite= new SimpleIntegerProperty(capacite);
        this.etat = new SimpleStringProperty(etat);
        this.jourDep=jourDep;
        this.jourArr=jourArr;
        this.heureDep=heureDep;
        this.heureArr=heureArr;



    }

    public Flight(String codeFli, Companies comp, Airport airArr, Airport airdep) {
        this.codeFli = new SimpleStringProperty(codeFli);
        this.com=comp;
        this.airArr=airArr;
        this.airDep=airdep;



    }

    public Airport getAirDep() {
        return airDep;
    }

    public void setAirDep(Airport airDep) {
        this.airDep = airDep;
    }

    public Airport getAirArr() {
        return airArr;
    }

    public void setAirArr(Airport airArr) {
        this.airArr = airArr;
    }

    public int getIdFli() {
        return idFli.get();
    }

    public SimpleIntegerProperty idFliProperty() {
        return idFli;
    }

    public void setIdFli(int idFli) {
        this.idFli.set(idFli);
    }

    public String getCodeFli() {
        return codeFli.get();
    }

    public SimpleStringProperty codeFliProperty() {
        return codeFli;
    }

    public void setCodeFli(String codeFli) {
        this.codeFli.set(codeFli);
    }

    public int getCapacite() {
        return capacite.get();
    }

    public SimpleIntegerProperty capaciteProperty() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite.set(capacite);
    }

    public String getEtat() {
        return etat.get();
    }

    public SimpleStringProperty etatProperty() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat.set(etat);
    }

    public Date getJourDep() {
        return jourDep;
    }

    public void setJourDep(Date jourDep) {
        this.jourDep = jourDep;
    }

    public Date getJourArr() {
        return jourArr;
    }

    public void setJourArr(Date jourArr) {
        this.jourArr = jourArr;
    }

    public Time getHeureDep() {
        return heureDep;
    }

    public void setHeureDep(Time heureDep) {
        this.heureDep = heureDep;
    }

    public Time getHeureArr() {
        return heureArr;
    }

    public void setHeureArr(Time heureArr) {
        this.heureArr = heureArr;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "idFli=" + idFli +
                ", codeFli=" + codeFli +
                ", capacite=" + capacite +
                ", etat=" + etat +
                ", jourDep=" + jourDep +
                ", jourArr=" + jourArr +
                ", heureDep=" + heureDep +
                ", heureArr=" + heureArr +
                '}';
    }
}
