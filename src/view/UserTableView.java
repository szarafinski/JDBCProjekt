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
public class UserTableView extends TableView {

    public UserTableView() {
        create();
    }

    private void create() {

        this.setEditable(true);

        TableColumn nameCol = new TableColumn("Imię");
        nameCol.setMinWidth(150);
        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("imie"));

        TableColumn lastNameCol = new TableColumn("Nazwisko");
        lastNameCol.setMinWidth(150);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<>("nazwisko"));

        TableColumn peselCol = new TableColumn("PESEL");
        peselCol.setMinWidth(160);
        peselCol.setCellValueFactory(
                new PropertyValueFactory<>("pesel"));

        TableColumn miastoCol = new TableColumn("Miasto");
        miastoCol.setMinWidth(100);
        miastoCol.setCellValueFactory(
                new PropertyValueFactory<>("miasto"));

        TableColumn ulicaCol = new TableColumn("Ulica");
        ulicaCol.setMinWidth(200);
        ulicaCol.setCellValueFactory(
                new PropertyValueFactory<>("ulica"));

        this.getColumns().setAll(
                nameCol,
                lastNameCol,
                peselCol,
                miastoCol,
                ulicaCol
        );

    }
}
