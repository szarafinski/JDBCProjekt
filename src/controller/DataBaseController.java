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
    ResultSet result;

    public DataBaseController() {
        try {
            Class.forName(DataBaseController.DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Brak sterownika JDBC");
        }

        try {
            conn = DriverManager.getConnection(DB_URL);
            stat = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("Problem z otwarciem polaczenia");
        }

    }

//    private void createTables() {
//        String createCzytelnicy = "CREATE TABLE IF NOT EXISTS "
//                + "czytelnicy "
//                + "(id_czytelnika INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + "imie varchar(255), "
//                + "nazwisko varchar(255), "
//                + "pesel int)";
//        String createKsiazki = "CREATE TABLE IF NOT EXISTS "
//                + "ksiazki "
//                + "(id_ksiazki INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + "tytul varchar(255), "
//                + "autor varchar(255))";
//        String createWypozyczenia = "CREATE TABLE IF NOT EXISTS "
//                + "wypozyczenia "
//                + "(id_wypozycz INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + "id_czytelnika int, "
//                + "id_ksiazki int)";
//        try {
//            stat.execute(createCzytelnicy);
//            stat.execute(createKsiazki);
//            stat.execute(createWypozyczenia);
//        } catch (SQLException e) {
//            System.err.println("Blad przy tworzeniu tabeli");
//
//        }
//    }
    public void insertCzytelnik(User czytelnik) {
        try {
            prepStmt = conn.prepareStatement(
                    "INSERT INTO czytelnicy VALUES (NULL, ?, ?, ?, ?, ?);");
            prepStmt.setString(1, czytelnik.getImie());
            prepStmt.setString(2, czytelnik.getNazwisko());
            prepStmt.setString(3, czytelnik.getPesel());
            prepStmt.setString(4, czytelnik.getMiasto());
            prepStmt.setString(5, czytelnik.getUlica());
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Błąd przy wstawianiu czytelnika");
        }

    }

    public void insertKsiazka(Book ksiazka) {
        try {
            prepStmt = conn.prepareStatement(
                    "INSERT INTO ksiazki VALUES (NULL, ?, ?, ?, ?, ?);");
            prepStmt.setString(1, ksiazka.getBookTitle());
            prepStmt.setString(2, ksiazka.getBookAuthor());
            prepStmt.setString(3, ksiazka.getBookWydawnictwo());
            prepStmt.setString(4, ksiazka.getBookISBN());
            prepStmt.setString(5, ksiazka.getBookRokWydania());
            prepStmt.addBatch();
            prepStmt.executeBatch();
        } catch (SQLException e) {
            System.err.println("Blad przy wstawianiu książki");
        }
    }

//    public void insertWypozycz(int idCzytelnik, int idKsiazka) {
//        try {
//            prepStmt = conn.prepareStatement(
//                    "insert into wypozyczenia values (NULL, ?, ?);");
//            prepStmt.setInt(1, idCzytelnik);
//            prepStmt.setInt(2, idKsiazka);
//            prepStmt.execute();
//        } catch (SQLException e) {
//            System.err.println("Blad przy wypozyczaniu");
//
//        }
//
//    }
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

    /* select wypożyczenia:
    
     select czytelnicy.imie, czytelnicy.nazwisko,  ksiazki.tytul
     from wypozyczenia 
     join czytelnicy on
     wypozyczenia.id_czytelnika=czytelnicy.id_czytelnika
     join ksiazki on
     wypozyczenia.id_ksiazki=ksiazki.id_ksiazki
     */
    
    public void usunCzytelnik(User czytelnik) {
        try {
            prepStmt = conn.prepareStatement(
                    "DELETE FROM czytelnicy WHERE id_czytelnika = ?");
            prepStmt.setInt(1, czytelnik.getidentyfikator());
            prepStmt.addBatch();
            prepStmt.executeBatch();
        } catch (SQLException e) {
            System.err.println("Blad przy usuwaniu czytelnika");
        }

    }

    public void usunKsiazka(Book ksiazka) {
        try {
            prepStmt = conn.prepareStatement(
                    "DELETE FROM ksiazki WHERE id_ksiazki = ?");
            prepStmt.setInt(1, ksiazka.getBookID());
            prepStmt.addBatch();
            prepStmt.executeBatch();
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
