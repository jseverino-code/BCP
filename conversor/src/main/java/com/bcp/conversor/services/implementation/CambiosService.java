/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bcp.conversor.services.implementation;

import com.bcp.conversor.dto.CambiosAddDTO;
import com.bcp.conversor.dto.CambiosDTO;
import com.bcp.conversor.dto.CambiosUpdateDTO;
import com.bcp.conversor.dto.ConversionesDTO;
import com.bcp.conversor.dto.ConversionesResponseDTO;
import com.bcp.conversor.entities.auditorias;
import com.bcp.conversor.entities.cambios;
import com.bcp.conversor.entities.monedas;
import com.bcp.conversor.respository.auditoriasRepository;
import com.bcp.conversor.respository.cambiosRespository;
import com.bcp.conversor.respository.monedasRepository;
import com.bcp.conversor.services.interfaces.ICambiosService;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author Julio
 */
@Transactional
@Service
@Component
public class CambiosService implements ICambiosService{

    @Autowired
    private cambiosRespository cambiosRepository;
    
    @Autowired
    private monedasRepository monedasRepository;
    
    @Autowired
    private auditoriasRepository auditoriasRepository;
    
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
       
    @Override
    public List<CambiosDTO> findAll(String fecha) {
        
        /*Obtengo la info de la BD*/        
        List<cambios> lstCambios = new ArrayList<>();
      
        Date date;
        
        if (!(fecha.isBlank() || fecha.isEmpty()))
        {          
            try 
            {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);               
                lstCambios = this.cambiosRepository.findByFecha(date);
                
            }catch(ParseException ex) {
                Logger.getLogger(CambiosService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            lstCambios = this.cambiosRepository.findAll();
        }
        
        /*Declaro la lista de DTO*/
        List<CambiosDTO> lstCambiosDTO = new ArrayList<>();
        
        /*Cargo el DTO*/
        for(cambios cambioItem: lstCambios){
            
            CambiosDTO cambioDtoItem = new CambiosDTO();
            cambioDtoItem.setId(cambioItem.getId());
            cambioDtoItem.setFecha(dateFormat.format(cambioItem.getFecha()));
            cambioDtoItem.setMonedaOrigen(cambioItem.getMonedaOrigen());
            cambioDtoItem.setMonedaDestino(cambioItem.getMonedaDestino());
            cambioDtoItem.setCotizacion(cambioItem.getCotizacion());            
            cambioDtoItem.setUsuario(cambioItem.getUsuario());
            cambioDtoItem.setFechaCreacion(dateFormat.format(cambioItem.getFechaCreacion()));
            cambioDtoItem.setFechaActualizacion(dateFormat.format(cambioItem.getFechaActualizacion()));
            
            monedas m = this.monedasRepository.findByCodigo(cambioItem.getMonedaOrigen());           
            cambioDtoItem.setMonedaDescripcionOrigen(m.getNombre());

            m = this.monedasRepository.findByCodigo(cambioItem.getMonedaDestino());           
            cambioDtoItem.setMonedaDescripcionDestino(m.getNombre());
            
            lstCambiosDTO.add(cambioDtoItem);
        }
        
        return lstCambiosDTO;
    }

    @Override
    public Boolean save(CambiosAddDTO cambio) {
        try{
            
            if(this.monedasRepository.findByCodigo(cambio.getMonedaOrigen()) ==null)
            {
                return false;
            }
            
            if(this.monedasRepository.findByCodigo(cambio.getMonedaDestino()) ==null)
            {
                return false;
            }
            
            Date dateConsulta = new SimpleDateFormat("yyyy-MM-dd").parse(cambio.getFecha());  
            if (this.cambiosRepository.findByFechaAndMonedaOrigenAndMonedaDestino(dateConsulta, cambio.getMonedaOrigen(), cambio.getMonedaDestino()) == null )
            {
                cambios cambioFilaNueva = new cambios();

                cambioFilaNueva.setFecha(dateConsulta);
                cambioFilaNueva.setMonedaOrigen(cambio.getMonedaOrigen());
                cambioFilaNueva.setMonedaDestino(cambio.getMonedaDestino());
                cambioFilaNueva.setCotizacion(cambio.getCotizacion());            
                cambioFilaNueva.setUsuario(cambio.getUsuario());
                cambioFilaNueva.setFechaCreacion(new Date());               

                this.cambiosRepository.save(cambioFilaNueva);
                return true;
            }         
            
            return false;
        }
        catch(ParseException e){
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Boolean update(CambiosUpdateDTO moneda) {
        
        try
        {
            Optional<cambios> data = this.cambiosRepository.findById(moneda.getId());
            
            BigDecimal valorAnterior = data.get().getCotizacion();
            
            data.get().setCotizacion(moneda.getTipocambio());               
            
            
            auditorias valorAuditoria = new auditorias();
            
            valorAuditoria.setFecha(data.get().getFecha());
            valorAuditoria.setMonedaOrigen(data.get().getMonedaOrigen());
            valorAuditoria.setMonedaDestino(data.get().getMonedaDestino());
            valorAuditoria.setCotizacionAnterior(valorAnterior);
            valorAuditoria.setCotizacionActual(data.get().getCotizacion());
            valorAuditoria.setFechaCambio(new Date());
            valorAuditoria.setUsuario(moneda.getUsuario());
                    
            auditoriasRepository.save(valorAuditoria);

                        
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
        
    }
    
    @Override
    public ConversionesResponseDTO Convertir(ConversionesDTO c) {
        
        ConversionesResponseDTO response = new ConversionesResponseDTO();
        
        monedas mo = this.monedasRepository.findByCodigo(c.getMonedaOrigen());
        monedas md = this.monedasRepository.findByCodigo(c.getMonedaDestino());
        
        try
        {
            Date dateConsulta = new SimpleDateFormat("yyyy-MM-dd").parse(c.fecha);  
            cambios cam = this.cambiosRepository.findByFechaAndMonedaOrigenAndMonedaDestino(dateConsulta, c.getMonedaOrigen(), c.getMonedaDestino());

            BigDecimal cotizacion = cam.getCotizacion();

            response.setMontoOrigen(c.getMonto());
            response.setMonedaOrigen(c.getMonedaOrigen());
            response.setMonedaDestino(c.getMonedaDestino());
            response.setMonedaDescripcionOrigen(mo.getNombre());
            response.setMonedaDescripcionDestino(md.getNombre());
            response.setFecha(c.fecha);
            response.setCotizacion(cotizacion);
            BigDecimal calculo = cotizacion.multiply(c.getMonto());
            response.setMontoDestino(calculo);
            return response; 
        }
        catch(ParseException e)
        {
            return response;      
        }
             
    }

    @Override
    public CambiosDTO findById(Long id) {
    
        Optional<cambios> c = this.cambiosRepository.findById(id);
                
        CambiosDTO cambioDtoItem = new CambiosDTO();
        
        cambioDtoItem.setId(c.get().getId());
        cambioDtoItem.setFecha(dateFormat.format(c.get().getFecha()));
        cambioDtoItem.setMonedaOrigen(c.get().getMonedaOrigen());
        cambioDtoItem.setMonedaDestino(c.get().getMonedaDestino());
        cambioDtoItem.setCotizacion(c.get().getCotizacion());            
        cambioDtoItem.setUsuario(c.get().getUsuario());
        cambioDtoItem.setFechaCreacion(dateFormat.format(c.get().getFechaCreacion()));
        cambioDtoItem.setFechaActualizacion(dateFormat.format(c.get().getFechaActualizacion()));
            
        monedas m = this.monedasRepository.findByCodigo(c.get().getMonedaOrigen());           
        cambioDtoItem.setMonedaDescripcionOrigen(m.getNombre());

        m = this.monedasRepository.findByCodigo(c.get().getMonedaDestino());           
        cambioDtoItem.setMonedaDescripcionDestino(m.getNombre());
        
        return cambioDtoItem;    
    }
}
