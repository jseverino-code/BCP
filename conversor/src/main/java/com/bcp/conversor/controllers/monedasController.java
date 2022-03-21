/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bcp.conversor.controllers;

import com.bcp.conversor.dto.MonedasAddDTO;
import com.bcp.conversor.dto.MonedasDTO;
import com.bcp.conversor.services.interfaces.IMonedasService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Julio
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class monedasController {
 
    @Autowired
    private IMonedasService monedasService;
    
    @GetMapping(path="/monedas", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<MonedasDTO>> ObtenerMonedas() {
        return ResponseEntity.ok(this.monedasService.findAll());
    }
    
    @GetMapping(path="/monedas/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<MonedasDTO> ObtenerMonedaEspecifica(@PathVariable(value = "id") String cod) {
        return ResponseEntity.ok(this.monedasService.findById(cod));
    }
    
    @PostMapping(path="/monedas")
    public ResponseEntity<Integer> AgregarMonedas(@RequestBody MonedasAddDTO monedaNueva) {
        
        if (monedaNueva.getCodigo().isBlank() || monedaNueva.getNombre().isBlank())
        {       
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);      
        }
        
        if (monedaNueva.getCodigo().isEmpty()|| monedaNueva.getNombre().isEmpty())
        {       
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);      
        }
               
        this.monedasService.save(monedaNueva);
        
        return new ResponseEntity<>(HttpStatus.OK);      
    }
    
    @DeleteMapping("/monedas/{id}")
    public ResponseEntity<String> deleteMoneda(@PathVariable(value = "id") Long cod) throws Exception {
        this.monedasService.deleteById(cod);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
