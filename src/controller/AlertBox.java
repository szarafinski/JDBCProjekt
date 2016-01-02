/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author KrzysieK
 */
public class AlertBox {

    public void alert(String wiadomosc) {
        BorderPane root = new BorderPane();
        root.setCenter(new Text(wiadomosc));
        Scene scena = new Scene(root, 500, 200);
        Stage popUP = new Stage(); //nowe okno
        popUP.setScene(scena);
        popUP.setTitle("Informacja");
        popUP.initModality(Modality.APPLICATION_MODAL);
        popUP.showAndWait();
    }

    public void informacja(String wiadomosc) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Informacja");
        alert.setHeaderText(null);
        alert.setContentText(wiadomosc);

        alert.showAndWait();
    }
    
    public void blad(String wiadomosc) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Informacja");
        alert.setHeaderText(null);
        alert.setContentText(wiadomosc);

        alert.showAndWait();
    }
}
