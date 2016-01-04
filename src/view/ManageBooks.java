/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.AlertBox;
import controller.DataBaseController;
import controller.Logic;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.Book;

/**
 *
 * @author KrzysieK
 */
public class ManageBooks extends Tab {
    
    TextField bookNameTextField, wydawnictwoNameTextField, bookIssueDateTextField, bookAuthorTextField, bookISBNTextField;
    TableView tabelaBook;
    DataBaseController dataBase;
    
    public ManageBooks(String tekst) {
        setText(tekst);
        createContent();
        dataBase = new DataBaseController();
    }
    
    private void createContent() {
        Label bookName = new Label("Tytł:");
        Label wydawnictwoName = new Label("Wydawnictwo:");
        Label bookIssueDate = new Label("Rok wydania:");
        Label bookAuthor = new Label("Autor:");
        Label bookISBN = new Label("ISBN:");
        
        bookNameTextField = new TextField();
        wydawnictwoNameTextField = new TextField();
        bookIssueDateTextField = new TextField();
        bookAuthorTextField = new TextField();
        bookISBNTextField = new TextField();
        
        Button bookSzukajBtn = new Button("Szukaj");
        Button bookDodajBtn = new Button("Dodaj");
        Button bookUsunBtn = new Button("Usuń");
        Button bookClearBtn = new Button("Wyczyść");
        Button chooseBookButton = new Button("Wybierz książkę");
        
        bookSzukajBtn.setOnAction(event -> new ButtonActions().szukaj());
        bookDodajBtn.setOnAction(event -> new ButtonActions().dodaj());
        bookClearBtn.setOnAction(event -> new ButtonActions().clear());
        bookUsunBtn.setOnAction(event -> new ButtonActions().usun());
        chooseBookButton.setOnAction(event -> new ButtonActions().wybierz());
        
        VBox uklad = new VBox(8);
        uklad.setPadding(new Insets(10, 10, 10, 10));
        
        Text sceneTitle = new Text("Dane Książki");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        
        HBox poziomePrzyciski = new HBox();
        poziomePrzyciski.setSpacing(10);
        poziomePrzyciski.setAlignment(Pos.CENTER);
        poziomePrzyciski.getChildren().addAll(
                bookSzukajBtn,
                bookDodajBtn,
                bookClearBtn,
                chooseBookButton
        );
        
        GridPane siatka = new GridPane();
        siatka.setAlignment(Pos.CENTER);
        siatka.setHgap(10);
        siatka.setVgap(5);
        siatka.add(bookName, 0, 1);
        siatka.add(bookNameTextField, 0, 2);
        siatka.add(bookAuthor, 1, 1);
        siatka.add(bookAuthorTextField, 1, 2);
        siatka.add(wydawnictwoName, 2, 1);
        siatka.add(wydawnictwoNameTextField, 2, 2);
        siatka.add(bookISBN, 0, 4);
        siatka.add(bookISBNTextField, 0, 5);
        siatka.add(bookIssueDate, 1, 4);
        siatka.add(bookIssueDateTextField, 1, 5);
        siatka.add(poziomePrzyciski, 2, 5);
        
        uklad.getChildren().addAll(sceneTitle,
                siatka,
                tabelaBook = new BookTableView(),
                bookUsunBtn
        );
        this.setContent(uklad);
    }
    
    private Book newBook() {
        return new Book(
                bookNameTextField.getText(),
                bookAuthorTextField.getText(),
                bookISBNTextField.getText(),
                wydawnictwoNameTextField.getText(),
                bookIssueDateTextField.getText()
        );
    }
    
    class ButtonActions {
        
        private void clear() {
            bookNameTextField.clear();
            wydawnictwoNameTextField.clear();
            bookIssueDateTextField.clear();
            bookAuthorTextField.clear();
            bookISBNTextField.clear();
            tabelaBook.getSelectionModel().clearSelection();
            szukaj();
        }
        
        private void dodaj() {
            if (!bookNameTextField.getText().isEmpty()
                    && !bookAuthorTextField.getText().isEmpty()
                    && !bookISBNTextField.getText().isEmpty()
                    && !wydawnictwoNameTextField.getText().isEmpty()
                    && !bookIssueDateTextField.getText().isEmpty()) {
                tabelaBook.getItems().add(newBook());
                dataBase.insertKsiazka(newBook());
                clear();
                new AlertBox().informacja("została dodana książka");
            } else {
                new AlertBox().blad("Nie wypełniono wszystkich pól");
            }
        }
        
        private void szukaj() {
            Logic logic = new Logic();
            List<Book> ksiazki = logic.selectKsiazki(newBook());
            tabelaBook.getItems().clear();
            for (Book ksiazka : ksiazki) {
                tabelaBook.getItems().add(ksiazka);
            }
        }
        
        private void usun() {
            if (tabelaBook.getSelectionModel().getSelectedIndex() >= 0) {
                Book selectedBook = (Book) tabelaBook.getSelectionModel().getSelectedItem();
                dataBase.usunKsiazka(selectedBook);
                tabelaBook.getItems().remove(selectedBook);
                clear();
                new AlertBox().informacja("została usunięta książka: \n"
                        + selectedBook.getBookAuthor()
                        + " "
                        + selectedBook.getBookTitle());
            } else {
                new AlertBox().informacja("Zaznacz książkę na liście do usunięcia z bazy");
            }
            
        }
        
        private void wybierz() {
            if (tabelaBook.getSelectionModel().getSelectedIndex() >= 0) {
                View.chosenBook = (Book) tabelaBook.getSelectionModel().getSelectedItem();
                View.tabPane.getSelectionModel().selectNext();
            }
        }
    }
}
