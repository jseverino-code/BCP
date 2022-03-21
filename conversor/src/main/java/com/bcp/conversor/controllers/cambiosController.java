/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bcp.conversor.controllers;

import com.bcp.conversor.dto.CambiosAddDTO;
import com.bcp.conversor.dto.CambiosDTO;
import com.bcp.conversor.dto.CambiosUpdateDTO;
import com.bcp.conversor.dto.ConversionesDTO;
import com.bcp.conversor.dto.ConversionesResponseDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.bcp.conversor.services.interfaces.ICambiosService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Julio
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class cambiosController {
    
    @Autowired
    private ICambiosService cambiosService;
    
    @GetMapping(path="/cambios", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<CambiosDTO>> ObtenerCambios(
                                                            @RequestParam(name = "fecha", required = false, defaultValue = "") String fecha
                                                           )
    {
        return ResponseEntity.ok(this.cambiosService.findAll(fecha));
    }
    
    @GetMapping(path="/cambios/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<CambiosDTO> ObtenerCambiosId(@PathVariable(value = "id") Long cod) {
        return ResponseEntity.ok(this.cambiosService.findById(cod));
    }
           
    @PostMapping(path="/cambios")
    public ResponseEntity<String> AgregarMonedas(@RequestBody CambiosAddDTO cambioNuevo) {
                
        if (cambioNuevo.getMonedaDestino().isBlank() || cambioNuevo.getMonedaDestino().isEmpty())
        {       
            return new ResponseEntity<>("Verifique la Moneda Destino", HttpStatus.BAD_REQUEST);      
        }
        
        if (cambioNuevo.getMonedaOrigen().isBlank() || cambioNuevo.getMonedaOrigen().isEmpty())
        {       
            return new ResponseEntity<>("Verifique la Moneda Origen", HttpStatus.BAD_REQUEST);      
        }
        
        if (cambioNuevo.getUsuario().isBlank() || cambioNuevo.getUsuario().isEmpty())
        {       
            return new ResponseEntity<>("Verifique el Usuario", HttpStatus.BAD_REQUEST);      
        }
        
        if(this.cambiosService.save(cambioNuevo) == true) {        
            return new ResponseEntity<>(HttpStatus.OK);      
        }
        else{
            return new ResponseEntity<>(HttpStatus.CONFLICT);  
        }
    }
    
    @PatchMapping(path="/cambios")
    public ResponseEntity<CambiosDTO> ActualizarTipoCambio(@RequestBody CambiosUpdateDTO cambioNuevo) {
        
        CambiosDTO dataResponse;
        
        if (this.cambiosService.update(cambioNuevo)) {
        
            dataResponse = this.cambiosService.findById(cambioNuevo.getId());
        
            return new ResponseEntity<>(dataResponse, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
  
    }
    
    @PostMapping(path="/cambios/conversiones")
    @ResponseBody
    public ResponseEntity<ConversionesResponseDTO> ConversionesResponseDTO(@RequestBody ConversionesDTO conversion) {
        return new ResponseEntity<>(this.cambiosService.Convertir(conversion), HttpStatus.OK);      
    }
       
}
