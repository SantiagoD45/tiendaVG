package com.ubosque.DTO.tiendaVG;


/*
 * Autor: SANTIAGO DIAZ PINZON - GRUPO 16 - EQUIPO 11
 * */

public class Product {
    private long productId;
    private double purchaseVAT;
    private long providerNit;
    private String productName;
    private double purchasePrice;
    private double salePrice;

    public Product(long productId, double purchaseVAT, long providerNit, String productName, double purchasePrice, double salePrice) {
        this.productId = productId;
        this.purchaseVAT = purchaseVAT;
        this.providerNit = providerNit;
        this.productName = productName;
        this.purchasePrice = purchasePrice;
        this.salePrice = salePrice;
    }
    public Product(){

    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public double getPurchaseVAT() {
        return purchaseVAT;
    }

    public void setPurchaseVAT(double purchaseVAT) {
        this.purchaseVAT = purchaseVAT;
    }

    public long getProviderNit() {
        return providerNit;
    }

    public void setProviderNit(long providerNit) {
        this.providerNit = providerNit;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }
}
