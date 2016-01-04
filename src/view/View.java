package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Book;
import model.User;
import model.Wypozyczenie;

/**
 *
 * @author KrzysieK Sz.
 */
public final class View {

    static ObservableList dataUserBooks;
    ObservableList<User> userList;
    ObservableList<Book> bookList;
    static TabPane tabPane = new TabPane();

    static User chosenUser;
    static Book chosenBook;
    static Wypozyczenie chosenLent;

    public View() {
        View.dataUserBooks = FXCollections.observableArrayList();
        this.userList = FXCollections.observableArrayList();
        this.bookList = FXCollections.observableArrayList();
        
        chosenUser = null;
        chosenBook = null;
        chosenLent = null;
    }

    //@Override
    public void start(Stage stage) {

        BorderPane border = new BorderPane();
        border.setCenter(
                createTabPane()
        );
        Scene scene = new Scene(border, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Aplikacja Biblioteka");
        stage.show();
    }

    private TabPane createTabPane() {
        tabPane.getTabs().addAll(
                new ManageUsers("Zarzadzanie Czytelnikami"),
                new ManageBooks("Zarządzanie Książkami"),
                new ManageUsersBooks("Książki Czytelnika")
        );
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        return tabPane;
    }

}
