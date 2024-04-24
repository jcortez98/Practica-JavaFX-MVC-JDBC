/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.carloscortez.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import org.carloscortez.dao.Conexion;
import org.carloscortez.dto.ClienteDTO;
import org.carloscortez.model.Cliente;
import org.carloscortez.system.Main;
import org.carloscortez.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author Carlos
 */
public class FormClientesController implements Initializable {
    private Main stage;
    private int op;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    
    @FXML
    Button btnGuardar, btnCancelar;
    @FXML
    TextField tfClienteId, tfNombre, tfApellido, tfTelefono, tfDireccion, tfNit;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(ClienteDTO.getClienteDTO().getCliente() != null){
            cargarDatos(ClienteDTO.getClienteDTO().getCliente());
        }
    }   
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnGuardar){
            if(op == 1){
                if(!tfNombre.getText().equals("") && !tfApellido.getText().equals("") && !tfDireccion.getText().equals("") && !tfNit.getText().equals("")){
                    agregarCliente();
                    SuperKinalAlert.getInstance().mostrarAlerta(401);
                    stage.menuClientesView();
                } else{
                    SuperKinalAlert.getInstance().mostrarAlerta(400);
                    tfNombre.requestFocus();
                    return;
                }
                
            }else if(op == 2){
                if(!tfNombre.getText().equals("") && !tfApellido.getText().equals("") && !tfDireccion.getText().equals("") && !tfNit.getText().equals("")){
                    if(SuperKinalAlert.getInstance().mostrarConfirmacion(600).get() == ButtonType.OK){
                        editarCliente();
                        ClienteDTO.getClienteDTO().setCliente(null);
                        stage.menuClientesView();
                    }
                }else{
                    SuperKinalAlert.getInstance().mostrarAlerta(400);
                    tfNombre.requestFocus();
                    return;
                }
            }
        }else if(event.getSource() == btnCancelar){
            stage.menuClientesView();
            ClienteDTO.getClienteDTO().setCliente(null);
        }
    } 
    
    public void cargarDatos(Cliente cliente){
        tfClienteId.setText(Integer.toString(cliente.getClienteId()));
        tfNombre.setText(cliente.getNombre());
        tfApellido.setText(cliente.getApellido());
        tfTelefono.setText(cliente.getTelefono());
        tfDireccion.setText(cliente.getDireccion());
        tfNit.setText(cliente.getNit());
    }

    public void agregarCliente(){
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarCliente(?, ?, ?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombre.getText());
            statement.setString(2, tfApellido.getText());
            statement.setString(3, tfTelefono.getText());
            statement.setString(4, tfDireccion.getText());
            statement.setString(5, tfNit.getText());
            statement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(statement != null){
                    statement.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    public void editarCliente(){
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarCliente(?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfClienteId.getText()));
            statement.setString(2, tfNombre.getText());
            statement.setString(3, tfApellido.getText());
            statement.setString(4, tfTelefono.getText());
            statement.setString(5, tfDireccion.getText());
            statement.setString(6, tfNit.getText());
            statement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally{
            try {
                if(statement != null){
                    statement.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

    public void setOp(int op) {
        this.op = op;
    }

    
}
