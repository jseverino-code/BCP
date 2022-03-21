/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bcp.conversor.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Julio
 */
@Getter
@Setter
public class CambiosDTO {
    public Long id;
    public String fecha;
    public String monedaOrigen;
    public String monedaDescripcionOrigen;
    public String monedaDestino;
    public String monedaDescripcionDestino;    
    public BigDecimal cotizacion;
    public String usuario;
    public String fechaCreacion;
    public String fechaActualizacion;
}
