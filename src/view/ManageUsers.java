/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.AlertBox;
import controller.DataBaseController;
import controller.Logic;
import controller.PESELvalidation;
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
import model.User;
import model.Wypozyczenie;

/**
 *
 * @author KrzysieK
 */
public class ManageUsers extends Tab {

    TextField userNameTextField, userLastNameTextField, userPESELTextField, userTownTextField, userStrTextField;
    TableView tableUser;

    public ManageUsers(String tekst) {
        setText(tekst);
        stworz();
    }

    private void stworz() {
        Label userName = new Label("Imię:");
        Label userLastName = new Label("Nazwisko:");
        Label userPESEL = new Label("PESEL:");
        Label userTown = new Label("Miasto:");
        Label userStr = new Label("Ulica:");

        userNameTextField = new TextField();
        userLastNameTextField = new TextField();
        userPESELTextField = new TextField();
        userTownTextField = new TextField();
        userStrTextField = new TextField();

        Button userSzukajBtn = new Button("Szukaj");
        Button userDodajBtn = new Button("Dodaj");
        Button userUsunBtn = new Button("Usuń");
        Button userClearBtn = new Button("Wyczyść");
        Button userShowBooks = new Button("Książki Czytelnika");

        Button chooseUserButton = new Button("Wybierz Czytelnika by wypozyczyć");

        userSzukajBtn.setOnAction(event -> new ButtonActions().szukaj());
        userDodajBtn.setOnAction(event -> new ButtonActions().dodaj());
        userUsunBtn.setOnAction(event -> new ButtonActions().usun());
        userClearBtn.setOnAction(event -> new ButtonActions().wyczysc());
        userShowBooks.setOnAction(event -> new ButtonActions().pokaz());
        chooseUserButton.setOnAction(event -> new ButtonActions().wybierz());

        VBox uklad = new VBox(8);
        uklad.setPadding(new Insets(10, 10, 10, 10));

        Text sceneTitle = new Text("Dane Czytelnika");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        HBox poziomePrzyciski1 = new HBox();
        poziomePrzyciski1.setSpacing(10);
        poziomePrzyciski1.setAlignment(Pos.CENTER);
        poziomePrzyciski1.getChildren().addAll(
                userSzukajBtn,
                userDodajBtn,
                userClearBtn
        );
        
        HBox poziomePrzyciski2 = new HBox();
        poziomePrzyciski2.setSpacing(10);
        poziomePrzyciski2.setAlignment(Pos.CENTER);
        poziomePrzyciski2.getChildren().addAll(
                userShowBooks,
                chooseUserButton
        );

        GridPane grid = new GridPane();
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
        grid.add(poziomePrzyciski1, 2, 4);
        grid.add(poziomePrzyciski2, 2, 5);

        uklad.getChildren().addAll(
                sceneTitle,
                grid,
                tableUser = new UserTableView(),
                userUsunBtn
        );
        this.setContent(uklad);
    }

    class ButtonActions {

        private User newUser() {
            return new User(
                    userNameTextField.getText(),
                    userLastNameTextField.getText(),
                    userPESELTextField.getText(),
                    userTownTextField.getText(),
                    userStrTextField.getText()
            );
        }

        private void wyczysc() {
            userNameTextField.clear();
            userLastNameTextField.clear();
            userPESELTextField.clear();
            userTownTextField.clear();
            userStrTextField.clear();
            tableUser.getSelectionModel().clearSelection();
        }

        private void szukaj() {

            Logic logic = new Logic();
            List<User> czytelnicy = logic.selectCzytelnicy(newUser());
            tableUser.getItems().clear();
            for (User czytelnik : czytelnicy) {
                tableUser.getItems().add(czytelnik);
            }
        }

        private void usun() {
            if (tableUser.getSelectionModel().getSelectedIndex() >= 0) {
                User selectedUser = (User) tableUser.getSelectionModel().getSelectedItem();

                DataBaseController db = new DataBaseController();
                db.usunCzytelnik(selectedUser);
                tableUser.getItems().remove(selectedUser);
                wyczysc();
                new AlertBox().alert("został usunięty użytkownik: \n"
                        + selectedUser.getImie()
                        + " "
                        + selectedUser.getNazwisko());
            } else {
                new AlertBox().informacja("Zaznacz czytelnika na liście do usunięcia z bazy");
            }
            
        }

        private void dodaj() {
            if (!userNameTextField.getText().isEmpty()
                    && !userLastNameTextField.getText().isEmpty()
                    && !userPESELTextField.getText().isEmpty()
                    && !userTownTextField.getText().isEmpty()
                    && !userStrTextField.getText().isEmpty()) {
                if (new PESELvalidation(userPESELTextField.getText()).isValid()) {
                    DataBaseController db = new DataBaseController();

                    tableUser.getItems().add(newUser());
                    db.insertCzytelnik(newUser());
                    wyczysc();
                    new AlertBox().alert("został dodany użytkownik");
                } else {
                    new AlertBox().blad("wprowadzono nieprawidłowy numer PESEL");
                }
            } else {
                new AlertBox().blad("Nie wypełniono wszystkich pól");
            }
        }

        private void pokaz() {
            if (tableUser.getSelectionModel().getSelectedIndex() >= 0) {
                User selectedUser = (User) tableUser.getSelectionModel().getSelectedItem();

                DataBaseController db = new DataBaseController();
                List<Wypozyczenie> wypozyczone = db.selectKsiazkiCzytelnika(selectedUser);
                View.dataUserBooks.clear();
                for (Wypozyczenie wypozyczoneWszystkieKsiazki : wypozyczone) {
                    View.dataUserBooks.add(wypozyczoneWszystkieKsiazki);
                }

                if (View.dataUserBooks.isEmpty()) {
                    new AlertBox().informacja("Brak wypożyczonych książek przez użytkownika");
                } else {
                    new AlertBox().informacja("Dane zostały pobrane dla: \n"
                            + selectedUser.getImie() + " " + selectedUser.getNazwisko());
                    View.tabPane.getSelectionModel().selectLast();
                }
            }
            tableUser.getSelectionModel().clearSelection();

        }

        private void wybierz() {
            if (tableUser.getSelectionModel().getSelectedIndex() >= 0) {
                View.chosenUser = (User) tableUser.getSelectionModel().getSelectedItem();
                View.tabPane.getSelectionModel().selectNext();
            }
        }
    }
}
