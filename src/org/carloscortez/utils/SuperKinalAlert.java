/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.carloscortez.utils;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Carlos
 */
public class SuperKinalAlert {
    private static SuperKinalAlert instance;
    
    private SuperKinalAlert(){
    }
    
    public static SuperKinalAlert getInstance(){
        if(instance == null){
            instance = new SuperKinalAlert();
        }
        return instance;
    }
    
    public void mostrarAlerta(int errorCode){
        if(errorCode == 400){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos pendientes");
            alert.setHeaderText("Campos pendientes");
            alert.setContentText("Algunos campos necesarios para el registro estan vacíos.");
            alert.showAndWait();
        }else if(errorCode == 401){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registro");
            alert.setHeaderText("Registro");
            alert.setContentText("Registro realizado con éxito!");
            alert.showAndWait();
        }else if(errorCode == 500){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Eliminación de registro");
            alert.setHeaderText("Eliminación de registro");
            alert.setContentText("Eliminado con éxito!");
            alert.showAndWait();
        }
    }
    
    public Optional<ButtonType> mostrarConfirmacion(int confirmationCode){
        Optional<ButtonType> action = null;
        if(confirmationCode == 500){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminación de registro");
            alert.setHeaderText("Eliminación de registro");
            alert.setContentText("Desea confirmar la eliminación del registro?");
            action = alert.showAndWait();
        }else if (confirmationCode == 600){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Edición de registro");
            alert.setHeaderText("Edición de registro");
            alert.setContentText("Desea confirmar la edición del registro?");
            action = alert.showAndWait();
        }
        return action;
    }
}
