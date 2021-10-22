package com.ubosque.DAO.tiendaVG;

import com.ubosque.DTO.tiendaVG.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/*
 * Autor: SANTIAGO DIAZ PINZON - GRUPO 16 - EQUIPO 11
 * */

public class ProductDAO {

    public static final String SQL_PRODUCT_TABLE_NAME = "productos";
    public static final String SQL_PRODUCT_ID = "codigo_producto";
    public static final String SQL_PURCHASE_VAT = "ivacompra";
    public static final String SQL_PROVIDER_NIT = "nitproveedor";
    public static final String SQL_PRODUCT_NAME = "nombre_producto";
    public static final String SQL_PURCHASE_PRICE = "precio_compra";
    public static final String SQL_SALE_PRICE = "precio_venta";

    Connection connection = new Connection();

    public void createProduct(long productId, double purchaseVAT, long providerNit, String productName, double purchasePrice, double salePrice) {
        String query = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?)",
                SQL_PRODUCT_TABLE_NAME, SQL_PRODUCT_ID, SQL_PURCHASE_VAT, SQL_PROVIDER_NIT, SQL_PRODUCT_NAME, SQL_PURCHASE_PRICE, SQL_SALE_PRICE);
        try {
            PreparedStatement statement = connection.getConnection().prepareStatement(query);
            statement.setLong(1, productId);
            statement.setDouble(2, purchaseVAT);
            statement.setLong(3, providerNit);
            statement.setString(4, productName);
            statement.setDouble(5, purchasePrice);
            statement.setDouble(6, salePrice);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error con la base de datos añadiendo productos");
            e.printStackTrace();
        }
    }

    public void createProduct(Product product) {
        String query = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?)",
                SQL_PRODUCT_TABLE_NAME, SQL_PRODUCT_ID, SQL_PURCHASE_VAT, SQL_PROVIDER_NIT, SQL_PRODUCT_NAME, SQL_PURCHASE_PRICE, SQL_SALE_PRICE);
        try {
            PreparedStatement statement = connection.getConnection().prepareStatement(query);
            statement.setLong(1, product.getProductId());
            statement.setDouble(2, product.getPurchaseVAT());
            statement.setLong(3, product.getProviderNit());
            statement.setString(4, product.getProductName());
            statement.setDouble(5, product.getPurchasePrice());
            statement.setDouble(6, product.getSalePrice());
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error con la base de datos añadiendo productos");
            e.printStackTrace();
        }
    }

    public ArrayList<Product> listProducts() {

        ArrayList<Product> products = new ArrayList<>();
        try {
            PreparedStatement statement = connection.getConnection().prepareStatement("SELECT * FROM " + SQL_PRODUCT_TABLE_NAME);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                long productId = result.getLong(SQL_PRODUCT_ID);
                double purchaseVAT = result.getDouble(SQL_PURCHASE_VAT);
                long providerNit = result.getLong(SQL_PROVIDER_NIT);
                String productName = result.getString(SQL_PRODUCT_NAME);
                double purchasePrice = result.getDouble(SQL_PURCHASE_PRICE);
                double salePrice = result.getDouble(SQL_SALE_PRICE);
                products.add(new Product(productId, purchaseVAT, providerNit, productName, purchasePrice, salePrice));
            }
            result.close();
            statement.close();

        } catch (SQLException e) {
            System.out.println("Ocurrió un error leyendo la base de datos de productos");
            e.printStackTrace();

        }
        return products;
    }

    public Product searchProduct(String parameterName, String parameter) {
        String query = String.format("SELECT * FROM %s WHERE %s= %s", SQL_PRODUCT_TABLE_NAME, parameterName, parameter);
        Product response = null;
        try {
            PreparedStatement statement = connection.getConnection().prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                long productId = result.getLong(SQL_PRODUCT_ID);
                double purchaseVAT = result.getDouble(SQL_PURCHASE_VAT);
                long providerNit = result.getLong(SQL_PROVIDER_NIT);
                String productName = result.getString(SQL_PRODUCT_NAME);
                double purchasePrice = result.getDouble(SQL_PURCHASE_PRICE);
                double salePrice = result.getDouble(SQL_SALE_PRICE);
                response = new Product (productId, purchaseVAT, providerNit, productName, purchasePrice, salePrice);
            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error con la base de datos buscando el producto");
            e.printStackTrace();

        }
        return response;

    }

    public void updateProduct(Product product) {
        String query = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=? WHERE %s=?",
                SQL_PRODUCT_TABLE_NAME, SQL_PURCHASE_VAT, SQL_PROVIDER_NIT, SQL_PRODUCT_NAME, SQL_PURCHASE_PRICE, SQL_SALE_PRICE, SQL_PRODUCT_ID);
        try {
            PreparedStatement statement = connection.getConnection().prepareStatement(query);
            statement.setDouble(1, product.getPurchaseVAT());
            statement.setLong(2, product.getProviderNit());
            statement.setString(3, product.getProductName());
            statement.setDouble(4, product.getPurchasePrice());
            statement.setDouble(5, product.getSalePrice());
            statement.setLong(6, product.getProductId());
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error con la base de datos actualizando al product");
            e.printStackTrace();
        }
    }

    public void deleteProduct(Product product) {
        String query = String.format("DELETE FROM %s WHERE %s=?",
                SQL_PRODUCT_TABLE_NAME, SQL_PRODUCT_ID);
        try {
            PreparedStatement statement = connection.getConnection().prepareStatement(query);
            statement.setLong(1, product.getProductId());
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error con la base de datos eliminando al producto");
            e.printStackTrace();
        }
    }

    public void deleteProduct(long productId) {
        String query = String.format("DELETE FROM %s WHERE %s=?",
                SQL_PRODUCT_TABLE_NAME, SQL_PRODUCT_ID);
        try {
            PreparedStatement statement = connection.getConnection().prepareStatement(query);
            statement.setLong(1, productId);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error con la base de datos eliminando al producto");
            e.printStackTrace();
        }
    }
}
