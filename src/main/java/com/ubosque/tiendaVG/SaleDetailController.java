package com.ubosque.tiendaVG;

import com.ubosque.DAO.tiendaVG.SaleDetailDAO;

import org.springframework.web.bind.annotation.CrossOrigin;
import com.ubosque.DTO.tiendaVG.SaleDetail;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


/*
 * Autor: SANTIAGO DIAZ PINZON - GRUPO 16 - EQUIPO 11
 * */

@CrossOrigin(origins = {"http:localhost:8080","http://3.80.62.114"})
@RestController
@RequestMapping("/saleDetails")
public class SaleDetailController {
    SaleDetailDAO saleDetailDAO = new SaleDetailDAO();

    @RequestMapping("/list")
    public ArrayList<SaleDetail> listSaleDetails() {
        return saleDetailDAO.listSaleDetails();
    }

    @PostMapping("/create")
    public SaleDetail createSaleDetail(@RequestBody SaleDetail sale) {
        saleDetailDAO.createSaleDetail(sale);
        return sale;
    }

    @GetMapping("/lastId")
    public long getLastSaleId() {
        return saleDetailDAO.getLastSaleDetailId();
    }


}
