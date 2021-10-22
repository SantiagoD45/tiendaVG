package com.ubosque.tiendaVG;

import com.ubosque.DAO.tiendaVG.CustomerDAO;


import com.ubosque.DTO.tiendaVG.Customer;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.*;


/*
 * Autor: SANTIAGO DIAZ PINZON - GRUPO 16 - EQUIPO 11
 * */

@CrossOrigin(origins = {"http:localhost:8080","http://3.80.62.114"})
@RestController
@RequestMapping("/customers")
public class CustomerController {
    CustomerDAO customerDAO = new CustomerDAO();

    @RequestMapping("/list")
    public ArrayList<Customer> listCustomers(){
        return customerDAO.listCustomers();
    }

    @PostMapping("/create")
    public Customer createCustomer(@RequestBody Customer customer) {
        customerDAO.createCustomer(customer);
        return customer;
    }

    // Read = GET
    @GetMapping(value = "{customerIdCard}")
    public Customer getCustomerById(@PathVariable("customerIdCard") long customerIdCard) {
        return customerDAO.searchCustomer(CustomerDAO.SQL_CUSTOMER_ID_CARD, customerIdCard+"");
    }

    @PutMapping("/update")
    public Customer updateCustomer(@RequestBody Customer customer) {
        customerDAO.updateCustomer(customer);
        return customer;
    }

    @DeleteMapping(value = "{customerIdCard}")
    public void deleteCustomer(@PathVariable("customerIdCard") long customerIdCard) {
        customerDAO.deleteCustomer(customerIdCard);
    }
}