/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

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

        Button usunUserBookBtn = new Button("Książka oddana przez Czytelnika");
        usunUserBookBtn.setOnAction(event -> new ButtonActions().usun());
        Button addLendFactButton = new Button("Dodaj wypozyczenie");
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
        grid.add(wypozyczoneBookBtn, 0, 1);
        grid.add(addLendFactButton, 1, 1);

        tabUserBooks.getChildren().addAll(
                sceneTitle,
                grid,
                tableUserBooks,
                usunUserBookBtn
        );
        this.setContent(tabUserBooks);

    }

    class ButtonActions {
        private void wypozyczone(){
            tableUserBooks.getItems().clear();
                
                DataBaseController db = new DataBaseController();
                List<Wypozyczenie> wypozyczone = db.selectWszszystkieKsiazkiCzytelnikow();
                

                    for (Wypozyczenie wypozyczenie : wypozyczone) {
                        tableUserBooks.getItems().add(wypozyczenie);
                        
                
                }
        }
        private void usun(){
             if (tableUserBooks.getSelectionModel().getSelectedIndex() >= 0) {
                    Wypozyczenie selectedBook = (Wypozyczenie) tableUserBooks.getSelectionModel().getSelectedItem();
                    
                    DataBaseController db = new DataBaseController();
                    db.usunWypozyczenie(selectedBook);
                    tableUserBooks.getItems().remove(selectedBook);
//
//                    komunikat.setText("Została usunięta ksiazka o parametrach: \n"
//                            + selectedBook.toString());
//                    newStage.showAndWait();
                }
                tableUserBooks.getSelectionModel().clearSelection();
        }
        private void addLendFact(){
            if (View.chosenUser != null && View.chosenBook != null) {
                    DataBaseController db = new DataBaseController();
                    db.insertWypozyczenieKS(View.chosenUser.getidentyfikator(), View.chosenBook.getBookID());
                }
            View.dataUserBooks.clear();
            wypozyczone();
        }
    }
}

