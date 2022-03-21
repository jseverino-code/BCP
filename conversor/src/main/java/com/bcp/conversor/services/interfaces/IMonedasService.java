/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.bcp.conversor.services.interfaces;

import com.bcp.conversor.dto.MonedasAddDTO;
import com.bcp.conversor.dto.MonedasDTO;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Julio
 */
@Service
public interface IMonedasService {
    List<MonedasDTO> findAll();
    MonedasDTO findById(String id);
    Boolean save(MonedasAddDTO moneda);
    Boolean deleteById(Long id);
}
