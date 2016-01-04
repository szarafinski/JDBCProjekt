
package controller;

import view.View;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


/**
 *
 * @author KrzysieK
 */
public class Program extends Application implements EventHandler<WindowEvent> {
   
    Stage window;
    Button button;
    
    public static void main(String[] args) {
      Application.launch(args);
   }
   @Override
    public void start(final Stage primaryStage) throws Exception{
        window = primaryStage;
        
        View projektZespolowy = new View();
        projektZespolowy.start(primaryStage);
        window.setOnCloseRequest(this);
        //.consume();
    } 
        

    @Override
    public void handle(final WindowEvent event) {
      boolean answer = ConfirmBox.display("Wyjście z programu", " Czy napewno zamknąć program?");
      if(answer== true)
          window.close();
      else if (answer == false)
         window.toBack();
          System.out.println("nie");
    }
          

            
    }
