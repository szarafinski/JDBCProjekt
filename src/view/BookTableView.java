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
public class BookTableView extends TableView {
        
    public BookTableView(){
        create();
    }
    
    
    private void create() {
        this.setEditable(true);

        TableColumn bookNameCol = new TableColumn("Tytuł");
        bookNameCol.setMinWidth(150);
        bookNameCol.setCellValueFactory(
                new PropertyValueFactory<>("bookTitle"));

        TableColumn bookAuthorCol = new TableColumn("Autor");
        bookAuthorCol.setMinWidth(150);
        bookAuthorCol.setCellValueFactory(
                new PropertyValueFactory<>("bookAuthor"));

        TableColumn wydawnictwoCol = new TableColumn("Wydawnictwo");
        wydawnictwoCol.setMinWidth(250);
        wydawnictwoCol.setCellValueFactory(
                new PropertyValueFactory<>("bookWydawnictwo"));

        TableColumn isbnCol = new TableColumn("ISBN");
        isbnCol.setCellValueFactory(
                new PropertyValueFactory<>("bookISBN"));

        TableColumn bookYearCol = new TableColumn("Rok Wydania");
        bookYearCol.setMinWidth(100);
        bookYearCol.setCellValueFactory(
                new PropertyValueFactory<>("bookRokWydania"));
        
        TableColumn bookAvailableCol = new TableColumn("Stan");
        bookAvailableCol.setMinWidth(100);
        bookAvailableCol.setCellValueFactory(
        new PropertyValueFactory<>("bookStan")
        );

        this.getColumns().setAll(
                bookNameCol,
                bookAuthorCol,
                wydawnictwoCol,
                isbnCol,
                bookYearCol,
                bookAvailableCol
        );
        
    }
    
}
