package com.ubosque.DAO.tiendaVG;

import com.ubosque.DTO.tiendaVG.Sale;

import com.ubosque.DTO.tiendaVG.SaleDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/*
 * Autor: SANTIAGO DIAZ PINZON - GRUPO 16 - EQUIPO 11
 * */

public class SaleDetailDAO {

    public static final String SQL_SALE_DETAIL_TABLE_NAME = "detalle_ventas";
    public static final String SQL_SALE_DETAIL_ID = "codigo_detalle_venta";
    public static final String SQL_PRODUCT_QUANTITY = "cantidad_producto";
    public static final String SQL_PRODUCT_ID = "codigo_producto";
    public static final String SQL_SALE_ID = "codigo_venta";
    public static final String SQL_TOTAL_VALUE = "valor_total";
    public static final String SQL_SALE_VALUE = "valor_venta";
    public static final String SQL_VAT_VALUE = "valoriva";

    Connection connection = new Connection();

    public void createSaleDetail(long saleDetailId, int productQuantity, long productId, long saleId, double totalValue, double saleValue, double VATValue) {
        String query = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?)",
                SQL_SALE_DETAIL_TABLE_NAME, SQL_SALE_DETAIL_ID, SQL_PRODUCT_ID, SQL_PRODUCT_QUANTITY, SQL_SALE_ID, SQL_TOTAL_VALUE, SQL_SALE_VALUE, SQL_VAT_VALUE);
        try {
            PreparedStatement statement = connection.getConnection().prepareStatement(query);
            statement.setDouble(1, saleDetailId);
            statement.setLong(2, saleId);
            statement.setInt(3, productQuantity);
            statement.setLong(4, productId);
            statement.setDouble(5, totalValue);
            statement.setDouble(6, saleValue);
            statement.setDouble(7, VATValue);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            System.out.println(query);
            System.out.println("Error con la base de datos añadiendo el detalle de venta");
            e.printStackTrace();
        }
    }

    public void createSaleDetail(SaleDetail saleDetail) {
        String query = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?)",
                SQL_SALE_DETAIL_TABLE_NAME, SQL_SALE_DETAIL_ID, SQL_PRODUCT_QUANTITY, SQL_PRODUCT_ID, SQL_SALE_ID, SQL_TOTAL_VALUE, SQL_SALE_VALUE, SQL_VAT_VALUE);
        try {
            PreparedStatement statement = connection.getConnection().prepareStatement(query);
            statement.setDouble(1, saleDetail.getSaleDetailId());
            statement.setInt(2, saleDetail.getProductQuantity());
            statement.setLong(3, saleDetail.getProductId());
            statement.setLong(4, saleDetail.getSaleId());
            statement.setDouble(5, saleDetail.getTotalValue());
            statement.setDouble(6, saleDetail.getSaleValue());
            statement.setDouble(7, saleDetail.getVATValue());
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            System.out.println(query);
            System.out.println("Error con la base de datos añadiendo el detalle de venta");
            e.printStackTrace();
        }
    }

    public ArrayList<SaleDetail> listSaleDetails() {
        String query = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?)",
                SQL_SALE_DETAIL_TABLE_NAME, SQL_SALE_DETAIL_ID, SQL_PRODUCT_QUANTITY, SQL_PRODUCT_ID, SQL_SALE_ID, SQL_TOTAL_VALUE, SQL_SALE_VALUE, SQL_VAT_VALUE);
        ArrayList<SaleDetail> sales = new ArrayList<>();
        try {
            PreparedStatement statement = connection.getConnection().prepareStatement("SELECT * FROM " + SQL_SALE_DETAIL_TABLE_NAME);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                long saleDetailId = result.getLong(SQL_SALE_DETAIL_ID);
                int productQuantity = result.getInt(SQL_PRODUCT_QUANTITY);
                long productId = result.getLong(SQL_PRODUCT_ID);
                long saleId = result.getLong(SQL_SALE_ID);
                double totalValue = result.getDouble(SQL_TOTAL_VALUE);
                double saleValue = result.getDouble(SQL_SALE_VALUE);
                double VATValue = result.getDouble(SQL_VAT_VALUE);
                sales.add(new SaleDetail(saleDetailId, productQuantity, productId, saleId, totalValue, saleValue, VATValue));
            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Ocurrió un error leyendo la base de datos de detalle de ventas");
            e.printStackTrace();

        }
        return sales;
    }
    // Obtiene el id de la última venta y lo regresa, si no lo encuentra regresa -1
    public long getLastSaleDetailId(){
        String query = String.format("SELECT max(%s) FROM %s", SQL_SALE_DETAIL_ID, SQL_SALE_DETAIL_TABLE_NAME);
        long response = -1;
        try {
            PreparedStatement statement = connection.getConnection().prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                response = result.getLong(String.format("max(%s)",SQL_SALE_DETAIL_ID));
            }
            result.close();
            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ocurrió un error obteniendo el último consecutivo detalle de ventas");
        }
        return response;
    }


    //
    //
    //
    //
    // Desde aquí no son necesarios y todavía están por terminarse
    public Sale searchSale(String parameterName, String parameter) {
        String query = String.format("SELECT * FROM %s WHERE %s= %s", SQL_SALE_DETAIL_TABLE_NAME, parameterName, parameter);
        Sale response = null;
        try {
            PreparedStatement statement = connection.getConnection().prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                long saleId = result.getLong(SQL_SALE_DETAIL_ID);
                long userIdCard = result.getLong(SQL_PRODUCT_QUANTITY);
                long customerIdCard = result.getLong(SQL_PRODUCT_ID);
                double saleVAT = result.getDouble(SQL_SALE_ID);
                double saleTotal = result.getDouble(SQL_TOTAL_VALUE);
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
                SQL_SALE_DETAIL_TABLE_NAME, SQL_PRODUCT_QUANTITY, SQL_PRODUCT_ID, SQL_SALE_ID, SQL_TOTAL_VALUE, SQL_SALE_VALUE, SQL_SALE_DETAIL_ID);
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
                SQL_SALE_DETAIL_TABLE_NAME, SQL_SALE_DETAIL_ID);
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
                SQL_SALE_DETAIL_TABLE_NAME, SQL_SALE_DETAIL_ID);
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
