/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author KrzysieK
 */
public class Wypozyczenie {
    private int idKsiazka;
    private int idCzytelnik;
 
    public int getIdKsiazka() {
        return idKsiazka;
    }
    public void setIdKsiazka(int idKsiazka) {
        this.idKsiazka = idKsiazka;
    }
    public int getIdCzytelnik() {
        return idCzytelnik;
    }
    public void setIdCzytelnik(int idCzytelnik) {
        this.idCzytelnik = idCzytelnik;
    }
 
    public Wypozyczenie() {}
    public Wypozyczenie(int idKsiazka, int idCzytelnik) {
        this.idKsiazka = idKsiazka;
        this.idCzytelnik = idCzytelnik;
 
    }
}
