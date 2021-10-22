package com.ubosque.tiendaVG;

import com.ubosque.DAO.tiendaVG.UserDAO;


import com.ubosque.DTO.tiendaVG.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.*;


/*
 * Autor: SANTIAGO DIAZ PINZON - GRUPO 16 - EQUIPO 11
 * */

@CrossOrigin(origins = {"http:localhost:8080","http://3.80.62.114"})
@RestController
@RequestMapping("/users")
public class UserController {
    UserDAO userDAO = new UserDAO();

    @RequestMapping("/list")
    public ArrayList<User> listUsers(){
        return userDAO.listUsers();
    }

    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        userDAO.createUser(user);
        return user;
    }

// Read = GET
    @GetMapping(value = "{userIdCard}")
    public User getUserById(@PathVariable("userIdCard") long userIdCard) {
        return userDAO.searchUser("cedula_usuario", userIdCard + "");
    }

    @GetMapping("/user/{user}/{password}")
    public String getUser(@PathVariable("user") String user, @PathVariable("password") String password) {
        return userDAO.checkCredentials(user, password);
    }


    @PutMapping("/update")
    public User updateUser(@RequestBody User user) {
        userDAO.updateUser(user);
        return user;
    }

    @DeleteMapping(value = "{userIdCard}")
    public void deleteUser(@PathVariable("userIdCard") long userIdCard) {
        userDAO.deleteUser(userIdCard);
    }
}
