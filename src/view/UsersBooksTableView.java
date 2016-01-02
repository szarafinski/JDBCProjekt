/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author KrzysieK
 */
public class UsersBooksTableView extends TableView {
    
    public UsersBooksTableView() {
        create();
        
    }

    private void create() {
        this.setEditable(true);

        TableColumn userBookNameCol = new TableColumn("Imię");
        TableColumn userBookAuthorCol = new TableColumn("Autor");
        TableColumn userBookLastNameCol = new TableColumn("Nazwisko");
        TableColumn userBookTitleCol = new TableColumn("Tytuł");
        userBookTitleCol.setMinWidth(200);
        TableColumn userIDWypozyczeniaCol = new TableColumn("Wypożyczeni Nr");

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

        this.getColumns().addAll(
                userIDWypozyczeniaCol,
                userBookNameCol,
                userBookLastNameCol,
                userBookAuthorCol,
                userBookTitleCol
        );
        
        this.setItems(View.dataUserBooks);
    }
}
