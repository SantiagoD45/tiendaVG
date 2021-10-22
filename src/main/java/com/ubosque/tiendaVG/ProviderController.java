package com.ubosque.tiendaVG;

import com.ubosque.DAO.tiendaVG.ProviderDAO;

import com.ubosque.DTO.tiendaVG.Provider;

import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.*;


/*
 * Autor: SANTIAGO DIAZ PINZON - GRUPO 16 - EQUIPO 11
 * */

@CrossOrigin(origins = {"http:localhost:8080","http://3.80.62.114"})
@RestController
@RequestMapping("/providers")
public class ProviderController {
    ProviderDAO providerDAO = new ProviderDAO();

    @RequestMapping("/list")
    public ArrayList<Provider> listProviders(){
        return providerDAO.listProviders();
    }

    @PostMapping("/create")
    public Provider createProvider(@RequestBody Provider provider) {
        providerDAO.createProvider(provider);
        return provider;
    }

    // Read = GET
    @GetMapping(value = "{providerNit}")
    public Provider getProviderByNit(@PathVariable("providerNit") long providerNit) {
        return providerDAO.searchProvider("nitproveedor", providerNit+"");
    }

    @PutMapping("/update")
    public Provider updateProvider(@RequestBody Provider provider) {
        providerDAO.updateProvider(provider);
        return provider;
    }

    @DeleteMapping(value = "{providerNit}")
    public void deleteProvider(@PathVariable("providerNit") long providerNit) {
        providerDAO.deleteProvider(providerNit);
    }
}
