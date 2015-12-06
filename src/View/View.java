
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

/**
 *
 * @author KrzysieK Sz.
 */
public final class View {

    ObservableList<User> data;
    ObservableList<Book> dataBook;
    BorderPane border, border2;
    Scene scene, popup;
    Stage stage, newStage;
    TabPane tabPane;
    Tab tab;
    Text sceneTitle, komunikat;
    Label userName, userLastName, userPESEL, userTown, userStr,
            bookName, wydawnictwoName, bookIssueDate, bookAuthor, bookISBN;
    TextField userNameTextField, userLastNameTextField, userPESELTextField, userTownTextField, userStrTextField,
            bookNameTextField, wydawnictwoNameTextField, bookIssueDateTextField, bookAuthorTextField, bookISBNTextField;
    Button userSzukajBtn, userDodajBtn, userUsunBtn, okZamknij,
            bookSzukajBtn, bookUsunBtn, bookDodajBtn;
    VBox uklad;
    HBox poziomePrzyciski;
    GridPane grid;
    TableView tabelaUser, tabelaBook;
    TableColumn nameCol, lastNameCol, peselCol, miastoCol, ulicaCol,
            bookNameCol, bookAuthorCol, wydawnictwoCol, isbnCol, bookYearCol;

    public View() {
        this.data = FXCollections.observableArrayList();
        this.dataBook = FXCollections.observableArrayList();
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
                createMniam()
        );
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        return tabPane;
    }

    private Tab manageBookTab() {
        tab = new Tab("Zarządzanie Książkami");

        bookName = new Label("Tytł:");
        wydawnictwoName = new Label("Wydawnictwo:");
        bookIssueDate = new Label("Rok wydania:");
        bookAuthor = new Label("Autor:");
        bookISBN = new Label("ISBN:");

        bookNameTextField = new TextField();
        wydawnictwoNameTextField = new TextField();
        bookIssueDateTextField = new TextField();
        bookAuthorTextField = new TextField();
        bookISBNTextField = new TextField();

        bookSzukajBtn = new Button("Szukaj");
        bookDodajBtn = new Button("Dodaj");
        bookUsunBtn = new Button("Usuń");

        bookSzukajBtn.setOnAction(new ButtonActions());
        bookDodajBtn.setOnAction(new ButtonActions());
        bookUsunBtn.setOnAction(new ButtonActions());

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
                bookUsunBtn
        );

        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(5);
        //grid.add(sceneTitle, 0, 0, 2, 1);
        grid.add(bookName, 0, 1);
        grid.add(bookNameTextField, 0, 2);
        grid.add(bookAuthor, 1, 1);
        grid.add(bookAuthorTextField, 1, 2);
        grid.add(wydawnictwoName, 2, 1);
        grid.add(wydawnictwoNameTextField, 2, 2);
        grid.add(bookISBN, 0, 4);
        grid.add(bookISBNTextField, 0, 5);
        grid.add(bookIssueDate, 1, 4);
        grid.add(bookIssueDateTextField, 1, 5);
        grid.add(poziomePrzyciski, 2, 5);

        uklad.getChildren().addAll(
                sceneTitle,
                grid,
                createBookTableView()
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

        userSzukajBtn.setOnAction(new ButtonActions());
        userDodajBtn.setOnAction(new ButtonActions());
        userUsunBtn.setOnAction(new ButtonActions());

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
                userUsunBtn
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
        grid.add(poziomePrzyciski, 2, 5);
        uklad.getChildren().addAll(
                sceneTitle,
                grid,
                createUserTableView()
        );
        tab.setContent(uklad);
        return tab;
    }

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
        tabelaUser.setItems(data);
        return tabelaUser;
    }

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

        tabelaBook.getColumns().setAll(
                bookNameCol,
                bookAuthorCol,
                wydawnictwoCol,
                isbnCol,
                bookYearCol
        );
        tabelaBook.setItems(dataBook);
        return tabelaBook;
    }

    private Tab createMniam() {
        tab = new Tab("Zarządzanie Książkami");
        TableView table = new TableView();

        final Label label = new Label("Address Book");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        nameCol = new TableColumn("Nazwisko");
        lastNameCol = new TableColumn("Nazwisko");
        peselCol = new TableColumn("PESEL");

        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("imie")
        );
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<>("nazwisko")
        );
        peselCol.setCellValueFactory(
                new PropertyValueFactory<>("kupa")
        );

        nameCol.setMinWidth(100);
        lastNameCol.setMinWidth(100);
        peselCol.setMinWidth(200);

        table.setItems(data);

        table.getColumns().addAll(nameCol, lastNameCol, peselCol);
        tab.setContent(table);
        return tab;
    }

    private class ButtonActions implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {
            komunikat.wrappingWidthProperty();
            komunikat.setWrappingWidth(150);
            if (userUsunBtn == e.getSource()) {

                if (tabelaUser.getSelectionModel().getSelectedIndex() > 0) {
                    User selectedUser = (User) tabelaUser.getSelectionModel().getSelectedItem();

                    DataBaseController db = new DataBaseController();
                    db.usunCzytelnik(selectedUser);
                    data.remove(selectedUser);
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
                data.add(newUser);
                db.insertCzytelnik(newUser);
                userNameTextField.clear();
                userLastNameTextField.clear();
                userPESELTextField.clear();
                userTownTextField.clear();
                userStrTextField.clear();
                komunikat.setText("został doadny użytkownik");
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
                data.clear();
                for (User czytelnik : czytelnicy) {
                    data.add(czytelnik);
                }
            }

            if (bookUsunBtn == e.getSource()) {

                if (tabelaBook.getSelectionModel().getSelectedIndex() > 0) {
                    Book selectedBook = (Book) tabelaBook.getSelectionModel().getSelectedItem();
                    DataBaseController db = new DataBaseController();
                    db.usunKsiazka(selectedBook);
                    dataBook.remove(selectedBook);

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
                        bookIssueDateTextField.getText()
                

                );
                dataBook.add(newBook);
                db.insertKsiazka(newBook);
                bookNameTextField.clear();
                wydawnictwoNameTextField.clear();
                bookIssueDateTextField.clear();
                bookAuthorTextField.clear();
                bookISBNTextField.clear();
                komunikat.setText("została doadna książka");
                newStage.showAndWait();
            }

            if (bookSzukajBtn == e.getSource()) {
                Book newBook = new Book(
                        bookNameTextField.getText(),
                        bookAuthorTextField.getText(),
                        bookISBNTextField.getText(),
                        //Integer.parseInt(bookISBNTextField.getText())
                        wydawnictwoNameTextField.getText(),
                        bookIssueDateTextField.getText()
                
                );

                Logic logic = new Logic();
                List<Book> ksiazki = logic.selectKsiazki(newBook);
                dataBook.clear();
                for (Book ksiazka : ksiazki) {
                    dataBook.add(ksiazka);
                }
            }

        }
    }

}
