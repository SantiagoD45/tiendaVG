package com.ubosque.DTO.tiendaVG;


/*
 * Autor: SANTIAGO DIAZ PINZON - GRUPO 16 - EQUIPO 11
 * */

public class SaleDetail {
    private long saleDetailId;
    private int productQuantity;
    private long productId;
    private long saleId;
    private double totalValue;
    private double saleValue;
    private double VATValue;

    public SaleDetail(long saleDetailId, int productQuantity, long productId, long saleId, double totalValue, double saleValue, double VATValue) {
        this.saleDetailId = saleDetailId;
        this.productQuantity = productQuantity;
        this.productId = productId;
        this.saleId = saleId;
        this.totalValue = totalValue;
        this.saleValue = saleValue;
        this.VATValue = VATValue;

    }
    public long getSaleDetailId() {
        return saleDetailId;
    }

    public void setSaleDetailId(long saleDetailId) {
        this.saleDetailId = saleDetailId;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getSaleId() {
        return saleId;
    }

    public void setSaleId(long saleId) {
        this.saleId = saleId;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

    public double getSaleValue() {
        return saleValue;
    }

    public void setSaleValue(double saleValue) {
        this.saleValue = saleValue;
    }

    public double getVATValue() {
        return VATValue;
    }

    public void setVATValue(double VATValue) {
        this.VATValue = VATValue;
    }

}

