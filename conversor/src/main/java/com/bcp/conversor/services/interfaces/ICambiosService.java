/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.bcp.conversor.services.interfaces;

import com.bcp.conversor.dto.CambiosAddDTO;
import com.bcp.conversor.dto.CambiosDTO;
import com.bcp.conversor.dto.CambiosUpdateDTO;
import com.bcp.conversor.dto.ConversionesDTO;
import com.bcp.conversor.dto.ConversionesResponseDTO;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Julio
 */
@Service
public interface ICambiosService {
    List<CambiosDTO> findAll(String fecha);
    CambiosDTO findById(Long id);
    Boolean save(CambiosAddDTO moneda);
    Boolean update(CambiosUpdateDTO moneda);
    
    ConversionesResponseDTO Convertir(ConversionesDTO c);
}
