/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author KrzysieK
 */

import java.util.List;
 
import model.User;
import model.Book;
import controller.DataBaseController;
import java.sql.SQLException;

public class Program {

    public static void main(String[] args) {
        DataBaseController b = new DataBaseController();
        b.insertCzytelnik("Karol", "Maciaszek", "99");
        b.insertCzytelnik("Piotr", "Wojtecki", "89273849128");
        b.insertCzytelnik("Abdul", "Dabdul", "");
 
        b.insertKsiazka("Cień Wiatru", "Carlos Ruiz Zafon");
        b.insertKsiazka("W pustyni i w puszczy", "Henryk Sienkiewicz");
        b.insertKsiazka("Harry Potter", "Joanne Kathleen Rowling.");
 
        b.selectAllCzytelnicy();
        b.selectAllKsiazki();
        b.usun("czytelnicy", "imie", "Karol");
        b.selectAllCzytelnicy();
        
        
 
        b.closeConnection();
    }
    
}
