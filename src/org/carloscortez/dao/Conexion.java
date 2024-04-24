/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.carloscortez.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Carlos
 */
public class Conexion {
    private static Conexion instance;

    private String jdbcurl = "jdbc:mysql://localhost:3306/SuperKinalIN5CVDB?serverTimezone=GMT-6&useSSL=false";
    private String usuario = "root";
    private String contrasenia = "admin";
    
    private Conexion(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error en la conexion a la base de datos");
        }
    }

    public static Conexion getInstance(){
        if (instance == null){
            instance = new Conexion();
        }
        return instance;
    }

    public Connection obtenerConexion() throws SQLException{
        return DriverManager.getConnection(jdbcurl, usuario, contrasenia);
    }
    
}
