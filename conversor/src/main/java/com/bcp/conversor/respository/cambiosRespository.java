/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.bcp.conversor.respository;

import com.bcp.conversor.entities.cambios;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Julio
 */
@Repository
public interface cambiosRespository extends JpaRepository<cambios, Long> {
    List<cambios> findByFecha(Date fecha);
    cambios findByFechaAndMonedaOrigen(Date fecha, String monedaOrigen);
    cambios findByFechaAndMonedaOrigenAndMonedaDestino(Date fecha, String monedaOrigen, String monedaDestino);
    
}
