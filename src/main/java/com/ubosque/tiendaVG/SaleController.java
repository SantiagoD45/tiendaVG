package com.ubosque.tiendaVG;

import com.ubosque.DAO.tiendaVG.SaleDAO;

import org.springframework.web.bind.annotation.CrossOrigin;
import com.ubosque.DTO.tiendaVG.Sale;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


/*
 * Autor: SANTIAGO DIAZ PINZON - GRUPO 16 - EQUIPO 11
 * */

@CrossOrigin(origins = {"http:localhost:8080","http://3.80.62.114"})
@RestController
@RequestMapping("/sales")
public class SaleController {
    SaleDAO saleDAO = new SaleDAO();

    @RequestMapping("/list")
    public ArrayList<Sale> listSales() {
        return saleDAO.listSales();
    }

    @PostMapping("/create")
    public Sale createSale(@RequestBody Sale sale) {
        saleDAO.createSale(sale);
        return sale;
    }

    // Read = GET
    @GetMapping(value = "{saleId}")
    public Sale getSaleById(@PathVariable("saleId") long saleId) {
        return saleDAO.searchSale(SaleDAO.SQL_SALE_ID, saleId + "");
    }

    @GetMapping("/lastId")
    public long getLastSaleId() {
        return saleDAO.getLastSaleId();
    }


    @PutMapping("/update")
    public Sale updateSale(@RequestBody Sale sale) {
        saleDAO.updateSale(sale);
        return sale;
    }

    @DeleteMapping(value = "{saleId}")
    public void deleteSale(@PathVariable("saleId") long saleId) {
        saleDAO.deleteSale(saleId);
    }
}
