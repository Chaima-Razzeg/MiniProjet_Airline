package com.example.airline.entities;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Companies {
    private SimpleIntegerProperty idComp;

    private SimpleStringProperty libelle;
    private SimpleStringProperty code;


    public Companies(String code,  String libelle) {
        this.idComp = new SimpleIntegerProperty();

        this.code = new SimpleStringProperty(code);
        this.libelle= new SimpleStringProperty(libelle);

    }






    public int getIdComp() {
        return idComp.get();
    }
    public SimpleIntegerProperty idCompProperty() {
        return idComp;
    }

    public void setIdComp(int idComp) {
        this.idComp.set(idComp);
    }

    public String getLibelle() {
        return libelle.get();
    }

    public SimpleStringProperty libelleProperty() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle.set(libelle);
    }

    public String getCode() {
        return code.get();
    }

    public SimpleStringProperty codeProperty() {
        return code;
    }

    public void setCode(String code) {
        this.code.set(code);
    }


    @Override
    public String toString() {
        return "Companies{" +
                "idComp=" + idComp +
                ", libelle=" + libelle.get() +
                ", code=" + code.get() +
                '}';
    }

    public void updateCompany(String code, String libelle) {
        this.code.set(code);
        this.libelle.set(libelle);
    }

}
