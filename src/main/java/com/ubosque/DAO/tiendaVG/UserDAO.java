package com.ubosque.DAO.tiendaVG;

import com.ubosque.DTO.tiendaVG.User;
import java.sql.*;
import java.util.ArrayList;


/*
 * Autor: SANTIAGO DIAZ PINZON - GRUPO 16 - EQUIPO 11
 * */

public class UserDAO {

    Connection connection = new Connection();

    public void createUser(long userIdCard, String userEmail, String userName, String password, String user) {
        String query = "INSERT INTO usuarios (cedula_usuario, email_usuario, nombre_usuario, password, usuario) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.getConnection().prepareStatement(query);
            statement.setLong(1, userIdCard);
            statement.setString(2, userEmail);
            statement.setString(3, userName);
            statement.setString(4, password);
            statement.setString(5, user);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error con la base de datos añadiendo al usuario");
            e.printStackTrace();
        }
    }

    public void createUser(User user) {
        String query = "INSERT INTO usuarios (cedula_usuario, email_usuario, nombre_usuario, password, usuario) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.getConnection().prepareStatement(query);
            statement.setLong(1, user.getUserIdCard());
            statement.setString(2, user.getUserEmail());
            statement.setString(3, user.getUserName());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getUser());
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error con la base de datos añadiendo al usuario");
            e.printStackTrace();
        }
    }

    public ArrayList<User> listUsers() {

        ArrayList<User> users = new ArrayList<>();
        try {
            PreparedStatement statement = connection.getConnection().prepareStatement("SELECT * FROM usuarios");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                long userIdCard = result.getLong("cedula_usuario");
                String userEmail = result.getString("email_usuario");
                String userName = result.getString("nombre_usuario");
                String password = result.getString("password");
                String user = result.getString("usuario");
                users.add(new User(userIdCard, userEmail, userName, password, user));
            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Ocurrió un error leyendo la base de datos de usuarios");
            e.printStackTrace();

        }
        return users;
    }

    public User searchUser(String parameterName, String parameter) {
        String query = "SELECT * FROM usuarios WHERE " + parameterName + "=" + "'" + parameter + "'";
        User response = null;
        try {
            PreparedStatement statement = connection.getConnection().prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                long userIdCard = result.getLong("cedula_usuario");
                String userEmail = result.getString("email_usuario");
                String userName = result.getString("nombre_usuario");
                String password = result.getString("password");
                String user = result.getString("usuario");
                response = new User(userIdCard, userEmail, userName, password, user);
            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error con la base de datos buscando al usuario");
            e.printStackTrace();

        }
        return response;

    }
// Checkear un usuario para iniciar sesion
    public String checkCredentials(String user, String password){
        User userToCheck = searchUser("usuario", user);
        if (userToCheck != null){
            if (userToCheck.getPassword().equals(password)){
                return userToCheck.getUserIdCard()+"";
            }
            else {
                return "incorrectPassword";
            }
        }
        return "incorrectName";
    }

    public void updateUser(User user) {
        String query = "UPDATE usuarios SET email_usuario=? , nombre_usuario=? , password=? , usuario=? WHERE cedula_usuario=?";
        try {
            PreparedStatement statement = connection.getConnection().prepareStatement(query);
            statement.setString(1, user.getUserEmail());
            statement.setString(2, user.getUserName());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getUser());
            statement.setLong(5, user.getUserIdCard());
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error con la base de datos actualizando al usuario");
            e.printStackTrace();
        }
    }

    public void deleteUser(User user) {
        String query = "DELETE FROM usuarios WHERE cedula_usuario=?";
        try {
            PreparedStatement statement = connection.getConnection().prepareStatement(query);
            statement.setLong(1, user.getUserIdCard());
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error con la base de datos eliminando al usuario");
            e.printStackTrace();
        }
    }

    public void deleteUser(long userIdCard) {
        String query = "DELETE FROM usuarios WHERE cedula_usuario=?";
        try {
            PreparedStatement statement = connection.getConnection().prepareStatement(query);
            statement.setLong(1, userIdCard);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error con la base de datos eliminando al usuario");
            e.printStackTrace();
        }
    }
}