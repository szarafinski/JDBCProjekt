package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
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

    static ObservableList dataUserBooks;
    BorderPane border, border2, border3;
    Scene scene, scene2, popup, popup2;
    Stage stage, newStage, newStage2;
    static TabPane tabPane = new TabPane();
    Tab ManageUsers, ManageBooks, ManageUsersBooks;

    static User chosenUser;
    static Book chosenBook;

    public View() {
        View.dataUserBooks = FXCollections.observableArrayList();
        chosenUser = null;
        chosenBook = null;
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
                new Text("")
        );
        border2.setStyle("-fx-background-color:green;-fx-padding:10px;");
        popup = new Scene(border2, 500, 200);
        newStage = new Stage(); //nowe okno
        newStage.setScene(popup);
        newStage.setTitle("Informacja");
        newStage.initModality(Modality.APPLICATION_MODAL);
    }

    private TabPane createTabPane() {
        tabPane.getTabs().addAll(
                ManageUsers = new ManageUsers("Zarzadzanie Czytelnikami"),
                ManageBooks = new ManageBooks("Zarządzanie Książkami"),
                ManageUsersBooks = new ManageUsersBooks("Książki Czytelnika")
        );
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        return tabPane;
    }

}
