
package biblioteka;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
 
import model.Czytelnik;
import model.Ksiazka;
 
public class Biblioteka {
 
    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:biblioteka.db";
 
    private Connection conn;
    private Statement stat;
 
    public Biblioteka() {
        try {
            Class.forName(Biblioteka.DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Brak sterownika JDBC");
        }
 
        try {
            conn = DriverManager.getConnection(DB_URL);
            stat = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("Problem z otwarciem polaczenia");
        }
 
        createTables();
    }
 
    private void createTables()  {
        String createCzytelnicy = "CREATE TABLE IF NOT EXISTS "
                + "czytelnicy "
                + "(id_czytelnika INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "imie varchar(255), "
                + "nazwisko varchar(255), "
                + "pesel int)";
        String createKsiazki = "CREATE TABLE IF NOT EXISTS "
                + "ksiazki "
                + "(id_ksiazki INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "tytul varchar(255), "
                + "autor varchar(255))";
        String createWypozyczenia = "CREATE TABLE IF NOT EXISTS "
                + "wypozyczenia "
                + "(id_wypozycz INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "id_czytelnika int, "
                + "id_ksiazki int)";
        try {
            stat.execute(createCzytelnicy);
            stat.execute(createKsiazki);
            stat.execute(createWypozyczenia);
        } catch (SQLException e) {
            System.err.println("Blad przy tworzeniu tabeli");
        
        }
    }
 
    public void insertCzytelnik(String imie, String nazwisko, String pesel) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into czytelnicy values (NULL, ?, ?, ?);");
            prepStmt.setString(1, imie);
            prepStmt.setString(2, nazwisko);
            prepStmt.setString(3, pesel);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy wstawianiu czytelnika");
        }
      
    }
 
    public void insertKsiazka(String tytul, String autor) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into ksiazki values (NULL, ?, ?);");
            prepStmt.setString(1, tytul);
            prepStmt.setString(2, autor);
            prepStmt.addBatch();
            prepStmt.executeBatch();
        } catch (SQLException e) {
            System.err.println("Blad przy wypozyczaniu");
        }
    }
 
    public void insertWypozycz(int idCzytelnik, int idKsiazka) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into wypozyczenia values (NULL, ?, ?);");
            prepStmt.setInt(1, idCzytelnik);
            prepStmt.setInt(2, idKsiazka);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy wypozyczaniu");
          
        }
        
    }
 
    public void selectAllCzytelnicy() {
        List<Czytelnik> czytelnicy = new LinkedList<>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM czytelnicy");
            int id;
            String imie, nazwisko, pesel;
            while(result.next()) {
                id = result.getInt("id_czytelnika");
                imie = result.getString("imie");
                nazwisko = result.getString("nazwisko");
                pesel = result.getString("pesel");
                czytelnicy.add(new Czytelnik(id, imie, nazwisko, pesel));
            }
        } catch (SQLException e) {
           System.err.println("Blad przy wybieraniu Czytelników");
        }
        
        System.out.println("Lista wszystkich czytelników: ");
        for(Czytelnik c: czytelnicy)
            System.out.println(c); 
    }
 
    public void selectAllKsiazki() {
        List<Ksiazka> ksiazki = new LinkedList<>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM ksiazki");
            int id;
            String tytul, autor;
            while(result.next()) {
                id = result.getInt("id_ksiazki");
                tytul = result.getString("tytul");
                autor = result.getString("autor");
                ksiazki.add(new Ksiazka(id, tytul, autor));
            }
        } catch (SQLException e) {
            System.err.println("Blad przy wybieraniu Książek");
        }
        System.out.println("Lista wszystkich książek:");
        for(Ksiazka k: ksiazki)
            System.out.println(k);
    }
    
    public void selectInKsiazki(String pole, String wartosc) {
        List<Ksiazka> ksiazki = new LinkedList<>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM ksiazki WHERE "+ pole + "='" + wartosc + "';");
            int id;
            String tytul, autor;
            while(result.next()) {
                id = result.getInt("id_ksiazki");
                tytul = result.getString("tytul");
                autor = result.getString("autor");
                ksiazki.add(new Ksiazka(id, tytul, autor));
            }
        } catch (SQLException e) {
            System.err.println("Blad przy wybieraniu Książek");
        }
        System.out.println("Lista wszystkich książek:");
        for(Ksiazka k: ksiazki)
            System.out.println(k);
    }
    
    
    /**
     *
     * @param baza - tablica
     * @param pole - pole jakie należ wybrać
     * @param wartosc - wartość jaką ma mieć pole do usunięcia
     */
    public void usun(String baza, String pole, String wartosc) {
        try {
            String usunSQL = "DELETE FROM " + baza + " WHERE " + pole +
                    "='" + wartosc + "';";
            ResultSet wynik = stat.executeQuery(usunSQL);
            wynik.close();
        } catch (Exception e){
        //System.out.println("Nie mogę usunąć danych " + e.getMessage());
        }
    }
    
    public void zamien (String baza, String poleZmieniane, String nowaWartosc, String poleSzukane, String wartoscSzukana){
         try {
            String zmienSQL = "UPDATE " + baza + " SET "
                    + poleZmieniane + " = '" + nowaWartosc
                    + "' WHERE " + poleSzukane + "=='" + wartoscSzukana + "';";
 
            ResultSet wynik = stat.executeQuery(zmienSQL);
            System.out.println("Wynik polecenia:\n" + zmienSQL);
            wynik.close();
        } catch (Exception e) {
            System.out.println("Nie mogę poprawić danych " + e.getMessage());
        }
    }
 
    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println("Problem z zamknieciem polaczenia");
        }
    }
}
