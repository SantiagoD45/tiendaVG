package com.ubosque.tiendaVG;

import com.ubosque.DAO.tiendaVG.ProductDAO;

import com.ubosque.DTO.tiendaVG.Product;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


/*
 * Autor: SANTIAGO DIAZ PINZON - GRUPO 16 - EQUIPO 11
 * */

@CrossOrigin(origins = {"http:localhost:8080","http://3.80.62.114"})
@RestController
@RequestMapping("/products")
public class ProductController {
    ProductDAO productDAO = new ProductDAO();

    @RequestMapping("/list")
    public ArrayList<Product> listProducts(){
        return productDAO.listProducts();
    }

    @PostMapping("/create")
    public Product createProduct(@RequestBody Product product) {
        productDAO.createProduct(product);
        return product;
    }

    // Read = GET
    @GetMapping(value = "{productId}")
    public Product getProductById(@PathVariable("productId") long productId) {
        return productDAO.searchProduct(ProductDAO.SQL_PRODUCT_ID, productId+"");
    }

    @PutMapping("/update")
    public Product updateProduct(@RequestBody Product product) {
        productDAO.updateProduct(product);
        return product;
    }

    @DeleteMapping(value = "{productId}")
    public void deleteProduct(@PathVariable("productId") long productId) {
        productDAO.deleteProduct(productId);
    }
}
