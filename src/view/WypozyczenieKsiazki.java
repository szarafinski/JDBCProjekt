package View;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class WypozyczenieKsiazki {

    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:baza.db";
    
    private Connection conn;
    private Statement stat;
    private PreparedStatement prepStmt;
    
    private PreparedStatement pobierzIdUzytkownika;


    StringProperty title = new SimpleStringProperty();
    
    
    public static String display(String dane)
    {
    
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Wypożyczenie książek");
        window.setMinWidth(250);
        PreparedStatement prepStmt;
   
        //     Connection con;
    // PreparedStatement stmt;

       Text text1 = new Text(20, 20, "Wpisz id użytkownika:");
       Text text2 = new Text(20, 20, "Wpisz id ksiazki:");

        TextField user = new TextField();
        TextField book = new TextField();
        
        Button zapisz = new Button("Zapisz dane");
        Button wypozycz = new Button("Wypożycz książkę");
        
        zapisz.setOnAction( e -> isInt( user ));
        zapisz.setOnAction( e -> isInt( book ));
        
        //wypozycz.setOnAction(e -> insertWypozycz(user,book));
          



       VBox layout = new VBox(10);
       layout.setPadding(new Insets(20,20,20,20));
       layout.getChildren().addAll(text1, user,text2,book, zapisz, wypozycz);
       
//        
       Scene scene = new Scene(layout,400,400);
       window.setScene(scene);
       window.show();
        
       return dane;
    }
// sprawdzenie czy nr id jest liczba
         private static boolean isInt(TextField input){
        
           try{
                int userId = Integer.parseInt(input.getText());
                System.out.println("user Id: " + userId);
                return true;
            }catch (NumberFormatException e){
                System.out.println("Wpisana wartość nie jest liczba!!");
                return false;
            }
         
        }
        
    /**
     *
//     * @param idCzytelnik
//     * @param idKsiazka
//     */
//    public insertWypozycz()  {
//            
//        try {
//               
//            Class.forName(DataBaseController.DRIVER);
//            conn = DriverManager.getConnection(DB_URL);
//            stat = conn.createStatement();
//            
//            pobierzIdUzytkownika= conn.prepareStatement(
//                    "insert into wypozyczenia (id_czytelnika, id_ksiazki) values (null, ?, ?);");
                    
                    
            
            
            
//            int userId = Integer.parseInt(idCzytelnik.getText());
//            int bookId = Integer.parseInt(idKsiazka.getText());

//            String dodajSQL = "insert into wypozyczenia (id_czytelnika, id_ksiazki) values (null, ?, ?);";
//            
//            ResultSet wynik = stat.executeQuery(dodajSQL);
//            System.out.println(dodajSQL);
//            while (wynik.next()){
//                int id = wynik.getInt("id");
            }
            
           
//           return polaczenie; 
//        } 
//       
//        catch (SQLException e) {
//            System.err.println("Blad przy wypozyczaniu");
//            System.out.println(e);
//
//        } 
//return polaczenie;
//    }

         
        
         
//            public static void insertWypozycz( TextField user, TextField book ) throws ClassNotFoundException {
//                Connection con = null ;
//                Statement stmt ;
//        try {
//            
//           // Class.forName("org.sqlite.JDBC");
//           // /con = DriverManager.getConnection("jdbc:sqlite:baza.db");
//          //  stmt = con.createStatement();
//           PreparedStatement szukanie;
//                    szukanie = con.prepareStatement(
////                    "select czytelnicy.id_czytelnika , ksiazki.id_ksiazki   FROM czytelnicy, ksiazki ");
//                            "INSERT into wypozyczenia values (null,?,?);");
//           
//            szukanie.setString(1,user.getText());
//            szukanie.setString(2,book.getText());
//            
//            szukanie.addBatch();
//            szukanie.executeBatch();
//            
//            //return 
//
//            
//        } 
//       
//        catch (SQLException e) {
//            System.err.println("Blad przy wypozyczaniu");
////            System.out.println(e);
//
//        }
//            }
 

 
   
         
 
 