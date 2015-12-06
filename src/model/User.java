/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author KrzysieK
 */
public class User {
    private SimpleIntegerProperty id;
    private SimpleStringProperty imie;
    private SimpleStringProperty nazwisko;
    private SimpleIntegerProperty pesel;
 
    public int getId() {
        return id.getValue();
    }
    public void setId(int id) {
        this.id = new SimpleIntegerProperty(id);
    }
    public String getImie() {
        return imie.get();
    }
    public void setImie(String imie) {
        this.imie = new SimpleStringProperty(imie);
    }
    public String getNazwisko() {
        return nazwisko.get();
    }
    public void setNazwisko(String nazwisko) {
        this.nazwisko = new SimpleStringProperty(nazwisko);
    }
    public int getPesel() {
        return pesel.getValue();
    }
    public void setPesel(int pesel) {
        this.pesel = new SimpleIntegerProperty(pesel);
    }
 
    public User() { }
    
    public User(int id, String imie, String nazwisko, int pesel) {
        this.id = new SimpleIntegerProperty(id);
        this.imie = new SimpleStringProperty(imie);
        this.nazwisko = new SimpleStringProperty(nazwisko);;
        this.pesel = new SimpleIntegerProperty(pesel);
    }
 
    @Override
    public String toString() {
        return "["+id+"] - "+imie+" "+nazwisko+" - "+pesel;
    }
}
