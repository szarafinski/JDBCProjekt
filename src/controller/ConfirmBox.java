/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author saicon_02
 */
public class ConfirmBox {
    
    static boolean answer;
    
    
    public static boolean display (String title, String message){
        
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(350);
        window.setMinHeight(250);
        Label label = new Label();
        label.setText(message);
        
        Button yesButton = new Button ("TAK");
        Button noButton = new Button ("NIE");
        
        yesButton.setOnAction(e ->{
            e.consume();
            answer = true;
            window.close();
            
        });
        
        noButton.setOnAction(e->{
            answer = false;
            //window.toBack();
//            
        });
     
        
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label,yesButton, noButton);
        layout.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene (layout);
        window.setScene(scene);
        window.showAndWait();
        
        return answer;
        
    }
}
