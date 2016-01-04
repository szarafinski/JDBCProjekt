/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.AlertBox;
import controller.DataBaseController;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.Wypozyczenie;

/**
 *
 * @author KrzysieK
 */
public class ManageUsersBooks extends Tab {

    public TableView tableUserBooks;

    public ManageUsersBooks(String tekst) {
        this.setText(tekst);
        create();
    }

    private void create() {

        tableUserBooks = new UsersBooksTableView();

        final Label label = new Label("Address Book");
        label.setFont(new Font("Arial", 20));

        Button usunSelectedLent = new Button("Zwrot książki");
        usunSelectedLent.setOnAction(event -> new ButtonActions().usun());
        Button addLendFactButton = new Button("Dodaj wypożyczenie");
        addLendFactButton.setOnAction(event -> new ButtonActions().addLendFact());
        Button wypozyczoneBookBtn = new Button("Wszystkie wypożyczone książki");
        wypozyczoneBookBtn.setOnAction(event -> new ButtonActions().wypozyczone());

        Text sceneTitle = new Text("Wypożyczone Książki");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        VBox tabUserBooks = new VBox(8);
        tabUserBooks.setPadding(new Insets(10, 10, 10, 10));
        tabUserBooks.setSpacing(10);
        tabUserBooks.setAlignment(Pos.TOP_LEFT);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER_LEFT);
        grid.setHgap(10);
        grid.setVgap(5);
//        grid.add(wypozyczoneBookBtn, 0, 1);
//        grid.add(addLendFactButton, 1, 1);
        grid.add(addLendFactButton, 0, 1);
        grid.add(wypozyczoneBookBtn, 1, 1);
        grid.add(usunSelectedLent, 2, 1);


        tabUserBooks.getChildren().addAll(
                sceneTitle,
                grid,
                tableUserBooks
                //usunSelectedLent
        );
        this.setContent(tabUserBooks);

    }

    class ButtonActions {

        private void wypozyczone() {
            tableUserBooks.getItems().clear();
            DataBaseController db = new DataBaseController();
            List<Wypozyczenie> wypozyczone = db.selectWszszystkieKsiazkiCzytelnikow();
            for (Wypozyczenie wypozyczenie : wypozyczone) {
                tableUserBooks.getItems().add(wypozyczenie);
            }
        }

        private void usun() {
            if (tableUserBooks.getSelectionModel().getSelectedIndex() >= 0) {
                View.chosenLent = (Wypozyczenie) tableUserBooks.getSelectionModel().getSelectedItem();
                DataBaseController db = new DataBaseController();
                db.usunWypozyczenieKS(View.chosenLent);
                //tableUserBooks.getItems().remove(View.chosenLent);
                //tableUserBooks.getSelectionModel().clearSelection();
//                new AlertBox().informacja("Została usunięta ksiazka: \n"
//                        + selectedBook.getAutor()
//                        + " "
//                        + selectedBook.getTytul());
            } else {
                new AlertBox().informacja("Zaznacz pozycję na liście do usunięcia z bazy");
            }

        }

        private void addLendFact() {
            if (View.chosenUser != null && View.chosenBook != null) {
                DataBaseController db = new DataBaseController();
                db.insertWypozyczenieKS(View.chosenUser.getidentyfikator(), View.chosenBook.getBookID());

                //View.chosenBook = null;
                //View.chosenUser = null;
                //View.dataUserBooks.clear();
                wypozyczone();
                new AlertBox().informacja("Dodano nowe wypożyczenie");
            } else
            {
                new AlertBox().blad("nie wybrano wcześniej użytkownika lub książki");
            }
        }
    }
}
