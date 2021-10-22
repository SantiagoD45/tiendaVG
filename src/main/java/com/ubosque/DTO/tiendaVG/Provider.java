package com.ubosque.DTO.tiendaVG;


/*
 * Autor: SANTIAGO DIAZ PINZON - GRUPO 16 - EQUIPO 11
 * */

public class Provider {
    private long providerNit;
    private String providerCity;
    private String providerAddress;
    private String providerName;
    private String providerPhone;

    public Provider(long providerNit, String providerCity, String providerAddress, String providerName, String providerPhone) {
        this.providerNit = providerNit;
        this.providerCity = providerCity;
        this.providerAddress = providerAddress;
        this.providerName = providerName;
        this.providerPhone = providerPhone;
    }

    public Provider() {

    }

    public long getProviderNit() {
        return providerNit;
    }

    public void setProviderNit(long providerNit) {
        this.providerNit = providerNit;
    }

    public String getProviderCity() {
        return providerCity;
    }

    public void setProviderCity(String providerCity) {
        this.providerCity = providerCity;
    }

    public String getProviderAddress() {
        return providerAddress;
    }

    public void setProviderAddress(String providerAddress) {
        this.providerAddress = providerAddress;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderPhone() {
        return providerPhone;
    }

    public void setProviderPhone(String providerPhone) {
        this.providerPhone = providerPhone;
    }
}
