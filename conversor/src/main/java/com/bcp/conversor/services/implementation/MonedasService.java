/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bcp.conversor.services.implementation;

import com.bcp.conversor.dto.MonedasAddDTO;
import com.bcp.conversor.dto.MonedasDTO;
import com.bcp.conversor.entities.monedas;
import com.bcp.conversor.respository.monedasRepository;
import com.bcp.conversor.services.interfaces.IMonedasService;
import java.util.ArrayList;
import java.util.List;
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
public class MonedasService implements IMonedasService{
    
    @Autowired
    private monedasRepository monedasRepository;

    @Override
    public List<MonedasDTO> findAll() {
        
        /*Obtengo la info de la BD*/
        List<monedas> lstMonedas = (List<monedas>) this.monedasRepository.findAll();
               
        /*Declaro la lista de DTO*/
        List<MonedasDTO> lstMonedasDTO = new ArrayList<>();
        
        /*Cargo el DTO*/
        for(monedas monedaItem: lstMonedas){
            
            MonedasDTO monedaDtoItem = new MonedasDTO();
            monedaDtoItem.setId(monedaItem.getId());
            monedaDtoItem.setCodigo(monedaItem.getCodigo());
            monedaDtoItem.setNombre(monedaItem.getNombre());
            
            lstMonedasDTO.add(monedaDtoItem);
        }
        
        return lstMonedasDTO;
    }

    @Override
    public Boolean save(MonedasAddDTO moneda) {
        try{
            
            if (this.monedasRepository.findByCodigo(moneda.getCodigo().toUpperCase()) == null )
            {
                monedas monedaNueva = new monedas();
                monedaNueva.setCodigo(moneda.getCodigo().toUpperCase());
                monedaNueva.setNombre(moneda.getNombre());
            
                this.monedasRepository.save(monedaNueva);
            }           
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    @Override
    public Boolean deleteById(Long id) {
        try {
            this.monedasRepository.deleteById(id);
            return true;
        }
        catch (Exception e) {
            return false;
        }      
    }

    @Override
    public MonedasDTO findById(String id) {
        monedas m = this.monedasRepository.findByCodigo(id.toUpperCase());
        
        MonedasDTO response = new MonedasDTO();
        
        response.setId(m.getId());
        response.setCodigo(m.getCodigo());
        response.setNombre(m.getNombre());
        
        return response;
    }
}
