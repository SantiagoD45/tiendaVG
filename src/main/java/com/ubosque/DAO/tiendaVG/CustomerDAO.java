package com.ubosque.DAO.tiendaVG;

import com.ubosque.DTO.tiendaVG.Customer;
import java.sql.*;
import java.util.ArrayList;


/*
 * Autor: SANTIAGO DIAZ PINZON - GRUPO 16 - EQUIPO 11
 * */

public class CustomerDAO {

    public static final String SQL_CUSTOMER_TABLE_NAME = "clientes";
    public static final String SQL_CUSTOMER_ID_CARD = "cedula_cliente";
    public static final String SQL_CUSTOMER_ADDRESS = "direccion_cliente";
    public static final String SQL_CUSTOMER_EMAIL = "email_cliente";
    public static final String SQL_CUSTOMER_NAME = "nombre_cliente";
    public static final String SQL_CUSTOMER_PHONE = "telefono_cliente";

    Connection connection = new Connection();

    public void createCustomer(long customerIdCard, String customerAddress, String customerEmail, String customerName, String customerPhone) {
       // String query = "INSERT INTO " + SQL_TABLE_NAME+ " (" + SQL_CUSTOMER_ID_CARD +", customerAddress, customerEmail, customerName, customerPhone) VALUES (?, ?, ?, ?, ?)";
        String query = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?)",
                SQL_CUSTOMER_TABLE_NAME, SQL_CUSTOMER_ID_CARD, SQL_CUSTOMER_ADDRESS, SQL_CUSTOMER_EMAIL, SQL_CUSTOMER_NAME, SQL_CUSTOMER_PHONE);
        try {
            PreparedStatement statement = connection.getConnection().prepareStatement(query);
            statement.setLong(1, customerIdCard);
            statement.setString(2, customerAddress);
            statement.setString(3, customerEmail);
            statement.setString(4, customerName);
            statement.setString(5, customerPhone);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            System.out.println(query);
            System.out.println("Error con la base de datos añadiendo al cliente");
            e.printStackTrace();
        }
    }

    public void createCustomer(Customer customer) {
        //String query = "INSERT INTO customers (customerIdCard, customerAddress, customerEmail, customerName, customerPhone) VALUES (?, ?, ?, ?, ?)";
        String query = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?)",
                SQL_CUSTOMER_TABLE_NAME, SQL_CUSTOMER_ID_CARD, SQL_CUSTOMER_ADDRESS, SQL_CUSTOMER_EMAIL, SQL_CUSTOMER_NAME, SQL_CUSTOMER_PHONE);
        try {
            PreparedStatement statement = connection.getConnection().prepareStatement(query);
            statement.setLong(1, customer.getCustomerIdCard());
            statement.setString(2, customer.getCustomerAddress());
            statement.setString(3, customer.getCustomerEmail());
            statement.setString(4, customer.getCustomerName());
            statement.setString(5, customer.getCustomerPhone());
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error con la base de datos añadiendo al cliente");
            e.printStackTrace();
        }
    }

    public ArrayList<Customer> listCustomers() {

        ArrayList<Customer> customers = new ArrayList<>();
        try {
            PreparedStatement statement = connection.getConnection().prepareStatement("SELECT * FROM " + SQL_CUSTOMER_TABLE_NAME);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                long customerIdCard = result.getLong(   SQL_CUSTOMER_ID_CARD);
                String customerAddress = result.getString(SQL_CUSTOMER_ADDRESS);
                String customerEmail = result.getString(SQL_CUSTOMER_EMAIL);
                String customerName = result.getString(SQL_CUSTOMER_NAME);
                String customerPhone = result.getString(SQL_CUSTOMER_PHONE);
                customers.add(new Customer(customerIdCard, customerAddress, customerEmail, customerName, customerPhone));
            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Ocurrió un error leyendo la base de datos de clientes");
            e.printStackTrace();

        }
        return customers;
    }

    public Customer searchCustomer(String parameterName, String parameter) {
        //String query = "SELECT * FROM " + SQL_CUSTOMER_TABLE_NAME + " WHERE " + parameterName + "=" + "'" + parameter + "'";
        String query = String.format("SELECT * FROM %s WHERE %s= %s", SQL_CUSTOMER_TABLE_NAME, parameterName, parameter);
        Customer response = null;
        try {
            PreparedStatement statement = connection.getConnection().prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                long customerIdCard = result.getLong(   SQL_CUSTOMER_ID_CARD);
                String customerAddress = result.getString(SQL_CUSTOMER_ADDRESS);
                String customerEmail = result.getString(SQL_CUSTOMER_EMAIL);
                String customerName = result.getString(SQL_CUSTOMER_NAME);
                String customerPhone = result.getString(SQL_CUSTOMER_PHONE);
                response = new Customer(customerIdCard, customerAddress, customerEmail, customerName, customerPhone);
            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error con la base de datos buscando al cliente");
            e.printStackTrace();

        }
        return response;

    }

    public void updateCustomer(Customer customer) {
        //String query = "UPDATE customers SET customerAddress=?, customerEmail=?, customerName=?, customerPhone=? WHERE customerIdCard=?";
        String query = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=? WHERE %s=?",
                SQL_CUSTOMER_TABLE_NAME, SQL_CUSTOMER_ADDRESS, SQL_CUSTOMER_EMAIL, SQL_CUSTOMER_NAME, SQL_CUSTOMER_PHONE, SQL_CUSTOMER_ID_CARD);
        try {
            PreparedStatement statement = connection.getConnection().prepareStatement(query);
            statement.setString(1, customer.getCustomerAddress());
            statement.setString(2, customer.getCustomerEmail());
            statement.setString(3, customer.getCustomerName());
            statement.setString(4, customer.getCustomerPhone());
            statement.setLong(5, customer.getCustomerIdCard());
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error con la base de datos actualizando al cliente");
            e.printStackTrace();
        }
    }

    public void deleteCustomer(Customer customer) {
       // String query = "DELETE FROM " + SQL_TABLE_NAME + " WHERE customerIdCard=?";
        String query = String.format("DELETE FROM %s WHERE %s=?",
                SQL_CUSTOMER_TABLE_NAME, SQL_CUSTOMER_ID_CARD);
        try {
            PreparedStatement statement = connection.getConnection().prepareStatement(query);
            statement.setLong(1, customer.getCustomerIdCard());
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error con la base de datos eliminando al cliente");
            e.printStackTrace();
        }
    }

    public void deleteCustomer(long customerIdCard) {
        String query = String.format("DELETE FROM %s WHERE %s=?",
                SQL_CUSTOMER_TABLE_NAME, SQL_CUSTOMER_ID_CARD);
        try {
            PreparedStatement statement = connection.getConnection().prepareStatement(query);
            statement.setLong(1, customerIdCard);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error con la base de datos eliminando al cliente");
            e.printStackTrace();
        }
    }
}
