/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.carloscortez.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import org.carloscortez.system.Main;

/**
 *
 * @author Carlos
 */

public class MenuPrincipalController implements Initializable {
    private Main stage;
    
    @FXML
    MenuItem btnMenuClientes, btnTicketSoporte;
    
    @FXML
    public void handleButtonAction(ActionEvent event) throws Exception{
        if(event.getSource() == btnMenuClientes){
           stage.menuClientesView();
        } else if(event.getSource() == btnTicketSoporte){
            stage.menuTicketSoporteView();
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
}
