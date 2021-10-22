package com.ubosque.DTO.tiendaVG;


/*
 * Autor: SANTIAGO DIAZ PINZON - GRUPO 16 - EQUIPO 11
 * */

public class Sale {
    private long saleId;
    private long userIdCard;
    private long customerIdCard;
    private double saleVAT;
    private double saleTotal;
    private double saleValue;

    public Sale(long saleId, long userIdCard, long customerIdCard, double saleVAT, double saleTotal, double saleValue) {
        this.saleId = saleId;
        this.userIdCard = userIdCard;
        this.customerIdCard = customerIdCard;
        this.saleVAT = saleVAT;
        this.saleTotal = saleTotal;
        this.saleValue = saleValue;
    }

    public long getSaleId() {
        return saleId;
    }

    public void setSaleId(long saleId) {
        this.saleId = saleId;
    }

    public long getUserIdCard() {
        return userIdCard;
    }

    public void setUserIdCard(long userIdCard) {
        this.userIdCard = userIdCard;
    }

    public long getCustomerIdCard() {
        return customerIdCard;
    }

    public void setCustomerIdCard(long customerIdCard) {
        this.customerIdCard = customerIdCard;
    }

    public double getSaleVAT() {
        return saleVAT;
    }

    public void setSaleVAT(double saleVAT) {
        this.saleVAT = saleVAT;
    }

    public double getSaleTotal() {
        return saleTotal;
    }

    public void setSaleTotal(double saleTotal) {
        this.saleTotal = saleTotal;
    }

    public double getSaleValue() {
        return saleValue;
    }

    public void setSaleValue(double saleValue) {
        this.saleValue = saleValue;
    }
}
