package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import model.User;
import model.Book;
import model.Wypozyczenie;

/**
 *
 * @author KrzysieK Sz.
 */
public class DataBaseController {

    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:baza.db";

    private Connection conn;
    private Statement stat;
    private PreparedStatement prepStmt;

    private ResultSet result;
    private PreparedStatement czytelnicyInstertPS;
    private PreparedStatement ksiazkiInsertPS;
    private PreparedStatement usunCzytelnikPS;
    private PreparedStatement usunKsiazkaPS;
    private PreparedStatement usunWypozyczeniePS;
    private PreparedStatement wypozyczenieksiazki;

    public DataBaseController() {
        try {
            Class.forName(DataBaseController.DRIVER);
            conn = DriverManager.getConnection(DB_URL);
            stat = conn.createStatement();

            czytelnicyInstertPS = conn.prepareStatement(
                    "INSERT INTO czytelnicy VALUES (NULL, ?, ?, ?, ?, ?);");
            ksiazkiInsertPS = conn.prepareStatement(
                    "INSERT INTO ksiazki VALUES (NULL, ?, ?, ?, ?, ?);");
            usunCzytelnikPS = conn.prepareStatement(
                    "DELETE FROM czytelnicy WHERE id_czytelnika = ?");
            usunKsiazkaPS = conn.prepareStatement(
                    "DELETE FROM ksiazki WHERE id_ksiazki = ?");
            usunWypozyczeniePS = conn.prepareStatement(
                    "DELETE FROM wypozyczenia WHERE id_wypozycz = ?");
            wypozyczenieksiazki = conn.prepareStatement(
                    //"insert into wypozyczenia (id_czytelnika, id_ksiazki) values ( ?, ?);");
                    "INSERT INTO wypozyczenia (id_czytelnika, id_ksiazki) VALUES  (?,?);");
                     //"insert into wypozyczenia values (NULL, ?, ?);");
            
            
        } catch (ClassNotFoundException e) {
            System.err.println("Brak sterownika JDBC");
        } catch (SQLException e) {
            System.err.println("Problem z otwarciem polaczenia");
        }
        dataBaseInitiator();
    }

    public DataBaseController(int i, int i0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void dataBaseInitiator() {
        try {
        PreparedStatement createWypozyczenia = conn.prepareStatement("CREATE TABLE IF NOT EXISTS wypozyczenia ("
                + "id_wypozycz INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "id_czytelnika int, id_ksiazki int)");
        PreparedStatement createKsiazki = conn.prepareStatement( "CREATE TABLE IF NOT EXISTS ksiazki ("
                + "id_ksiazki INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "tytul STRING, autor STRING"
                + ", wydawnictwo STRING, "
                + "isbn STRING, "
                + "rok_wydania STRING);");
        PreparedStatement createCzytelnicy = conn.prepareStatement( "CREATE TABLE IF NOT EXISTS czytelnicy ("
                + "id_czytelnika INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "imie STRING, nazwisko STRING, "
                + "pesel STRING, "
                + "miasto STRING, "
                + "ulica STRING);");

            createWypozyczenia.execute();
            createKsiazki.execute();
            createCzytelnicy.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy tworzeniu tabeli");
        }
    }

    public void insertCzytelnik(User czytelnik) {
        try {
            czytelnicyInstertPS.setString(1, czytelnik.getImie());
            czytelnicyInstertPS.setString(2, czytelnik.getNazwisko());
            czytelnicyInstertPS.setString(3, czytelnik.getPesel());
            czytelnicyInstertPS.setString(4, czytelnik.getMiasto());
            czytelnicyInstertPS.setString(5, czytelnik.getUlica());
            czytelnicyInstertPS.execute();
        } catch (SQLException e) {
            System.err.println("Błąd przy wstawianiu czytelnika");
        }

    }
    
    public  boolean insertWypozyczenieKS(int id_czytelnika, int id_ksiazki){
        try{
           wypozyczenieksiazki.setInt(1, id_czytelnika);
           wypozyczenieksiazki.setInt(2, id_ksiazki);
           wypozyczenieksiazki.execute();
        }
        catch (SQLException e){
            System.err.println("Błąd przy wypozyczaniu ksiazki");
            return false;
        }
    return true;
    }

    public void insertKsiazka(Book ksiazka) {
        try {

            ksiazkiInsertPS.setString(1, ksiazka.getBookTitle());
            ksiazkiInsertPS.setString(2, ksiazka.getBookAuthor());
            ksiazkiInsertPS.setString(3, ksiazka.getBookWydawnictwo());
            ksiazkiInsertPS.setString(4, ksiazka.getBookISBN());
            ksiazkiInsertPS.setString(5, ksiazka.getBookRokWydania());
            ksiazkiInsertPS.addBatch();
            ksiazkiInsertPS.executeBatch();
        } catch (SQLException e) {
            System.err.println("Blad przy wstawianiu książki");
        }
    }

    public List<User> selectCzytelnicy(String command) {
        List<User> czytelnicy = new LinkedList<>();
        try {
            result = stat.executeQuery(command);
            int id;
            String imie, nazwisko, pesel, miasto, ulica;
            while (result.next()) {
                id = result.getInt("id_czytelnika");
                imie = result.getString("imie");
                nazwisko = result.getString("nazwisko");
                pesel = result.getString("pesel");
                miasto = result.getString("miasto");
                ulica = result.getString("ulica");
                czytelnicy.add(new User(id, imie, nazwisko, pesel, miasto, ulica));
            }
        } catch (SQLException e) {
            System.err.println("Blad przy wybieraniu Czytelników");
        }
        return czytelnicy;
    }

    public List<Book> selectKsiazki(String command) {
        List<Book> ksiazki = new LinkedList<>();
        try {
            result = stat.executeQuery(command);
            int id;
            String tytul, autor, wydawnictwo, isbn, rok_wydania;
            while (result.next()) {
                id = result.getInt("id_ksiazki");
                tytul = result.getString("tytul");
                autor = result.getString("autor");
                isbn = result.getString("isbn");
                rok_wydania = result.getString("rok_wydania");
                wydawnictwo = result.getString("wydawnictwo");
                ksiazki.add(new Book(tytul, autor, isbn, id, wydawnictwo, rok_wydania));
            }
        } catch (SQLException e) {
            System.err.println("Blad przy wybieraniu Książek");
        }
        return ksiazki;

    }

    public List<Wypozyczenie> selectKsiazkiCzytelnika(User czytelnik) {
        List<Wypozyczenie> wypozyczone = new LinkedList<>();
        try {
            result = stat.executeQuery(
                    "select wypozyczenia.id_wypozycz, czytelnicy.imie, czytelnicy.nazwisko,  ksiazki.tytul, ksiazki.autor\n"
                    + "from wypozyczenia \n"
                    + "join czytelnicy on\n"
                    + "wypozyczenia.id_czytelnika=czytelnicy.id_czytelnika\n"
                    + "join ksiazki on\n"
                    + "wypozyczenia.id_ksiazki=ksiazki.id_ksiazki\n"
                    + "where czytelnicy.id_czytelnika='" + czytelnik.getidentyfikator() + "'"
            );
            int id;
            String tytul, autor, imie, nazwisko;
            while (result.next()) {
                id = result.getInt("id_wypozycz");
                tytul = result.getString("tytul");
                autor = result.getString("autor");
                imie = result.getString("imie");
                nazwisko = result.getString("nazwisko");
                wypozyczone.add(new Wypozyczenie(imie, nazwisko, tytul, autor, id));
            }

        } catch (SQLException e) {
            System.err.println("Blad przy wybieraniu wypożyczonych Książek");
        }
        return wypozyczone;

    }

    public List<Wypozyczenie> selectWszszystkieKsiazkiCzytelnikow() {
        List<Wypozyczenie> wypozyczone = new LinkedList<>();
        try {
            result = stat.executeQuery(
                    "select wypozyczenia.id_wypozycz, czytelnicy.imie, czytelnicy.nazwisko,  ksiazki.tytul, ksiazki.autor\n"
                    + "from wypozyczenia \n"
                    + "join czytelnicy on\n"
                    + "wypozyczenia.id_czytelnika=czytelnicy.id_czytelnika\n"
                    + "join ksiazki on\n"
                    + "wypozyczenia.id_ksiazki=ksiazki.id_ksiazki\n"
            );
            int id;
            String tytul, autor, imie, nazwisko;
            while (result.next()) {
                id = result.getInt("id_wypozycz");
                tytul = result.getString("tytul");
                autor = result.getString("autor");
                imie = result.getString("imie");
                nazwisko = result.getString("nazwisko");
                wypozyczone.add(new Wypozyczenie(imie, nazwisko, tytul, autor, id));
            }

        } catch (SQLException e) {
            System.err.println("Blad przy wybieraniu wypożyczonych Książek");
        }
        return wypozyczone;

    }

    public void usunCzytelnik(User czytelnik) {
        try {

            usunCzytelnikPS.setInt(1, czytelnik.getidentyfikator());
            usunCzytelnikPS.addBatch();
            usunCzytelnikPS.executeBatch();
        } catch (SQLException e) {
            System.err.println("Blad przy usuwaniu czytelnika");
        }

    }

    public void usunKsiazka(Book ksiazka) {
        try {

            usunKsiazkaPS.setInt(1, ksiazka.getBookID());
            usunKsiazkaPS.addBatch();
            usunKsiazkaPS.executeBatch();
        } catch (SQLException e) {
            System.err.println("Blad przy usuwaniu książki");
        }

    }

    public void usunWypozyczenie(Wypozyczenie wypozyczenie) {
        try {

            usunWypozyczeniePS.setInt(1, wypozyczenie.getId_wypozyczenia());
            usunWypozyczeniePS.addBatch();
            usunWypozyczeniePS.executeBatch();
        } catch (SQLException e) {
            System.err.println("Blad przy usuwaniu książki");
        }

    }
    
    

//    public void zamien(String baza, String poleZmieniane, String nowaWartosc, String poleSzukane, String wartoscSzukana) {
//        try {
//            String zmienSQL = "UPDATE " + baza + " SET "
//                    + poleZmieniane + " = '" + nowaWartosc
//                    + "' WHERE " + poleSzukane + "=='" + wartoscSzukana + "';";
//
//            ResultSet wynik = stat.executeQuery(zmienSQL);
//            System.out.println("Wynik polecenia:\n" + zmienSQL);
//            wynik.close();
//        } catch (Exception e) {
//            System.out.println("Nie mogę poprawić danych " + e.getMessage());
//        }
//    }
    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println("Problem z zamknieciem polaczenia");
        }
    }
}
