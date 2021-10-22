package com.ubosque.DAO.tiendaVG;

import java.sql.*;


/*
 * Autor: SANTIAGO DIAZ PINZON - GRUPO 16 - EQUIPO 11
 * */

public class Connection {
	
	String database = "grupo16_equipo11";
    String user = "admin";
    String password = "tiendagenericamaoe2";
    String port = "3306";
    //String url = "localhost";
    String url = "tiendagenericamaoe2.czo3ixoe3xoe.us-east-1.rds.amazonaws.com";
    String jdbcUrl = "jdbc:mariadb://" + url+ ":" + port + "/" + database + "?user=" + user + "&password=" + password;

    java.sql.Connection connection = null;

    public java.sql.Connection getConnection() {
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver");
        	Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcUrl);
            if (connection != null) {
                System.out.println("Conexión exitosa a la base de datos");
            }
        }

        catch (SQLException | ClassNotFoundException e) {
            e.getMessage();
            e.printStackTrace();
        }
        return connection;
    }
    
    
    /* Conexion de manera Local
     * 
	String database = "bdorig";
    String user = "root";
    String password = "admin";
    String url = "jdbc:mysql://localhost/" + database;

    java.sql.Connection connection = null;

    public java.sql.Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                System.out.println("Conexión exitosa a la base de datos");
            }
        }

        catch (SQLException | ClassNotFoundException e) {
            e.getMessage();
            e.printStackTrace();
        }
        return connection;
    }*/
	
}
