package View;


import controller.DataBaseController;
import controller.Logic;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Book;
import model.User;
import model.Wypozyczenie;

/**
 *
 * @author KrzysieK Sz.
 */
public final class View {

    ObservableList<User> userList;
    ObservableList<Book> bookList;
    ObservableList dataUserBook;
    BorderPane border, border2, border3;
    Scene scene, scene2, popup, popup2;
    Stage stage, newStage, newStage2;
    TabPane tabPane, tabMenu;
    Tab tab;
    Text sceneTitle, komunikat;
    Label userName, userLastName, userPESEL, userTown, userStr,
            bookName, wydawnictwoName, bookIssueDate, bookAuthor, bookISBN, bookAvailable;
    TextField userNameTextField, userLastNameTextField, userPESELTextField, userTownTextField, userStrTextField, 
            bookNameTextField, wydawnictwoNameTextField, bookIssueDateTextField, bookAuthorTextField, bookISBNTextField, bookAvailableTextField;
    Button userSzukajBtn, userDodajBtn, userUsunBtn, userClearBtn, userShowBooks, okZamknij,wyjscieProgram,
            bookSzukajBtn, bookUsunBtn, bookDodajBtn, bookClearBtn, bookWypozyczBtn, 
            usunUserBookBtn, wypozyczoneBookBtn, chooseUserButton, chooseBookButton, addLendFactButton, usunSelectedLent;
    VBox uklad, tabUserBooks;
    HBox poziomePrzyciski;
    GridPane grid;
    TableView tabelaUser, tabelaBook, tableUserBooks;
    TableColumn nameCol, lastNameCol, peselCol, miastoCol, ulicaCol,
            bookNameCol, bookAuthorCol, wydawnictwoCol, isbnCol, bookYearCol,bookAvailableCol,
            userBookNameCol, userBookAuthorCol, userBookLastNameCol, userBookTitleCol, userIDWypozyczeniaCol, userBookStatusCol;
    
    User chosenUser;
    Book chosenBook;
    Wypozyczenie chosenLent;
    
    public View() {
        this.userList = FXCollections.observableArrayList();
        this.bookList = FXCollections.observableArrayList();
        this.dataUserBook = FXCollections.observableArrayList();
        chosenUser = null;
        chosenBook = null;
        chosenLent = null;
    }

    //@Override
    public void start(Stage stage) {
        
        
        this.stage = stage;
        

        border = new BorderPane();
        border.setCenter(
                createTabPane()
        );
        scene = new Scene(border, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Aplikacja Biblioteka");
        stage.show();
       

        border2 = new BorderPane();
        border2.setCenter(
                komunikat = new Text("mniam")
                
      
        );
        //border2.setStyle("-fx-background-color:green;-fx-padding:10px;");
        popup = new Scene(border2, 500, 200);
        newStage = new Stage(); //nowe okno
        newStage.setScene(popup);
        newStage.setTitle("Informacja");
        newStage.initModality(Modality.APPLICATION_MODAL);

    }

    private TabPane createTabPane() {
        tabPane = new TabPane();
        tabPane.getTabs().addAll(
                manageCzytelnikTab(),
                manageBookTab(),
                createUserBooks()
        );
        
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        
        
        return tabPane;
        
    }

//zakladka Zarzadzanie ksiazkami
    private Tab manageBookTab() {
        tab = new Tab("Zarządzanie Książkami");

        bookName = new Label("Tytł:");
        wydawnictwoName = new Label("Wydawnictwo:");
        bookIssueDate = new Label("Rok wydania:");
        bookAuthor = new Label("Autor:");
        bookISBN = new Label("ISBN:");
        bookAvailable = new Label("Stan:");

        bookNameTextField = new TextField();
        wydawnictwoNameTextField = new TextField();
        bookIssueDateTextField = new TextField();
        bookAuthorTextField = new TextField();
        bookISBNTextField = new TextField();
        bookAvailableTextField = new TextField();
                
        bookSzukajBtn = new Button("Szukaj");
        bookDodajBtn = new Button("Dodaj");
        bookUsunBtn = new Button("Usuń");
       // Button buttonWyjscie = new Button("Zamknij program");
        bookClearBtn = new Button("Wyczyść");
        
        chooseBookButton = new Button("Wybierz książkę by wypożyczyć");

        bookSzukajBtn.setOnAction(new ButtonActions());
        bookDodajBtn.setOnAction(new ButtonActions());
        bookClearBtn.setOnAction(new ButtonActions());
        bookUsunBtn.setOnAction(new ButtonActions());
        //buttonWyjscie.setOnAction ( e -> wyjscieProgram());
        chooseBookButton.setOnAction(new ButtonActions());
        

        uklad = new VBox(8);
        uklad.setPadding(new Insets(10, 10, 10, 10));

        sceneTitle = new Text("Dane Książki");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        poziomePrzyciski = new HBox();
        poziomePrzyciski.setSpacing(10);
        poziomePrzyciski.setAlignment(Pos.CENTER);
        poziomePrzyciski.getChildren().addAll(
                bookSzukajBtn,
                bookDodajBtn,
                bookClearBtn,
                
                chooseBookButton
        );
        

        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(5);
        grid.add(bookName, 0, 1);
        grid.add(bookNameTextField, 0, 2);
        grid.add(bookAuthor, 1, 1);
        grid.add(bookAuthorTextField, 1, 2);
        grid.add(wydawnictwoName, 2, 1);
        grid.add(wydawnictwoNameTextField, 2, 2);
        grid.add(bookISBN, 0, 4);
        grid.add(bookISBNTextField, 0, 5);
        grid.add(bookAvailable, 0, 6);
        grid.add(bookAvailableTextField,0,7);
        
        grid.add(bookIssueDate, 1, 4);
        grid.add(bookIssueDateTextField, 1, 5);
        grid.add(poziomePrzyciski, 2, 5);

        uklad.getChildren().addAll(
                sceneTitle,
                grid,
                createBookTableView(),
                bookUsunBtn
                //buttonWyjscie
        );
        tab.setContent(uklad);
        return tab;
    }

    private Tab manageCzytelnikTab() {
        tab = new Tab("Zarządzanie Czytelnikami");

        userName = new Label("Imię:");
        userLastName = new Label("Nazwisko:");
        userPESEL = new Label("PESEL:");
        userTown = new Label("Miasto:");
        userStr = new Label("Ulica:");

        userNameTextField = new TextField();
        userLastNameTextField = new TextField();
        userPESELTextField = new TextField();
        userTownTextField = new TextField();
        userStrTextField = new TextField();
        

        userSzukajBtn = new Button("Szukaj");
        userDodajBtn = new Button("Dodaj");
        userUsunBtn = new Button("Usuń");
        
        userClearBtn = new Button("Wyczyść");
        userShowBooks = new Button("Książki Czytelnika");
        
        chooseUserButton = new Button("Wybierz Czytelnika by wypożyczyć");
    

        userSzukajBtn.setOnAction(new ButtonActions());
        userDodajBtn.setOnAction(new ButtonActions());
        userUsunBtn.setOnAction(new ButtonActions());
        userClearBtn.setOnAction(new ButtonActions());
        userShowBooks.setOnAction(new ButtonActions());
        
        chooseUserButton.setOnAction(new ButtonActions());
                
        uklad = new VBox(8);
        uklad.setPadding(new Insets(10, 10, 10, 10));

        sceneTitle = new Text("Dane Czytelnika");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        poziomePrzyciski = new HBox();
        poziomePrzyciski.setSpacing(10);
        poziomePrzyciski.setAlignment(Pos.CENTER);
        poziomePrzyciski.getChildren().addAll(
                userSzukajBtn,
                userDodajBtn,
                userClearBtn,
                chooseUserButton
        );

        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(5);
        grid.add(userName, 0, 1);
        grid.add(userNameTextField, 0, 2);
        grid.add(userLastName, 1, 1);
        grid.add(userLastNameTextField, 1, 2);
        grid.add(userPESEL, 2, 1);
        grid.add(userPESELTextField, 2, 2);
        grid.add(userTown, 0, 4);
        grid.add(userTownTextField, 0, 5);
        grid.add(userStr, 1, 4);
        grid.add(userStrTextField, 1, 5);
        grid.add(poziomePrzyciski, 2, 4);
        grid.add(userShowBooks, 2, 5, 3, 1);
        
        
        uklad.getChildren().addAll(
                sceneTitle,
                grid,
                createUserTableView(),
                userUsunBtn
               // buttonWyjscie
        );
        tab.setContent(uklad);
        return tab;
    }
//zarzadzanie czytelnikami
    private TableView createUserTableView() {
        tabelaUser = new TableView();
        tabelaUser.setEditable(true);

        nameCol = new TableColumn("Imię");
        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("imie"));

        lastNameCol = new TableColumn("Nazwisko");
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<>("nazwisko"));

        peselCol = new TableColumn("PESEL");
        peselCol.setMaxWidth(100);
        peselCol.setCellValueFactory(
                new PropertyValueFactory<>("pesel"));

        miastoCol = new TableColumn("Miasto");
        miastoCol.setCellValueFactory(
                new PropertyValueFactory<>("miasto"));

        ulicaCol = new TableColumn("Ulica");
        ulicaCol.setMinWidth(200);
        ulicaCol.setCellValueFactory(
                new PropertyValueFactory<>("ulica"));

        tabelaUser.getColumns().setAll(
                nameCol,
                lastNameCol,
                peselCol,
                miastoCol,
                ulicaCol
        );
        tabelaUser.setItems(userList);
        return tabelaUser;
    }
//zarzadzenie ksiazkami
    private TableView createBookTableView() {
        tabelaBook = new TableView();
        tabelaBook.setEditable(true);

        bookNameCol = new TableColumn("Tytuł");
        bookNameCol.setMinWidth(150);
        bookNameCol.setCellValueFactory(
                new PropertyValueFactory<>("bookTitle"));

        bookAuthorCol = new TableColumn("Autor");
        bookAuthorCol.setCellValueFactory(
                new PropertyValueFactory<>("bookAuthor"));

        wydawnictwoCol = new TableColumn("Wydawnictwo");
        wydawnictwoCol.setMinWidth(250);
        wydawnictwoCol.setCellValueFactory(
                new PropertyValueFactory<>("bookWydawnictwo"));

        isbnCol = new TableColumn("ISBN");
        isbnCol.setCellValueFactory(
                new PropertyValueFactory<>("bookISBN"));

        bookYearCol = new TableColumn("Rok Wydania");
        bookYearCol.setMinWidth(100);
        bookYearCol.setCellValueFactory(
                new PropertyValueFactory<>("bookRokWydania"));
        
        bookAvailableCol = new TableColumn("Stan");
        bookAvailableCol.setCellValueFactory(
                new PropertyValueFactory<>("bookStan"));

        tabelaBook.getColumns().setAll(
                bookNameCol,
                bookAuthorCol,
                wydawnictwoCol,
                isbnCol,
                bookYearCol,
                bookAvailableCol
                
        );
        tabelaBook.setItems(bookList);
        return tabelaBook;
    }
    //walidacja pola rok 
//    private static boolean czyLiczba(TextField input){
//        
//           try{
//                int userId = Integer.parseInt(input.getText());
//                
//                return true;
//            }catch (NumberFormatException e){
//                System.out.println("Wpisana wartość nie jest liczba!!");
//                return false;
//            }
//         
//        }
    // obsługa przycisku wyjścia z programu
    /*private void wyjscieProgram(){
        Boolean potwierdzenie = ConfirmBox.display("Display","Czy napewno chcesz zamknąć program? ");
        if (potwierdzenie )
            stage.close();
    }*/

    private Tab createUserBooks() {
        tab = new Tab("Zarządzanie Książkami Użytkowników");
        
        tableUserBooks = new TableView();

        final Label label = new Label("Address Book");
        label.setFont(new Font("Arial", 20));

        tableUserBooks.setEditable(true);

        userBookNameCol = new TableColumn("Imię");
        userBookAuthorCol = new TableColumn("Autor");
        userBookLastNameCol = new TableColumn("Nazwisko");
        userBookTitleCol = new TableColumn("Tytuł");
        userBookTitleCol.setMinWidth(200);
        userIDWypozyczeniaCol = new TableColumn("Numer wypożyczenia"); 
        userBookStatusCol = new TableColumn("Status wypożyczenia"); 
        userIDWypozyczeniaCol.setMinWidth(200);

        userBookNameCol.setCellValueFactory(
                new PropertyValueFactory<>("imie")
        );
        userBookLastNameCol.setCellValueFactory(
                new PropertyValueFactory<>("nazwisko")
        );
        userBookAuthorCol.setCellValueFactory(
                new PropertyValueFactory<>("autor")
        );
        userBookTitleCol.setCellValueFactory(
                new PropertyValueFactory<>("tytul")
        );
        userIDWypozyczeniaCol.setCellValueFactory(
                new PropertyValueFactory<>("id_wypozyczenia")
        );
        userBookStatusCol.setCellValueFactory(
                new PropertyValueFactory<>("status")
        );

        tableUserBooks.setItems(dataUserBook);

        tableUserBooks.getColumns().addAll(
                userIDWypozyczeniaCol, 
                userBookNameCol, 
                userBookLastNameCol, 
                userBookAuthorCol, 
                userBookTitleCol,
                userBookStatusCol
        );
        
        usunSelectedLent = new Button("Zwrot książki");
        usunSelectedLent.setOnAction(new ButtonActions());
        addLendFactButton = new Button("Dodaj wypożyczenie");
        addLendFactButton.setOnAction(new ButtonActions());
        wypozyczoneBookBtn = new Button("Wszystkie wypożyczone książki");
        wypozyczoneBookBtn.setOnAction(new ButtonActions());
        
        sceneTitle = new Text("Wypożyczone Książki");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        
        tabUserBooks  = new VBox(8);
        tabUserBooks.setPadding(new Insets(10, 10, 10, 10));
        
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(5);
        grid.add(addLendFactButton, 0, 1);
        grid.add(wypozyczoneBookBtn, 1, 1);
        grid.add(usunSelectedLent, 2, 1);
        
        tabUserBooks.setSpacing(10);
        tabUserBooks.setAlignment(Pos.CENTER);
       
        tabUserBooks.getChildren().addAll(
                sceneTitle,
                grid,
                tableUserBooks
               
        );
        tab.setContent(tabUserBooks);
        return tab;
    }

    private class ButtonActions implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {
            komunikat.wrappingWidthProperty();
            komunikat.setWrappingWidth(150);
            
            if (userUsunBtn == e.getSource()) {

                if (tabelaUser.getSelectionModel().getSelectedIndex() >= 0) {
                    User selectedUser = (User) tabelaUser.getSelectionModel().getSelectedItem();

                    DataBaseController db = new DataBaseController();
                    db.usunCzytelnik(selectedUser);
                    userList.remove(selectedUser);
                    komunikat.setText("Został usunięty czytelnik: \n"
                            + selectedUser.toString());
                    newStage.showAndWait();
                }
                tabelaUser.getSelectionModel().clearSelection();

            }


            if (userDodajBtn == e.getSource()) {
                DataBaseController db = new DataBaseController();
                User newUser = new User(
                        userNameTextField.getText(),
                        userLastNameTextField.getText(),
                        userPESELTextField.getText(),
                        userTownTextField.getText(),
                        userStrTextField.getText()
                );
                userList.add(newUser);
                db.insertCzytelnik(newUser);
                userNameTextField.clear();
                userLastNameTextField.clear();
                userPESELTextField.clear();
                userTownTextField.clear();
                userStrTextField.clear();
                komunikat.setText("został dodany użytkownik");
                newStage.showAndWait();
            }

            if (userSzukajBtn == e.getSource()) {
                User user = new User(
                        userNameTextField.getText(),
                        userLastNameTextField.getText(),
                        userPESELTextField.getText(),
                        userTownTextField.getText(),
                        userStrTextField.getText()
                );

                Logic logic = new Logic();
                List<User> czytelnicy = logic.selectCzytelnicy(user);
                userList.clear();
                for (User czytelnik : czytelnicy) {
                    userList.add(czytelnik);
                }
            }

            if (bookUsunBtn == e.getSource()) {

                if (tabelaBook.getSelectionModel().getSelectedIndex() >= 0) {
                    Book selectedBook = (Book) tabelaBook.getSelectionModel().getSelectedItem();
                    DataBaseController db = new DataBaseController();
                    db.usunKsiazka(selectedBook);
                    bookList.remove(selectedBook);

                    komunikat.setText("Została usunięta ksiazka o parametrach: \n"
                            + selectedBook.toString());
                    newStage.showAndWait();
                }
                tabelaBook.getSelectionModel().clearSelection();
            }
            

    
            if (bookDodajBtn == e.getSource()) {
                DataBaseController db = new DataBaseController();
                Book newBook = new Book(
                        bookNameTextField.getText(),
                        bookAuthorTextField.getText(),
                        bookISBNTextField.getText(),
                        //Integer.parseInt(bookISBNTextField.getText())
                        wydawnictwoNameTextField.getText(),
                        bookIssueDateTextField.getText(),
                        bookAvailableTextField.getText()
                );
                bookList.add(newBook);
                db.insertKsiazka(newBook);
                bookNameTextField.clear();
                wydawnictwoNameTextField.clear();
                bookIssueDateTextField.clear();
                bookAuthorTextField.clear();
                bookISBNTextField.clear();
                komunikat.setText("została dodana książka");
                newStage.showAndWait();
            }
            

            if (bookSzukajBtn == e.getSource()) {
                Book newBook = new Book(
                        bookNameTextField.getText(),
                        bookAuthorTextField.getText(),
                        bookISBNTextField.getText(),
                        wydawnictwoNameTextField.getText(),
                        bookIssueDateTextField.getText(),
                        bookAvailableTextField.getText()
                );

                Logic logic = new Logic();
                List<Book> ksiazki = logic.selectKsiazki(newBook);
                bookList.clear();
                for (Book ksiazka : ksiazki) {
                    bookList.add(ksiazka);
                }
            }
            if (userClearBtn == e.getSource()) {
                userNameTextField.clear();
                userLastNameTextField.clear();
                userPESELTextField.clear();
                userTownTextField.clear();
                userStrTextField.clear();
                tabelaUser.getSelectionModel().clearSelection();
            }

            if (bookClearBtn == e.getSource()) {
                bookNameTextField.clear();
                wydawnictwoNameTextField.clear();
                bookIssueDateTextField.clear();
                bookAuthorTextField.clear();
                bookISBNTextField.clear();
                tabelaBook.getSelectionModel().clearSelection();
            }
            if (userShowBooks == e.getSource()) {
                if (tabelaUser.getSelectionModel().getSelectedIndex() >= 0) {
                    dataUserBook.clear();

                    User selectedUser = (User) tabelaUser.getSelectionModel().getSelectedItem();

                    DataBaseController db = new DataBaseController();
                    List<Wypozyczenie> wypozyczone = db.selectKsiazkiCzytelnika(selectedUser);

                    for (Wypozyczenie wypozyczenie : wypozyczone) {
                        dataUserBook.add(wypozyczenie);
                        
                    }
                    if (dataUserBook.isEmpty()) {
                        komunikat.setText("Brak wypożyczonych książek przez użytkownika");
                    } else {
                        komunikat.setText("Dane zostały pobrane dla: \n"
                                + selectedUser.getImie() + " " + selectedUser.getNazwisko());
                    }

                    newStage.showAndWait();
                }
                tabelaUser.getSelectionModel().clearSelection();
            }
              
            if (usunUserBookBtn == e.getSource()) {

                if (tableUserBooks.getSelectionModel().getSelectedIndex() >= 0) {
                    Wypozyczenie selectedBook = (Wypozyczenie) tableUserBooks.getSelectionModel().getSelectedItem();
                    
                    DataBaseController db = new DataBaseController();
                    db.usunWypozyczenie(selectedBook);
                    dataUserBook.remove(selectedBook);

                    komunikat.setText("Została usunięta ksiazka o parametrach: \n"
                            + selectedBook.toString());
                    newStage.showAndWait();
                }
                tableUserBooks.getSelectionModel().clearSelection();
            }
            
            if (wypozyczoneBookBtn == e.getSource()) {
                dataUserBook.clear();
                
                DataBaseController db = new DataBaseController();
                List<Wypozyczenie> wypozyczone = db.selectWszszystkieKsiazkiCzytelnikow();
                

                    for (Wypozyczenie wypozyczenie : wypozyczone) {
                        dataUserBook.add(wypozyczenie);
                        
                
                }
            }
            
            if (chooseUserButton == e.getSource()) {
                if (tabelaUser.getSelectionModel().getSelectedIndex() >= 0) 
                    chosenUser = (User) tabelaUser.getSelectionModel().getSelectedItem();
            }
            
            if (chooseBookButton == e.getSource()) {
                if (tabelaBook.getSelectionModel().getSelectedIndex() >= 0) 
                    chosenBook = (Book) tabelaBook.getSelectionModel().getSelectedItem();
            }
            
            if(addLendFactButton == e.getSource()){
                if(chosenUser != null && chosenBook != null){
                    DataBaseController db = new DataBaseController();
                    db.insertWypozyczenieKS(chosenUser.getidentyfikator(), chosenBook.getBookID());
                }
            }
            
            if (usunSelectedLent == e.getSource()) {

                if (tableUserBooks.getSelectionModel().getSelectedIndex() >= 0) {
                    chosenLent = (Wypozyczenie) tableUserBooks.getSelectionModel().getSelectedItem();
                    
                    DataBaseController db = new DataBaseController();
                    db.usunWypozyczenieKS(chosenLent);
                    //dataUserBook.remove(chosenLent);
                    
                }
                //tableUserBooks.getSelectionModel().clearSelection();
              
            }
        }
    }

}
