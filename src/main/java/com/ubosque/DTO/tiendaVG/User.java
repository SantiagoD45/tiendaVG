package com.ubosque.DTO.tiendaVG;


/*
 * Autor: SANTIAGO DIAZ PINZON - GRUPO 16 - EQUIPO 11
 * */

public class User {
    private long userIdCard;
    private String userEmail;
    private String userName;
    private String password;
    private String user;

    public User(long userIdCard, String userEmail, String userName, String password, String user) {
        this.userIdCard = userIdCard;
        this.userEmail = userEmail;
        this.userName = userName;
        this.password = password;
        this.user = user;
    }

    public User() {

    }

    public long getUserIdCard() {
        return userIdCard;
    }

    public void setUserIdCard(long userIdCard) {
        this.userIdCard = userIdCard;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
