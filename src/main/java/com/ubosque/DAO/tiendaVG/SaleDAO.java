package com.ubosque.DAO.tiendaVG;

import com.ubosque.DTO.tiendaVG.Customer;

import com.ubosque.DTO.tiendaVG.Sale;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/*
 * Autor: SANTIAGO DIAZ PINZON - GRUPO 16 - EQUIPO 11
 * */

public class SaleDAO {

    public static final String SQL_SALE_TABLE_NAME = "ventas";
    public static final String SQL_SALE_ID = "codigo_venta";
    public static final String SQL_USER_ID_CARD = "cedula_usuario";
    public static final String SQL_CUSTOMER_ID_CARD = "cedula_cliente";
    public static final String SQL_SALE_VAT = "ivaventa";
    public static final String SQL_SALE_TOTAL = "total_venta";
    public static final String SQL_SALE_VALUE = "valor_venta";

    Connection connection = new Connection();

    public void createSale(long saleId, long userIdCard, long customerIdCard, double saleVAT, double saleTotal, double saleValue) {
        String query = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?)",
                SQL_SALE_TABLE_NAME, SQL_SALE_ID, SQL_USER_ID_CARD, SQL_CUSTOMER_ID_CARD, SQL_SALE_VAT, SQL_SALE_TOTAL, SQL_SALE_VALUE);
        try {
            PreparedStatement statement = connection.getConnection().prepareStatement(query);
            statement.setLong(1, saleId);
            statement.setLong(2, userIdCard);
            statement.setLong(3, customerIdCard);
            statement.setDouble(4, saleVAT);
            statement.setDouble(5, saleTotal);
            statement.setDouble(6, saleValue);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            System.out.println(query);
            System.out.println("Error con la base de datos añadiendo la venta");
            e.printStackTrace();
        }
    }

    public void createSale(Sale sale) {
        String query = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?)",
                SQL_SALE_TABLE_NAME, SQL_SALE_ID, SQL_USER_ID_CARD, SQL_CUSTOMER_ID_CARD, SQL_SALE_VAT, SQL_SALE_TOTAL, SQL_SALE_VALUE);
        try {
            PreparedStatement statement = connection.getConnection().prepareStatement(query);
            statement.setLong(1, sale.getSaleId());
            statement.setLong(2, sale.getUserIdCard());
            statement.setLong(3, sale.getCustomerIdCard());
            statement.setDouble(4, sale.getSaleVAT());
            statement.setDouble(5, sale.getSaleTotal());
            statement.setDouble(6, sale.getSaleValue());
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            System.out.println(query);
            System.out.println("Error con la base de datos añadiendo la venta");
            e.printStackTrace();
        }
    }

    public ArrayList<Sale> listSales() {
        String query = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?)",
                SQL_SALE_TABLE_NAME, SQL_SALE_ID, SQL_USER_ID_CARD, SQL_CUSTOMER_ID_CARD, SQL_SALE_VAT, SQL_SALE_TOTAL, SQL_SALE_VALUE);
        ArrayList<Sale> sales = new ArrayList<>();
        try {
            PreparedStatement statement = connection.getConnection().prepareStatement("SELECT * FROM " + SQL_SALE_TABLE_NAME);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                long saleId = result.getLong(SQL_SALE_ID);
                long userIdCard = result.getLong(SQL_USER_ID_CARD);
                long customerIdCard = result.getLong(SQL_CUSTOMER_ID_CARD);
                double saleVAT = result.getDouble(SQL_SALE_VAT);
                double saleTotal = result.getDouble(SQL_SALE_TOTAL);
                double saleValue = result.getDouble(SQL_SALE_VALUE);
                sales.add(new Sale(saleId, userIdCard, customerIdCard, saleVAT, saleTotal, saleValue));
            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Ocurrió un error leyendo la base de datos de clientes");
            e.printStackTrace();

        }
        return sales;
    }
    // Obtiene el id de la última venta y lo regresa, si no lo encuentra regresa -1
    public long getLastSaleId(){
        String query = String.format("SELECT max(%s) FROM %s", SQL_SALE_ID, SQL_SALE_TABLE_NAME);
        long response = -1;
        try {
            PreparedStatement statement = connection.getConnection().prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {

                response = result.getLong(String.format("max(%s)",SQL_SALE_ID));
            }
            result.close();
            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ocurrió un error obteniendo el último consecutivo");
        }
        return response;
    }

    public Sale searchSale(String parameterName, String parameter) {
        String query = String.format("SELECT * FROM %s WHERE %s= %s", SQL_SALE_TABLE_NAME, parameterName, parameter);
        Sale response = null;
        try {
            PreparedStatement statement = connection.getConnection().prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                long saleId = result.getLong(SQL_SALE_ID);
                long userIdCard = result.getLong(SQL_USER_ID_CARD);
                long customerIdCard = result.getLong(SQL_CUSTOMER_ID_CARD);
                double saleVAT = result.getDouble(SQL_SALE_VAT);
                double saleTotal = result.getDouble(SQL_SALE_TOTAL);
                double saleValue = result.getDouble(SQL_SALE_VALUE);
                response = new Sale(saleId, userIdCard, customerIdCard, saleVAT, saleTotal, saleValue);
            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error con la base de datos buscando la Venta");
            e.printStackTrace();

        }
        return response;

    }

    public void updateSale(Sale sale) {
        //String query = "UPDATE customers SET customerAddress=?, customerEmail=?, customerName=?, customerPhone=? WHERE customerIdCard=?";
        String query = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=? WHERE %s=?",
                SQL_SALE_TABLE_NAME, SQL_USER_ID_CARD, SQL_CUSTOMER_ID_CARD, SQL_SALE_VAT, SQL_SALE_TOTAL, SQL_SALE_VALUE, SQL_SALE_ID);
        try {
            PreparedStatement statement = connection.getConnection().prepareStatement(query);
            statement.setLong(1, sale.getUserIdCard());
            statement.setLong(2, sale.getCustomerIdCard());
            statement.setDouble(3, sale.getSaleVAT());
            statement.setDouble(4, sale.getSaleTotal());
            statement.setDouble(5, sale.getSaleValue());
            statement.setLong(6, sale.getSaleId());
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error con la base de datos actualizando la Venta");
            e.printStackTrace();
        }
    }

    public void deleteSale(Sale sale) {
        String query = String.format("DELETE FROM %s WHERE %s=?",
                SQL_SALE_TABLE_NAME, SQL_SALE_ID);
        try {
            PreparedStatement statement = connection.getConnection().prepareStatement(query);
            statement.setLong(1, sale.getSaleId());
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error con la base de datos eliminando la venta");
            e.printStackTrace();
        }
    }

    public void deleteSale(long saleId) {
        String query = String.format("DELETE FROM %s WHERE %s=?",
                SQL_SALE_TABLE_NAME, SQL_SALE_ID);
        try {
            PreparedStatement statement = connection.getConnection().prepareStatement(query);
            statement.setLong(1, saleId);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error con la base de datos eliminando la venta");
            e.printStackTrace();
        }
    }
}
