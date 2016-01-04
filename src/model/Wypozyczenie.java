package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author KrzysieK Sz.
 */
public class Wypozyczenie {

    private final SimpleIntegerProperty id_wypozyczenia;
    private final SimpleStringProperty imie;
    private final SimpleStringProperty nazwisko;
    private final SimpleStringProperty tytul;
    private final SimpleStringProperty autor;
    private final SimpleStringProperty status;

    public Wypozyczenie(String imie, String nazwisko, String tytul, String autor, String status) {
        this.imie = new SimpleStringProperty(imie);
        this.nazwisko = new SimpleStringProperty(nazwisko);
        this.tytul = new SimpleStringProperty(tytul);
        this.autor = new SimpleStringProperty(autor);
        id_wypozyczenia = new SimpleIntegerProperty();
        this.status = new SimpleStringProperty(status);
    }

    public Wypozyczenie(String imie, String nazwisko, String tytul, String autor, Integer id, String status) {
        this.imie = new SimpleStringProperty(imie);
        this.nazwisko = new SimpleStringProperty(nazwisko);
        this.tytul = new SimpleStringProperty(tytul);
        this.autor = new SimpleStringProperty(autor);
        this.id_wypozyczenia = new SimpleIntegerProperty(id);
        this.status = new SimpleStringProperty(status);
    }
    
    public void setStatus(String status) {
        this.status.set(status);
    }
    
    public void setImie(String imie) {
        this.imie.set(imie);
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko.set(nazwisko);
    }

    public void setTytul(String tytul) {
        this.tytul.set(tytul);
    }

    public void setAutor(String autor) {
        this.autor.set(autor);
    }

    public void setId_wypozyczenia(Integer id) {
        this.id_wypozyczenia.set(id);
    }

    public String getImie() {
        return imie.get();
    }

    public String getNazwisko() {
        return nazwisko.get();
    }

    public String getTytul() {
        return tytul.get();
    }

    public String getAutor() {
        return autor.get();
    }

    public Integer getId_wypozyczenia() {
        return id_wypozyczenia.get();
    }
    
    public String getStatus(){
        return status.get();
    }

}
